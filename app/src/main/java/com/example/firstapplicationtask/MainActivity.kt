package com.example.firstapplicationtask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapplicationtask.data.BeneficiariesParser
import com.example.firstapplicationtask.recyclerAdapter.MyAdapter

/**
 * RecyclerView here to allow for larger data sets.
 * In the event that we have a very large json file, the list will scroll
 * Also added padding to allow the user to scroll past the list to give room
 * to display the popup window properly
 * */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create RecyclerView
        val recyclerView = RecyclerView(this).apply {
            layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.MATCH_PARENT
            )
            // Set the LayoutManager
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        // Add bottom padding to the RecyclerView to allow for scrolling past the list
        recyclerView.setPadding(0, 0, 0, 800)

        // Ensure the RecyclerView respects the padding
        recyclerView.clipToPadding = false

        // Initialize data using the jsonParser
        val data = BeneficiariesParser().parseJsonFile(this)

        // Set the adapter
        recyclerView.adapter = MyAdapter(data, this)

        // Set the RecyclerView as the content view
        setContentView(recyclerView)
    }
}