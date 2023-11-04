package nook.test.market_nookat.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Advertisement(
    @SerializedName("id")
    val id: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("whatsapp")
    val whatsapp: String,
    @SerializedName("currency_id")
    val currency_id: Int,
    @SerializedName("location_id")
    val location_id: Int,
    @SerializedName("category_id")
    val category_id: Int,
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("updated_at")
    val updated_at: String,
    @SerializedName("created_at")
    val created_at: String,
)

data class Currency(
    val id: Int,
    val name: String,
    val created_at: String,
    val updated_at: String
) : Serializable


data class loginUser(
    val email: String,
    val password: String
)


data class User(
    @SerializedName("user_id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("token")
    val token: String
)


data class Directory(val data: List<Category>)

data class AdsOne(
    @SerializedName("whatsapp")
    val whatsapp: String = "",
    @SerializedName("active")
    val active: Boolean = false,
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("photos")
    val photos: List<String>?,
    @SerializedName("content")
    val content: String = "",
    @SerializedName("view")
    val view: Int = 0,
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("currency")
    val currency: String = "",
    @SerializedName("location")
    val location: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("vip")
    val vip: Boolean = false,
    @SerializedName("category")
    val category: String = "",
    @SerializedName("favorite")
    val favorite: Int = 0
) : Serializable

data class Category(
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("id")
    val id: Int = 0
) : Serializable


data class Data(
    @SerializedName("per_page")
    val perPage: Int = 0,
    @SerializedName("data")
    val data: List<DataItem>?,
    @SerializedName("last_page")
    val lastPage: Int = 0,
    @SerializedName("next_page_url")
    val nextPageUrl: String? = null,
    @SerializedName("prev_page_url")
    val prevPageUrl: String? = null,
    @SerializedName("first_page_url")
    val firstPageUrl: String = "",
    @SerializedName("path")
    val path: String = "",
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("last_page_url")
    val lastPageUrl: String = "",
    @SerializedName("from")
    val from: Int = 0,
    @SerializedName("links")
    val links: List<LinksItem>?,
    @SerializedName("to")
    val to: Int = 0,
    @SerializedName("current_page")
    val currentPage: Int = 0
)


data class DataItem(
    @SerializedName("whatsapp")
    val whatsapp: String = "",
    @SerializedName("active")
    val active: Boolean = false,
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("photos")
    val photos: List<PhotosItem>?,
    @SerializedName("content")
    val content: String = "",
    @SerializedName("location_id")
    val locationId: Int = 0,
    @SerializedName("category_id")
    val categoryId: Int = 0,
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("user_id")
    val userId: Int = 0,
    @SerializedName("price")
    val price: String = "",
    @SerializedName("currency")
    val currency: Currency,
    @SerializedName("location")
    val location: Location,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("vip")
    val vip: String = "",
    @SerializedName("category")
    val category: Category,
    @SerializedName("currency_id")
    val currencyId: Int = 0
) : Serializable

data class LinksItem(
    @SerializedName("active")
    val active: Boolean = false,
    @SerializedName("label")
    val label: String = "",
    @SerializedName("url")
    val url: String? = null
)

data class Location(
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("id")
    val id: Int = 0
) : Serializable

data class PhotosItem(
    @SerializedName("advertisement_id")
    val advertisementId: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("updated_at")
    val updated_at: String = "",
    @SerializedName("full_path")
    val full_path: String = ""
) : Serializable


data class Reclemi(
    @SerializedName("data")
    val data: Data
)

data class OneRec(@SerializedName("data")
                  val data: AdsOne
)


data class Currencye(
    val id: Int,
    val name: String,
    val created_at: String,
    val updated_at: String
)

data class Locationd(
    val id: Int,
    val name: String,
    val created_at: String,
    val updated_at: String
)

data class Categoory(
    val id: Int,
    val name: String,
    val created_at: String,
    val updated_at: String
)

data class Datya(
    val id: Int,
    val content: String,
    val category_id: Int,
    val location_id: Int,
    val currency_id: Int,
    val price: String,
    val phone: String,
    val whatsapp: String,
    val active: Boolean,
    val view: Int,
    val vip: String,
    val user_id: Int,
    val created_at: String,
    val updated_at: String,
    val currency: Currencye,
    val location: Locationd,
    val category: Categoory
): Serializable

data class ResponseData(
    val data: List<Datya>
)

