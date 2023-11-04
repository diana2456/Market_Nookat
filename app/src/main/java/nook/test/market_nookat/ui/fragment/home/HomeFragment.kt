package nook.test.market_nookat.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import nook.test.market_nookat.R
import nook.test.market_nookat.data.model.DataItem
import nook.test.market_nookat.data.result.Status
import nook.test.market_nookat.databinding.FragmentHomeBinding
import nook.test.market_nookat.ui.base.BaseFragment
import nook.test.market_nookat.ui.fragment.add.AddViewModel
import nook.test.market_nookat.ui.fragment.settings.LocateHelper
import nook.test.market_nookat.ui.fragment.util.Pref
import nook.test.market_nookat.ui.fragment.util.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment  : BaseFragment<HomeViewModel,FragmentHomeBinding>(){

    override val viewModel: HomeViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?, boolean: Boolean): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater,container,false)
    }

    private var token = ""
    private var  id = 0
    private var isClick = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root
    }

    override fun initFragment() {
        binding.tvCategory.text = Pref(requireContext()).isCatery()
        binding.tvValute.text = Pref(requireContext()).isMaxPrice()
        binding.tvLocation.text = Pref(requireContext()).isMinPrice()
        val selectedLanguage = Pref(requireContext()).getSelectedLanguage()
        if (selectedLanguage == "ru") {
            showRuText()
        } else {
            showKgText()
        }
        onCheck()
        onViewModel()
        initView()
        search()
        token()
        binding.ivFil.setOnClickListener{
            findNavController().navigate(R.id.filterFragment)
        }
    }

    private fun token() {
        lifecycleScope.launch {
            viewModel.loginUser(
                Pref(requireContext()).isLogin(),
                Pref(requireContext()).isPasword()
            ).collect() {
                when (it.status) {
                    Status.SUCCESS -> {
                        token = it.data?.token.toString()
                    }

                    Status.LOADING -> {}
                    Status.ERROR -> {}
                }
            }
        }
    }

    private fun onCheck(){
        binding.swLayt.setOnRefreshListener {
            onViewModel()
            binding.swLayt.isRefreshing = false
            binding.llFilCv.isVisible = false
            binding.llFilIv.isVisible = false
            binding.rv.isVisible = true
            binding.rvAdsFil.isVisible = false
            binding.rvAds.isVisible = true
            back()
            binding.ivBackF.isVisible = false
        }
        binding.ivBackF.setOnClickListener {
            binding.llFilCv.isVisible = false
            binding.llFilIv.isVisible = false
            binding.rv.isVisible = true
            binding.rvAdsFil.isVisible = false
            binding.rvAds.isVisible = true
            back()
            binding.ivBackF.isVisible = false
        }

        if (binding.tvCategory.text.isEmpty()) {
            binding.tvCategory.isVisible = false
            binding.llFilCv.isVisible = false
            binding.llFilIv.isVisible = false
            binding.rv.isVisible = true
         } else {
            onFilter()
        }
        if (binding.tvValute.text.isEmpty()) {
            binding.tvValute.isVisible = false
            binding.llFilCv.isVisible = false
            binding.llFilIv.isVisible = false
            binding.rv.isVisible = true
          } else {
            onFilter()
        }
        if (binding.tvLocation.text.isEmpty()) {
            binding.tvLocation.isVisible = false
            binding.llFilCv.isVisible = false
            binding.llFilIv.isVisible = false
            binding.rv.isVisible = true
          } else {
            onFilter()
        }
        if (binding.tvMax.text.isEmpty()) {
            binding.tvMax.isVisible = false
            binding.llFilCv.isVisible = false
            binding.llFilIv.isVisible = false
            binding.rv.isVisible = true
         } else {
            onFilter()
        }
        if (binding.tvMin.text.isEmpty()) {
            binding.tvMin.isVisible = false
            binding.llFilCv.isVisible = false
            binding.llFilIv.isVisible = false
            binding.rv.isVisible = true
         } else {
            onFilter()
        }
    }
    private fun onFilter(){
        val pref = Pref(requireContext())
        lifecycleScope.launch {
            viewModel.filterAds(
                pref.isCategory(),
                pref.isMaxPrice()
            ).collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.rvAds.isVisible = false
                        binding.rvAdsFil.isVisible = true
                        val adapterAds = AdapterAds(
                            requireContext(),
                            this@HomeFragment::onFavorite,
                            this@HomeFragment::onDelete,
                            this@HomeFragment::getAdsOne
                        )
                        binding.swLayt.isVisible = false
                        binding.rv.isVisible = false
                        binding.llFil.isVisible = false
                        binding.llFilIv.isVisible = true
                        binding.ivBackF.isVisible = true
                        binding.llFilCv.isVisible = true
                        binding.rvAdsFil.adapter = adapterAds
                        adapterAds.submitList(it.data?.data?.data as MutableList<DataItem>?)
                    }

                    Status.LOADING -> {}
                    Status.ERROR -> {}
                }
            }
        }
    }

    private fun onViewModel() {
        lifecycleScope.launch {
        viewModel.getAds().collect(){
            when (it.status) {
                Status.SUCCESS -> {
                    val response = it.data
                    if (response != null) {
                        val adapter = AdapterAds(
                            requireContext(),
                            this@HomeFragment::onFavorite,
                            this@HomeFragment::onDelete,
                            this@HomeFragment::getAdsOne
                        ).apply {
                            submitList(it.data.data.data as MutableList<DataItem>?)
                        }
                        binding.rvAds.adapter = adapter
                        binding.rvAds.isVisible = true
                        binding.rvAdsFil.isVisible = false
                        Log.i("sxdc", "onViewModel:$it")
                    }
                }

                Status.ERROR -> {
                    Log.d("sdxcfv", "onViewModel:$it")
                }

                Status.LOADING -> {
                    Log.d("sdujmv", "onViewModel:$it")
                }
            }
        }
        }

        lifecycleScope.launch {
            viewModel.getCategory().collect() {
                when (it.status) {
                    Status.SUCCESS -> {
                        val adapterAds = AdapterDirectory(this@HomeFragment::onClick)
                        binding.rv.adapter = adapterAds
                        adapterAds.submitList(it.data?.data)
                    }

                    Status.LOADING -> {}
                    Status.ERROR -> {}
                }
            }
        }
    }

    private fun onClick(id: Int, title: String) {
        lifecycleScope.launch {
            viewModel.getFilter(id).collect() {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.rvAds.isVisible = false
                        binding.rvAdsFil.isVisible = true
                        val adapterAds = AdapterAds(
                            requireContext(),
                            this@HomeFragment::onFavorite,
                            this@HomeFragment::onDelete,
                            this@HomeFragment::getAdsOne
                        )
                        binding.rv.isVisible = false
                        binding.llFil.isVisible = true
                        binding.rvAdsFil.adapter = adapterAds
                        adapterAds.submitList(it.data?.data?.data as MutableList<DataItem>?)
                        binding.title.text = title
                    }

                    Status.ERROR -> {}
                    Status.LOADING -> {}
                }
            }
        }
    }

    private fun initView() {
        binding.ivBack.setOnClickListener {
            binding.rv.isVisible = true
            binding.llFil.isVisible = false
            binding.rvAdsFil.isVisible = false
            binding.rvAds.isVisible = true
        }
    }

    private fun onFavorite(id: Int,isClick:Boolean){
        this.id = id
        this.isClick = isClick
        val tokenWith = "Bearer $token"
        lifecycleScope.launch {
            viewModel.addFavorite(tokenWith, id).collect() {
                when (it.status) {
                    Status.SUCCESS -> {
                        showToast(requireContext(), "Добавлено в избранное!")
                    }

                    Status.ERROR -> {
                        showToast(requireContext(),"Вы не зарегестрировались!!")
                        Pref(requireContext()).setCur("Вы не зарегестрировались!!")
                    }

                    Status.LOADING -> {
                    }
                }
            }
        }
    }


    private fun onDelete(id: Int){
        val tokenWith = "Bearer $token"
        lifecycleScope.launch {
            viewModel.deleteFavorite(tokenWith, id).collect() {
                when (it.status) {
                    Status.SUCCESS -> {
                        showToast(requireContext(), "Избранное удаленно!!")
                        Log.i("xcvbnh", "onFavorite:$it")
                    }

                    Status.ERROR -> {
                        Log.i("xcvh", "onFavorite:$it")
                    }

                    Status.LOADING -> {
                        Log.i("bnh", "onFavorite:$it")
                    }
                }
            }
        }
    }

    private fun search() {
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.svSearch.clearFocus()
                binding.rv.isVisible = true
                binding.llFil.isVisible = false
                binding.rvAds.isVisible = true
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    lifecycleScope.launch {
                        viewModel.searchAds(it).collect { data ->
                            when (data.status) {
                                Status.SUCCESS -> {
                                    binding.rvAds.isVisible = false
                                    binding.rvAdsFil.isVisible = true
                                    val adapterAds = AdapterAds(
                                        requireContext(),
                                        this@HomeFragment::onFavorite,
                                        this@HomeFragment::onDelete,
                                        this@HomeFragment::getAdsOne
                                    )
                                    binding.rvAdsFil.adapter = adapterAds
                                    adapterAds.submitList(data.data?.data?.data as MutableList<DataItem>?)
                                }

                                Status.LOADING -> {}
                                Status.ERROR -> {}
                            }
                        }
                    }
                }
                return false
            }
        })
    }



    private fun back(){
        val sap = Pref(requireContext()).sharedPref.edit()
        sap.remove("catery")
        sap.remove("cu")
        sap.remove("locati")
        sap.remove("priceMax")
        sap.remove("priceMin")
        sap.apply()
    }


    private fun showKgText(){
        val context = LocateHelper().setLocale(requireContext(),"kg")
        val resource = context.resources
        binding.svSearch.queryHint = resource.getString(R.string.search)

    }

    private fun showRuText(){
        val context = LocateHelper().setLocale(requireContext(),"ru")
        val resource = context.resources
        binding.svSearch.queryHint = resource.getString(R.string.search)
    }

    private fun getAdsOne(id: DataItem){
        findNavController().navigate(R.id.adsFragment, bundleOf(ID to id))
    }

    companion object {
        const val ID = "id_ads"
    }

    override fun initResume() {}

    override fun onResume() {
        super.onResume()
        val sap = Pref(requireContext()).sharedPref.edit()
        sap.remove("catery")
        sap.remove("cu")
        sap.remove("locati")
        sap.remove("priceMax")
        sap.remove("priceMin")
        sap.apply()
    }

    override fun onPause() {
        super.onPause()
        val sap = Pref(requireContext()).sharedPref.edit()
        sap.remove("catery")
        sap.remove("cu")
        sap.remove("locati")
        sap.remove("priceMax")
        sap.remove("priceMin")
        sap.apply()
    }
    override fun initPause() {}

    override fun initDestroy() {}

    override fun onDestroyView() {
        super.onDestroyView()
        val sap = Pref(requireContext()).sharedPref.edit()
        sap.remove("catery")
        sap.remove("cu")
        sap.remove("locati")
        sap.remove("priceMax")
        sap.remove("priceMin")
        sap.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        val sap = Pref(requireContext()).sharedPref.edit()
        sap.remove("catery")
        sap.remove("cu")
        sap.remove("locati")
        sap.remove("priceMax")
        sap.remove("priceMin")
        sap.apply()
    }
}