package com.example.wavesoffood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.adaptar.NotificationAdapter
import com.example.wavesoffood.databinding.FragmentNotificationBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class NotificationBottom_Fragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentNotificationBottomBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentNotificationBottomBinding.inflate(layoutInflater,container,false)
        val notifications= listOf("Your order has been Canceled Successfully","Orders has been taken by driver taxi","Congrats your order placed")
        val notiticationImage= listOf(R.drawable.sademoji,R.drawable.truck_driver,R.drawable.congrats)
        val adapter =NotificationAdapter(
            ArrayList(notifications),
            ArrayList(notiticationImage)
        )
        binding.notificationRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.notificationRecyclerView.adapter=adapter
        return binding.root

    }

    companion object {

    }
}