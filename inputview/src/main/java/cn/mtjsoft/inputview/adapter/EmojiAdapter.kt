package cn.mtjsoft.inputview.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import cn.mtjsoft.inputview.R
import cn.mtjsoft.inputview.entity.EmojiEntry
import cn.mtjsoft.inputview.iml.AdapterItemClickListener
import com.bumptech.glide.Glide

class EmojiAdapter(
    private val context: Context,
    private val wh: Int,
    private val data: List<EmojiEntry>,
    private val clickListener: AdapterItemClickListener
) :
    RecyclerView.Adapter<EmojiAdapter.EmojiViewHolder>() {

    private lateinit var layoutParams: ViewGroup.LayoutParams

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmojiAdapter.EmojiViewHolder {
        layoutParams = ViewGroup.LayoutParams(wh, wh)
        return EmojiViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_emoji, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: EmojiAdapter.EmojiViewHolder,
        position: Int
    ) {
        holder.itemLayout.layoutParams = layoutParams
        holder.itemLayout.setOnClickListener {
            clickListener.onItemClick(it, position)
        }
        Glide.with(context).load(Uri.parse("file:///android_asset/" + data[position].src))
            .into(holder.imageView)
    }

    inner class EmojiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemLayout: RelativeLayout = view.findViewById(R.id.itemLayout)
        var imageView: ImageView = view.findViewById(R.id.iv_item)
    }
}