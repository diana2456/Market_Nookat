package nook.test.market_nookat.ui.fragment.home

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import nook.test.market_nookat.R
import nook.test.market_nookat.data.model.DataItem
import nook.test.market_nookat.databinding.ItemAdsBinding
import nook.test.market_nookat.ui.fragment.util.Pref
import nook.test.market_nookat.ui.fragment.util.convertDateFormat
import nook.test.market_nookat.ui.fragment.util.loadImage
import nook.test.market_nookat.ui.fragment.util.showToast
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class AdapterAds(private val context: Context,
    private val clickFav: (Int, Boolean) -> Unit,
    private val delete: (Int) -> Unit,
    private val click: (DataItem) -> Unit
) : ListAdapter<DataItem, AdapterAds.AdsHolder>(DiffCallback()) {

    private val clickStateManager = ClickStateManager(context)

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
        fun bind(item: DataItem) {
            with(binding) {
                val apartmentImages = item.photos
                if (apartmentImages != null) {
                    if (apartmentImages.isNotEmpty()) {
                        val firstImage = apartmentImages[0]
                        val img = firstImage.full_path
                        ivPhoto.loadImage(img)
                        tvId.text = item.id.toString()
                        val russianLocale = Locale("ru", "RU")
                        val formattedDate = item.createdAt.convertDateFormat(russianLocale)
                        binding.tvData.text = formattedDate
                    }
                    Log.i("dfvrgtf", "bind:${item.vip}")
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


                    val itemId = item.id
                    val isClicked = clickStateManager.isClicked(itemId)
                    itemView.setOnClickListener {
                        click(item)
                    }
                    ivHeat.setImageResource(if (isClicked) R.drawable.heart_favorite else R.drawable.heart)

                         ivHeat.setOnClickListener {
                            val isClicked = clickStateManager.isClicked(itemId)
                            if (isClicked) {
                                // Если элемент был в "R.drawable.heart_favorite", вернем его в исходное состояние "R.drawable.heart"
                                ivHeat.setImageResource(R.drawable.heart)
                                delete(item.id)
                            } else {
                                // Если элемент был в исходном состоянии "R.drawable.heart", переключим его в "R.drawable.heart_favorite"
                                ivHeat.setImageResource(R.drawable.heart_favorite)
                                clickFav(item.id, true)
                            }
                            clickStateManager.setClicked(itemId, !isClicked)
                            notifyItemChanged(adapterPosition)
                         }
                }
            }
        }

    }


    private class DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }
}