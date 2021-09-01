package com.nuu.cloudinteractive

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nuu.cloudinteractive.databinding.ListItemThumbnailBinding
import com.nuu.cloudinteractive.model.ThumbnailInfo
import com.nuu.cloudinteractive.viewmodel.ThumbnailViewModel
import java.util.*

class ThumbnailAdapter(private val viewModel: ThumbnailViewModel, private val context: Context): RecyclerView.Adapter<ThumbnailAdapter.ThumbnailHolder>() {
    private var thumbInfoArray: Array<ThumbnailInfo>? = null
    private var thumbInfoAllArray: Array<ThumbnailInfo>? = null
    private var nowCount = 2000

    fun updateThumbInfoAllArray(list: Array<ThumbnailInfo>){
        thumbInfoAllArray = list
        thumbInfoArray = (Arrays.copyOfRange(thumbInfoAllArray,0,nowCount))
        nowCount = thumbInfoArray!!.size
        notifyDataSetChanged()
    }

    fun updateList(){
        if (thumbInfoAllArray!!.size > nowCount + 1000) {
            nowCount += 1000
        }else {
            nowCount = thumbInfoAllArray!!.size
        }
        thumbInfoArray = (Arrays.copyOfRange(thumbInfoAllArray,0,nowCount))
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
