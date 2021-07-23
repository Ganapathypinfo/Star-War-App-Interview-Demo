package com.assignment.demo.starwarsapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import javax.inject.Inject


class ImageUtils @Inject constructor(private val context: Context) {

    fun loadImagesToView(imageView: ImageView, imageUrl: String?) {
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_gallery)
        loadImage(imageView, imageUrl, options)
    }


    private fun loadImage(imageView: ImageView, imageUrl: String?, options: RequestOptions) {
        Glide.with(context)
            .load(imageUrl)
            .apply(options).into(imageView)
    }


}