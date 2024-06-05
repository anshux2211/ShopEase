package com.example.shopease


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class ListProduct : AppCompatActivity() {

    lateinit var txtHead:TextView
    lateinit var Image:ImageView
    lateinit var edtName:EditText
    lateinit var edtDetail:EditText
    lateinit var edtPrice:EditText
    lateinit var btnSelect:Button
    lateinit var btnUpload:Button
    lateinit var progressBar:ProgressBar
    lateinit var uri:Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_product)

        txtHead=findViewById(R.id.txt_head)
        Image=findViewById(R.id.img_product)
        edtName=findViewById(R.id.edt_Name)
        edtDetail=findViewById(R.id.edt_Detail)
        edtPrice=findViewById(R.id.edt_price)
        btnSelect=findViewById(R.id.btn_selectImg)
        btnUpload=findViewById(R.id.btn_upload)
        progressBar=findViewById(R.id.progress_bar)

        val mSpannableString = SpannableString(txtHead.text)
        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
        txtHead.text=mSpannableString

        btnSelect.setOnClickListener {
            val galleryIntent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent,101)
        }

        btnUpload.setOnClickListener {
            progressBar.visibility=View.VISIBLE
            val image_name:String= UUID.randomUUID().toString()+".jpg"
            val storageRef=FirebaseStorage.getInstance().reference.child("ProductImages/$image_name")
            storageRef.putFile(uri).addOnSuccessListener {
                val result=it.metadata?.reference?.downloadUrl
                if (result != null) {
                    result.addOnSuccessListener {

                        uploadProduct(
                            edtName.text.toString(),
                            edtPrice.text.toString(),
                            edtDetail.text.toString(),
                            it.toString()
                        )
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==101 && resultCode== RESULT_OK){
            uri= data!!.data!!
            Image.setImageURI(uri)
        }
    }

    private fun uploadProduct(name:String,price:String,detail:String,link:String)
    {
        val product:String=UUID.randomUUID().toString()
        Firebase.database.getReference("products").child(product).setValue(
            Product(
                product_name = name,
                product_price = price,
                product_detail = detail,
                product_image = link
            )
        ).addOnSuccessListener {
            progressBar.visibility=View.GONE
            Toast.makeText(this,"Product SuccessFully Added",Toast.LENGTH_SHORT).show()
        }
    }
}

