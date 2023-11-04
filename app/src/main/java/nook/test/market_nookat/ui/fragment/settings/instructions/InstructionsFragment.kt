package nook.test.market_nookat.ui.fragment.settings.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import nook.test.market_nookat.R
import nook.test.market_nookat.databinding.FragmentInstructionsBinding
import nook.test.market_nookat.databinding.FragmentProfileBinding
import nook.test.market_nookat.ui.base.BaseFragment
import nook.test.market_nookat.ui.fragment.profile.ProfileViewModel
import nook.test.market_nookat.ui.fragment.settings.LocateHelper
import nook.test.market_nookat.ui.fragment.util.Pref
import org.koin.androidx.viewmodel.ext.android.viewModel


class InstructionsFragment : BaseFragment<InstructionsViewModel,FragmentInstructionsBinding>(){

    override val viewModel: InstructionsViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?, boolean: Boolean): FragmentInstructionsBinding {
        return FragmentInstructionsBinding.inflate(inflater,container,false)
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
        binding.tvInstruc.text = resource.getString(R.string.instruction)
    }

    private fun showRuText(){
        val context = LocateHelper().setLocale(requireContext(),"ru")
        val resource = context.resources
        binding.tvWrite.text = resource.getString(R.string.application)
        binding.tvInstruc.text = resource.getString(R.string.instruction)
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