package nook.test.market_nookat.ui.fragment.photo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import nook.test.market_nookat.data.result.Status
import nook.test.market_nookat.databinding.FragmentAddBinding
import nook.test.market_nookat.databinding.FragmentPhotoBinding
import nook.test.market_nookat.ui.base.BaseFragment
import nook.test.market_nookat.ui.fragment.add.AddViewModel
import nook.test.market_nookat.ui.fragment.ads.AdsFragment.Companion.SLIG
import nook.test.market_nookat.ui.fragment.ads.Model
import nook.test.market_nookat.ui.fragment.image_sliger.PhotoSliderAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class PhotoFragment : BaseFragment<PhotoViewModel,FragmentPhotoBinding>()  {

    override val viewModel: PhotoViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?, boolean: Boolean): FragmentPhotoBinding {
        return FragmentPhotoBinding.inflate(inflater,container,false)
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
            model = arguments?.getSerializable(SLIG) as Model
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