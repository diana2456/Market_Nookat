package nook.test.market_nookat.ui.fragment.add

import androidx.lifecycle.ViewModel
import nook.test.market_nookat.data.repository.Repository
import nook.test.market_nookat.ui.base.BaseViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody


class AddViewModel(private val repository: Repository): BaseViewModel() {

    fun addAds(
        token: String, content: RequestBody, price: RequestBody, phone: RequestBody, whatsapp: RequestBody, currencyId: RequestBody, locationId: RequestBody,
        category: RequestBody, photo: List<MultipartBody.Part>
    ) = repository.addAds(token, content, price, phone, whatsapp, currencyId, locationId,category, photo)

    fun getCurrency() = repository.getCurrency()

    fun getCategory() = repository.getCategory()

    fun getLocation() = repository.getLocation()

    fun loginUser(loinUser: String,password:String) = repository.loginUser(loinUser,password)

}