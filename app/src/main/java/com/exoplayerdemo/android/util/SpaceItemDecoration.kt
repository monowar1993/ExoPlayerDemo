package com.exoplayerdemo.android.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(private val space: Int, private val includeEdge: Boolean = false) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager is LinearLayoutManager) {
            val orientation = (parent.layoutManager as LinearLayoutManager).orientation

            if (orientation == LinearLayoutManager.VERTICAL) {
                addVerticalSpace(outRect, view, parent)
            } else {
                addHorizontalSpace(outRect, view, parent)
            }
        }
    }

    private fun addVerticalSpace(outRect: Rect, view: View, parent: RecyclerView) {
        if (includeEdge) {
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space
            }
            outRect.bottom = space
        } else {
            if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount?.minus(1) ?: 0) {
                outRect.bottom = space
            }
        }
    }

    private fun addHorizontalSpace(outRect: Rect, view: View, parent: RecyclerView) {
        if (includeEdge) {
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.left = space * 2
            }
            outRect.right = space
        } else {
            if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount?.minus(1) ?: 0) {
                outRect.right = space
            }
        }
    }
}
