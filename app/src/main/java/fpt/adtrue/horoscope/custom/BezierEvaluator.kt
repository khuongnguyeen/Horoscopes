package fpt.adtrue.horoscope.custom

import android.animation.TypeEvaluator
import android.graphics.PointF
import fpt.adtrue.horoscope.custom.BezierCurve.bezier

class BezierEvaluator : TypeEvaluator<PointF> {
    override fun evaluate(
        fraction: Float, startValue: PointF,
        endValue: PointF
    ): PointF {
        return bezier(fraction, startValue, endValue)
    }
}