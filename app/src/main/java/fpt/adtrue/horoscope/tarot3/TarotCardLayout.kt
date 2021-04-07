package fpt.adtrue.horoscope.tarot3

import android.animation.*
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PointF
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.activity.ResultsTarotActivity
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.custom.BezierEvaluator
import fpt.adtrue.horoscope.model.DataTarot
import java.util.*
import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.hypot

class TarotCardLayout : FrameLayout {
    var mContext: Context? = null
    private var mLastX = 0f
    private var mLastY = 0f
    private var mStartAngle = 0f
    private var mTmpAngle = 0f
    private var mRadius = 0
    private val mChildItemCount = 26
    private var mCanTouchScroll = false
    private var mDownTime: Long = 0
    private val mFlingAbleValue = 100
    private var isFling = false
    private var mCardWidth = 0
    private var mCardHeight = 0
    private var mCardPointX = 0f
    private var mCardPointY = 0f
    private var mFlingRunnable: AutoFlingRunnable? = null
    var k = 0
    private val data = mutableListOf<DataTarot>()

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }


    private fun get26Card() {
        data.clear()
        val ints = mutableListOf<Int>()
        ints.clear()
        for (i in 0..77) {
            ints.add(i)
        }
        val rand = Random()
        for (i in 0..25) {
            val randomIndex: Int = rand.nextInt(ints.size - 1)
            val a = ints[randomIndex]
            data.add(App.getTarot()[a])
            ints.removeAt(randomIndex)
        }
    }


    private fun initView(context: Context) {
        get26Card()

        mContext = context
        mCardWidth = mContext!!.resources.getDimensionPixelSize(R.dimen.card_width)
        mCardHeight = mContext!!.resources.getDimensionPixelSize(R.dimen.card_height)
        val mInflater = LayoutInflater.from(getContext())
        for (i in 0 until mChildItemCount) {
            val view: View =
                mInflater.inflate(R.layout.layout_circle_card, this, false)
            val cardView = view.findViewById<View>(R.id.card_view)
            val outView = view.findViewById<View>(R.id.outer_card_view)
            val chooseView = view.findViewById<View>(R.id.tarot_choose_view)
            val tarotDecodeLayout = view.findViewById<View>(R.id.layout_tarot_decode)
            val topRightPoint = view.findViewById<View>(R.id.right_top_point)
            val topCenterPoint = view.findViewById<View>(R.id.center_top_point)
            val topLeftPoint = view.findViewById<View>(R.id.left_top_point)
            val img = view.findViewById<ImageView>(R.id.img)
            val btnView = view.findViewById<Button>(R.id.btn_view)

            img.setImageResource(data[i].img!!)
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
                if (k == 0) {
                    App.POSITION_LOVE = data[i].name
                    expendCardAnim(chooseView, outView, cardView, tarotDecodeLayout, topLeftPoint)
                    cardView.isEnabled = false
                    val enableButton = Runnable { cardView.isEnabled = true }
                    Handler(Looper.getMainLooper()).postDelayed(enableButton, 1000)
                }
                if (k == 1) {

                    App.POSITION_CAREER = data[i].name
                    expendCardAnim(chooseView, outView, cardView, tarotDecodeLayout, topCenterPoint)
                    cardView.isEnabled = false
                    val enableButton = Runnable { cardView.isEnabled = true }
                    Handler(Looper.getMainLooper()).postDelayed(enableButton, 1000)
                }
                if (k == 2) {

                    App.POSITION_FUTURE = data[i].name
                    expendCardAnim(chooseView, outView, cardView, tarotDecodeLayout, topRightPoint)
                    cardView.isEnabled = false
                    val enableButton2 = Runnable { cardView.isEnabled = true }
                    Handler(Looper.getMainLooper()).postDelayed(enableButton2, 1000)
                    mCanTouchScroll = false
                    val enableButton = Runnable {
                        btnView.visibility = View.VISIBLE
                        btnView.setOnClickListener {
                            Log.e("__________________", "$i")
                            ResultsTarotActivity.start(context)
                        }
                    }
                    Handler(Looper.getMainLooper()).postDelayed(enableButton, 1000)
                }
                mCanTouchScroll = true
                k++

            }
            this.addView(view)
        }
    }

    fun startExpendCard() {
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            val cardView = view.findViewById<View>(R.id.card_view)
            val outView = view.findViewById<View>(R.id.outer_card_view)
            val chooseView = view.findViewById<View>(R.id.tarot_choose_view)
            val tarotDecodeLayout = view.findViewById<View>(R.id.layout_tarot_decode)
            val topRightPoint = view.findViewById<View>(R.id.right_top_point)
            val topCenterPoint = view.findViewById<View>(R.id.center_top_point)
            val topLeftPoint = view.findViewById<View>(R.id.left_top_point)
            val btnView = view.findViewById<Button>(R.id.btn_view)
            if (i in 0..4) {
                view.x = mCardPointX
                view.y = mCardPointY
            }
            view.visibility = View.VISIBLE
            cardView.setOnClickListener {
                if (k == 3) {
//                    dismissTarotOtherCards(i)
                    mCanTouchScroll = false
                }
                if (k == 0) {
                    App.POSITION_LOVE = data[i].name
                    expendCardAnim(chooseView, outView, cardView, tarotDecodeLayout, topLeftPoint)
                    cardView.isEnabled = false
                    val enableButton = Runnable { cardView.isEnabled = true }
                    Handler(Looper.getMainLooper()).postDelayed(enableButton, 1000)
                }
                if (k == 1) {

                    App.POSITION_CAREER = data[i].name
                    expendCardAnim(chooseView, outView, cardView, tarotDecodeLayout, topCenterPoint)
                    cardView.isEnabled = false
                    val enableButton = Runnable { cardView.isEnabled = true }
                    Handler(Looper.getMainLooper()).postDelayed(enableButton, 1000)
                }
                if (k == 2) {
                    App.POSITION_FUTURE = data[i].name
                    expendCardAnim(chooseView, outView, cardView, tarotDecodeLayout, topRightPoint)
                    cardView.isEnabled = false
                    val enableButton2 = Runnable { cardView.isEnabled = true }
                    Handler(Looper.getMainLooper()).postDelayed(enableButton2, 1000)
                    mCanTouchScroll = false
                    val enableButton = Runnable {
                        btnView.visibility = View.VISIBLE
                        btnView.setOnClickListener {
                            Log.e("__________________", "$i")
                            ResultsTarotActivity.start(context)
                        }
                    }
                    Handler(Looper.getMainLooper()).postDelayed(enableButton, 1000)
                }
                mCanTouchScroll = true
                k++
                view.isEnabled = false
                val enableButton = Runnable { view.isEnabled = true }
                Handler(Looper.getMainLooper()).postDelayed(enableButton, 1000)
            }
            startRotationAnim(outView, 0f, CARD_INIT_ANGLE.toFloat(),
                (childCount - i) * CARD_SPACE_ANGLE + CARD_INIT_ANGLE.toFloat())
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
            val outView = view.findViewById<View>(R.id.outer_card_view)
            val chooseView = view.findViewById<View>(R.id.tarot_choose_view)
            val tarotDecodeLayout = view.findViewById<View>(R.id.layout_tarot_decode)
            val topRightPoint = view.findViewById<View>(R.id.right_top_point)
            val topCenterPoint = view.findViewById<View>(R.id.center_top_point)
            val topLeftPoint = view.findViewById<View>(R.id.left_top_point)
            val btnView = view.findViewById<Button>(R.id.btn_view)
            cardView?.setOnClickListener {
                if (k == 0) {
                    App.POSITION_LOVE = data[i].name
                    expendCardAnim(chooseView, outView, cardView, tarotDecodeLayout, topLeftPoint)
                    cardView.isEnabled = false
                    val enableButton = Runnable { cardView.isEnabled = true }
                    Handler(Looper.getMainLooper()).postDelayed(enableButton, 1000)

                }
                if (k == 1) {

                    App.POSITION_CAREER = data[i].name
                    expendCardAnim(chooseView, outView, cardView, tarotDecodeLayout, topCenterPoint)
                    cardView.isEnabled = false
                    val enableButton = Runnable { cardView.isEnabled = true }
                    Handler(Looper.getMainLooper()).postDelayed(enableButton, 1000)
                }
                if (k == 2) {

                    App.POSITION_FUTURE = data[i].name
                    expendCardAnim(chooseView, outView, cardView, tarotDecodeLayout, topRightPoint)
                    cardView.isEnabled = false
                    val enableButton2 = Runnable { cardView.isEnabled = true }
                    Handler(Looper.getMainLooper()).postDelayed(enableButton2, 1000)
                    mCanTouchScroll = false
                    val enableButton = Runnable {
                        btnView.visibility = View.VISIBLE
                        btnView.setOnClickListener {
                            Log.e("__________________", "$i")
                            ResultsTarotActivity.start(context)
                        }
                    }
                    Handler(Looper.getMainLooper()).postDelayed(enableButton, 1000)
                }
                mCanTouchScroll = true
                k++

            }
            if (view.visibility == View.GONE) {
                continue
            }
            val es = (getChildCount() - i) * CARD_SPACE_ANGLE + CARD_INIT_ANGLE.toFloat()
            outView.rotation = -mStartAngle + es
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(specWidth, specHeight)
        mRadius = measuredWidth.coerceAtLeast(measuredHeight)
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
                if (abs(anglePerSecond) > mFlingAbleValue && !isFling) {
                    post(AutoFlingRunnable(anglePerSecond).also { mFlingRunnable = it })
                    return true
                }
                if (abs(mTmpAngle) > MAX_CAN_CLICK_ANGLE) {
                    return true
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return true
    }

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

    private fun startRotationAnim(
        innerCardView: View?,
        startAngle: Float,
        passAngle: Float,
        endAngle: Float
    ) {
        val translateLeftX = mCardWidth / 2.toFloat()
        val translateBottomY = getScreenHeight(mContext) / 2 - (mCardHeight * 0.8).toFloat()
        val translationXAnim =
            ObjectAnimator.ofFloat(innerCardView, "translationX", -translateLeftX)
        val translationYAnim =
            ObjectAnimator.ofFloat(innerCardView, "translationY", translateBottomY)
        val rotationAnim =
            ObjectAnimator.ofFloat(innerCardView, "rotation", startAngle, passAngle)
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
                mCanTouchScroll = true
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        mAnimSet.start()
    }

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
            BezierEvaluator(), PointF(
                outCardView.x,
                outCardView.y
            ), PointF(centerX - mCardWidth / 2, centerY - mCardHeight / 2)
        )
        animator.addUpdateListener { animation ->
            val pointF = animation.animatedValue as PointF
            outCardView.x = pointF.x
            outCardView.y = pointF.y
        }
        val view1Anim = ObjectAnimator.ofFloat(outCardView, "rotation", outCardView.rotation, 0f)
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

    fun translateTopRightAnim(innerCardView: View, x: Float, y: Float) {
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

    private fun setCameraDistance(innerCardView: View, chooseView: View) {
        val distance = 16000
        val scale = resources.displayMetrics.density * distance
        chooseView.cameraDistance = scale
        innerCardView.cameraDistance = scale
    }

    private fun getAngle(xTouch: Float, yTouch: Float): Float {
        val x = xTouch - mRadius / 2.0
        val y = yTouch - mRadius / 2.0
        return (asin(y / hypot(x, y)) * 180 / Math.PI).toFloat()
    }

    private fun getQuadrant(x: Float, y: Float): Int {
        val tmpX = (x - mRadius / 2).toInt()
        val tmpY = (y - mRadius / 2).toInt()
        return if (tmpX >= 0) {
            if (tmpY >= 0) 4 else 1
        } else {
            if (tmpY >= 0) 3 else 2
        }
    }

    private inner class AutoFlingRunnable(private var angelPerSecond: Float) : Runnable {
        override fun run() {
            if (abs(angelPerSecond) < 20) {
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
        private const val CARD_INIT_ANGLE = -60
        private const val CARD_SPACE_ANGLE = 10
        private const val RIGHT_MAX_ANGLE = 130
        private const val LEFT_MAX_ANGLE = -40

        fun getScreenHeight(context: Context?): Int {
            val dm = context!!.resources.displayMetrics
            return dm.heightPixels
        }
    }
}