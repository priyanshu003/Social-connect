package com.example.social_connect

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.social_connect.daos.PostDao
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_creat_post.*

class CreatPostActivity : AppCompatActivity() {

    private lateinit var postDao: PostDao
    lateinit var filePath: Uri
    private var databaseReference: DatabaseReference? = null
    private var storageReference: StorageReference? = null

    var imageUrl: Uri? = null
//    lateinit var imageFilePath: String
//    private val PICK_IMAGE_REQUEST = 71
//    private var filePath: Uri? = null
//    private var firebaseStore: FirebaseStorage? = null
//    private var storageReference: StorageReference? = null

    private val TAG = "StorageActivity"
    //track Choosing Image Intent
//    private val CHOOSING_IMAGE_REQUEST = 1234
//
//    private var imageUri: Uri? = null
//    private var bitmap: Bitmap? = null
//    private var imageReference: StorageReference? = null
//    private lateinit var u:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creat_post)
        databaseReference = FirebaseDatabase.getInstance().getReference("User_Details")
        storageReference = FirebaseStorage.getInstance().reference
//        firebaseStore = FirebaseStorage.getInstance()
//        storageReference = FirebaseStorage.getInstance().reference
        postDao = PostDao()

//        chooseButton.setOnClickListener{
//            launchGallery()
//        }
        chooseButton.setOnClickListener() {
            getPicture()
        }


        postButton.setOnClickListener {
            val input = postInput.text.toString().trim()


            val ref = storageReference!!.child(

                    "upload/" + System.currentTimeMillis() + "." + getFileExt(filepath = filePath)
            )

            ref.putFile(filePath).addOnSuccessListener { taskSnapshot ->

                val data = taskSnapshot.storage.downloadUrl

                while (!data.isSuccessful);
                imageUrl = data.result
                val imageFilePath = imageUrl.toString()
                if (input.isNotEmpty()) {
                    postDao.addPost(input, imageFilePath)
                    finish()
                }
//                val product = Post.post()
//                product.url = imageFilePath
//                val childId = databaseReference!!.push().key
//                databaseReference!!.child(childId!!).setValue(product)
//                Toast.makeText(
//                        this@MainActivity,
//                        "data uploaded successfully",
//                        Toast.LENGTH_SHORT
//                ).show()
            }.addOnFailureListener { e ->
                //  Toast.makeText(this@MainActivity, "" + e.message, Toast.LENGTH_SHORT).show()
            }


            //            imageUri?.let { uploadImageToFirebase(it) }
//              val url: String = imageUri.toString()
//            if(input.isNotEmpty()) {
//                postDao.addPost(input,imageFilePath)
//                finish()
//            }
        }

        setUpRecyclerView()
    }
//    private fun launchGallery() {
////        val intent = Intent()
////        intent.type = "image/*"
//      //  intent.action = Intent.ACTION_GET_CONTENT
//    //    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//       startActivityForResult(intent, REQUEST_CODE)
////        val intent = Intent()
////        intent.type = "image/*"
////        intent.action = Intent.ACTION_GET_CONTENT
////        startActivityForResult(Intent.createChooser(intent, "Select Image"), CHOOSING_IMAGE_REQUEST)
//    }

    private fun setUpRecyclerView() {

    }
//    companion object {
//        private val REQUEST_CODE = 0
//        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
//    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
//            if (data != null) {
//                imageUri = data.data
//            }
//            imgvView.setImageURI(imageUri) // handle chosen image
//
//        }
//    }
//    fun uploadImageToFirebase(fileUri: Uri) {
//        if (fileUri != null) {
//            val fileName = UUID.randomUUID().toString() +".jpg"
//
//            val database = FirebaseDatabase.getInstance()
//            val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")
//
//            refStorage.putFile(fileUri)
//                    .addOnSuccessListener(
//                            OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
//                                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
//                                     imageUrl = it.toString()
//                                }
//                            })
//
//                    ?.addOnFailureListener(OnFailureListener { e ->
//                        print(e.message)
//                    })
//        }
//    }

    fun uploadPic() {

    }


//
//    private fun addUploadRecordToDb(uri: String){
//        val db = FirebaseFirestore.getInstance()
//
//        val data = HashMap<String, Any>()
//        data["imageUrl"] = uri
//
//        db.collection("post")
//                .add(data)
//                .addOnSuccessListener { documentReference ->
//                    Toast.makeText(this, "Saved to DB", Toast.LENGTH_LONG).show()
//                }
//                .addOnFailureListener { e ->
//                    Toast.makeText(this, "Error saving to DB", Toast.LENGTH_LONG).show()
//                }
//    }

    private fun getPicture() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(i, 0)
    }

    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            filePath = data?.data!!
            //Toast.makeText(this,""+filePath,Toast.LENGTH_SHORT).show()
            imgvView.setImageURI(filePath)
        } else {
            Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getFileExt(filepath: Uri): String? {
        val cr = contentResolver
        val map = MimeTypeMap.getSingleton()

        return map.getExtensionFromMimeType(cr.getType(filepath))
    }


}
