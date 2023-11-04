package nook.test.market_nookat.ui.fragment.add

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import nook.test.market_nookat.databinding.ItemPhotoGalleryBinding

class YourViewPagerAdapter : RecyclerView.Adapter<YourViewPagerAdapter.PhotoViewHolder>() {
    private val list = mutableListOf<Uri>()

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(uris: List<Uri>) {
        list.clear()
        list.addAll(uris)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            ItemPhotoGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class PhotoViewHolder(val binding: ItemPhotoGalleryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photoPart: Uri) {
            Glide.with(binding.photoImageView).load(photoPart).into(binding.photoImageView)
        }
    }
}
