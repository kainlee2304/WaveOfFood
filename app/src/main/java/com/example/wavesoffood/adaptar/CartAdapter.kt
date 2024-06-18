package com.example.wavesoffood.adaptar

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wavesoffood.databinding.CartItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CartAdapter(
    private val context:Context,
    private val cartItems:MutableList<String>,
    private val cartItemPrices:MutableList<String>,
    private val cartImages: MutableList<String>,
    private val cartDescriptions:MutableList<String>,
    private val cartIngredient:MutableList<String>,
    private val cartQuatity:MutableList<Int>,


) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(){
    // instance Firebase
    private val auth= FirebaseAuth.getInstance()

    //Initialation of Firebase
    init {
        val database= FirebaseDatabase.getInstance()
        val userId=auth.currentUser?.uid?:""
        val cartItemNumber=cartItems.size
        itemQuantities= IntArray(cartItemNumber){1}
        cartItemsReference=database.reference.child("user").child(userId).child("CartItems")
    }
    companion object{
        private var itemQuantities:IntArray= intArrayOf()
        private lateinit var cartItemsReference:DatabaseReference
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(from(parent.context),parent,false)
        return CartViewHolder(binding)
    }



    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }


    override fun getItemCount(): Int = cartItems.size

    //get updated quantity
    fun getUpdatedItemsQuantities(): MutableList<Int> {
        val itemQuantity= mutableListOf<Int>()
        itemQuantity.addAll(cartQuatity)
        return itemQuantity
    }


    inner class CartViewHolder(private val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                cartFoodName.text = cartItems[position]
                cartItemPrice.text= cartItemPrices[position]
                //load image using Glide
                val  uriString=cartImages[position]
                val uri=Uri.parse(uriString)
                Glide.with(context).load(uri).into(cartImage)
                catItemQuantity.text = quantity.toString()

                minusbutton.setOnClickListener{
                    deceaseQuantity(position)
                }
                plusebutton.setOnClickListener{
                    inceaseQuantity(position)
                }

                deleteButton.setOnClickListener {
                    val itemPositon = adapterPosition
                    if (itemPositon !=RecyclerView.NO_POSITION){
                        deleteItem(itemPositon)
                    }
                }

            }
        }
        private fun inceaseQuantity(position: Int){
            if (itemQuantities[position]<10){
                itemQuantities[position]++
                cartQuatity[position]= itemQuantities[position]
                binding.catItemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun deceaseQuantity(position: Int){
            if (itemQuantities[position]>1){
                itemQuantities[position]--
                cartQuatity[position]= itemQuantities[position]
                binding.catItemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun deleteItem(position: Int){
         //  val positionRetrieve= position
            getUniqueKeyAtPosition(position) { uniqueKey ->
                if (uniqueKey != null) {
                    removeItem(position, uniqueKey)
                }
            }
        }

        private fun removeItem(position: Int, uniqueKey: String) {
            if (uniqueKey!=null){
                cartItemsReference.child(uniqueKey).removeValue().addOnSuccessListener {
                    cartItems.removeAt(position)
                    Toast.makeText(context, "Item  deleted", Toast.LENGTH_SHORT).show()

                    //update item quatity
                    itemQuantities= itemQuantities.filterIndexed{index, i -> index!=position }.toIntArray()

                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position,cartItems.size )
                }.addOnFailureListener {
                     Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun getUniqueKeyAtPosition(positionRetrieve: Int, onComplete:(String?)->Unit) {
            cartItemsReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var uniqueKey:String?=null
                    //loop for snapshot children
                    snapshot.children.forEachIndexed{index, dataSnapshot ->
                        if (index==positionRetrieve){
                            uniqueKey=dataSnapshot.key
                            return@forEachIndexed
                        }
                    }
                    onComplete(uniqueKey)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        }

    }

}