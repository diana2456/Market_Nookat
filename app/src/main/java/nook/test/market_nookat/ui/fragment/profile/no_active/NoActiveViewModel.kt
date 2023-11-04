package nook.test.market_nookat.ui.fragment.profile.no_active

import androidx.lifecycle.ViewModel
import nook.test.market_nookat.data.repository.Repository
import nook.test.market_nookat.ui.base.BaseViewModel

class NoActiveViewModel (private val repository: Repository) :BaseViewModel() {

    fun getNoActive(token:String) = repository.noActive(token)

    fun loginUser(loinUser: String,password:String) = repository.loginUser(loinUser,password)

}