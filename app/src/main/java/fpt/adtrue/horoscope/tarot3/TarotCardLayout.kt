package fpt.adtrue.horoscope.tarot3

import android.animation.*
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.custom.BezierEvaluator
import kotlin.math.abs

/**
 * Created by chenshaolong on 2019/2/28.
 */
class TarotCardLayout : FrameLayout {
    var mContext: Context? = null
    private var mLastX = 0f
    private var mLastY = 0f
    private var mStartAngle = 0f
    private var mTmpAngle = 0f
    private var mRadius = 0
    private val mChildItemCount = 26
    private var mCanTouchScroll  = false
    private var mDownTime: Long = 0
    private val mFlingAbleValue = 100
    private var isFling = false
    private var mCardWidth= 0
    private var mCardHeight = 0
    private var mCardPointX = 0f
    private var mCardPointY = 0f
    private var mFlingRunnable: AutoFlingRunnable? = null
    var mItemClickListener: OnItemClickListener? = null

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        initView(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context) {
        mContext = context
        mCardWidth = mContext!!.resources.getDimensionPixelSize(R.dimen.card_width)
        mCardHeight = mContext!!.resources.getDimensionPixelSize(R.dimen.card_height)
        val mInflater = LayoutInflater.from(getContext())
        for (i in 0 until mChildItemCount) {
            val view: View =
                mInflater.inflate(R.layout.layout_circle_card, this, false)
            val cardView = view.findViewById<View>(R.id.card_view)
            val outView =
                view.findViewById<View>(R.id.outer_card_view)
            val chooseView =
                view.findViewById<View>(R.id.tarot_choose_view)
            val tarotDecodeLayout =
                view.findViewById<View>(R.id.layout_tarot_decode)
            val topRightPoint =
                view.findViewById<View>(R.id.right_top_point)
            if (i == 0) {
                mCardPointX = view.x
                mCardPointY = view.y
            }
            view.visibility = View.GONE
            val translate = 20
            if (i in 0..4) {
                view.translationX = translate * (5 - i).toFloat()
                view.translationY = translate * (5 - i).toFloat()
                view.visibility = View.VISIBLE
            }
            cardView.setOnClickListener {
                mCanTouchScroll = false
                expendCardAnim(chooseView, outView, cardView, tarotDecodeLayout, topRightPoint)
                dismissTarotOtherCards(i)
            }
            this.addView(view)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(specWidth, specHeight)
        mRadius = Math.max(measuredWidth, measuredHeight)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (!mCanTouchScroll) {
            return true
        }
        val x = ev.x
        val y = ev.y
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = x
                mLastY = y
                mTmpAngle = 0f
                mDownTime = System.currentTimeMillis()
                if (isFling) {
                    removeCallbacks(mFlingRunnable)
                    isFling = false
                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val startTouchAngle = getAngle(mLastX, mLastY)
                val endTouchAngle = getAngle(x, y)
                if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
                    mStartAngle += endTouchAngle - startTouchAngle
                    mTmpAngle += endTouchAngle - startTouchAngle
                } else {
                    mStartAngle += startTouchAngle - endTouchAngle
                    mTmpAngle += startTouchAngle - endTouchAngle
                }
                translateView()
                mLastX = x
                mLastY = y
            }
            MotionEvent.ACTION_UP -> {
                val anglePerSecond =
                    mTmpAngle * 1000 / (System.currentTimeMillis() - mDownTime)
                if (Math.abs(anglePerSecond) > mFlingAbleValue && !isFling) {
                    post(AutoFlingRunnable(anglePerSecond).also { mFlingRunnable = it })
                    return true
                }
                if (Math.abs(mTmpAngle) > MAX_CAN_CLICK_ANGLE) {
                    return true
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return true
    }

    /**
     * 开始展牌
     */
    fun startExpendCard() {
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            val cardView = view.findViewById<View>(R.id.card_view)
            val outView =
                view.findViewById<View>(R.id.outer_card_view)
            val chooseView =
                view.findViewById<View>(R.id.tarot_choose_view)
            val tarotDecodeLayout =
                view.findViewById<View>(R.id.layout_tarot_decode)
            val topRightPoint =
                view.findViewById<View>(R.id.right_top_point)
            if (i >= 0 && i <= 4) {
                view.x = mCardPointX
                view.y = mCardPointY
            }
            view.visibility = View.VISIBLE
            cardView.setOnClickListener {
                mCanTouchScroll = false
                expendCardAnim(chooseView, outView, cardView, tarotDecodeLayout, topRightPoint)
                dismissTarotOtherCards(i)
            }
            startRotationAnim(
                outView,
                0f,
                CARD_INIT_ANGLE.toFloat(),
                (childCount - i) * CARD_SPACE_ANGLE + CARD_INIT_ANGLE.toFloat()
            )
        }
    }

    private fun translateView() {
        mStartAngle =
            if (mStartAngle >= RIGHT_MAX_ANGLE) RIGHT_MAX_ANGLE.toFloat() else mStartAngle
        mStartAngle =
            if (mStartAngle <= LEFT_MAX_ANGLE) LEFT_MAX_ANGLE.toFloat() else mStartAngle
        val childCount = childCount
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            val cardView = view!!.findViewById<View>(R.id.card_view)
            val outView =
                view.findViewById<View>(R.id.outer_card_view)
            val chooseView =
                view.findViewById<View>(R.id.tarot_choose_view)
            val tarotDecodeLayout =
                view.findViewById<View>(R.id.layout_tarot_decode)
            val topRightPoint =
                view.findViewById<View>(R.id.right_top_point)
            if (view != null && cardView != null) {
                cardView.setOnClickListener {
                    mCanTouchScroll = false
                    expendCardAnim(
                        chooseView,
                        outView,
                        cardView,
                        tarotDecodeLayout,
                        topRightPoint
                    )
                    dismissTarotOtherCards(i)
                }
            }
            if (view.visibility == View.GONE) {
                continue
            }
            val es =
                (getChildCount() - i) * CARD_SPACE_ANGLE + CARD_INIT_ANGLE.toFloat()
            outView.rotation = -mStartAngle + es
        }
    }

    /**
     * 隐藏其他塔罗牌
     *
     * @param exceptViewPosition
     */
    private fun dismissTarotOtherCards(exceptViewPosition: Int) {
        val childCount = childCount
        for (i in 0 until childCount) {
            if (exceptViewPosition == i) {
                continue
            }
            val view = getChildAt(i)
            val mHiddenAction = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f
            )
            mHiddenAction.duration = 1000
            view.visibility = View.GONE
            view.animation = mHiddenAction
        }
    }

    fun startRotationAnim(
        innerCardView: View?,
        startAngle: Float,
        passAngle: Float,
        endAngle: Float
    ) {
        val translateLeftX = mCardWidth / 2.toFloat()
        val translateBottomY =
            getScreenHeight(mContext) / 2 - (mCardHeight * 0.8).toFloat()

        //平移卡片到底部待展开形似
        val translationXAnim =
            ObjectAnimator.ofFloat(innerCardView, "translationX", -translateLeftX)
        val translationYAnim =
            ObjectAnimator.ofFloat(innerCardView, "translationY", translateBottomY)
        //卡片旋转到初始角度待展开
        val rotationAnim =
            ObjectAnimator.ofFloat(innerCardView, "rotation", startAngle, passAngle)
        //卡片开始展开，没张卡片旋转的角度通过公式计算：(getChildCount() - i) * 10 - 60
        val afterRotationAnim =
            ObjectAnimator.ofFloat(innerCardView, "rotation", passAngle, endAngle)
        translationXAnim.interpolator = LinearInterpolator()
        translationYAnim.interpolator = LinearInterpolator()
        rotationAnim.interpolator = LinearInterpolator()
        afterRotationAnim.interpolator = LinearInterpolator()
        val mAnimSet = AnimatorSet()
        mAnimSet.duration = 1000
        mAnimSet.play(translationXAnim).with(translationYAnim).with(rotationAnim)
            .before(afterRotationAnim)
        mAnimSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                //展牌结束后才可以用手势滑动
                mCanTouchScroll = true
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        mAnimSet.start()
    }

    /**
     * 对选择的卡片进行展示动画
     *
     * @param chooseView
     * @param outCardView
     * @param innerCardView
     * @param tarotDecodeLayout
     * @param topRightPoint
     */
    private fun expendCardAnim(
        chooseView: View,
        outCardView: View,
        innerCardView: View,
        tarotDecodeLayout: View,
        topRightPoint: View
    ) {
        val centerX = chooseView.x + mCardWidth / 2
        val centerY = chooseView.y + mCardHeight / 2
        setCameraDistance(innerCardView, chooseView)
        val animator = ValueAnimator.ofObject(
            BezierEvaluator(),
            PointF(outCardView.x, outCardView.y),
            PointF(centerX - mCardWidth / 2, centerY - mCardHeight / 2)
        )
        animator.addUpdateListener { animation ->
            val pointF = animation.animatedValue as PointF
            outCardView.x = pointF.x
            outCardView.y = pointF.y
        }
        val view1Anim =
            ObjectAnimator.ofFloat(outCardView, "rotation", outCardView.rotation, 0f)
        view1Anim.interpolator = LinearInterpolator()
        val animatorSet = AnimatorSet()
        animatorSet.play(animator).with(view1Anim)
        animatorSet.duration = 1000
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                cardDanceAnim(innerCardView, chooseView, tarotDecodeLayout, topRightPoint)
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        animatorSet.start()
    }

    /**
     * 卡牌翻转动画
     *
     * @param innerCardView
     * @param chooseView
     * @param tarotDecodeLayout
     * @param topRightPoint
     */
    private fun cardDanceAnim(
        innerCardView: View,
        chooseView: View,
        tarotDecodeLayout: View,
        topRightPoint: View
    ) {
        chooseView.visibility = View.VISIBLE
        val mDismissSet =
            AnimatorInflater.loadAnimator(mContext, R.animator.rotate_dismiss) as AnimatorSet
        val mDisplaySet =
            AnimatorInflater.loadAnimator(mContext, R.animator.rotate_display) as AnimatorSet
        mDismissSet.setTarget(innerCardView)
        mDisplaySet.setTarget(chooseView)
        mDisplaySet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                tarotDecodeLayout.visibility = View.VISIBLE
                val toX =
                    (topRightPoint.x - chooseView.width * 0.6 / 2).toFloat()
                val toY =
                    (topRightPoint.y - chooseView.height * 0.6 / 2).toFloat()
                translateTopRightAnim(chooseView, toX, toY)
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        mDismissSet.start()
        mDisplaySet.start()
    }

    /**
     * 卡片翻转后的平移右上角的动画
     *
     * @param innerCardView
     * @param x
     * @param y
     */
    fun translateTopRightAnim(
        innerCardView: View,
        x: Float,
        y: Float
    ) {
        val animator = ValueAnimator.ofObject(
            BezierEvaluator(),
            PointF(innerCardView.x, innerCardView.y),
            PointF(x, y)
        )
        animator.addUpdateListener { animation ->
            val pointF = animation.animatedValue as PointF
            innerCardView.x = pointF.x
            innerCardView.y = pointF.y
        }
        val scaleXAnim = ObjectAnimator.ofFloat(innerCardView, "scaleX", 1f, 0.76f)
        val scaleYAnim = ObjectAnimator.ofFloat(innerCardView, "scaleY", 1f, 0.76f)
        scaleXAnim.interpolator = LinearInterpolator()
        scaleYAnim.interpolator = LinearInterpolator()
        val animatorSet = AnimatorSet()
        animatorSet.play(animator).with(scaleXAnim).with(scaleYAnim)
        animatorSet.duration = 1000
        animatorSet.start()
    }

    /**
     * 翻转卡片时，改变视角距离, 贴近屏幕
     *
     * @param innerCardView
     * @param chooseView
     */
    private fun setCameraDistance(
        innerCardView: View,
        chooseView: View
    ) {
        val distance = 16000
        val scale = resources.displayMetrics.density * distance
        chooseView.cameraDistance = scale
        innerCardView.cameraDistance = scale
    }

    /**
     * 获取旋转的角度
     *
     * @param xTouch 触摸的x坐标
     * @param yTouch 触摸的y坐标
     * @return
     */
    private fun getAngle(xTouch: Float, yTouch: Float): Float {
        val x = xTouch - mRadius / 2.0
        val y = yTouch - mRadius / 2.0
        return (Math.asin(
            y / Math.hypot(
                x,
                y
            )
        ) * 180 / Math.PI).toFloat()
    }

    /**
     * 根据当前位置计算象限
     *
     * @param x
     * @param y
     * @return
     */
    private fun getQuadrant(x: Float, y: Float): Int {
        val tmpX = (x - mRadius / 2).toInt()
        val tmpY = (y - mRadius / 2).toInt()
        return if (tmpX >= 0) {
            if (tmpY >= 0) 4 else 1
        } else {
            if (tmpY >= 0) 3 else 2
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mItemClickListener = listener
    }

    private inner class AutoFlingRunnable(private var angelPerSecond: Float) : Runnable {
        override fun run() {
            if (abs(angelPerSecond) <20) {
                isFling = false
                return
            }
            isFling = true
            mStartAngle += angelPerSecond / 40
            angelPerSecond /= 1.0666f
            postDelayed(this, 40)
            translateView()
        }

    }

    companion object {
        private const val MAX_CAN_CLICK_ANGLE = 3
        private const val CARD_INIT_ANGLE = -60 //卡片开始展开时的角度
        private const val CARD_SPACE_ANGLE = 10 //每张卡片相差的角度
        private const val RIGHT_MAX_ANGLE = 130 //滑动到右边的最大角度
        private const val LEFT_MAX_ANGLE = -40 //滑动到左边的最大角度

        /**
         * 获取屏幕高度
         */
        fun getScreenHeight(context: Context?): Int {
            val dm = context!!.resources.displayMetrics
            return dm.heightPixels
        }
    }
}