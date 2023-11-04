package nook.test.market_nookat.data.model

import com.google.gson.annotations.SerializedName

data class ActiveData(@SerializedName("data")
                      val data: List<Dataem>?)