package edu.rosehulman.cuiy1.photobucket

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.pic_view_holder.view.*

class PicViewHolder(itemView: View, val adapter: PiclistAdapter): RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener{
            adapter.selectPic(adapterPosition)
        }
        itemView.setOnLongClickListener{
            adapter.showEditDialog(adapterPosition)
            true
        }
    }
    fun bind(pic : Pic){
        itemView.name.text = pic.name
        itemView.url.text = pic.url
    }

}