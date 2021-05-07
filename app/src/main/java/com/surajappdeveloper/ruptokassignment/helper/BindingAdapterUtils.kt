package com.surajappdeveloper.ruptokassignment.helper

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

class BindingAdapterUtils {
    companion object{
        @JvmStatic
        @BindingAdapter(value = ["imageUrl", "placeholder", "scaleType"], requireAll = false)
        fun setImageUrl(view: ImageView, imageUrl: String?, placeholder: String?, scaleType: Int?) {
            ImageHelper.load(view, imageUrl, placeholder, scaleType)
        }
    }
}