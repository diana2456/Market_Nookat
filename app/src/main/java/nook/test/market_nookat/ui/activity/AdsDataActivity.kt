package nook.test.market_nookat.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import nook.test.market_nookat.R
import nook.test.market_nookat.data.result.Status
import nook.test.market_nookat.databinding.ActivityAdsDataBinding
import nook.test.market_nookat.ui.base.BaseActivity
import nook.test.market_nookat.ui.fragment.ads.Model
import nook.test.market_nookat.ui.fragment.image_sliger.CustomSliderAdapter
import nook.test.market_nookat.ui.fragment.settings.LocateHelper
import nook.test.market_nookat.ui.fragment.util.Pref
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdsDataActivity : BaseActivity<MainViewModel,ActivityAdsDataBinding>(){

    override val viewModel : MainViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityAdsDataBinding {
        return ActivityAdsDataBinding.inflate(layoutInflater)
    }

    override fun initActivity() {
        supportActionBar?.hide()
        val selectedLanguage = Pref(applicationContext).getSelectedLanguage()
        if (selectedLanguage == "ru") {
            showRuText()
        } else {
            showKgText()
        }
        onViewModel()
        onListener()
    }

    private var id = ""
    private var phoneNumber = ""
    private var what = ""


    @SuppressLint("LogNotTimber")
    private fun onViewModel() {
        binding.ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
          val notificationId = intent?.getStringExtra("FIFI")
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
                                            Pref(applicationContext).setBoardingFire(true)
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
                                            val adapter = data.data?.data?.photos?.let { CustomSliderAdapter(it, this@AdsDataActivity::onClick) }
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
        }else {
            /*val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()*/
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
        val context = LocateHelper().setLocale(this, "kg")
        val resource = context.resources
        binding.tvDesc.text = resource.getString(R.string.description)
    }

    private fun showRuText() {
        val context = LocateHelper().setLocale(this, "ru")
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
     //   findNavController().navigate(R.id.photoNotifiFragment, bundleOf(FGH to fgh))
    }
    companion object {
        const val FGH = "awergv"
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