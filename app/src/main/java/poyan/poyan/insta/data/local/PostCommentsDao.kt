package poyan.poyan.insta.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import poyan.poyan.insta.data.model.PostComments

@Dao
interface PostCommentsDao {
    @Insert/*(onConflict = OnConflictStrategy.IGNORE)*/
    fun setComment(postComments: ArrayList<PostComments>)

    @Insert
    fun setOneComment(postComments: PostComments)

    @Query("SELECT * FROM postComments WHERE post_id = :postId LIMIT 10 OFFSET :offset")
    fun getComments(postId: Int, offset: Int): List<PostComments>
}