package poyan.poyan.insta.usecase.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import poyan.poyan.insta.R
import poyan.poyan.insta.data.local.DataBase
import poyan.poyan.insta.data.model.Post
import poyan.poyan.insta.databinding.ItemPostBinding
import poyan.poyan.insta.usecase.presentation.CommentActivity

class AdapterPost(
    private val items: ArrayList<Post>,
    private val cnx: Context,
) : RecyclerView.Adapter<AdapterPost.ViewHolder>() {

    val db = DataBase.getInstance(cnx)
    inner class ViewHolder(val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPost.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.binding.txtCaption.text = item.caption
        holder.binding.txtCountLikes.text = "liked by " + item.getLikesCount().toString() + " people"
        Thread{
            holder.binding.txtCountComment.text = db?.postDao()?.commentsCount(item.id!!).toString() + " comment"
        }.start()
        Glide
            .with(cnx)
            .load(item.image)
            .into(holder.binding.imageView)


        if (item.isLike != 0) {
            holder.binding.imgLike.setImageDrawable(cnx.getDrawable(R.drawable.ic_fill_like))
        } else {
            holder.binding.imgLike.setImageDrawable(cnx.getDrawable(R.drawable.ic_like))
        }

        holder.binding.imgLike.setOnClickListener {
            if (item.isLike == 0) {
                item.isLike = 1
                holder.binding.imgLike.setImageDrawable(cnx.getDrawable(R.drawable.ic_fill_like))
                Thread {
                    item.id?.let { it1 -> db?.postDao()?.like(it1) }
                    holder.binding.txtCountLikes.text = "liked by " + db?.postDao()?.likesCounts(item.id!!)!!.toString() + " people"

                }.start()

            } else {
                holder.binding.imgLike.setImageDrawable(cnx.getDrawable(R.drawable.ic_like))
                item.isLike = 0
                Thread {
                    item.id?.let { it1 -> db?.postDao()?.unLike(it1) }
                    holder.binding.txtCountLikes.text = "liked by " + db?.postDao()?.likesCounts(item.id!!)!!.toString() + " people"

                }.start()
            }


        }


        holder.binding.imgComment.setOnClickListener {
            val intent = Intent(cnx, CommentActivity::class.java)

            intent.putExtra("postId", item.id)
            cnx.startActivity(intent)
        }
        holder.binding.txtCountComment.setOnClickListener {
            val intent = Intent(cnx, CommentActivity::class.java)

            intent.putExtra("postId", item.id)
            cnx.startActivity(intent) }


    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}