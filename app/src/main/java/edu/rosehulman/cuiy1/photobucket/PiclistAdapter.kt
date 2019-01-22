package edu.rosehulman.cuiy1.photobucket

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import edu.rosehulman.cuiy1.photobucket.PicListWrapper.picList
import kotlinx.android.synthetic.main.add_edit_dialog.view.*

class PiclistAdapter(
    val context : Context,
    val listener : PicList.OnPicSelectedListener?): RecyclerView.Adapter<PicViewHolder>() {
    private var picListRef = FirebaseFirestore.getInstance().collection(Constants.PIC_COLLECTION)
//    private var picList = ArrayList<Pic>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pic_view_holder,parent,false)
        return PicViewHolder(view,this)
    }

    fun addSnapshotListener(){
        picListRef
            .orderBy("timestamp")
            .addSnapshotListener{snapshot : QuerySnapshot?, firebaseFirestoreException ->
                if(firebaseFirestoreException != null){
                    Log.d(Constants.TAG, "Firebase Error: $firebaseFirestoreException")
                    return@addSnapshotListener
                }
                processSnapshot(snapshot!!)
            }
    }

    private fun processSnapshot(snapshot: QuerySnapshot) {
        for(documentChange in snapshot.documentChanges){
            val pic = Pic.fromSnapshot(documentChange.document)
            when (documentChange.type){
                DocumentChange.Type.ADDED -> {
                    picList.add(0,pic)
                    notifyItemInserted(0)
                }
                DocumentChange.Type.REMOVED -> {
                    val pos = picList.indexOf(pic)
                    picList.removeAt(pos)
                    notifyItemRemoved(pos)
                }
                DocumentChange.Type.MODIFIED -> {
                    val pos = picList.indexOfFirst { pic.id == it.id }
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

    fun showEditDialog(pos : Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Add a pic")
        val view = LayoutInflater.from(context).inflate(R.layout.add_edit_dialog,null,false)
        builder.setView(view)
        view.add_dialog_pic_title.setText(picList[pos].name)
        view.add_dialog_url.setText(picList[pos].url)
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            val title = view.add_dialog_pic_title.text.toString()
            val url = view.add_dialog_url.text.toString()
            edit(pos, title, url)
        }

        builder.setNegativeButton(android.R.string.cancel,null)

        builder.setNeutralButton("Remove"){_,_->
            remove(pos)
        }
        builder.create().show()
    }

    private fun remove(pos : Int){
        picListRef.document(picList[pos].id).delete()
    }


    private fun edit(pos: Int, title: String, url: String) {
        val temp = picList[pos].copy()
        temp.name = title
        temp.url = url
        picListRef.document(picList[pos].id).set(picList[pos])
    }


    fun randomAdd(){
        //TODO
    }

    fun selectPic(pos : Int){
        val id = PicListWrapper.picList[pos].id
        listener?.onPicSelected(id)
    }

}