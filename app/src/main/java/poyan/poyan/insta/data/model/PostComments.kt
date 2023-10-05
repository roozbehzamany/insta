package poyan.poyan.insta.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class PostComments (
    @PrimaryKey(autoGenerate = true)
    val postCommentId: Long?,

    @ColumnInfo(name = "post_id")
    var postId: Int,

    @ColumnInfo(name = "comment")
    var comment: String
    ){



}