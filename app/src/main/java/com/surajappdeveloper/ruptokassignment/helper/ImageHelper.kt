package com.surajappdeveloper.ruptokassignment.helper

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.surajappdeveloper.ruptokassignment.R

class ImageHelper {

    companion object {

        @JvmStatic
        fun load(view: ImageView, imageUrl: String?, placeholder: String? = "", scaleType: Int?) {
            view.background = ContextCompat.getDrawable(view.context, R.color.transparent)
            view.scaleType = ImageView.ScaleType.FIT_CENTER
            view.setImageDrawable(
                ContextCompat.getDrawable(view.context, R.drawable.ruptok)
            )


            val glideCallBack = object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    /*if (parentView is ShimmerFrameLayout) {
                        parentView.showShimmer(false)
                    }*/
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    /*if (parentView is ShimmerFrameLayout) {
                        parentView.showShimmer(false)
                    }*/
                    view.background = ContextCompat.getDrawable(view.context, R.color.transparent)
                    //view.scaleType = getScaleType(scaleType)
                    return false
                }
            }

            if (!imageUrl?.trim().isNullOrEmpty()) {
                Glide.with(view.context)
                    .load(imageUrl ?: "")
                    .thumbnail(0.1f)
                    .listener(glideCallBack)
                    .apply(
                        RequestOptions()
                            /*.placeholder(ColorDrawable(Color.parseColor(placeholder)))*/
                            .placeholder(R.drawable.ruptok)
                            .error(R.drawable.ruptok)
                            .dontAnimate()
                    )
                    .into(view)
            }
        }

        private fun getScaleType(scaleType: Int?): ImageView.ScaleType {
            when (scaleType) {
                0 -> {
                    return ImageView.ScaleType.MATRIX
                }
                1 -> {
                    return ImageView.ScaleType.FIT_XY
                }
                2 -> {
                    return ImageView.ScaleType.FIT_START
                }
                3 -> {
                    return ImageView.ScaleType.FIT_CENTER
                }
                4 -> {
                    return ImageView.ScaleType.FIT_END
                }
                5 -> {
                    return ImageView.ScaleType.CENTER
                }
                6 -> {
                    return ImageView.ScaleType.CENTER_CROP
                }
                7 -> {
                    return ImageView.ScaleType.CENTER_INSIDE
                }
                else -> {
                    return ImageView.ScaleType.FIT_XY
                }
            }
        }

        /*@JvmStatic
        fun load(view: ImageView, imageUrl: String?, placeholder: String?) {
            if (placeholder.isNullOrBlank()) {
                Glide.with(view.context)
                    .load(imageUrl ?: "")
                    .thumbnail(0.1f)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.placeholder)
                            .dontAnimate()
                    )
                    .into(view)
            } else {
                Glide.with(view.context)
                    .load(imageUrl ?: "")
                    .thumbnail(0.1f)
                    .apply(
                        RequestOptions()
                            .placeholder(ColorDrawable(Color.parseColor(placeholder)))
                            .dontAnimate()
                    )
                    .into(view)
            }
        }*/
    }
}