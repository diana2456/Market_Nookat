package nook.test.market_nookat.ui.fragment.favorite.ads

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import nook.test.market_nookat.R
import nook.test.market_nookat.data.model.DataItem
import nook.test.market_nookat.data.result.Status
import nook.test.market_nookat.databinding.FragmentAddBinding
import nook.test.market_nookat.databinding.FragmentFavAdsBinding
import nook.test.market_nookat.ui.activity.MainActivity
import nook.test.market_nookat.ui.base.BaseFragment
import nook.test.market_nookat.ui.fragment.add.AddViewModel
import nook.test.market_nookat.ui.fragment.favorite.FavFragment.Companion.FAV
import nook.test.market_nookat.ui.fragment.home.ClickStateManager
import nook.test.market_nookat.ui.fragment.image_sliger.CustomSliderAdapter
import nook.test.market_nookat.ui.fragment.settings.LocateHelper
import nook.test.market_nookat.ui.fragment.util.Pref
import nook.test.market_nookat.ui.fragment.util.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavAdsFragment :  BaseFragment<FavAdsViewModel,FragmentFavAdsBinding>() {

    override val viewModel: FavAdsViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?, boolean: Boolean): FragmentFavAdsBinding {
        return FragmentFavAdsBinding.inflate(inflater,container,false)
    }

    private lateinit var model: DataItem
    private var token = ""
    private var id = ""
    private var phoneNumber = ""
    private var what = ""


    override fun initFragment() {
        val selectedLanguage = Pref(requireContext()).getSelectedLanguage()
        if (selectedLanguage == "ru") {
            showRuText()
        } else {
            showKgText()
        }
        token()
        onViewModel()
        onListener()
    }

    private fun token() {
        lifecycleScope.launch {
            viewModel.loginUser(
                Pref(requireContext()).isLogin(),
                Pref(requireContext()).isPasword()
            )
                .collect {
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

    private fun onViewModel() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        if (arguments != null) {
            model = arguments?.getSerializable(FAV) as DataItem
            Log.d("ddcfvg", "onViewModel:$id")
            val clickStateManager = ClickStateManager(requireContext())
            val itemId = model.id
            val isClicked = clickStateManager.isClicked(itemId)

            if (isClicked) {
                binding.ivHeart.setBackgroundResource(R.drawable.heart)
                clickStateManager.setClicked(itemId, false)
                onDelete(model.id)
            } else {
                binding.ivHeart.setBackgroundResource(R.drawable.heart_favorite)
                clickStateManager.setClicked(itemId, true)
                onFav(model.id)
            }

            lifecycleScope.launch {
                viewModel.getOneAds(model.id).collect() { data ->
                    when (data.status) {
                        Status.SUCCESS -> {
                            binding.tvLocation.text = data.data?.data?.location
                            val formattedNumber =
                                data.data?.data?.price?.replace(".0+$".toRegex(), "")
                            binding.tvPrice.text = formattedNumber
                            if (data.data?.data?.vip != null) {
                                binding.tvVip.isVisible = data.data.data.vip
                            }
                            what = data.data?.data?.whatsapp.toString()
                            phoneNumber = data.data?.data?.phone.toString()
                            binding.tvCyr.text = data.data?.data?.currency
                            binding.tvNumber.text = data.data?.data?.phone
                            binding.tvNumberWhatsapp.text = data.data?.data?.whatsapp
                            binding.tvDesc.text = data.data?.data?.content
                            binding.tvType.text = data.data?.data?.category
                            id = data.data?.data?.id.toString()
                            val adapter = data.data?.data?.photos?.let { CustomSliderAdapter(it, this@FavAdsFragment::onClick) }
                            if (adapter != null) {
                                binding.vpPhoto.setSliderAdapter(adapter)
                            }
                        }

                        Status.LOADING -> {}
                        Status.ERROR -> {}
                    }
                }
            }
        }
    }

    private fun showKgText() {
        val context = LocateHelper().setLocale(requireContext(), "kg")
        val resource = context.resources
        binding.tvDesc.text = resource.getString(R.string.description)
    }

    private fun showRuText() {
        val context = LocateHelper().setLocale(requireContext(), "ru")
        val resource = context.resources
        binding.tvDesc.text = resource.getString(R.string.description)
    }

    private fun onDelete(id: Int) {
        val tokenWith = "Bearer $token"
        lifecycleScope.launch {
            viewModel.deleteFavorite(tokenWith, id).collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        showToast(requireContext(), "Изобранное удаленно!!")
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

    private fun onFav(id: Int) {
        val tokenWith = "Bearer $token"
        lifecycleScope.launch {
            viewModel.addFavorite(tokenWith, id).collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        showToast(requireContext(), "Изобранное добавлено!!")
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

    fun makePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val uri = Uri.parse("tel:$phoneNumber")
        intent.data = uri
        startActivity(intent)
    }

    private fun onListener(){
        binding.cvTwo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://wa.me/$what")
            startActivity(intent)
        }
        binding.cvOne.setOnClickListener {
            makePhoneCall(phoneNumber)
        }
    }


    private fun onClick(pos: Int){
        findNavController().navigate(R.id.favPhotoFragment, bundleOf(FAV_ADS to id))
    }
   companion object {
        const val FAV_ADS = "awergv"
    }

    override fun initResume() {
        TODO("Not yet implemented")
    }

    override fun initPause() {
        TODO("Not yet implemented")
    }

    override fun initDestroy() {
        TODO("Not yet implemented")
    }

}