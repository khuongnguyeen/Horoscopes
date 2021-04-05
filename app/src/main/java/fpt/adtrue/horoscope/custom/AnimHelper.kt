package fpt.adtrue.horoscope.custom

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.PointF
import android.view.View
import android.view.animation.LinearInterpolator

object AnimHelper {
    fun startAnimation(
        duration: Int,
        delayTime: Int,
        mCardView: View,
        goByX: Int,
        goByY: Int,
        doneY: Int,
        listener: Animator.AnimatorListener?
    ) {
        val animator1 =
            ValueAnimator.ofObject(PointEvaluator(PointF(mCardView.x + goByX, mCardView.y + goByY)),
                PointF(mCardView.x, mCardView.y),
                PointF(mCardView.x, mCardView.y + doneY))
        animator1.addUpdateListener { animation ->
            val pointF = animation.animatedValue as PointF
            mCardView.x = pointF.x
            mCardView.y = pointF.y
        }
        val animator2 =
            ValueAnimator.ofObject(PointEvaluator(PointF(mCardView.x - goByX, mCardView.y + goByY)),
                PointF(mCardView.x, mCardView.y + doneY),
                PointF(mCardView.x, mCardView.y))
        animator2.addUpdateListener { animation ->
            val pointF = animation.animatedValue as PointF
            mCardView.x = pointF.x
            mCardView.y = pointF.y
        }
        val view1Anim = ObjectAnimator.ofFloat(mCardView,
            "rotation",
            0f,
            30f,
            60f,
            90f,
            120f,
            150f,
            180f,
            210f,
            240f,
            270f,
            300f,
            330f,
            360f,
            390f,
            420f,
            450f,
            480f,
            510f,
            540f,
            570f,
            600f,
            630f,
            660f,
            690f,
            720f)
        view1Anim.interpolator = LinearInterpolator()
        animator1.repeatMode = ValueAnimator.REVERSE
        animator1.interpolator = LinearInterpolator()
        animator2.repeatMode = ValueAnimator.REVERSE
        animator2.interpolator = LinearInterpolator()
        val set2 = AnimatorSet()
        set2.play(animator2).with(view1Anim)
        val set1 = AnimatorSet()
        set1.play(animator1).with(view1Anim).before(set2)
        set1.duration = duration.toLong()
        set1.startDelay = delayTime.toLong()
        set1.addListener(listener)
        set1.start()
    }

    fun translateXAnim(leftView: View, rightView: View, listener: Animator.AnimatorListener?) {
        val leftAnimator = ValueAnimator.ofFloat(leftView.x, leftView.x - 280, leftView.x)
        leftAnimator.addUpdateListener { valueAnimator ->
            val x = valueAnimator.animatedValue as Float
            leftView.x = x
        }
        val rightAnimator = ValueAnimator.ofFloat(rightView.x, rightView.x + 280, rightView.x)
        rightAnimator.addUpdateListener { valueAnimator ->
            val x = valueAnimator.animatedValue as Float
            rightView.x = x
        }
        val animatorSet = AnimatorSet()
        animatorSet.play(leftAnimator).with(rightAnimator)
        animatorSet.duration = 1500
        animatorSet.addListener(listener)
        animatorSet.start()
    }

    fun zoomOutAnimatorWithTranslation(view: View?, fromX: Int, fromY: Int) {
        val translationX = ObjectAnimator.ofFloat(view, "translationX", fromX.toFloat(), 0f)
        translationX.interpolator = LinearInterpolator()
        val translationY = ObjectAnimator.ofFloat(view, "translationY", fromY.toFloat(), 0f)
        translationY.interpolator = LinearInterpolator()
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.7f)
        scaleX.interpolator = LinearInterpolator()
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.7f)
        scaleY.interpolator = LinearInterpolator()
        val animatorSet = AnimatorSet()
        animatorSet.duration = 400
        animatorSet.playTogether(translationX, translationY, scaleX, scaleY)
        animatorSet.start()
    }

    fun zoomOutAnimator(view: View?, listener: Animator.AnimatorListener?) {
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.7f)
        scaleX.interpolator = LinearInterpolator()
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.7f)
        scaleY.interpolator = LinearInterpolator()
        val animatorSet = AnimatorSet()
        animatorSet.duration = 400
        animatorSet.playTogether(scaleX, scaleY)
        animatorSet.addListener(listener)
        animatorSet.start()
    }

    fun translateYToTopAnim(view: View?, toY: Int, listener: Animator.AnimatorListener?) {
        val translationY = ObjectAnimator.ofFloat(view, "translationY", -3*toY.toFloat())
        translationY.interpolator = LinearInterpolator()
        translationY.duration = 400
        translationY.addListener(listener)
        translationY.start()
    }
}