package nook.test.market_nookat.ui.fragment.add.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import nook.test.market_nookat.databinding.ImagePrikerListBinding
import nook.test.market_nookat.databinding.ItemPhotoGalleryBinding

class ImageAdapter(
    private val context: Context,
    private val imageList: ArrayList<ImageModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            IMAGE_LIST -> {
                val binding = ItemPhotoGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ImageListViewHolder(binding)
            }
            else -> {
                val binding = ImagePrikerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ImagePickerViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < 0) IMAGE_PICKER else IMAGE_LIST
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == IMAGE_LIST) {
            val viewHolder = holder as ImageListViewHolder
            viewHolder.bind(imageList[position])
        } else {
            val viewHolder = holder as ImagePickerViewHolder
            viewHolder.bind(imageList[position])
        }
    }

    override fun getItemCount(): Int = imageList.size


    inner class ImageListViewHolder(private val binding: ItemPhotoGalleryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageModel) {
            Glide.with(context)
                .load(item.image)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(binding.photoImageView)

            binding.circle.isChecked = item.isSelected

            itemView.setOnClickListener { v ->
                onItemClickListener?.onItemClick(adapterPosition, v)
            }
        }
    }

    inner class ImagePickerViewHolder(private val binding: ImagePrikerListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageModel) {
            binding.image.setImageResource(item.resImg)
            binding.title.text = item.title

            itemView.setOnClickListener { v ->
                onItemClickListener?.onItemClick(adapterPosition, v)
            }
        }
    }

    fun setOnItemClickListener(listener:  OnItemClickListener) {
        this.onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, v: View)
    }

    companion object {
        private const val IMAGE_LIST = 0
        private const val IMAGE_PICKER = 1
    }
}
