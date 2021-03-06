package cn.mtjsoft.inputview.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cn.mtjsoft.inputview.R
import cn.mtjsoft.inputview.entity.FunctionEntity
import cn.mtjsoft.inputview.iml.AdapterItemClickListener

class FuncationAdapter(
    private val context: Context,
    private val data: List<FunctionEntity>,
    private val clickListener: AdapterItemClickListener
) :
    RecyclerView.Adapter<FuncationAdapter.FuncationViewHolder>() {

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FuncationAdapter.FuncationViewHolder = FuncationViewHolder(
        LayoutInflater.from(context)
            .inflate(R.layout.item_funcation, parent, false)
    )

    fun getData(): List<FunctionEntity> = data

    @SuppressLint("NotifyDataSetChanged")
    fun notifyDataChanged() {
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: FuncationAdapter.FuncationViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val bean = data[position]
        holder.imageView.setImageResource(bean.imgResId)
        holder.nameView.text = bean.name
        holder.nameView.setTextColor(ContextCompat.getColor(context, bean.textColorResId))
        holder.nameView.setTextSize(TypedValue.COMPLEX_UNIT_SP, bean.textSizeSp)
        holder.itemLayout.setOnClickListener {
            clickListener.onItemClick(it, position)
        }
    }

    inner class FuncationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemLayout: LinearLayout = view.findViewById(R.id.itemLayout)
        var imageView: ImageView = view.findViewById(R.id.iv_item)
        var nameView: TextView = view.findViewById(R.id.tv_name)
    }
}