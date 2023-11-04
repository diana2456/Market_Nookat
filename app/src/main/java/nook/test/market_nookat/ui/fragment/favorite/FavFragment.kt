package nook.test.market_nookat.ui.fragment.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch
import nook.test.market_nookat.R
import nook.test.market_nookat.data.model.DataItem
import nook.test.market_nookat.data.result.Status
import nook.test.market_nookat.databinding.FragmentFavBinding
import nook.test.market_nookat.ui.base.BaseFragment
import nook.test.market_nookat.ui.fragment.settings.LocateHelper
import nook.test.market_nookat.ui.fragment.util.Pref
import nook.test.market_nookat.ui.fragment.util.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavFragment  : BaseFragment<FavoriteViewModel, FragmentFavBinding>() {

    override val viewModel: FavoriteViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?, boolean: Boolean): FragmentFavBinding {
        return FragmentFavBinding.inflate(inflater,container,false)
    }

    private var token = ""
    private lateinit var gso : GoogleSignInOptions
    private lateinit var gsc : GoogleSignInClient
    private lateinit var callbackManager: CallbackManager


    override fun initFragment() {
        val selectedLanguage = Pref(requireContext()).getSelectedLanguage()
        if (selectedLanguage == "ru") {
            showRuText()
        } else {
            showKgText()
        }
        onListener()
        token()
      }

    private fun onListener() {
        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(requireContext(), gso)
        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(requireContext())
        callbackManager = CallbackManager.Factory.create()
        val accessToken = AccessToken.getCurrentAccessToken()


        binding.btnRegis.isVisible = Pref(requireContext()).isBoardingShowed()

        if (accessToken != null && !accessToken.isExpired) {
            binding.btnRegis.isVisible = Pref(requireContext()).isBoardingShowed()
        }

        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    TODO("Not yet implemented")
                }

                override fun onError(error: FacebookException) {
                    TODO("Not yet implemented")
                }

                override fun onSuccess(result: LoginResult) {
                    binding.btnRegis.isVisible = false
                }

            })

        if (account != null) {
            lifecycleScope.launch {
                //запрос на регистрацию
                account.displayName?.let { it1 ->
                    account.email?.let { it2 ->
                        viewModel.regis(
                            it2,
                            it1, account.email!!, account.email!!
                        ).collect {
                            when(it.status) {
                                Status.SUCCESS ->{
                                     lifecycleScope.launch {
                                        Pref(requireContext()).setLogin(account.email!!)
                                        Pref(requireContext()).setPasword(account.email!!)
                                        Pref(requireContext()).setToken(account.displayName!!)
                                        Pref(requireContext()).setBoardingShowed(false)
                                         //запрос на регистрацию
                                        viewModel.loginUser(account.email!!, account.email!!)
                                            .collect {
                                                Pref(requireContext()).setBoardingShowed(false)
                                                Log.i("adfvgcf", "onViewModel ${it.data}")
                                            }
                                    }
                                }
                                Status.ERROR ->{
                                    Log.i("asdcfv", "onViewModel ${it.data}")
                                    Toast.makeText(requireContext(), "Проишошла ошибка!!", Toast.LENGTH_SHORT).show()
                                }
                                Status.LOADING ->{}
                            }
                        }
                    }
                }
            }
        }
        binding.btnRegis.setOnClickListener {
            goToSingIn()
        }
    }

    private fun goToSingIn(){
        val singInIntent = gsc.signInIntent
        startActivityForResult(singInIntent,1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(resultCode,resultCode,data)
        if (resultCode == 1000){
            val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            Toast.makeText(requireContext(), "Вы успешно зарегестрировались!", Toast.LENGTH_SHORT).show()
            try {
                task.getResult(ApiException::class.java)
            }
            catch (e: java.lang.Exception){
                Toast.makeText(requireContext(), "$e", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun token() {
        lifecycleScope.launch {
            viewModel.loginUser(
                Pref(requireContext()).isLogin(),
                Pref(requireContext()).isPasword()
            ).collect { data ->
                when (data.status) {
                    Status.SUCCESS -> {
                        token = data.data?.token.toString()
                         val tokenWith = "Bearer $token"
                        viewModel.getFavorite(tokenWith).collect(){
                            when (it.status) {
                                Status.SUCCESS -> {
                                   val adapterAds =
                                        AdapterFav(requireContext(), this@FavFragment::click, this@FavFragment::oneClick)
                                    adapterAds.submitList(it.data?.data?.data)
                                    binding.rvFavorite.isVisible = true
                                   binding.rvFavorite.adapter = adapterAds
                                    binding.tvFavorite.isVisible = adapterAds.itemCount <= 0
                                }

                                Status.LOADING -> {}
                                Status.ERROR -> {}
                            }
                        }
                    }

                    Status.LOADING -> {}
                    Status.ERROR -> {}
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun click(id:Int, int: Int){
        val tokenWith = "Bearer $token"
        lifecycleScope.launch {
            viewModel.deleteFavorite(tokenWith, id).collect() {
                when (it.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(requireContext(), "Избранное удаленно!!", Toast.LENGTH_SHORT).show()
                        Log.i("xcvbnh", "onFavorite:$it")
                        token()
                    }

                    Status.ERROR -> {
                        Log.i("xcvh", "onFavorite:$it")
                    }

                    Status.LOADING -> {
                        Log.i("bnh", "onFavorite:$it")
                    }
                }
            }
        }
    }


    private fun showKgText(){
        val context = LocateHelper().setLocale(requireContext(),"kg")
        val resource = context.resources
        binding.tvFav.text = resource.getString(R.string.featured)
        binding.tvFavorite.text = resource.getString(R.string.you_have_no_favorites)
    }

    private fun showRuText(){
        val context = LocateHelper().setLocale(requireContext(),"ru")
        val resource = context.resources
        binding.tvFav.text = resource.getString(R.string.featured)
        binding.tvFavorite.text = resource.getString(R.string.you_have_no_favorites)
    }

    private fun oneClick(item: DataItem){
        findNavController().navigate(R.id.favAdsFragment, bundleOf(FAV to item))
    }

    companion object {
        const val  FAV = "fav_one"
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