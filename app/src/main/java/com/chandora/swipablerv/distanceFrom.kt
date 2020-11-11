package com.chandora.swipablerv

import android.graphics.Point

fun Point.distanceFrom(point: Point): Int {
    val diffX = x - point.x
    val diffY = y - point.y
    return Math.hypot(diffX.toDouble(), diffY.toDouble()).toInt()
}

/**
 * syntax sugar to make deep copy
 */
fun Point.copy(point: Point): Point {
    return Point(point)
}