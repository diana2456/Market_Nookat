package nook.test.market_nookat.ui.fragment.profile.no_active

import android.annotation.SuppressLint
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
import nook.test.market_nookat.databinding.FragmentNoActiveBinding
import nook.test.market_nookat.ui.base.BaseFragment
import nook.test.market_nookat.ui.fragment.ads.Model
import nook.test.market_nookat.ui.fragment.home.AdapterAds
import nook.test.market_nookat.ui.fragment.settings.LocateHelper
import nook.test.market_nookat.ui.fragment.util.Pref
import org.koin.androidx.viewmodel.ext.android.viewModel


class NoActiveFragment :  BaseFragment<NoActiveViewModel,FragmentNoActiveBinding>() {

    private var token = ""
    override val viewModel: NoActiveViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?, boolean: Boolean): FragmentNoActiveBinding {
        return FragmentNoActiveBinding.inflate(inflater, container, false)
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

    @SuppressLint("LogNotTimber")
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
                        viewModel.getNoActive(tokenWith).collect() {
                            when (it.status) {
                                Status.SUCCESS -> {
                                    val adapterAds = AdapterAct(this@NoActiveFragment::click).apply {
                                        submitList(it.data?.data)
                                    }
                                    binding.rvAds.adapter = adapterAds
                                    Log.i("asxdcf", "onViewModel ${it.data}")
                                    binding.tvText.isVisible = adapterAds.itemCount <= 0
                                }

                                Status.LOADING -> {}
                                Status.ERROR -> {
                                    Log.i("sdftg", "onViewModel ${it.message}")
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
        val  add = Model(int)
        findNavController().navigate(R.id.adsTwoFragment, bundleOf(ID_TWO to add ))
    }

    private fun showKgText(){
        val context = LocateHelper().setLocale(requireContext(),"kg")
        val resource = context.resources
        binding.tvText.text = resource.getString(R.string.no_no_actives)
    }

    private fun showRuText(){
        val context = LocateHelper().setLocale(requireContext(),"ru")
        val resource = context.resources
        binding.tvText.text = resource.getString(R.string.no_no_actives)
    }

    companion object {
        const val ID_TWO = "ifghj"
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