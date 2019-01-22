package edu.rosehulman.cuiy1.photobucket

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_edit_dialog.view.*

class MainActivity : AppCompatActivity(), PicList.OnPicSelectedListener{


    private lateinit var adapter : PiclistAdapter
    private val picList = PicList()
    private val picListRef = FirebaseFirestore.getInstance().collection(Constants.PIC_COLLECTION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_holder,picList)
        ft.commit()
        fab.setOnClickListener {_ ->
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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPicSelected(id: String) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_holder,PicDetail.newInstance(id), "info")
        ft.addToBackStack("list")
        ft.commit()
    }

    fun showAddDialog() {
        //TODO
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add a pic")
        val view = LayoutInflater.from(this).inflate(R.layout.add_edit_dialog,null,false)
        builder.setView(view)

        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            val title = view.add_dialog_pic_title.text.toString()
            val url = view.add_dialog_url.text.toString()
            picListRef.add(Pic(title,url))
        }
        builder.setNegativeButton(android.R.string.cancel,null)
        builder.create().show()
    }

}
