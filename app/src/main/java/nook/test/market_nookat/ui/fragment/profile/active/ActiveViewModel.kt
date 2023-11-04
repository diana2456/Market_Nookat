package nook.test.market_nookat.ui.fragment.profile.active

import nook.test.market_nookat.data.repository.Repository
import nook.test.market_nookat.ui.base.BaseViewModel


class ActiveViewModel (private val repository: Repository) : BaseViewModel() {
    fun getActive(token: String) = repository.getActive(token)

    fun loginUser(loinUser: String,password:String) = repository.loginUser(loinUser,password)
}