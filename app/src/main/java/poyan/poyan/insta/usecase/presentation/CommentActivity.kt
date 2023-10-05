package poyan.poyan.insta.usecase.presentation

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import poyan.poyan.insta.data.local.DataBase
import poyan.poyan.insta.data.model.PostComments
import poyan.poyan.insta.databinding.ActivityCommentBinding
import poyan.poyan.insta.domain.EndlessRecyclerOnScrollListener
import poyan.poyan.insta.domain.Utils
import poyan.poyan.insta.usecase.adapter.AdapterComments
import java.util.UUID

class CommentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentBinding
    private  var adapterComments: AdapterComments? =null
    private var manager: LinearLayoutManager? = null
    val db = DataBase.getInstance(this)

    val comment = arrayListOf<PostComments>()
    var postId = 1
    var offset = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        postId = intent.getIntExtra("postId", 1)

        initRecycler()
        getData(offset)

        binding.imgSend.setOnClickListener {
            Thread {
                db?.postCommentsDao()?.setOneComment(
                    PostComments(
                        Utils.uuidToInt(UUID.randomUUID()),
                        postId = postId, binding.etGetText.text.toString()
                    )
                )
            }.start()
            initRecycler()
            binding.etGetText.text?.clear()
        }
    }

    private fun initRecycler() {
        manager = LinearLayoutManager(this)
        binding.recyComment.layoutManager = manager
    }

    private fun getData(offset: Int) {

        Thread {
            Log.e("rrr", "commentsCount: " + db?.postDao()?.commentsCount(postId)!! )
            if (db?.postDao()?.commentsCount(postId)!! >=offset) {

                db.postCommentsDao().getComments(postId = postId, offset = offset)?.let { comment.addAll(it) }
            }
            Log.e("rrr", "getData: " + offset )
        }.start()
        showItemsList(comment)

    }


    private fun showItemsList(comment: ArrayList<PostComments>) {

        if (adapterComments == null || offset == 0) {
            adapterComments = AdapterComments(comment, this)
            binding.recyComment.adapter = adapterComments
            binding.recyComment.addOnScrollListener(object :
                EndlessRecyclerOnScrollListener(manager) {
                override fun onLoadMore(current_page: Int) {
                    offset = current_page
                    Log.e("rrr", "onLoadMore: " + offset )
                    getData(offset)


                }
            })
        } else {
            adapterComments?.addAll(comment)
        }
    }
}
