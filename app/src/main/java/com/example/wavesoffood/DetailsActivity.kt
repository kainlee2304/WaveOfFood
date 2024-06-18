package com.example.wavesoffood

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.wavesoffood.databinding.ActivityDetailsBinding
import com.example.wavesoffood.model.CartItems
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    private lateinit var  binding:ActivityDetailsBinding
    private var foodName:String?=null
    private  var foodPrice:String?=null
    private  var foodDescription:String?=null
    private  var foodImage:String?=null
    private  var foodIngredient:String?=null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialation of Firebase Auth
        auth= FirebaseAuth.getInstance()

        foodName=intent.getStringExtra("MenuItemName")
        foodPrice=intent.getStringExtra("MenuItemPrice")
        foodDescription=intent.getStringExtra("MenuItemDescription")
        foodImage=intent.getStringExtra("MenuItemImage")
        foodIngredient=intent.getStringExtra("MenuItemIngridients")

        with(binding){
            detailsFoodName.text=foodName
            detailDescription.text=foodDescription
            detailIngredients.text=foodIngredient
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(detailFoodImage)


        }

        binding.imageButton.setOnClickListener {
            finish()
        }
        binding.addItemsButton.setOnClickListener {
            addItemCart()
        }

    }
    private fun addItemCart() {
        val database=FirebaseDatabase.getInstance().reference
        val userId=auth.currentUser?.uid?:""

        //create a cartItem object
        val cartItem=CartItems(
            foodName.toString(),
            foodPrice.toString(),
            foodDescription.toString(),
            foodImage.toString(),
            foodQuantity = 1
        )
        //save data to cartItem to  firebase database
        database.child("user").child(userId).child("CartItems").push().setValue(cartItem).addOnSuccessListener {
            Toast.makeText(this, "Items added into  cart successfully \uD83D\uDE04 ", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Item not added \uD83D\uDE13", Toast.LENGTH_SHORT).show()
        }
    }
}