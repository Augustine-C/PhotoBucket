package edu.rosehulman.cuiy1.photobucket

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_edit_dialog.view.*

class MainActivity : AppCompatActivity(), PicListFragment.OnPicSelectedListener{


    private lateinit var adapter : PiclistAdapter
    private val picListRef = FirebaseFirestore.getInstance().collection(Constants.PIC_COLLECTION)
    private lateinit var picListFragment: Fragment
    private val titleRef = FirebaseFirestore.getInstance().collection(Constants.TITLE)

    private fun updateAppTitle(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("App's Title")
        val autoEditText = EditText(this)
        autoEditText.setText(title)
        autoEditText.hint = "add a title"
        builder.setView(autoEditText)
        builder.setPositiveButton("ok"){_,_->
            titleRef.document(Constants.TITLE).set(mapOf(Pair(Constants.TITLE,autoEditText.text.toString())))
            Utils.title = autoEditText.text.toString()
        }
        builder.create().show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        titleRef.addSnapshotListener{snapshot : QuerySnapshot?, firebaseFirestoreException ->
            if(firebaseFirestoreException != null){
                Log.w(Constants.TAG, "Firebase Error: $firebaseFirestoreException")
                return@addSnapshotListener
            }
            for(documentChange in snapshot!!.documentChanges){
                val title = (documentChange.document.get(Constants.TITLE)?: " ") as String
                when (documentChange.type){
                    DocumentChange.Type.MODIFIED -> {
                        Log.d("!!!","MODIFY")
                        this.title = title
                        Utils.title = title

                    }
                    DocumentChange.Type.ADDED -> {
                        Log.d("!!!","ADDED TITLE")
                        this.title = title
                        Utils.title = title
                    }
                }
            }

        }

        if(savedInstanceState == null) {
            picListFragment = PicListFragment()
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_holder, picListFragment)
            fab.show()
            ft.commit()
        }
        fab.show()
        fab.setOnClickListener {_ ->
            Log.d("!!!","add clicked")
            showAddDialog()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.set_title -> {
                updateAppTitle()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPicSelected(id: String) {
        fab.hide()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_holder,PicDetail.newInstance(id), "info")
        ft.addToBackStack("list")
        ft.commit()
    }

    fun showAddDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add a ${Utils.title}")
        val view = LayoutInflater.from(this).inflate(R.layout.add_edit_dialog,null,false)
        builder.setView(view)

        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            val title = view.add_dialog_pic_title.text.toString()
            var url = view.add_dialog_url.text.toString()
            if(url == ""){
                url = Utils.randomImageUrl()
            }
            picListRef.add(Pic(title,url))
        }
        builder.setNegativeButton(android.R.string.cancel,null)
        builder.create().show()
    }

}
