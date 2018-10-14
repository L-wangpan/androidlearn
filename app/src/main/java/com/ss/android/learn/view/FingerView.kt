package com.ss.android.learn.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText

import com.ss.android.util.UIUtils

import androidx.appcompat.widget.AppCompatTextView

class FingerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : EditText(context, attrs, defStyle) {
    internal var startX = 0
    internal var startY = 0
    private val mScreenWidth = UIUtils.getScreenWidth(context)
    private val mScreenHeight = UIUtils.getScreenHeight(context)
    private var canSlide = true
    private val TAG = "FingerView"

    init {
        isFocusable = true;
        isFocusableInTouchMode = true;
        requestFocus()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (canSlide.not()) {
            return super.onTouchEvent(event)
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.rawX.toInt()
                startY = event.rawY.toInt()

                Log.d(TAG, "Down")
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG, "Move")
                val moveX = event.rawX.toInt()
                val moveY = event.rawY.toInt()
                val move_bigX = moveX - startX
                val move_bigY = moveY - startY

                var left = left
                var top = top
                left += move_bigX
                top += move_bigY
                val right = left + width
                val bottom = top + height
                layout(left, top, right, bottom)
                startX = moveX
                startY = moveY
            }
            MotionEvent.ACTION_UP -> Log.d(TAG, "UP")
        }
        return true
    }

    override fun layout(l: Int, t: Int, r: Int, b: Int) {

        if (l < 0 || t < 0 || b >= mScreenHeight || r >= mScreenWidth) {
            return
        }
        super.layout(l, t, r, b)
    }

    fun setCustomTextColor(color: Int) {
        setTextColor(color)
    }

    fun setSlideable(slideAble: Boolean) {
        canSlide = slideAble
    }


}
