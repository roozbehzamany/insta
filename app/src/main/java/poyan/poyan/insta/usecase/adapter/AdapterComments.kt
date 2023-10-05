package poyan.poyan.insta.usecase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import poyan.poyan.insta.data.local.DataBase
import poyan.poyan.insta.data.model.PostComments
import poyan.poyan.insta.databinding.ItemCommentBinding

class AdapterComments(
    private val items: ArrayList<PostComments>,
    private val cnx: Context
) : RecyclerView.Adapter<AdapterComments.ViewHolder>() {


    val db = DataBase.getInstance(cnx)

    inner class ViewHolder(val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterComments.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCommentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterComments.ViewHolder, position: Int) {
        holder.binding.txtCaption.text = items[position].comment

    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return position
    }


    private fun add(item: PostComments) {
        items.add(item)
        notifyItemInserted(items.size)
    }

    fun addAll(itemsList:  ArrayList<PostComments>) {
//        items.clear()
//        items.addAll(itemsList)
//        for (item in itemsList) {
//            add(item)
//        }
        items.addAll(itemsList)
        notifyDataSetChanged()
    }

}