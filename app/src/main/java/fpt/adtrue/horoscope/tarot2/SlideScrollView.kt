package fpt.adtrue.horoscope.tarot2

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.HorizontalScrollView

class SlideScrollView : HorizontalScrollView {
    private var isSlide = true 
    var mListener: OnScrollListener? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr) {
    }

    interface OnScrollListener {
        fun onScroll(scrollX: Int, scrollY: Int, oldX: Int, oldY: Int)
    }

    fun setScrollChangedListener(listener: OnScrollListener?) {
        mListener = listener
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (mListener != null) {
            mListener!!.onScroll(l, t, oldl, oldt)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        Log.e("SlideScrollView", "$ev")
        return if (isSlide) super.onTouchEvent(ev) else false
    }

    fun setSlide(slide: Boolean) {
        isSlide = slide
    }
}