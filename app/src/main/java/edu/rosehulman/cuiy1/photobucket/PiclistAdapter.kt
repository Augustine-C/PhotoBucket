package edu.rosehulman.cuiy1.photobucket

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class PiclistAdapter(): RecyclerView.Adapter<PicViewHolder>() {
    private var picList = ArrayList<Pic>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PicViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return picList.size
    }


    override fun onBindViewHolder(viewHolder: PicViewHolder, pos: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        viewHolder.bind(picList[pos])
    }

    fun showEditDialog(pos: Int) {
        //TODO
    }

    fun showAddDialog(){
        //TODO
    }

    fun randomAdd(){
        //TODO
    }
}