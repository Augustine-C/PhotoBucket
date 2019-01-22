package edu.rosehulman.cuiy1.photobucket

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp


data class Pic(var name : String = " ", var url : String = " ") {

    @ServerTimestamp var timestamp : Timestamp? = null
    @get:Exclude var id = ""
    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot) : Pic{
            val pic = snapshot.toObject(Pic::class.java)!!
            pic.id = snapshot.id
            return pic
        }
    }
}