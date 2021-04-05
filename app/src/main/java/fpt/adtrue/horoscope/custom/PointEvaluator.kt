package fpt.adtrue.horoscope.custom

import android.animation.TypeEvaluator
import android.graphics.PointF

class PointEvaluator(private val point1: PointF) : TypeEvaluator<PointF> {
    override fun evaluate(
        fraction: Float, startValue: PointF,
        endValue: PointF
    ): PointF {
        val oneMinusT = 1.0f - fraction
        val point = PointF()
        val point0 = startValue
        val point3 = endValue
        point.x =
            oneMinusT * oneMinusT * point0.x + 2 * point1.x * fraction * oneMinusT + fraction * fraction * endValue.x
        point.y =
            oneMinusT * oneMinusT * point0.y + 2 * point1.y * fraction * oneMinusT + fraction * fraction * endValue.y

        return point
    }
}