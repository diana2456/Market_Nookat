package nook.test.market_nookat.data.model

import com.google.gson.annotations.SerializedName

data class Categy(@SerializedName("updated_at")
                    val updatedAt: String = "",
                    @SerializedName("name")
                    val name: String = "",
                    @SerializedName("created_at")
                    val createdAt: String = "",
                    @SerializedName("id")
                    val id: Int = 0)