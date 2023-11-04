package nook.test.market_nookat.ui.fragment.filter

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import nook.test.market_nookat.R
import nook.test.market_nookat.data.result.Status
import nook.test.market_nookat.databinding.FragmentAddBinding
import nook.test.market_nookat.databinding.FragmentFilterBinding
import nook.test.market_nookat.ui.base.BaseFragment
import nook.test.market_nookat.ui.fragment.add.AddViewModel
import nook.test.market_nookat.ui.fragment.edit.EditViewModel
import nook.test.market_nookat.ui.fragment.settings.LocateHelper
import nook.test.market_nookat.ui.fragment.util.Pref
import nook.test.market_nookat.ui.fragment.util.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel


class FilterFragment : BaseFragment<FilterViewModel,FragmentFilterBinding>() {


    override val viewModel: FilterViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater,container: ViewGroup?,boolean: Boolean): FragmentFilterBinding {
        return FragmentFilterBinding.inflate(inflater,container,false)
    }

    override fun initFragment() {
        val selectedLanguage = Pref(requireContext()).getSelectedLanguage()
        if (selectedLanguage == "ru") {
            showRuText()
        } else {
            showKgText()
        }
        onViewModel()
        initClickListener()
    }

    private fun initClickListener(){
        binding.btnUpdate.setOnClickListener{
            Pref(requireContext()).setMinPrice(binding.etPriceMin.text.toString())
            Pref(requireContext()).setMaxPrice(binding.etPriceMax.text.toString())
            onViewModel()
            findNavController().navigate(R.id.navigation_home)
        }
        binding.ivBackFil.setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }

        binding.btnClear.setOnClickListener{
           binding.tvOne.text.clear()
            binding.etPriceMin.text?.clear()
            binding.etPriceMax.text?.clear()
        }
    }
    private fun onViewModel() {
        lifecycleScope.launch {
            viewModel.getCategory().collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        val adapter = it.data?.data?.let { it1 ->
                            AdapterDire(
                                requireContext(),
                                R.layout.list_item,
                                it1
                            )
                        }
                        binding.tvOne.setAdapter(adapter)
                        binding.tvOne.onItemClickListener =
                            AdapterView.OnItemClickListener { _, _, i, _ ->
                                val selectedItem = adapter?.getItem(i)
                                if (selectedItem != null) {
                                    binding.tvOne.setText(selectedItem.name, false)
                                    Pref(requireContext()).setCategory(selectedItem.id.toString())
                                    Pref(requireContext()).setCatery(selectedItem.name)
                                }
                            }
                    }

                    Status.LOADING -> {}
                    Status.ERROR -> {
                        showToast(requireContext(),"роишошла ошибка!!")
                        showToast(requireContext(),"${it.message}")

                    }
                }
            }
        }
    }


    private fun showKgText(){
        val context = LocateHelper().setLocale(requireContext(),"kg")
        val resource = context.resources
        binding.tvFil.text = resource.getString(R.string.filtration)
        binding.tvOne.hint = resource.getString(R.string.category)
        binding.etPriceMax.hint = resource.getString(R.string.price_max)
        binding.etPriceMin.hint = resource.getString(R.string.price_min)
        binding.tvClear.text = resource.getString(R.string.clear)
        binding.btnUpdate.text = resource.getString(R.string.apply)
    }

    private fun showRuText(){
        val context = LocateHelper().setLocale(requireContext(),"ru")
        val resource = context.resources
        binding.tvFil.text = resource.getString(R.string.filtration)
        binding.tvOne.hint = resource.getString(R.string.category)
        binding.etPriceMax.hint = resource.getString(R.string.price_max)
        binding.etPriceMin.hint = resource.getString(R.string.price_min)
        binding.tvClear.text = resource.getString(R.string.clear)
        binding.btnUpdate.text = resource.getString(R.string.apply)
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