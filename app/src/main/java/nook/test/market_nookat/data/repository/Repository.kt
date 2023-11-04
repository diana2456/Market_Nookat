package nook.test.market_nookat.data.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repository(private val remoteDataSource: RemoteDataSource) {

   fun getAds() = remoteDataSource.getAds()

   fun searchAds(text: String) = remoteDataSource.searchAds(text)

   fun searchFilterAds(category: String, priceMax: String) = remoteDataSource.searchFilterAds(category, priceMax)

   fun filterAds(categoryId : Int) = remoteDataSource.filterAds(categoryId)

   fun addAds( token: String, content: RequestBody, price: RequestBody, phone: RequestBody, whatsapp: RequestBody, currencyId: RequestBody, locationId: RequestBody,
               category: RequestBody, photo: List<MultipartBody.Part>) = remoteDataSource.addAds(token, content, price, phone, whatsapp, currencyId, locationId,category, photo)

   fun oneAds(id: Int) = remoteDataSource.oneAds(id)

   fun editAds(authorization: String,
               advertisementId: Int,
               method: RequestBody,
               content: RequestBody,
               price: RequestBody,
               phone: RequestBody,
               whatsapp: RequestBody,
               currencyId: RequestBody,
               locationId: RequestBody,
               categoryId: RequestBody,
               photos: List<MultipartBody.Part>) = remoteDataSource.editAds(authorization, advertisementId, method, content, price, phone, whatsapp, currencyId, locationId, categoryId, photos)

   fun setVipStatus(authorization: String,
                    reclameId: Int,
                    day: Int) = remoteDataSource.setVipStatus(authorization, reclameId, day)

   fun getFavorite(token: String) = remoteDataSource.getFavorite(token)

   fun deleteFavorite(
      token: String,
      advertisementId: Int) = remoteDataSource.deleteFavorite(token, advertisementId)

   fun loginUser(email: String, password: String) = remoteDataSource.loginUser(email, password)

   fun getActive(token: String) = remoteDataSource.getActive(token)

   fun noActive(token: String) = remoteDataSource.noActive(token)

   fun regisUser(
      name: String,
      email: String,
      password: String,
      passwordConfirmation: String) = remoteDataSource.regisUser(name, email, password, passwordConfirmation)


   fun getCategory() = remoteDataSource.getCategory()

   fun getCurrency() = remoteDataSource.getCurrency()

   fun getLocation() = remoteDataSource.getLocation()

   fun addFavorite(
      token: String,
      advertisementId: Int) = remoteDataSource.addFavorite(token, advertisementId)
}