package com.chandora.swipablerv

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager

class VerticalViewPager @JvmOverloads constructor(context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    val FINGER_MOVE_THRESHOLD = 20 // pixels, need to use dp later on
    var initialTouchPoint = Point(0, 0)
    var firstTime = true

    override fun canScrollHorizontally(direction: Int): Boolean {
        return false
    }

    override fun canScrollVertically(direction: Int): Boolean {
        return super.canScrollHorizontally(direction)
    }

    private fun injectActionDown(event: MotionEvent) {
        event.action = MotionEvent.ACTION_DOWN
        super.onTouchEvent(event)
        event.action = MotionEvent.ACTION_MOVE
    }

    private fun init() {
        setPageTransformer(true, VerticalPageTransformer())
        overScrollMode = View.LAYER_TYPE_HARDWARE
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        val action = event.actionMasked
        val currentPoint = Point(event.x.toInt(), event.y.toInt())

        if (action == MotionEvent.ACTION_DOWN) {
            // mark the beginning, when finger touched down
            initialTouchPoint = Point(currentPoint)
        } else if (action == MotionEvent.ACTION_UP) {
            // reset the marking, when finger is lifted up
            initialTouchPoint = Point(0, 0)
        } else {
            val moveDistance = currentPoint.distanceFrom(initialTouchPoint)
            if (moveDistance > FINGER_MOVE_THRESHOLD) {
                val direction = MotionUtil.getDirection(initialTouchPoint, currentPoint)
                // check if the scrolling is vertical
                if (direction == MotionUtil.Direction.up || direction == MotionUtil.Direction.down) {
                    return true
                }
            }
        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        event.swapXY()

        // this portion is used for injection ACTION_DOWN
        if (firstTime && event.actionMasked == MotionEvent.ACTION_MOVE) {
            injectActionDown(event)
            firstTime = false
        }
        if (event.actionMasked == MotionEvent.ACTION_UP) {
            firstTime = true
        }
        super.onTouchEvent(event)
        return true
    }

    private fun flipXY(ev: MotionEvent): MotionEvent {
        val width = width.toFloat()
        val height = height.toFloat()
        val x = ev.y / height * width
        val y = ev.x / width * height
        ev.setLocation(x, y)
        return ev
    }

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

//    private class VerticalPageTransformer : PageTransformer {
//        override fun transformPage(
//            view: View,
//            position: Float
//        ) {
//            if (position < -1) { // [-Infinity,-1)
//                // This page is way off-screen to the left.
//                view.alpha = 0f
//            } else if (position <= 1) { // [-1,1]
//                view.alpha = 1f
//
//                // Counteract the default slide transition
//                view.translationX = view.width * -position
//
//                //set Y position to swipe in from top
//                val yPosition = position * view.height
//                view.translationY = yPosition
//            } else { // (1,+Infinity]
//                // This page is way off-screen to the right.
//                view.alpha = 0f
//            }
//        }
//    }


    private inner class VerticalPageTransformer : PageTransformer {
        override fun transformPage(
            view: View,
            position: Float
        ) {
            view.alpha = 1 - position
            if (position <= 0) { // [-1,0]
                // Counteract the default slide transition
                view.translationX = view.width * -position

////                //set Y position to swipe in from top
                val yPosition = position * view.height
                view.translationY = yPosition
                view.scaleX = 1f
                view.scaleY = 1f
                Log.d("SECOND =>", position.toString() + "")
            } else if (position <= 1) { // [0,1]

                // Counteract the default slide transition
                view.translationX = view.width * -position


                // Scale the page down (between MIN_SCALE and 1)
                val scaleFactor = (Companion.MIN_SCALE
                        + (1 - Companion.MIN_SCALE) * (1 - Math.abs(
                    position
                )))
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
                Log.d("THIRD =>", position.toString() + "")
            }
        }

    }

//    class VerticalPageTransformer : PageTransformer {
//
//        override fun transformPage(
//            view: View,
//            position: Float
//        ) {
//            val pageWidth = view.width
//            if (position < -1) { // [-Infinity,-1)
//                // This page is way off-screen to the left.
//                view.alpha = 0f
//            } else if (position <= 0) { // [-1,0]
//                // Use the default slide transition when moving to the left page
//                view.alpha = 1f
//                view.translationX = 0f
//                view.scaleX = 1f
//                view.scaleY = 1f
//            } else if (position <= 1) { // (0,1]
//                // Fade the page out.
//                view.alpha = 1 - position
//
//                // Counteract the default slide transition
//                view.translationX = pageWidth * -position
//
//                // Scale the page down (between MIN_SCALE and 1)
//                val scaleFactor = (MIN_SCALE
//                        + (1 - MIN_SCALE) * (1 - Math.abs(
//                    position
//                )))
//                view.scaleX = scaleFactor
//                view.scaleY = scaleFactor
//            } else { // (1,+Infinity]
//                // This page is way off-screen to the right.
//                view.alpha = 0f
//            }
//        }
//
//        companion object {
//            private const val MIN_SCALE = 0.75f
//        }
//    }


    private fun MotionEvent.swapXY() {
        val width = width.toFloat()
        val height = height.toFloat()
        val newX = y / height * width
        val newY = x / width * height
        setLocation(newX, newY)
    }

    companion object {
        private const val MIN_SCALE = 0.30f
    }

    init {
        init()
    }
}