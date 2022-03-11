package cn.mtjsoft.inputview.extentions

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

val RecyclerView.gridLayoutManager: GridLayoutManager?
    get() = layoutManager as? GridLayoutManager

val RecyclerView.linearLayoutManager: LinearLayoutManager?
    get() = layoutManager as? LinearLayoutManager

fun RecyclerView.setItemDecorationSpacing(spacingPx: Int) {
    addItemDecoration(
        RecyclerViewItemDecoration(spacingPx)
    )
}