package cn.mtjsoft.inputview.extentions

import android.graphics.Rect
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.ceil

/**
 * Simple item decoration allows the application to add a special drawing and layout offset
 * to specific item views from the adapter's data set. Support the grid and linear layout.
 *
 * @see RecyclerView.ItemDecoration
 */
class RecyclerViewItemDecoration(
    @VisibleForTesting(otherwise = PRIVATE)
    internal val spacingPx: Int
) : RecyclerView.ItemDecoration() {

    /**
     * Retrieve any offsets for the given item.
     *
     * @param outRect Rect to receive the output.
     * @param view The child view to decorate
     * @param parent RecyclerView this ItemDecoration is decorating
     * @param state The current state of RecyclerView.
     * @see RecyclerView.ItemDecoration.getItemOffsets
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when (val layoutManager = parent.layoutManager) {
            is GridLayoutManager -> configSpacingForGridLayoutManager(
                outRect = outRect,
                layoutManager = layoutManager,
                position = parent.getChildViewHolder(view).adapterPosition,
                itemCount = state.itemCount
            )
            is LinearLayoutManager -> configSpacingForLinearLayoutManager(
                outRect = outRect,
                layoutManager = layoutManager,
                position = parent.getChildViewHolder(view).adapterPosition,
                itemCount = state.itemCount
            )
        }
    }

    private fun configSpacingForGridLayoutManager(
        outRect: Rect,
        layoutManager: GridLayoutManager,
        position: Int,
        itemCount: Int
    ) {
        val cols = layoutManager.spanCount
//        val rows = ceil(itemCount / cols.toDouble()).toInt()
        // 第一行设置距离顶部 spacingPx，其余行都设为 0
        outRect.top = if (position < cols) spacingPx else 0
        outRect.bottom = spacingPx
        // 如果是最后一列，右间距设为0，否则设为 spacingPx
        outRect.right = if ((position + 1) % cols == 0) 0 else spacingPx
    }

    private fun configSpacingForLinearLayoutManager(
        outRect: Rect,
        layoutManager: LinearLayoutManager,
        position: Int,
        itemCount: Int
    ) {
        val stackFromEnd = layoutManager.stackFromEnd
        if (layoutManager.canScrollHorizontally()) {
            if (stackFromEnd) {
                outRect.left = spacingPx
                outRect.right = if (position == 0) spacingPx else 0
            } else {
                outRect.left = if (position == 0) spacingPx else 0
                outRect.right = spacingPx
            }
        } else if (layoutManager.canScrollVertically()) {
            if (stackFromEnd) {
                outRect.top = spacingPx
                outRect.bottom = if (position == 0) spacingPx else 0
            } else {
                outRect.top = if (position == 0) spacingPx else 0
                outRect.bottom = spacingPx
            }
        }
    }
}
