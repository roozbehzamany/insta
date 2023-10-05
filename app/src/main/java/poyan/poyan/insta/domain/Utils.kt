package poyan.poyan.insta.domain

import android.content.Intent
import android.os.Build
import java.io.Serializable
import java.util.UUID

object Utils {


    fun uuidToInt(uuid: UUID): Long {
        val mostSigBits = uuid.mostSignificantBits
        val leastSigBits = uuid.leastSignificantBits

        // Combine mostSignificantBits and leastSignificantBits into a single long
        return mostSigBits.shl(32) or (leastSigBits and 0xFFFFFFFF)
    }




}