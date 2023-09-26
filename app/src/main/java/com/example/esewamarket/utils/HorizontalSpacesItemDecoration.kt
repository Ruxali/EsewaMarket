package com.example.esewamarket.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalSpacesItemDecoration(
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
                        left = space
                        right = space / 2
                    }

                    isLast -> {
                        left = space / 2
                        right = space
                    }

                    else -> {
                        left = space / 2
                        right = space / 2
                    }
                }
            }
        }
    }
