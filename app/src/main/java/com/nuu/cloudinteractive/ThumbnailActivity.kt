package com.nuu.cloudinteractive

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nuu.cloudinteractive.databinding.ActivityThumbnailBinding
import com.nuu.cloudinteractive.viewmodel.ThumbnailViewModel

class ThumbnailActivity : AppCompatActivity() {
    private lateinit var thumbnailViewModel: ThumbnailViewModel
    private lateinit var binding: ActivityThumbnailBinding
    private lateinit var context: Context
    private lateinit var thumbnailAdapter: ThumbnailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThumbnailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        thumbnailViewModel = ViewModelProvider(this).get(ThumbnailViewModel::class.java)
        binding.viewModel = thumbnailViewModel
        binding.lifecycleOwner = this
        context = this

        thumbnailAdapter = ThumbnailAdapter(thumbnailViewModel, context)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = thumbnailAdapter
        }

        thumbnailViewModel.thumbInfoAllArray.observe(this, {
            thumbnailAdapter.updateThumbInfoAllArray(it)
            setRecyclerViewScrollListener()
        })
    }

    private fun setRecyclerViewScrollListener() {
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!binding.recyclerView.canScrollVertically(1)) {
                        thumbnailAdapter.updateList()
                        Snackbar.make(binding.recyclerView, "加載中請稍後", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.recyclerView.addOnScrollListener(scrollListener)
    }
}