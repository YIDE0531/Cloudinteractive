package com.nuu.cloudinteractive

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nuu.cloudinteractive.databinding.ActivityThumbnailDetailBinding
import com.nuu.cloudinteractive.model.ThumbnailInfo
import com.nuu.cloudinteractive.viewmodel.ThumbnailDetailViewModel

class ThumbnailDetailActivity : AppCompatActivity() {
    private lateinit var thumbnailDetailViewModel: ThumbnailDetailViewModel
    private lateinit var binding: ActivityThumbnailDetailBinding
    private var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThumbnailDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        thumbnailDetailViewModel = ViewModelProvider(this).get(ThumbnailDetailViewModel::class.java)
        binding.viewModel = thumbnailDetailViewModel
        binding.lifecycleOwner = this
        context = this

        getIntentData()
        thumbnailDetailViewModel.clickEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                finish()
            }
        })
    }

    private fun getIntentData(){
        val thumbnailInfo = intent.getSerializableExtra("ThumbnailInfo") as? ThumbnailInfo
        binding.item = thumbnailInfo
        binding.executePendingBindings()
    }
}