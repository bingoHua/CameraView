package com.example.camerademo

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

class CameraView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val camera = Camera()

    init {
        camera.rotateX(30f)
        camera.setLocation(0f, 0f, -4 * resources.displayMetrics.density)
    }

    private val BITMAP_SIZE = 200.dp.toInt()
    private val PADDING = 100.dp
    private val drawable = getBeauty(BITMAP_SIZE)
    private val bwidth = drawable.width
    private val bheight = drawable.height
    override fun onDraw(canvas: Canvas) {
        //up
        canvas.save()
        canvas.translate((PADDING + bwidth / 2), (PADDING + bheight / 2))
        canvas.rotate(-40f)
        canvas.clipRect(
            -bwidth.toFloat(),
            -bheight.toFloat(),
            bwidth.toFloat(),
            0f
        )
        canvas.rotate(40f)
        canvas.translate(-(PADDING + bwidth / 2), -(PADDING + bheight / 2))
        canvas.drawBitmap(drawable, PADDING, PADDING, paint)
        canvas.restore()

        //down
        canvas.save()
        canvas.translate((PADDING + bwidth / 2), (PADDING + bheight / 2))
        canvas.rotate(-40f)
        camera.applyToCanvas(canvas)
        canvas.clipRect(
            -bwidth.toFloat(),
            0f,
            bwidth.toFloat(),
            bheight.toFloat()
        )
        canvas.rotate(40f)
        canvas.translate(-(PADDING + bwidth / 2), -(PADDING + bheight / 2))
        canvas.drawBitmap(drawable, PADDING, PADDING, paint)
        canvas.restore()
    }

    private fun getBeauty(width: Int): Bitmap {
        val option = BitmapFactory.Options()
        option.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.beauty, option)
        option.inJustDecodeBounds = false
        option.inDensity = option.outWidth
        option.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.beauty, option)
    }
}

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.dp
    get() = this.toFloat().dp
