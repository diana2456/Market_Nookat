package nook.test.market_nookat.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import nook.test.market_nookat.data.model.Category
import nook.test.market_nookat.databinding.ItemKindsBinding

class AdapterDirectory(private val click : (Int,String) -> Unit) : ListAdapter<Category, AdapterDirectory.DirectoryHolder>(
    DiffCallback()
) {

    inner class DirectoryHolder(private  val binding : ItemKindsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Category) {
            binding.id.text = item.id.toString()
            binding.title.text = item.name
            itemView.setOnClickListener {
                click(binding.id.text.toString().toInt(),binding.title.text.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectoryHolder {
       return DirectoryHolder(ItemKindsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: DirectoryHolder, position: Int) {
       holder.bind(getItem(position))
    }

    private class DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
}