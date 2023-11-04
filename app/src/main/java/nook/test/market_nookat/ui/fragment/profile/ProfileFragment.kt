package nook.test.market_nookat.ui.fragment.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.createBitmap
import androidx.viewpager2.widget.ViewPager2
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.tabs.TabLayout

import nook.test.market_nookat.R
import nook.test.market_nookat.databinding.FragmentProfileBinding
import nook.test.market_nookat.ui.base.BaseFragment
import nook.test.market_nookat.ui.fragment.util.Pref
import nook.test.market_nookat.ui.fragment.util.loadImage
import nook.test.market_nookat.ui.fragment.util.loadPhoto
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment<ProfileViewModel,FragmentProfileBinding>() {

    override val viewModel: ProfileViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?, boolean: Boolean): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater,container,false)
    }

    private lateinit var gso : GoogleSignInOptions
    private lateinit var gsc : GoogleSignInClient


    override fun initFragment() {
        onListener()
        adapter()
        photo()
    }

    private fun onListener() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(requireContext(),gso)
        val account : GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(requireContext())
        val accessToken =AccessToken.getCurrentAccessToken()
        val reguest= GraphRequest.newMeRequest(accessToken) { `object` ,response ->
            val email = `object`?.getString("email")
            val name = `object`?.getString("name")
            val profileUrl  = `object`?.getJSONObject("picture")?.getJSONObject("data")?.getString("url")
            binding.tvName.text = name
            binding.tvEmail.text = email
            profileUrl?.let { binding.ivIcon.loadImage(it) }
        }

        val parameters = Bundle()

        binding.backOut.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Ноокат Меркет")
            builder.setMessage("Вы точно хотите выйти из аккаунта?")
            builder.setNegativeButton("Да") { _, _ ->
                Pref(requireContext()).setBoardingShowed(true)
                onOut()
            }
            builder.setPositiveButton("Нет") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
        parameters.putString("field","name,link,picture.type(large),email")
        reguest.parameters = parameters
        reguest.executeAsync()

        if (account != null){
            Pref(requireContext()).setLogin(account.email!!)
            Pref(requireContext()).setPasword(account.email!!)
            Pref(requireContext()).setToken(account.displayName!!)
            binding.tvEmail.hint = account.email
            binding.tvName.hint = account.displayName
            account.photoUrl?.let { binding.ivIcon.loadPhoto(it) }
        } else {
            onOut()
        }
    }

     private fun onOut(){
        gsc.signOut().addOnSuccessListener {
            val sap = Pref(requireContext()).sharedPref.edit()
            sap.remove("token")
            sap.remove("loginm")
            sap.remove("id_ads")
            sap.apply()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun adapter() {
        val adapter = FragmentPageAdapter(requireActivity())
        binding.tabProf.addTab(binding.tabProf.newTab().setText(getString(R.string.active)))
        binding.tabProf.addTab(binding.tabProf.newTab().setText(getString(R.string.not_active)))
        binding.tabProf.setTabTextColors(requireContext().getColor(R.color.white), requireContext().getColor(R.color.white))
        binding.vp.adapter = adapter

        binding.tabProf.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.vp.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        binding.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabProf.selectTab(binding.tabProf.getTabAt(position))
            }
        })
        adapter.let {
            view?.post {
                it.notifyDataSetChanged()
            }
        }
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    binding.ivIcon.setImageURI(uri)
                    Pref(requireContext()).setPhoto(uri.toString())
                }
            }
        }

    private fun photo(){
        binding.cvPhotoAdd.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            intent.putExtra(Intent.ACTION_PICK, true)
            activityResultLauncher.launch(intent)
        }
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