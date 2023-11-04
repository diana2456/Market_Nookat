package nook.test.market_nookat.ui.fragment.settings



import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import nook.test.market_nookat.R
import nook.test.market_nookat.data.result.Status
import nook.test.market_nookat.databinding.FragmentAddBinding
import nook.test.market_nookat.databinding.FragmentSettingsBinding
import nook.test.market_nookat.ui.activity.MainActivity
import nook.test.market_nookat.ui.base.BaseFragment
import nook.test.market_nookat.ui.fragment.add.AddViewModel
import nook.test.market_nookat.ui.fragment.util.Notification
import nook.test.market_nookat.ui.fragment.util.Pref
import nook.test.market_nookat.ui.fragment.util.Server
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsFragment : BaseFragment<SettingsViewModel,FragmentSettingsBinding>()  {


    override val viewModel: SettingsViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?, boolean: Boolean): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(inflater,container,false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedLanguage = Pref(requireContext()).getSelectedLanguage()
        if (selectedLanguage == "ru") {
            showRuText()
        } else {
            showKgText()
        }
    }

    override fun initFragment() {
         onClickListener()
        onNotifi()
        binding.notifiSwich.isChecked = Pref(requireContext()).isSwichShowed()
    }

    private fun onNotifi() {
        binding.notifiSwich.setOnCheckedChangeListener { _, isChecked ->
            Pref(requireContext()).setSwichShowed(isChecked)
            if (isChecked) {
                 FirebaseMessaging.getInstance().subscribeToTopic("your_topic")
               enableNotifications()
            }else {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("your_topic")
            }
        }
        binding.llThree.setOnClickListener {
            shareText(requireContext(),"Рекомендую: http://play.google.com/store/apps/details?id=kg.android.leilekmarket")
        }

        binding.llFour.setOnClickListener {
            findNavController().navigate(R.id.aboutFragment)
        }

        binding.llFive.setOnClickListener {
            findNavController().navigate(R.id.instructionsFragment)
        }

        binding.llSix.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://wa.me/+996705205405")
            startActivity(intent)
        }
    }

    private fun enableNotifications() {
        lifecycleScope.launch {
        viewModel.getAds().collect() {
            when (it.status) {
            Status.SUCCESS -> {
                if (it.data?.data?.data != null) {
                for (notification in it.data?.data.data) {
                    val intent = Intent(requireContext(), Server::class.java)
                    intent.putExtra("title", notification.content)
                    intent.putExtra("message", "${notification.phone},${notification.whatsapp}")
                    requireContext().startService(intent)
                }
                }
            }
                Status.LOADING -> {}
            Status.ERROR -> {}
            }
        }
        }
    }


    private fun onClickListener() {
        binding.llOne.setOnClickListener{
            val list = arrayOf("Русский", "Кыргызский")
            val currentPosition = getLanguageIndex(Pref(requireContext()).getSelectedLanguage())

            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Поменять язык")
            alertDialogBuilder.setSingleChoiceItems(list, currentPosition) { dialog, which ->
                // When the user selects a new language, store it in SharedPreferences
                val selectedLanguage = when (which) {
                    0 -> "ru"
                    1 -> "kg"
                    else -> "ru" // Default to Russian
                }
                Pref(requireContext()).setSelectedLanguage(selectedLanguage)

                // Update your UI to display text in the selected language
                updateUIToSelectedLanguage(selectedLanguage)

                dialog.dismiss()
            }
            alertDialogBuilder.create().show()
        }

    }

    private fun getLanguageIndex(selectedLanguage: String): Int {
        val languages = arrayOf("ru", "kg") // Your list of languages
        return languages.indexOf(selectedLanguage)
    }
    private fun updateUIToSelectedLanguage(selectedLanguage: String) {
        val context = LocateHelper().setLocale(requireContext(), selectedLanguage)
        val resource = context.resources

        binding.tvLan.text = resource.getString(R.string.languages)
        binding.tvNotifi.text = resource.getString(R.string.notifications)
        binding.tvShare.text = resource.getString(R.string.share)
        binding.tvAbout.text = resource.getString(R.string.about_the_application)
        binding.tvApp.text = resource.getString(R.string.application)
        binding.tvContact.text = resource.getString(R.string.contact)
        binding.tvSet.text = resource.getString(R.string.settings)
    }

    private fun showKgText(){
        val context = LocateHelper().setLocale(requireContext(),"kg")
        val resource = context.resources
        binding.tvLan.text = resource.getString(R.string.languages)
        binding.tvNotifi.text = resource.getString(R.string.notifications)
        binding.tvShare.text = resource.getString(R.string.share)
        binding.tvAbout.text = resource.getString(R.string.about_the_application)
        binding.tvApp.text = resource.getString(R.string.application)
        binding.tvContact.text = resource.getString(R.string.contact)
        binding.tvSet.text = resource.getString(R.string.settings)
    }


    private fun showRuText(){
        val context = LocateHelper().setLocale(requireContext(),"ru")
        val resource = context.resources
        binding.tvLan.text = resource.getString(R.string.languages)
        binding.tvNotifi.text = resource.getString(R.string.notifications)
        binding.tvShare.text = resource.getString(R.string.share)
        binding.tvAbout.text = resource.getString(R.string.about_the_application)
        binding.tvApp.text = resource.getString(R.string.application)
        binding.tvContact.text = resource.getString(R.string.contact)
        binding.tvSet.text = resource.getString(R.string.settings)
    }


    fun shareText(context: Context, text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)

        val shareIntent = Intent.createChooser(intent, null)
        context.startActivity(shareIntent)
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