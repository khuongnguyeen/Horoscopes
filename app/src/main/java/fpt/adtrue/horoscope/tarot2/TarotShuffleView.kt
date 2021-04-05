package fpt.adtrue.horoscope.tarot2

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.custom.AnimHelper

class TarotShuffleView : FrameLayout {
    private var mContext: Context? = null
    private var translate = 20
    private var totalCardCount = 10
    private var mListener: OnShuffleViewListener? = null
    private var mInflater: LayoutInflater? = null

    constructor(context: Context) : super(context, null) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context,
        attrs,
        0) {
        initView(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
    ) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    fun setOnShuffleListener(listener: OnShuffleViewListener?) {
        mListener = listener
    }

    private fun initView(context: Context) {
        mContext = context
        mInflater = LayoutInflater.from(getContext())
        for (i in 0 until totalCardCount) {
            val view: View = mInflater!!.inflate(R.layout.item_shuffle_view, this, false)
            val cardView = view.findViewById<ImageView>(R.id.card_view)
            val width = ScreenUtil.dip2px(mContext!!, Constant.CARD_ZOOM_IN_WIDTH.toFloat())
            val height = ScreenUtil.dip2px(mContext!!, Constant.CARD_ZOOM_IN_HEIGHT.toFloat())
            val p = LayoutParams(width, height)
            p.topMargin = ScreenUtil.dip2px(mContext!!, Constant.CARD_TOP_MARGIN.toFloat())
            p.gravity = Gravity.CENTER_HORIZONTAL
            cardView.layoutParams = p
            cardView.visibility = View.GONE
            if (i in 0..4) {
                cardView.translationX = (translate * (4 - i)).toFloat()
                cardView.translationY = (translate * (4 - i)).toFloat()
                cardView.visibility = View.VISIBLE
            }
            this.addView(view)
        }
    }

    fun anim() {
        for (i in 0 until childCount) {
            val view: View = getChildAt(i)
            val cardView = view.findViewById<ImageView>(R.id.card_view)
            cardView.visibility = View.VISIBLE
            if (i in 0..4) {
                AnimHelper.zoomOutAnimatorWithTranslation(cardView,
                    translate * (4 - i),
                    translate * (4 - i))
                continue
            }
            if (i == childCount - 1) {
                AnimHelper.zoomOutAnimator(cardView, object : MyAnimatorListener() {
                    override fun onAnimationEnd(animation: Animator) {
                        startShuffleAnimator()
                    }
                })
                continue
            }
            AnimHelper.zoomOutAnimator(cardView, listenerNull)
        }
    }

    private fun startShuffleAnimator() {
        val count: Int = childCount
        for (i in 0 until count) {
            val position = count - 1 - i
            val view: View = getChildAt(position)
            val cardView = view.findViewById<View>(R.id.card_view)
            when (position) {
                0 -> {
                    AnimHelper.startAnimation(1000, 1350, cardView, 450, 200, 400, listenerNull)
                }
                1 -> {
                    AnimHelper.startAnimation(1300, 1300, cardView, 600, 300, 600,
                        object : MyAnimatorListener() {
                            override fun onAnimationEnd(animation: Animator) {
                                cuttingCardAnimator(object : MyAnimatorListener() {
                                    override fun onAnimationEnd(animation: Animator) {
                                        translationYToTop()
                                    }
                                })
                            }
                        })
                }
                2 -> {
                    AnimHelper.startAnimation(1250, 1050, cardView, 550, 260, 520, listenerNull)
                }
                3 -> {
                    AnimHelper.startAnimation(1500, 900, cardView, 500, 250, 500, listenerNull)
                }
                4 -> {
                    AnimHelper.startAnimation(1300, 750, cardView, 580, 280, 560, listenerNull)
                }
                5 -> {
                    AnimHelper.startAnimation(1400, 600, cardView, 650, 340, 680, listenerNull)
                }
                6 -> {
                    AnimHelper.startAnimation(1400, 450, cardView, 670, 320, 640, listenerNull)
                }
                7 -> {
                    AnimHelper.startAnimation(1400, 300, cardView, 630, 320, 640, listenerNull)
                }
                8 -> {
                    AnimHelper.startAnimation(1300, 150, cardView, 700, 350, 700, listenerNull)
                }
                else -> {
                    AnimHelper.startAnimation(1500, 0, cardView, 720, 360, 720, listenerNull)
                }
            }
        }
    }

    private fun cuttingCardAnimator(listener: MyAnimatorListener) {
        val view1: View = getChildAt(0)
        val view2: View = getChildAt(1)
        val view3: View = getChildAt(2)
        val view4: View = getChildAt(3)
        val view5: View = getChildAt(4)
        val view6: View = getChildAt(5)
        val view7: View = getChildAt(6)
        val view8: View = getChildAt(7)
        val view9: View = getChildAt(8)
        val view10: View = getChildAt(9)
        AnimHelper.translateXAnim(view4, view1, listenerNull)
        AnimHelper.translateXAnim(view5, view2, listenerNull)
        AnimHelper.translateXAnim(view6, view3, object : MyAnimatorListener() {
            override fun onAnimationEnd(animation: Animator) {
                AnimHelper.translateXAnim(view10, view9, listenerNull)
                AnimHelper.translateXAnim(view8, view7, listenerNull)
                AnimHelper.translateXAnim(view6, view5, listenerNull)
                AnimHelper.translateXAnim(view4, view3, listenerNull)
                AnimHelper.translateXAnim(view2, view1, listener)
            }
        })
    }

    private fun translationYToTop() {
        val scaleY = Constant.CARD_ZOOM_IN_WIDTH - Constant.CARD_DEFAULT_WIDTH
        val translateY = scaleY + Constant.CARD_TOP_MARGIN
        val toY = ScreenUtil.dip2px(mContext!!, translateY.toFloat())
        val count: Int = childCount
        for (i in 0 until count) {
            val view: View = getChildAt(i)
            val cardView = view.findViewById<View>(R.id.card_view)
            if (i == count - 1) {
                AnimHelper.translateYToTopAnim(cardView, toY, object : MyAnimatorListener() {
                    override fun onAnimationEnd(animation: Animator) {
                        if (mListener == null) return
                        mListener!!.onShuffleAnimDone()
                    }
                })
            } else {
                AnimHelper.translateYToTopAnim(cardView, toY, listenerNull)
            }
            print("ok")
        }
    }

    var listenerNull: MyAnimatorListener = object : MyAnimatorListener() {}
}