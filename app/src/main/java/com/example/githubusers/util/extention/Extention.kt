package com.example.githubusers.util.extention

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun View.visibilityLoading(isLoading : Boolean, container : View){
    if (isLoading){
        this.visibility = View.VISIBLE
        container.visibility = View.GONE
    }else{
        this.visibility = View.GONE
        container.visibility = View.VISIBLE
    }
}


fun RecyclerView.setupRecyclerView(layoutManager: RecyclerView.LayoutManager, adapter: RecyclerView.Adapter<*>){
    this.layoutManager = layoutManager
    this.adapter = adapter
    this.setHasFixedSize(true)

}