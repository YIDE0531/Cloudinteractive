package com.nuu.cloudinteractive

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nuu.cloudinteractive.databinding.ListItemThumbnailBinding
import com.nuu.cloudinteractive.model.ThumbnailInfo
import com.nuu.cloudinteractive.viewmodel.ThumbnailViewModel

class ThumbnailAdapter(private val viewModel: ThumbnailViewModel, private val context: Context): RecyclerView.Adapter<ThumbnailAdapter.ThumbnailHolder>() {
    var thumbInfoArray: Array<ThumbnailInfo>? = viewModel.thumbInfoArray.value

    fun updateList(list: Array<ThumbnailInfo>){
        thumbInfoArray = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ThumbnailAdapter.ThumbnailHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemThumbnailBinding.inflate(layoutInflater, parent, false)
        return ThumbnailHolder(binding)
    }

    override fun onBindViewHolder(holder: ThumbnailAdapter.ThumbnailHolder, position: Int) {
        if (!thumbInfoArray.isNullOrEmpty()) {
            holder.bind(viewModel, thumbInfoArray!![position])
        }
    }

    override fun getItemCount(): Int {
        return if (thumbInfoArray == null) 0 else thumbInfoArray!!.size
    }

    inner class ThumbnailHolder(private val binding: ListItemThumbnailBinding): RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(viewModel: ThumbnailViewModel, data: ThumbnailInfo?){
            binding.viewModel = viewModel
            binding.item = data
            binding.executePendingBindings()

            binding.constMain.setOnClickListener {
                val intent = Intent(context, ThumbnailDetailActivity::class.java)
                intent.putExtra("ThumbnailInfo", data)
                context.startActivity(intent)
            }
        }
    }
}
