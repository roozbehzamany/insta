package poyan.poyan.insta.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import poyan.poyan.insta.data.model.Post

@Dao
interface PostDao {
    @Query("UPDATE post SET isLike = 1 , likesCount = likesCount+1 WHERE id = :id")
    fun like(id: Int)

    @Query("UPDATE post SET isLike = 0 , likesCount = likesCount-1 WHERE id = :id")
    fun unLike(id: Int)

    @Query("SELECT LikesCount FROM POST WHERE id = :id")
    fun likesCounts(id: Int): Int

    @Query("SELECT count(*) FROM postComments WHERE post_id = :postId")
    fun commentsCount(postId: Int): Int

    @Query("SELECT * FROM post LIMIT 10 OFFSET :offset")
    fun getPosts(offset: Int): List<Post>

    @Insert
    fun insertPost(post: Post)

    @Insert
    fun insertAllPost(post: ArrayList<Post>)


}