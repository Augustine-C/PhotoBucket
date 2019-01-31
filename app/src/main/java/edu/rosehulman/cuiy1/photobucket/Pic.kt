package edu.rosehulman.cuiy1.photobucket

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp


data class Pic(var name: String = " ", var url: String = " ",var uid : String = " ") {

    @ServerTimestamp
    var timestamp: Timestamp? = null
    @get:Exclude
    var id = ""

    companion object {
        const val TIMESTAMP = "timestamp"
        fun fromSnapshot(snapshot: DocumentSnapshot): Pic {
            Log.d("!!!", snapshot.data.toString())
            val pic = snapshot.toObject(Pic::class.java)!!
            pic.id = snapshot.id
            return pic
        }
    }

    override fun toString(): String {
        return id + " " + name + " " + url
    }
}