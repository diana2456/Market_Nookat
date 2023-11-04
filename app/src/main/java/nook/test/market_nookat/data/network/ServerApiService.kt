package nook.test.market_nookat.data.network

import nook.test.market_nookat.data.model.ActiveData
import nook.test.market_nookat.data.model.Advertisement
import nook.test.market_nookat.data.model.Data
import nook.test.market_nookat.data.model.DataItem
import nook.test.market_nookat.data.model.Directory
import nook.test.market_nookat.data.model.OneRec
import nook.test.market_nookat.data.model.Reclemi
import nook.test.market_nookat.data.model.ResponseData
import nook.test.market_nookat.data.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ServerApiService {
    @GET("/api/V1/reclame/getAll")
    suspend fun getAds(): Response<Reclemi>

    @GET("/api/V1/reclame/search")
    suspend fun searchAds(@Query("search") text: String): Response<Reclemi>

    @GET("/api/V1/reclame/search")
    suspend fun searchFilterAds(
        @Query("category") category: String,
        @Query("price_min") priceMax: String
    ): Response<Reclemi>

    @GET("/api/V1/reclame/search")
    suspend fun filterAds(@Query("category") category: Int): Response<Reclemi>


    @Multipart
    @POST("/api/V1/user/reclame/add")
    suspend fun addAds(
        @Header("Authorization") token: String,
        @Part("content") content: RequestBody,
        @Part("price") price: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("whatsapp") whatsapp: RequestBody,
        @Part("currency_id") currencyId: RequestBody,
        @Part("location_id") locationId: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part photos: List<MultipartBody.Part>
    ): Response<Advertisement>


    @GET("/api/V1/reclame/getOne/{id}")
     suspend fun oneAds(
        @Path("id")id: Int): Response<OneRec>


    @Multipart
    @POST("/api/V1/user/reclame/edit/{advertisementId}")
    suspend fun editAds(
        @Header("Authorization") authorization: String,
        @Path("advertisementId") advertisementId: Int,
        @Part("_method") method: RequestBody,
        @Part("content") content: RequestBody,
        @Part("price") price: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("whatsapp") whatsapp: RequestBody,
        @Part("currency_id") currencyId: RequestBody,
        @Part("location_id") locationId: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part photos: List<MultipartBody.Part>
    ): Response<Advertisement>

    @FormUrlEncoded
    @POST("/api/V1/admin/reclame/vip")
    suspend fun setVipStatus(
        @Header("Authorization") authorization: String,
        @Field("reclame_id") reclameId: Int,
        @Field("day") day: Int
    ): Response<Advertisement>

    @GET("/api/V1/user/favorite/list")
    suspend fun getFavorite(
        @Header("Authorization") token: String): Response<Reclemi>

    @FormUrlEncoded
    @POST("/api/V1/user/favorite/add")
    suspend fun addFavorite(
        @Header("Authorization") token: String,
        @Field("advertisement_id") advertisementId: Int
    ): Response<String>

    @FormUrlEncoded
    @POST("/api/V1/user/favorite/destroy")
    suspend fun deleteFavorite(
        @Header("Authorization") token: String,
        @Field("advertisement_id") advertisementId: Int
    ): Response<Unit>

    @FormUrlEncoded
    @POST("/api/V1/auth/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<User>

    @GET("/api/V1/user/noactive")
   suspend fun getActive(@Header("Authorization") token: String): Response<ActiveData>

    @GET("/api/V1/user/noactive")
    suspend fun noActive(@Header("Authorization") token: String): Response<ActiveData>

    @FormUrlEncoded
    @POST("/api/V1/auth/register")
     suspend fun regisUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirmation: String
    ): Response<User?>

    @GET("/api/V1/catalog/category")
    suspend fun getCategory(): Response<Directory>

    @GET("/api/V1/catalog/currency")
    suspend fun getCurrency(): Response<Directory>

    @GET("/api/V1/catalog/location")
    suspend fun getLocation(): Response<Directory>



}
