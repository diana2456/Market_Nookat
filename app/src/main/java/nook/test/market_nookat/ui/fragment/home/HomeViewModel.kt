package nook.test.market_nookat.ui.fragment.home

import nook.test.market_nookat.data.repository.Repository
import nook.test.market_nookat.ui.base.BaseViewModel


class HomeViewModel(private val repository: Repository) : BaseViewModel() {

    fun getAds() = repository.getAds()


    fun getCategory() = repository.getCategory()


    fun addFavorite(token:String,id:Int) = repository.addFavorite(token, id)


    fun deleteFavorite(token:String,id:Int) = repository.deleteFavorite(token, id)


    fun searchAds(text:String) = repository.searchAds(text)

    fun filterAds(category: String, priceMax: String) = repository.searchFilterAds(category, priceMax)


    fun loginUser(loinUser: String,password:String) = repository.loginUser(loinUser,password)

    fun getFilter(category: Int) = repository.filterAds(category)
}