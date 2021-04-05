package fpt.adtrue.horoscope.tarot2

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PointF
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.custom.BezierEvaluator
import java.util.*

class TarotSelectionView : FrameLayout {
    private var mContext: Context? = null
    var mTvSelection: TextView? = null
    private var mCardContainer: FrameLayout? = null
    var mScrollView: SlideScrollView? = null
    private var mFirstOpenImage: ImageView? = null
    private var mSecondOpenImage: ImageView? = null
    private var mThirdOpenImage: ImageView? = null
    private var mIvTranslate: ImageView? = null
    private var mViewGroup: Group? = null
    var mScreenHalfWidth = 0
    private var isFirstViewFilled = false
    private var isSecondViewFilled = false
    private var isThirdViewFilled = false
    var mCardLocations: MutableList<CardXLocation> = ArrayList()

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

    private fun initView(context: Context) {
        mContext = context
        mScreenHalfWidth = -ScreenUtil.getScreenWidth(mContext!!)
        val view = View.inflate(context, R.layout.layout_selection_view, this)
        mScrollView = view.findViewById(R.id.scrollView)
        mTvSelection = view.findViewById(R.id.tv_selection)
        mCardContainer = view.findViewById(R.id.container)
        mFirstOpenImage = view.findViewById(R.id.first_open_image)
        mSecondOpenImage = view.findViewById(R.id.second_open_image)
        mThirdOpenImage = view.findViewById(R.id.third_open_image)
        mIvTranslate = view.findViewById(R.id.iv_translate)
        mViewGroup = view.findViewById(R.id.view_group)
        mScrollView!!.setScrollChangedListener(object : SlideScrollView.OnScrollListener {
            @SuppressLint("SetTextI18n")
            override fun onScroll(scrollX: Int, scrollY: Int, oldX: Int, oldY: Int) {
                val cardPosition = mScreenHalfWidth + scrollX
                for (i in mCardLocations.indices) {
                    val loc = mCardLocations[i]
                    if (cardPosition >= loc.startX && cardPosition < loc.endX) {
                        mTvSelection!!.text = "" + (i + 1) + ""
                        return
                    }
                }
            }
        })
        val cardDisplayWidth = ScreenUtil.dip2px(mContext!!, 35f)
        val rightPadding = mScreenHalfWidth - ScreenUtil.dip2px(mContext!!, 35f)
        val leftPadding = 5
        mCardContainer!!.setPadding(leftPadding, 0, rightPadding, 0)
        mCardLocations.clear()
        var k = 0
        for (i in 0 until MAX_CARD_COUNT) {
            val itemView = View.inflate(context, R.layout.item_card, null)
            itemView.tag = false
            itemView.setOnClickListener(OnClickListener {
                if (k == 3) return@OnClickListener
                if (itemView.tag as Boolean) {
                    mScrollView!!.setSlide(false)
                    val loc = IntArray(2)
                    itemView.getLocationOnScreen(loc)
                    mIvTranslate!!.x = loc[0].toFloat()
                    mIvTranslate!!.y = loc[1].toFloat()
                    itemView.visibility = View.INVISIBLE
                    mIvTranslate!!.alpha = 1.0f
                    mIvTranslate!!.visibility = View.VISIBLE
                    if (!isFirstViewFilled) {
                        k++
                        startCardTranslateAnim(mIvTranslate, mFirstOpenImage)
                        isFirstViewFilled = true
                    } else if (!isSecondViewFilled) {
                        k++
                        startCardTranslateAnim(mIvTranslate, mSecondOpenImage)
                        isSecondViewFilled = true
                    } else {
                        k++
                        startCardTranslateAnim(mIvTranslate, mThirdOpenImage)
                        isThirdViewFilled = true
                    }
                    return@OnClickListener
                }
                itemView.tag = true
                itemView.translationY = ScreenUtil.dip2px(mContext!!, -50f).toFloat()
                resetCardViews(i)
            })
            val p = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            p.leftMargin = i * cardDisplayWidth
            itemView.layoutParams = p
            mCardContainer!!.addView(itemView)
            val location = CardXLocation()
            location.startX = leftPadding + p.leftMargin
            location.endX = leftPadding + p.leftMargin + cardDisplayWidth
            mCardLocations.add(location)
        }
    }

    fun showTarotSelectionView() {
        visibility = View.VISIBLE
        Handler().postDelayed({ mViewGroup!!.visibility = View.VISIBLE }, 300)
    }

    private fun resetCardViews(currentPosition: Int) {
        val count: Int = mCardContainer!!.childCount
        for (i in 0 until count) {
            val view: View = mCardContainer!!.getChildAt(i)
            if (currentPosition != i && view.tag as Boolean) {
                view.translationY = 0f
                view.tag = false
            }
        }
        Log.e("currentPosition: ", "$currentPosition")
    }

    private fun startCardTranslateAnim(startView: View?, endView: View?) {
        val animator: ValueAnimator = ValueAnimator.ofObject(BezierEvaluator(),
            PointF(startView!!.x, startView.y),
            PointF(endView!!.x, endView.y))
        animator.addUpdateListener { animation ->
            val pointF: PointF = animation.animatedValue as PointF
            startView.x = pointF.x
            startView.y = pointF.y
        }
        animator.duration = 1000
        animator.addListener(object : MyAnimatorListener() {
            override fun onAnimationEnd(animation: Animator) {
                mScrollView!!.setSlide(true)
                cardDanceAnim(startView, endView)
            }
        })
        animator.start()
    }

    private fun cardDanceAnim(originView: View?, targetView: View?) {
        targetView!!.visibility = View.VISIBLE
        val mDismissSet: AnimatorSet =
            AnimatorInflater.loadAnimator(mContext, R.animator.tarot_rotate_dismiss) as AnimatorSet
        val mDisplaySet: AnimatorSet =
            AnimatorInflater.loadAnimator(mContext, R.animator.tarot_rotate_display) as AnimatorSet
        mDismissSet.setTarget(originView)
        mDisplaySet.setTarget(targetView)
        mDisplaySet.addListener(object : MyAnimatorListener() {})
        mDismissSet.start()
        mDisplaySet.start()
    }

    companion object {
        private const val MAX_CARD_COUNT = 78
    }
}