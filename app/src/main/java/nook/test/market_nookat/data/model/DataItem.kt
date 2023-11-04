package nook.test.market_nookat.data.model

import com.google.gson.annotations.SerializedName

data class Dataem(@SerializedName("whatsapp")
                    val whatsapp: String = "",
                    @SerializedName("active")
                    val active: Boolean = false,
                    @SerializedName("created_at")
                    val createdAt: String = "",
                    @SerializedName("photos")
                    val photos: List<PhotosIm>?,
                    @SerializedName("content")
                    val content: String = "",
                    @SerializedName("location_id")
                    val locationId: Int = 0,
                    @SerializedName("view")
                    val view: Int = 0,
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
                    val currency: Currcy,
                    @SerializedName("location")
                    val location:  Locan,
                    @SerializedName("id")
                    val id: Int = 0,
                    @SerializedName("vip")
                    val vip: String = "",
                    @SerializedName("category")
                    val category:Categy,
                    @SerializedName("currency_id")
                    val currencyId: Int = 0)