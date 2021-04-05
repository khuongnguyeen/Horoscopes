package fpt.adtrue.horoscope.custom

import android.graphics.PointF

object BezierCurve {
    fun bezier(t: Float, point0: PointF, point1: PointF): PointF {
        require(!(t < 0 || t > 1)) { "t must between 0 and 1" }
        val oneMinusT = 1.0f - t
        val point = PointF()
        point.x = oneMinusT * point0.x + t * point1.x
        point.y = oneMinusT * point0.y + t * point1.y
        return point
    }

}