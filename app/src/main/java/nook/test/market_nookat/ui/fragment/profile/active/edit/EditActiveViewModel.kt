package nook.test.market_nookat.ui.fragment.profile.active.edit

import nook.test.market_nookat.data.repository.Repository
import nook.test.market_nookat.ui.base.BaseViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody

class EditActiveViewModel  (private val repository: Repository) : BaseViewModel() {
    fun addAds(
        token: String,
        advertisementId: Int, method: RequestBody, content: RequestBody,
        price: RequestBody,
        phone: RequestBody, whatsapp: RequestBody, currencyId: RequestBody, locationId: RequestBody, categoryId: RequestBody, photos: List<MultipartBody.Part>
    ) = repository.editAds(token,advertisementId, method, content, price, phone, whatsapp, currencyId, locationId, categoryId, photos)

    fun getOneAds(int: Int) = repository.oneAds(int)


    fun getCurrency() = repository.getCurrency()

    fun getCategory() = repository.getCategory()

    fun getLocation() = repository.getLocation()

    fun loginUser(loinUser: String,password:String) = repository.loginUser(loinUser,password)

}