package poyan.poyan.insta.usecase.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import poyan.poyan.insta.databinding.ActivityMainBinding
import poyan.poyan.insta.data.local.DataBase
import poyan.poyan.insta.data.model.Post
import poyan.poyan.insta.data.model.PostComments
import poyan.poyan.insta.domain.Utils
import poyan.poyan.insta.usecase.adapter.AdapterPost
import java.util.UUID
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var post: Post
    private lateinit var db: DataBase
    private lateinit var binding: ActivityMainBinding
    val AllPost = arrayListOf<Post>()

    private lateinit var adapterPost: AdapterPost
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DataBase.getInstance(this)!!

        makeFakeDb()


    }

    private fun makeFakeDb() {
        Thread {
            if (db.postDao().getPosts(0).isEmpty()) {
                val allPost = arrayListOf<Post>()
                var image = ""
                Log.e("rrr", "makeFakeDb: make DB"  )

                for (i in 1..30) {

                    image = if (i % 2 == 0) {
                        "https://www.state.gov/wp-content/uploads/2018/11/Russia-2499x1406.jpg"
                    } else {
                        "https://cdn.pixabay.com/photo/2020/02/24/05/57/moscow-4875274_1280.jpg"
                    }

                    post =
                        Post(
                            i,
                            i * Random.nextInt(30, 300),
                            0,
                            image,
                            "Roozbeh $i"
                        )
                    allPost.add(post)

                    val allComment = arrayListOf<PostComments>()
                    for (j in 1..Random.nextInt(15, 40)) {
                        allComment.add(PostComments(Utils.uuidToInt(UUID.randomUUID()), i, "I like this picture $j"))

                    }
                    db.postCommentsDao().setComment(allComment)

                }

                db.postDao().insertAllPost(allPost)

            }
        }.start()
        Thread {

            db.postDao().getPosts(0).let {
                AllPost.addAll(it)
            }
        }.start()

        initRecycler(AllPost)

    }

    private fun initRecycler(post: ArrayList<Post>) {
        adapterPost = AdapterPost(post, this)
        binding.recyPost.layoutManager = LinearLayoutManager(this)
        binding.recyPost.adapter = adapterPost
    }
}