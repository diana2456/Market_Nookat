package nook.test.market_nookat.data.model

import com.google.gson.annotations.SerializedName

data class PhotosIm(@SerializedName("updated_at")
                      val updatedAt: String = "",
                      @SerializedName("name")
                      val name: String = "",
                      @SerializedName("advertisement_id")
                      val advertisementId: Int = 0,
                      @SerializedName("created_at")
                      val createdAt: String = "",
                      @SerializedName("id")
                      val id: Int = 0,
                      @SerializedName("full_path")
                      val fullPath: String = "")