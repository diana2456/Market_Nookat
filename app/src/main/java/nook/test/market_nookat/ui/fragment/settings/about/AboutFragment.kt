package nook.test.market_nookat.ui.fragment.settings.about

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import nook.test.market_nookat.R
import nook.test.market_nookat.databinding.FragmentAboutBinding
import nook.test.market_nookat.databinding.FragmentInstructionsBinding
import nook.test.market_nookat.ui.base.BaseFragment
import nook.test.market_nookat.ui.base.BaseViewModel
import nook.test.market_nookat.ui.fragment.settings.LocateHelper
import nook.test.market_nookat.ui.fragment.settings.instructions.InstructionsViewModel
import nook.test.market_nookat.ui.fragment.util.Pref
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutFragment : BaseFragment<AboutViewModel,FragmentAboutBinding>() {

    override val viewModel: AboutViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?, boolean: Boolean): FragmentAboutBinding{
        return FragmentAboutBinding.inflate(inflater,container,false)
    }

    override fun initFragment() {
        binding.ivBackAdd.setOnClickListener {
            findNavController().navigateUp()
        }
        val selectedLanguage = Pref(requireContext()).getSelectedLanguage()
        if (selectedLanguage == "ru") {
            showRuText()
        } else {
            showKgText()
        }
    }


    private fun showKgText(){
        val context = LocateHelper().setLocale(requireContext(),"kg")
        val resource = context.resources
        binding.tvWrite.text = resource.getString(R.string.application)
        binding.tvAbout.text = resource.getString(R.string.about)
    }

    private fun showRuText(){
        val context = LocateHelper().setLocale(requireContext(),"ru")
        val resource = context.resources
        binding.tvWrite.text = resource.getString(R.string.application)
        binding.tvAbout.text = resource.getString(R.string.about)
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