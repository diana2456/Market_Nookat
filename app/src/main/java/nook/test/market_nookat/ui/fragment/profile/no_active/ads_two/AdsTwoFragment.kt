package nook.test.market_nookat.ui.fragment.profile.no_active.ads_two

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import nook.test.market_nookat.R
import nook.test.market_nookat.data.result.Status
import nook.test.market_nookat.databinding.FragmentAdsTwoBinding
import nook.test.market_nookat.ui.base.BaseFragment
import nook.test.market_nookat.ui.fragment.ads.Model
import nook.test.market_nookat.ui.fragment.image_sliger.CustomSliderAdapter
import nook.test.market_nookat.ui.fragment.profile.no_active.NoActiveFragment.Companion.ID_TWO
import nook.test.market_nookat.ui.fragment.settings.LocateHelper
import nook.test.market_nookat.ui.fragment.util.Pref
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdsTwoFragment : BaseFragment<AdsTwoViewModel,FragmentAdsTwoBinding>() {


    override val viewModel: AdsTwoViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?, boolean: Boolean): FragmentAdsTwoBinding {
        return FragmentAdsTwoBinding.inflate(inflater,container,false)
    }

    private lateinit var model: Model
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
        onViewModel()
        onListener()
    }

    private fun onViewModel() {
        binding.btnVip.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Ноокат Меркет")
            builder.setMessage("Чтобы сделать это объявление VIP вам нужно связаться с админом!")

            builder.setNegativeButton("Связаться") { _, _ ->
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://wa.me/+996705205405")
                startActivity(intent)
            }
            builder.setPositiveButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        if (arguments != null) {
            model = arguments?.getSerializable(ID_TWO) as Model
            lifecycleScope.launch {
                viewModel.getOneAds(model.img).collect() { data ->
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
                            val adapter = data.data?.data?.photos?.let { CustomSliderAdapter(it, this@AdsTwoFragment::onClick) }
                            if (adapter != null) {
                                binding.vpPhoto.setSliderAdapter(adapter)
                            }

                            binding.btnEdit.setOnClickListener {
                                findNavController().navigate(R.id.editFragment, bundleOf(KLO to data.data?.data))
                            }
                        }

                        Status.LOADING -> {}
                        Status.ERROR -> {}
                    }
                }
            }
        }
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


    fun makePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val uri = Uri.parse("tel:$phoneNumber")
        intent.data = uri
        startActivity(intent)
    }


    private fun onClick(pos: Int){
        val fgh = Model(id.toInt())
        findNavController().navigate(R.id.photoAdsFragment, bundleOf(WERT to fgh))
    }
    companion object {
        const val KLO = "bghkk"
        const val WERT = "awergv"
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