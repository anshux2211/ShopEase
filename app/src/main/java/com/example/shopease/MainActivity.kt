package com.example.shopease

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import java.util.UUID
import java.util.concurrent.TimeUnit



class MainActivity : AppCompatActivity() {
    private lateinit var rv:RecyclerView
    val listOfProduct= mutableListOf<Product>()
    lateinit var fab:FloatingActionButton
    private lateinit var productAdapter:Product_Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv=findViewById(R.id.rv)
        fab =findViewById(R.id.btn_fab)

        FirebaseDatabase.getInstance().getReference("products")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    listOfProduct.clear();
                    for(dataSnapshot in snapshot.children){
                        val product=dataSnapshot.getValue(Product::class.java)
                        listOfProduct.add(product!!)
                    }
                    productAdapter= Product_Adapter(listOfProduct,this@MainActivity)
                    rv.adapter=productAdapter
                    rv.layoutManager=GridLayoutManager(this@MainActivity,2)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MainActivity,"Failed To Load Data",Toast.LENGTH_SHORT).show()
                }

            })





        fab.setOnClickListener{
                startActivity(
                    Intent(this,Floating_Button::class.java)
                )
        }
    }
}