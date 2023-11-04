package nook.test.market_nookat.ui.fragment.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import nook.test.market_nookat.R
import nook.test.market_nookat.data.model.Category

class AdapterDire(context: Context, resource: Int, private val items: List<Category>) :
    ArrayAdapter<Category>(context, resource, items) {

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                filterResults.values = items
                filterResults.count = items.size
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }
        }
    }

    override fun getItem(position: Int): Category {
        return items[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        val item = getItem(position)

        val text1 = view.findViewById<TextView>(R.id.tv_name)
        val text2 = view.findViewById<TextView>(R.id.tv_id)
        text1.text = item.name
        text2.text = item.id.toString()
        return view
    }
}