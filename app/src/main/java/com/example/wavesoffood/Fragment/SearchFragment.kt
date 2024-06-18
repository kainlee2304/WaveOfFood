package com.example.wavesoffood.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.adaptar.MenuAdapter
import com.example.wavesoffood.databinding.FragmentSearchBinding
import androidx.appcompat.widget.SearchView.*
import com.example.wavesoffood.model.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private lateinit var database:FirebaseDatabase
    private val orignalMenuItems= mutableListOf<MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSearchBinding.inflate(inflater,container,false)

        //retrieve  menu Item from database
        retrieveMenuItem()

        setupSearchView()

        showAllMenu()

        return binding.root
    }


    private fun retrieveMenuItem() {
        database=FirebaseDatabase.getInstance()
        //reference  to the menu node
        val foodReference: DatabaseReference =database.reference.child("menu")
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    var menuItems=foodSnapshot.getValue(MenuItem::class.java)
                    menuItems?.let {
                        orignalMenuItems.add(it)
                    }
                }
                showAllMenu()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun showAllMenu() {
        val filterMenuItem=ArrayList(orignalMenuItems)
        setAdapter(filterMenuItem)
    }

    private fun setAdapter(filterMenuItem: List<MenuItem>) {
        adapter=MenuAdapter(filterMenuItem,requireContext())
        binding.menuRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter=adapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(/* listener = */ object : OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItem(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItem(newText)
                return true
            }
        })
    }

    private fun filterMenuItem(query: String) {
        val filteredMenuItems=orignalMenuItems.filter {
            it.foodName?.contains(query, ignoreCase = true)==true
        }
        setAdapter(filteredMenuItems)
    }

}