package poyan.poyan.insta.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Post(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "likesCount") private var likesCount: Int?,
    @ColumnInfo(name = "isLike") var isLike: Int?,
    @ColumnInfo(name = "image") var image: String?,
    @ColumnInfo(name = "caption") var caption: String?
) {
    fun getLikesCount(): Int? {
        return likesCount
    }

    fun setLikesCount(likesCount: Int) {
        this.likesCount = likesCount
    }
}