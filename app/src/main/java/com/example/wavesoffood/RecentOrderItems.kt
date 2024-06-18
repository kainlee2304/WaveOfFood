package com.example.wavesoffood

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.adaptar.RecentBuyAdapter
import com.example.wavesoffood.databinding.ActivityRecentOrderItemsBinding
import com.example.wavesoffood.model.OrderDetails

class RecentOrderItems : AppCompatActivity() {
    private val binding:ActivityRecentOrderItemsBinding by lazy{
        ActivityRecentOrderItemsBinding.inflate(layoutInflater)
    }
    private lateinit var allFoodNames:ArrayList<String>
    private lateinit var allFoodPrices:ArrayList<String>
    private lateinit var allFoodImages:ArrayList<String>
    private lateinit var allFoodQuantities:ArrayList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.backButton.setOnClickListener {
            finish()
        }


        val recentOrderItems=intent.getSerializableExtra("RecentBuyOrderItem") as? ArrayList<OrderDetails>
        Log.d("RecentOrderItems", "Received order items: ${recentOrderItems?.size ?: "null"}")

        recentOrderItems?.let {orderDetails ->
            if (orderDetails.isNotEmpty()){
                val recentOrderItem=orderDetails[0]

                allFoodNames = ArrayList(recentOrderItem.foodNames.orEmpty())
                allFoodPrices = ArrayList(recentOrderItem.foodPrices.orEmpty())
                allFoodImages = ArrayList(recentOrderItem.foodImages.orEmpty())
                allFoodQuantities = ArrayList(recentOrderItem.foodQuantiies.orEmpty())
            }
        }
        setAdapter()

    }

    private fun setAdapter() {
        val rv=binding.recyclerViewRecentBuy
        rv.layoutManager=LinearLayoutManager(this)
        val adapter=RecentBuyAdapter(this,allFoodNames,allFoodPrices,allFoodImages,allFoodQuantities)
        rv.adapter=adapter

    }
}