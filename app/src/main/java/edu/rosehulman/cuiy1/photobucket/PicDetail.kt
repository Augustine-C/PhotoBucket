package edu.rosehulman.cuiy1.photobucket

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
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
class PicDetail : Fragment(),GetImageTask.ImageConsumer {


    private var id : String? = null
    private lateinit var rootView : View



    override fun onImageLoaded(pic: Bitmap?) {
        Log.d(Constants.TAG,"bitmap: " + pic.toString())
        rootView.photo.setImageBitmap(pic)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {bundle->
            id = bundle.getString(ARG_ID)
        }
    }

    override fun onStop() {
        activity!!.fab.show()
        super.onStop()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity!!.fab.hide()
        rootView = inflater.inflate(R.layout.fragment_pic_detail, container, false)
        FirebaseFirestore.getInstance().collection(Constants.PIC_COLLECTION).document(id!!).get().addOnSuccessListener{
                documentSnapshot: DocumentSnapshot ->
            val pic = Pic.fromSnapshot(documentSnapshot)
            Log.d("!!!", pic.toString())
            rootView.title.text = pic.name
            Picasso.with(context).load(pic.url).into(rootView.photo)
//            GetImageTask(this).execute(pic.url)
        }

        return rootView
    }

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
