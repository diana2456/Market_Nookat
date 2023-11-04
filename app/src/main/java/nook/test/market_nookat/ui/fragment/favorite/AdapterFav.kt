package nook.test.market_nookat.ui.fragment.favorite

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
import nook.test.market_nookat.databinding.ItemFavoriteBinding
import nook.test.market_nookat.ui.fragment.home.ClickStateManager
import nook.test.market_nookat.ui.fragment.util.convertDateFormat
import nook.test.market_nookat.ui.fragment.util.loadImage
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class AdapterFav (context: Context, private val click : (Int, Int) -> Unit,private val oneAds: (DataItem) -> Unit) : ListAdapter<DataItem, AdapterFav.AdsHolder>(DiffCallback()) {

    private var img : String = ""

    private val clickStateManager = ClickStateManager(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsHolder {
        return AdsHolder(ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AdsHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AdsHolder(private val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: DataItem) {

            with(binding) {
                val apartmentImages = item.photos
                if (apartmentImages!!.isNotEmpty()) {
                    val firstImage = apartmentImages[0]
                    img = firstImage.full_path
                    ivPhoto.loadImage(img)
                    tvCurrency.text = item.currency.name
                    tvId.text = item.id.toString()
                    val russianLocale = Locale("ru", "RU")
                    val formattedDate = item.createdAt.convertDateFormat(russianLocale)
                    binding.tvData.text = formattedDate
                }
                tvTittle.text = item.content
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val zoneId = ZoneId.systemDefault()
                val currentDate = LocalDate.now(zoneId)
                val parsedTargetDate = LocalDate.parse(item.vip, formatter)

                binding.tvVip.isVisible = !currentDate.isAfter(parsedTargetDate)

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
                    oneAds(item)
                }
                binding.ivHeat.setOnClickListener {
                    // Получаем текущее состояние нажатия из ClickStateManager
                    val itemId = binding.tvId.text.toString().toInt()
                    val isClicked = clickStateManager.isClicked(itemId)
                    if (isClicked) {
                        // Если элемент был в обратном состоянии (R.drawable.heart_favorite), вернем его в состояние R.drawable.heart
                        binding.ivHeat.setImageResource(R.drawable.heart)
                        clickStateManager.setClicked(itemId, false)
                        click(itemId, adapterPosition)
                    } else {
                        // Если элемент был в состоянии R.drawable.heart, устанавливаем его в обратное состояние (R.drawable.heart_favorite)
                        binding.ivHeat.setImageResource(R.drawable.heart_favorite)
                        clickStateManager.setClicked(itemId, true)
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