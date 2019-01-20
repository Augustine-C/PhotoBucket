package edu.rosehulman.cuiy1.photobucket

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.pic_view_holder.view.*

class PicViewHolder(itemView: View, val adapter: PiclistAdapter): RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener{
            adapter.showEditDialog(adapterPosition)
        }
    }
    fun bind(pic : Pic){
        itemView.name.text = pic.name
        itemView.url.text = pic.url
    }

}