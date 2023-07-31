package com.example.esewamarket

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalSpaceItemDecoration (
    private val space: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            val position = parent.getChildAdapterPosition(view)
            val isLast = position == state.itemCount - 1
            when {
                position == 0 -> {
                    top = space
                    bottom = space / 2
                }

                isLast -> {
                    top = space / 2
                    bottom = space
                }

                else -> {
                    top = space / 2
                    bottom = space / 2
                }
            }
        }
    }
}
