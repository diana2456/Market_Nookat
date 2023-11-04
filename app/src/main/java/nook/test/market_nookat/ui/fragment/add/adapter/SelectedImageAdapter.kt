package nook.test.market_nookat.ui.fragment.add.adapter
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import nook.test.market_nookat.R
import nook.test.market_nookat.databinding.ItemSelectedImageBinding

class SelectedImageAdapter(
    private val context: Context,
    private val stringArrayList: ArrayList<String>
) : RecyclerView.Adapter<SelectedImageAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemSelectedImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) {
            Glide.with(context)
                .load(url)
                .placeholder(R.color.codeGray)
                .centerCrop()
                .into(binding.photoImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSelectedImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stringArrayList[position])
    }

    override fun getItemCount(): Int = stringArrayList.size
}