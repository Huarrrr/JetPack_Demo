package me.huar.jetpack_demo.utils

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.blankj.utilcode.util.ObjectUtils
import com.blankj.utilcode.util.StringUtils
import com.bumptech.glide.Glide
import me.huar.jetpack_demo.R

object ImageLoader {
    @BindingAdapter("resImage")
    fun loadResImage(view: ImageView?, res: Int) {
        if (res == 0) {
            return
        }
        Glide.with(view!!)
            .load(res)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("networkImage")
    fun loadNetworkImage(view: ImageView?, url: String?) {
        if (StringUtils.isTrimEmpty(url)) {
            return
        }
        Glide.with(view!!)
            .load(url)
            .centerCrop()
            .into(view)
    }

    @BindingAdapter("loadUserAvatar")
    fun loadUserAvatar(view: ImageView?, url: String?) {
        Glide.with(view!!)
            .load(url)
            .placeholder(R.mipmap.icon_default_avatar)
            .error(R.mipmap.icon_default_avatar)
            .into(view)
    }

    @BindingAdapter(value = ["loadLocalImage"])
    fun loadLocalImage(view: ImageView?, `object`: Any?) {
        if (ObjectUtils.isEmpty(`object`)) {
            return
        }
        Glide.with(view!!)
            .load(`object`)
            .into(view)
    }

    fun loadLocalImage(view: ImageView?, path: String?) {
        if (StringUtils.isTrimEmpty(path)) {
            return
        }
        Glide.with(view!!)
            .load(path)
            .into(view)
    }

    fun loadLocalImage(view: ImageView?, uri: Uri?) {
        Glide.with(view!!)
            .load(uri)
            .into(view)
    }
}