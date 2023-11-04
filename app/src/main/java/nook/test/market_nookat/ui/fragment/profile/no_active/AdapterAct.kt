package nook.test.market_nookat.ui.fragment.profile.no_active

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import nook.test.market_nookat.data.model.DataItem
import nook.test.market_nookat.data.model.Dataem
import nook.test.market_nookat.databinding.ItemAdsBinding
import nook.test.market_nookat.ui.fragment.util.convertDateFormat
import nook.test.market_nookat.ui.fragment.util.loadImage
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class AdapterAct(private val onClick: (Int) -> Unit):
    ListAdapter<Dataem, AdapterAct.AdsHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsHolder {
        return AdsHolder(ItemAdsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AdsHolder, position: Int) {
        holder.bind(getItem(position))
    }

    @Suppress("NAME_SHADOWING")
    inner class AdsHolder(private val binding: ItemAdsBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: Dataem) {
            with(binding) {
                val apartmentImages = item.photos
                if (apartmentImages != null) {
                    if (apartmentImages.isNotEmpty()) {
                        val firstImage = apartmentImages[0]
                        val img = firstImage.fullPath
                        ivPhoto.loadImage(img)
                        tvId.text = item.id.toString()
                        val russianLocale = Locale("ru", "RU")
                        val formattedDate = item.createdAt.convertDateFormat(russianLocale)
                        binding.tvData.text = formattedDate
                    }

                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val zoneId = ZoneId.systemDefault()
                    val currentDate = LocalDate.now(zoneId)
                    val parsedTargetDate = LocalDate.parse(item.vip, formatter)

                    binding.tvVip.isVisible = !currentDate.isAfter(parsedTargetDate)
                    tvTittle.text = item.content
                    val maxLength = 6
                    val decimalSeparator = '.'
                    val parts = item.price.split(decimalSeparator)

                    if (parts.size == 2 && parts[1].toInt() == 0) {
                        // Если дробная часть равна 0, удаляем ее
                        val formattedPrice = parts[0]
                        tvPrice.text = if (formattedPrice.length > maxLength) {
                            formattedPrice.substring(0, maxLength) + "..."
                        } else {
                            formattedPrice
                        }
                    } else {
                        // Если есть ненулевая дробная часть, оставляем как есть
                        tvPrice.text = item.price
                    }


                    itemView.setOnClickListener {
                        onClick(item.id)
                    }

                    }
                }
            }
        }


    }



    private class DiffCallback : DiffUtil.ItemCallback<Dataem>() {
        override fun areItemsTheSame(oldItem: Dataem, newItem: Dataem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Dataem, newItem: Dataem): Boolean {
            return oldItem == newItem
        }
    }
