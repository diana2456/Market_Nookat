package nook.test.market_nookat.ui.fragment.profile.active

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
import nook.test.market_nookat.data.model.Dataem
import nook.test.market_nookat.data.result.Status
import nook.test.market_nookat.databinding.FragmentActiveBinding
import nook.test.market_nookat.databinding.FragmentAddBinding
import nook.test.market_nookat.ui.base.BaseFragment
import nook.test.market_nookat.ui.fragment.add.AddViewModel
import nook.test.market_nookat.ui.fragment.ads.Model
import nook.test.market_nookat.ui.fragment.home.AdapterAds
import nook.test.market_nookat.ui.fragment.profile.no_active.AdapterAct
import nook.test.market_nookat.ui.fragment.settings.LocateHelper
import nook.test.market_nookat.ui.fragment.util.Pref
import org.koin.androidx.viewmodel.ext.android.viewModel


class ActiveFragment :  BaseFragment<ActiveViewModel,FragmentActiveBinding>() {

    override val viewModel: ActiveViewModel by viewModel()
    private var token = ""
    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?, boolean: Boolean): FragmentActiveBinding {
        return FragmentActiveBinding.inflate(inflater,container,false)
    }

    override fun initFragment() {
         val selectedLanguage = Pref(requireContext()).getSelectedLanguage()
        if (selectedLanguage == "ru") {
            showRuText()
        } else {
            showKgText()
        }
        onViewModel()
    }

    private fun onViewModel() {
        lifecycleScope.launch {
            viewModel.loginUser(
                Pref(requireContext()).isLogin(),
                Pref(requireContext()).isPasword()
            ).collect() { data ->
                when (data.status) {
                    Status.SUCCESS -> {
                        token = data.data?.token.toString()
                         val tokenWith = "Bearer $token"
                        viewModel.getActive(tokenWith).collect() {
                            when (it.status) {
                                Status.SUCCESS -> {
                                    val adapterAds = AdapterAct(this@ActiveFragment::click).apply {
                                        submitList(it.data?.data) // Предполагается, что `data` в `ActiveData` это список `Dataem`
                                    }
                                    binding.rvAds.adapter = adapterAds
                                    binding.tvText.isVisible = adapterAds.itemCount <= 0
                                    Log.i("asxdcf", "onViewModel ${it.data}")
                                }

                                Status.LOADING -> {}
                                Status.ERROR -> {
                                    Log.i("edccf", "onViewModel ${it.message}")
                                }
                            }
                        }
                    }

                    Status.LOADING -> {}
                    Status.ERROR -> {}
                }
            }
        }
    }

    private fun click(int: Int){
        val sdf = Model(int)
        findNavController().navigate(R.id.adsActiveFragment, bundleOf(IOP to sdf))
    }


    private fun showKgText(){
        val context = LocateHelper().setLocale(requireContext(),"kg")
        val resource = context.resources
        binding.tvText.text = resource.getString(R.string.no_actives)
    }

    private fun showRuText(){
        val context = LocateHelper().setLocale(requireContext(),"ru")
        val resource = context.resources
        binding.tvText.text = resource.getString(R.string.no_actives)
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

    companion object {
        const val IOP = "gbhnj"
    }
}