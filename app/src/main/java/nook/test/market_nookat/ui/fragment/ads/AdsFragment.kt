package nook.test.market_nookat.ui.fragment.ads

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
import nook.test.market_nookat.databinding.FragmentAdsBinding
import nook.test.market_nookat.ui.base.BaseFragment
import nook.test.market_nookat.ui.fragment.home.ClickStateManager
import nook.test.market_nookat.ui.fragment.home.HomeFragment.Companion.ID
import nook.test.market_nookat.ui.fragment.image_sliger.CustomSliderAdapter
import nook.test.market_nookat.ui.fragment.settings.LocateHelper
import nook.test.market_nookat.ui.fragment.util.Pref
import nook.test.market_nookat.ui.fragment.util.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel


class AdsFragment : BaseFragment<AdsViewModel,FragmentAdsBinding>() {

    override val viewModel: AdsViewModel by viewModel()
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        boolean: Boolean
    ): FragmentAdsBinding {
        return FragmentAdsBinding.inflate(inflater, container, false)
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

    override fun initResume() {
        TODO("Not yet implemented")
    }

    override fun initPause() {
        TODO("Not yet implemented")
    }

    override fun initDestroy() {
        TODO("Not yet implemented")
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
            model = arguments?.getSerializable(ID) as DataItem
            val clickStateManager = ClickStateManager(requireContext())
            val itemId = model.id
            val isClicked = clickStateManager.isClicked(itemId)
            binding.ivHeart.setBackgroundResource(if (isClicked) R.drawable.heart_favorite else R.drawable.heart)
            binding.ivHeart.setOnClickListener {
                val isClicked = clickStateManager.isClicked(itemId)
                if (isClicked) {
                    binding.ivHeart.setBackgroundResource(R.drawable.heart)
                    onDelete(model.id)
                } else {
                    binding.ivHeart.setBackgroundResource(R.drawable.heart_favorite)
                    onFav(model.id)
                }
                clickStateManager.setClicked(itemId, !isClicked)
            }
        }
        lifecycleScope.launch {
            viewModel.getOneAds(model.id).collect { data ->
                when (data.status) {
                    Status.SUCCESS -> {
                        binding.tvLocation.text = data.data?.data?.location
                        val formattedNumber = data.data?.data?.price?.replace(".0+$".toRegex(), "")
                        binding.tvPrice.text = formattedNumber
                        id = data.data?.data?.id.toString()
                        if (data.data?.data?.vip != null) {
                            binding.tvVip.isVisible = data.data.data.vip
                        }
                        binding.tvCyr.text = data.data?.data?.currency
                        binding.tvNumber.text = data.data?.data?.phone
                        binding.tvNumberWhatsapp.text = data.data?.data?.whatsapp
                        binding.tvDescText.text = data.data?.data?.content
                        binding.tvType.text = data.data?.data?.category
                        what = data.data?.data?.whatsapp.toString()
                        phoneNumber = data.data?.data?.phone.toString()
                        val adapterAds = data.data?.data?.photos?.let {
                            CustomSliderAdapter(
                                it,
                                this@AdsFragment::onClick
                            )
                        }
                        if (adapterAds != null) {
                            binding.vpPhoto.setSliderAdapter(adapterAds)
                        }
                    }

                    Status.LOADING -> {}
                    Status.ERROR -> {}
                }
            }
        }
    }

    private fun onClick(pos: Int) {
        val model = Model(id.toInt())
        findNavController().navigate(R.id.photoFragment, bundleOf(SLIG to model))
    }

    private fun showKgText() {
        val context = LocateHelper().setLocale(requireContext(), "kg")
        val resource = context.resources
        binding.tvDesc.text = resource.getString(R.string.description)
    }

    companion object {
        const val SLIG = "sliger_view"
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
                    }

                    Status.ERROR -> {
                    }

                    Status.LOADING -> {
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

    private fun onFav(id: Int) {
        val tokenWith = "Bearer $token"
        lifecycleScope.launch {
            viewModel.addFavorite(tokenWith, id).collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        showToast(requireContext(), "Изобранное добавлено!!")
                    }

                    Status.ERROR -> {
                        showToast(requireContext(), "Вы не зарегестрировались!!")
                    }

                    Status.LOADING -> {
                    }
                }
            }
        }
    }

}

/*   val notificationId = arguments?.getString(NOTIFI)
        if (notificationId != null) {
                lifecycleScope.launch {
                    viewModel.searchAds(notificationId).collect{ item ->
                        Log.i("loyptt", "onViewModel:$notificationId")
                        when(item.status){
                            Status.SUCCESS -> {
                                notificationId.let {
                                    viewModel.getOneAds(item.data?.data?.data!![0].id).collect { data ->
                                        when (data.status) {
                                            Status.SUCCESS -> {
                                                binding.tvLocation.text = data.data?.data?.location
                                                val formattedNumber =
                                                    data.data?.data?.price?.replace(".0+$".toRegex(), "")
                                                binding.tvPrice.text = formattedNumber
                                                if (data.data?.data?.vip != null) {
                                                    binding.tvVip.isVisible = data.data.data.vip
                                                }
                                                what  = data.data?.data?.whatsapp.toString()
                                                phoneNumber = data.data?.data?.phone.toString()
                                                binding.tvCyr.text = data.data?.data?.currency
                                                binding.tvNumber.text = data.data?.data?.phone
                                                binding.tvNumberWhatsapp.text = data.data?.data?.whatsapp
                                                binding.tvDescText.text = data.data?.data?.content
                                                binding.tvType.text = data.data?.data?.category
                                                id = data.data?.data?.id.toString()
                                                val adapter = data.data?.data?.photos?.let { CustomSliderAdapter(it, this@AdsNotifiFragment::onClick) }
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
                            Status.LOADING ->{}
                            Status.ERROR ->{}
                        }
                    }
                }



                  viewModel.getOneAds(model.id).collect { data ->
                when (data.status) {
                    Status.SUCCESS -> {
                        binding.tvLocation.text = data.data?.data?.location
                        val formattedNumber = data.data?.data?.price?.replace(".0+$".toRegex(), "")
                        binding.tvPrice.text = formattedNumber
                        id = data.data?.data?.id.toString()
                        if (data.data?.data?.vip != null) {
                            binding.tvVip.isVisible = data.data.data.vip
                        }
                        binding.tvCyr.text = data.data?.data?.currency
                        binding.tvNumber.text = data.data?.data?.phone
                        binding.tvNumberWhatsapp.text = data.data?.data?.whatsapp
                        binding.tvDescText.text = data.data?.data?.content
                        binding.tvType.text = data.data?.data?.category
                        what = data.data?.data?.whatsapp.toString()
                        phoneNumber = data.data?.data?.phone.toString()
                        val adapterAds = data.data?.data?.photos?.let {
                            CustomSliderAdapter(
                                it,
                                this@AdsFragment::onClick
                            )
                        }
                        if (adapterAds != null) {
                            binding.vpPhoto.setSliderAdapter(adapterAds)
                        }
                    }

                    Status.LOADING -> {}
                    Status.ERROR -> {}
                }
            }
        }
            }*/