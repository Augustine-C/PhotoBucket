package edu.rosehulman.cuiy1.photobucket

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.add_edit_dialog.view.*

class PiclistAdapter(
    val context: Context,
    val listener: PicListFragment.OnPicSelectedListener?,
    val uid: String
) : RecyclerView.Adapter<PicViewHolder>() {

    lateinit var registration: ListenerRegistration
    var picList = ArrayList<Pic>()
    var picListRef = FirebaseFirestore
        .getInstance()
        .collection(Constants.PIC_COLLECTION)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pic_view_holder, parent, false)
        return PicViewHolder(view, this)
    }

    fun addSnapshotListener() {
        Log.d("!!!", "add snapshotlistener ${picList}")
        registration = picListRef
            .orderBy(Pic.TIMESTAMP)
            .addSnapshotListener { snapshot: QuerySnapshot?, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.w(Constants.TAG, "Firebase Error: $firebaseFirestoreException")
                    return@addSnapshotListener
                }
                processSnapshot(snapshot!!)
                Log.d("!!!", "get new snapshot ${picList} ")
            }
//        Log.d("!!!", "add snapshotlistener ${PicListWrapper.picList} ${PicListWrapper.picList[0].id}")
    }

    fun removeSnapshotListener() {
        Log.d("!!!", "Remove snapshotlistener")
        registration.remove()
    }


    private fun processSnapshot(snapshot: QuerySnapshot) {
        for (documentChange in snapshot.documentChanges) {
            val pic = Pic.fromSnapshot(documentChange.document)
            when (documentChange.type) {
                DocumentChange.Type.ADDED -> {
                    Log.d("!!!", "ADDED")
                    picList.add(0, pic)
                    notifyItemInserted(0)
                }
                DocumentChange.Type.REMOVED -> {
                    Log.d("!!!", "REMOVE ${pic.id}")
                    val pos = picList.indexOfFirst { it.id == pic.id }
                    picList.removeAt(pos)
                    notifyItemRemoved(pos)
                }
                DocumentChange.Type.MODIFIED -> {
                    val pos = picList.indexOfFirst { it.id == pic.id }
                    Log.d("!!!", "MODIFY" + pos.toString())
                    picList[pos] = pic
                    notifyItemChanged(pos)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return picList.size
    }


    override fun onBindViewHolder(viewHolder: PicViewHolder, pos: Int) {
        viewHolder.bind(picList[pos])
    }

    fun showEditDialog(pos: Int) {
        if(uid != picList[pos].uid){
            showDiffUser()
        } else {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Edit a ${Utils.title}")
            val view = LayoutInflater.from(context).inflate(R.layout.add_edit_dialog, null, false)
            builder.setView(view)
            view.add_dialog_pic_title.setText(picList[pos].name)
            view.add_dialog_url.setText(picList[pos].url)
            builder.setPositiveButton(android.R.string.ok) { _, _ ->
                val title = view.add_dialog_pic_title.text.toString()
                val url = view.add_dialog_url.text.toString()
                edit(pos, title, url)
            }

            builder.setNegativeButton(android.R.string.cancel, null)

            builder.setNeutralButton("Remove") { _, _ ->
                remove(pos)
            }
            builder.create().show()
        }
    }

    fun showDiffUser(){
        Toast.makeText(context,
            "This pic belongs to another user",
            Toast.LENGTH_LONG
        ).show()

    }

    fun remove(pos: Int) {
        picListRef.document(picList[pos].id).delete()
    }


    fun edit(pos: Int, title: String, url: String) {
        Log.d("!!!", "edit call")
        val temp = picList[pos].copy()
        temp.name = title
        temp.url = url
        picListRef.document(picList[pos].id).set(temp)
    }


    fun randomAdd() {
        //TODO
    }

    fun selectPic(pos: Int) {
        val id = picList[pos].id
        listener?.onPicSelected(id)
    }

}