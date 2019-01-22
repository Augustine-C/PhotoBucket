package edu.rosehulman.cuiy1.photobucket

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_ID = "ARG_ID"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PicList.OnPicSelectedListener] interface
 * to handle interaction events.
 * Use the [PicList.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PicList : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param2: String? = null
    private var listener: OnPicSelectedListener? = null
    var adapter : PiclistAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recyclerView = inflater.inflate(R.layout.fragment_pic_list,container,false) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        if(adapter == null) {
            adapter = PiclistAdapter(activity as Context, listener)
            adapter?.addSnapshotListener()
        }
        recyclerView!!.adapter = adapter

        return recyclerView
    }

    fun onButtonPressed(id : String) {
        listener?.onPicSelected(id)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnPicSelectedListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnPicSelectedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnPicSelectedListener {
        fun onPicSelected(id: String)
    }
}
