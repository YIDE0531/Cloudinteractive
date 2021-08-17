package com.nuu.cloudinteractive

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
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

        thumbnailViewModel.thumbInfoArray.observe(this, {
            thumbnailAdapter.updateList(it)
        })
    }
}