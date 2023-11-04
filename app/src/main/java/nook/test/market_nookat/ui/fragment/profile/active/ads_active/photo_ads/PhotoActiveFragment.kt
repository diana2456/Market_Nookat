package nook.test.market_nookat.ui.fragment.profile.active.ads_active.photo_ads

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import nook.test.market_nookat.R
import nook.test.market_nookat.data.result.Status
import nook.test.market_nookat.databinding.FragmentAdsActiveBinding
import nook.test.market_nookat.databinding.FragmentPhotoActiveBinding
import nook.test.market_nookat.databinding.FragmentPhotoAdsBinding
import nook.test.market_nookat.ui.base.BaseFragment
import nook.test.market_nookat.ui.fragment.ads.Model
import nook.test.market_nookat.ui.fragment.image_sliger.PhotoSliderAdapter
import nook.test.market_nookat.ui.fragment.profile.active.ads_active.AdsActiveFragment.Companion.WET
import nook.test.market_nookat.ui.fragment.profile.active.ads_active.AdsActiveViewModel
import nook.test.market_nookat.ui.fragment.profile.no_active.ads_two.AdsTwoFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoActiveFragment : BaseFragment<PhotoActiveViewModel,FragmentPhotoActiveBinding>() {

    override val viewModel: PhotoActiveViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?, boolean: Boolean): FragmentPhotoActiveBinding {
        return FragmentPhotoActiveBinding.inflate(inflater,container,false)
    }

    private lateinit var model: Model

    override fun initFragment() {
        onBoard()
    }

    private fun onBoard(){

        binding.ivBlack.setOnClickListener {
            findNavController().navigateUp()
        }
        if (arguments != null) {
            model = arguments?.getSerializable(WET) as Model
            lifecycleScope.launch {
                viewModel.getOneAds(model.img).collect { data ->
                    when (data.status) {
                        Status.SUCCESS -> {
                            val adapter = data.data?.data?.photos?.let { PhotoSliderAdapter(it) }
                            if (adapter != null) {
                                binding.vpv.setSliderAdapter(adapter)
                            }
                        }

                        Status.LOADING -> {}
                        Status.ERROR -> {}
                    }
                }
            }
        }
    }

    override fun initResume() {
        TODO("Not yet implemented")
    }

    override fun initPause() {
    }

    override fun initDestroy() {
        TODO("Not yet implemented")
    }
}