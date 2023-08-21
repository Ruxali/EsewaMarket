package com.example.esewamarket.helper

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

internal enum class ButtonsState {
    GONE, RIGHT_VISIBLE
}

class CartSwipeHelper: ItemTouchHelper.Callback() {

    private var swipeBack = false
    private var buttonShowedState = ButtonsState.GONE
    private val buttonWidth = 300f

    private var currentItemViewHolder: ViewHolder? = null

    private var buttonsActions:CartItemSwipeController?=null
    var buttonInstance: RectF? = null


    @SuppressLint("NotConstructor")
    fun cartSwipeHelperFunction(buttonsActions: CartItemSwipeController?) {
        this.buttonsActions = buttonsActions
    }



    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: ViewHolder
    ): Int {
        return makeMovementFlags(0,ItemTouchHelper.LEFT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean {
       return false
    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {

    }

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if (swipeBack) {
            swipeBack = buttonShowedState != ButtonsState.GONE
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        var dX = dX
        if (actionState == ACTION_STATE_SWIPE) {
            if (buttonShowedState != ButtonsState.GONE) {
                if (buttonShowedState == ButtonsState.RIGHT_VISIBLE)
                    dX = dX.coerceAtMost(-buttonWidth)
                    viewHolder.let {
                        super.onChildDraw(
                            c,recyclerView, it, dX, dY, actionState, isCurrentlyActive
                        )
                    }
            } else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        if (buttonShowedState == ButtonsState.GONE) {
            viewHolder.let {
                super.onChildDraw(
                    c, recyclerView, it, dX, dY, actionState, isCurrentlyActive
                )
            }
        }
        currentItemViewHolder = viewHolder
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        recyclerView.setOnTouchListener { _, event ->
            swipeBack =
                event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP
            if (swipeBack) {
                if (dX < -buttonWidth) {
                    buttonShowedState = ButtonsState.RIGHT_VISIBLE
                }

                if (buttonShowedState != ButtonsState.GONE) {
                    setTouchDownListener(
                        c,
                        recyclerView,
                        viewHolder,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                    setItemsClickable(recyclerView, false)
                }
            }
            return@setOnTouchListener false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchDownListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        dY: Float, actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        recyclerView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                setTouchUpListener(
                    c,
                    recyclerView,
                    viewHolder,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
            false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchUpListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        recyclerView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                super@CartSwipeHelper.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    0f,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                recyclerView.setOnTouchListener { _, event -> false }
                setItemsClickable(recyclerView, true)
                swipeBack = false

                if (buttonsActions != null && buttonInstance != null && buttonInstance!!.contains(
                        event.x,
                        event.y
                    )
                ) {
                    if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
                        buttonsActions!!.onRightClicked(viewHolder.adapterPosition)
                    }
                    swipeBack = true
                    buttonShowedState = ButtonsState.GONE
                }
                buttonShowedState = ButtonsState.GONE
                currentItemViewHolder = null

            }

            false
        }

    }


    private fun setItemsClickable(
        recyclerView: RecyclerView,
        isClickable: Boolean
    ) {
        for (i in 0 until recyclerView.childCount) {
            recyclerView.getChildAt(i).isClickable = isClickable
        }
    }


    private fun drawButtons(c: Canvas, viewHolder: ViewHolder) {
        val buttonWidthWithoutPadding = buttonWidth - 16
        val corners = 40f
        val itemView = viewHolder.itemView
        val p = Paint()


        val rightButton = RectF(
            itemView.right - buttonWidthWithoutPadding,
            itemView.top.toFloat() + 40,
            itemView.right.toFloat() - 50,
            itemView.bottom.toFloat() - 26
        )
        p.color = Color.RED
        c.drawRoundRect(rightButton, corners, corners, p)
        drawText("DELETE", c, rightButton, p)


        if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
            buttonInstance = rightButton
        }
    }

    private fun drawText(value: String, c: Canvas, button: RectF, p: Paint) {
        val textSize = 40f
        p.color = Color.WHITE
        p.isAntiAlias = true
        p.textSize = textSize
        val textWidth: Float = p.measureText(value)
        c.drawText(value, button.centerX() - textWidth / 2, button.centerY() + textSize / 2, p)
    }

    fun onDraw(c: Canvas?) {
        currentItemViewHolder?.let { drawButtons(c!!, it) }
    }


}