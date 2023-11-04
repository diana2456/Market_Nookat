package nook.test.market_nookat.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import nook.test.market_nookat.data.model.ActiveData
import nook.test.market_nookat.data.model.Advertisement
import nook.test.market_nookat.data.model.Data
import nook.test.market_nookat.data.model.DataItem
import nook.test.market_nookat.data.model.Directory
import nook.test.market_nookat.data.model.OneRec
import nook.test.market_nookat.data.model.Reclemi
import nook.test.market_nookat.data.model.ResponseData
import nook.test.market_nookat.data.model.User
import nook.test.market_nookat.data.network.BaseDataSource
import nook.test.market_nookat.data.network.ServerApiService
import nook.test.market_nookat.data.result.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.dsl.module
import retrofit2.Response

val remoteDataSourceModule = module {
    factory { RemoteDataSource(get()) }
}
class RemoteDataSource (private val api: ServerApiService) : BaseDataSource() {

    fun getAds() = flow<Resource<Reclemi>> {
        emit(Resource.loading())
        val result = getResult { api.getAds() }
        emit(result)
    }.flowOn(Dispatchers.IO)


    fun searchAds(text: String) = flow<Resource<Reclemi>> {
        emit(Resource.loading())
         val result = getResult{api.searchAds(text)}
        emit(result)
    }.flowOn(Dispatchers.IO)


    fun searchFilterAds(category: String, priceMax: String)  = flow<Resource<Reclemi>> {
        emit(Resource.loading())
        val result = getResult{api.searchFilterAds(category, priceMax)}
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun filterAds(categoryId : Int) = flow<Resource<Reclemi>> {
        emit(Resource.loading())
        val result = getResult{api.filterAds(categoryId)}
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun addAds( token: String, content: RequestBody, price: RequestBody, phone: RequestBody, whatsapp: RequestBody, currencyId: RequestBody, locationId: RequestBody,
                category: RequestBody, photo: List<MultipartBody.Part>) = flow<Resource<Advertisement>> {
        emit(Resource.loading())
        val result = getResult{api.addAds(token, content, price, phone, whatsapp, currencyId, locationId,category, photo)}
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun oneAds(id: Int) = flow<Resource<OneRec>> {
        emit(Resource.loading())
        val result = getResult{api.oneAds(id)}
        emit(result)
    }.flowOn(Dispatchers.IO)

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
                photos: List<MultipartBody.Part>) = flow<Resource<Advertisement>> {
                    emit(Resource.loading())
        val result = getResult{api.editAds(authorization,advertisementId,method,content,price,phone,whatsapp,currencyId,locationId,categoryId, photos)}
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun setVipStatus(authorization: String,
                     reclameId: Int,
                     day: Int) = flow<Resource<Advertisement>> {
        val result = getResult{ api.setVipStatus(authorization, reclameId, day)}
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun getFavorite(token: String) = flow<Resource<Reclemi>> {
        val result = getResult{api.getFavorite(token)}
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun addFavorite(
         token: String,
         advertisementId: Int) = flow<Resource<String>> {
        val result = getResult{api.addFavorite(token, advertisementId)}
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun deleteFavorite(
        token: String,
        advertisementId: Int) = flow<Resource<Unit>> {
        val result = getResult{api.deleteFavorite(token, advertisementId)}
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun loginUser(email: String, password: String) = flow<Resource<User>> {
        val result = getResult{api.loginUser(email, password)}
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun getActive(token: String) = flow<Resource<ActiveData>> {
        val result = getResult{api.getActive(token)}
        emit(result)
    }

    fun noActive(token: String) = flow<Resource<ActiveData>> {
        val result = getResult{api.noActive(token)}
        emit(result)
    }

    fun regisUser(
        name: String,
        email: String,
        password: String,
        passwordConfirmation: String) = flow<Resource<User?>> {
        val result = getResult{api.regisUser(name, email, password, passwordConfirmation)}
        emit(result)
    }

    fun getCategory() = flow<Resource<Directory>> {
        val result = getResult{api.getCategory()}
        emit(result)
    }

    fun getCurrency() = flow<Resource<Directory>> {
        val result = getResult{api.getCurrency()}
        emit(result)
    }

    fun getLocation() = flow<Resource<Directory>> {
        val result = getResult{api.getLocation()}
        emit(result)
    }



}