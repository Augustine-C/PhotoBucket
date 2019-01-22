package edu.rosehulman.cuiy1.photobucket

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_pic_detail.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_ID = "ARG_ID"
/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PicDetail.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PicDetail.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PicDetail : Fragment() {
    // TODO: Rename and change types of parameters
    private var pic : Pic? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {bundle->
            val id = bundle.getString(ARG_ID)
            pic = PicListWrapper.picList.first {it.id == id}
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pic_detail, container, false)
        view.title.text = pic?.name
        return view
    }



//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     *
//     *
//     * See the Android Training lesson [Communicating with Other Fragments]
//     * (http://developer.android.com/training/basics/fragments/communicating.html)
//     * for more information.
//     */
//    interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        fun onFragmentInteraction(uri: Uri)
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PicDetail.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(id : String) =
            PicDetail().apply {
                arguments = Bundle().apply {
                    putString(ARG_ID, id)
                }
            }
    }
}
