package nook.test.market_nookat.ui.fragment.ads

import nook.test.market_nookat.data.repository.Repository
import nook.test.market_nookat.ui.base.BaseViewModel

class AdsViewModel (private val repository: Repository): BaseViewModel() {

    fun getOneAds(int: Int) = repository.oneAds(int)

    fun loginUser(loinUser: String,password:String) = repository.loginUser(loinUser,password)


    fun addFavorite(token:String,id:Int) = repository.addFavorite(token, id)

    fun searchAds(title: String) = repository.searchAds(title)

    fun deleteFavorite(token:String,id:Int) = repository.deleteFavorite(token, id)

}