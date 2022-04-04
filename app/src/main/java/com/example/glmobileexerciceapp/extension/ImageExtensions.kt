package com.example.glmobileexerciceapp.extension

import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.glmobileexerciceapp.R

fun ImageView.getImageByUrl(url: String) {
    val mainImageOptions = RequestOptions()
        .placeholder(
            AppCompatResources.getDrawable(
                context, R.drawable.ic_placeholder_image
            )
        )
    Glide.with(context).load(url).apply(mainImageOptions).into(this)
}
