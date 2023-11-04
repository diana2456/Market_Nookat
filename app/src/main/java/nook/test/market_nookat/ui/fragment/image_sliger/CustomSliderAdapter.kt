package nook.test.market_nookat.ui.fragment.image_sliger
import android.view.LayoutInflater
import android.view.ViewGroup
import com.smarteist.autoimageslider.SliderViewAdapter
import nook.test.market_nookat.databinding.ItemSligerBinding
import nook.test.market_nookat.ui.fragment.util.loadImage

class CustomSliderAdapter(private val imageUrls: List<String>, private val onClick: (Int) -> Unit) :
    SliderViewAdapter<CustomSliderAdapter.SliderAdapterVH>() {

    inner class SliderAdapterVH(val binding: ItemSligerBinding) :
        ViewHolder(binding.root) {
        fun onBind(s: String) {
            binding.ivPhoto.loadImage(s)
            binding.ivPhoto.setOnClickListener {
                onClick(0)
            }
        }
    }

    override fun getCount(): Int = imageUrls.size

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val binding = ItemSligerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderAdapterVH(binding)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH?, position: Int) {
        viewHolder?.onBind(imageUrls[position])
    }
}