package nook.test.market_nookat.ui.fragment.favorite


import nook.test.market_nookat.data.repository.Repository
import nook.test.market_nookat.ui.base.BaseViewModel


class FavoriteViewModel (private val repository: Repository): BaseViewModel() {

    fun getFavorite(token:String) = repository.getFavorite(token)


    fun deleteFavorite(token:String,id:Int) = repository.deleteFavorite(token, id)


    fun loginUser(loinUser: String,password:String) = repository.loginUser(loinUser,password)

    fun regis(name:String,email:String,password: String,passwordWord: String) = repository.regisUser(email,name,password,passwordWord)
}