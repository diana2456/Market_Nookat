package nook.test.market_nookat.ui.fragment.image_sliger

import android.view.LayoutInflater
import android.view.ViewGroup
import com.smarteist.autoimageslider.SliderViewAdapter
import nook.test.market_nookat.databinding.ItemPhotosligerBinding
import nook.test.market_nookat.ui.fragment.util.loadImage

class PhotoSliderAdapter(private val imageUrls: List<String>) : SliderViewAdapter<PhotoSliderAdapter.SliderAdapterVH>(){

    inner class SliderAdapterVH(val binding: ItemPhotosligerBinding) :
        ViewHolder(binding.root) {
        fun onBind(s: String) {
            binding.plo.loadImage(s)
        }
    }

    override fun getCount(): Int = imageUrls.size

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val binding = ItemPhotosligerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderAdapterVH(binding)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH?, position: Int) {
        viewHolder?.onBind(imageUrls[position])
    }
}