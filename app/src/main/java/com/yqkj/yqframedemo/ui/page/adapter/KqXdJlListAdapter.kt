package com.yqkj.yqframedemo.ui.page.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import com.yqkj.yqframedemo.data.bean.KqXdJlBean
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.kunminx.architecture.data.config.Configs
import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter
import com.yqkj.yqframedemo.R
import com.yqkj.yqframedemo.databinding.ItemKqXdJlListBinding
import java.util.ArrayList

/**
 * author oyd
 * create time 2020.06.28
 */
class KqXdJlListAdapter(private val context: Context, private val list_data: List<KqXdJlBean>?) : SimpleDataBindingAdapter<KqXdJlBean?, ItemKqXdJlListBinding?>(context, R.layout.item_kq_xd_jl_list, DiffUtils.getInstance().kqXdJlBeanItemCallback){
    private var mClickListener: OnItemClickListener? = null
    fun setClickListener(clickListener: OnItemClickListener?) {
        mClickListener = clickListener
    }


    interface OnItemClickListener {
        fun onClick(cjXdJlBean: KqXdJlBean?, position: Int)
    }

    override fun onBindItem(binding: ItemKqXdJlListBinding?, item: KqXdJlBean?, holder: RecyclerView.ViewHolder?) {

        binding?.info = item
        if (item?.isCHoose == true) {
            holder?.itemView?.background = context.resources.getDrawable(R.color.color_085BB5)
        } else {
            holder?.itemView?.background = context.resources.getDrawable(R.color.transparent)
        }

        val list: MutableList<String?> = ArrayList()
        if (!TextUtils.isEmpty(item?.img)) {
            val result = item?.img?.split(",")?.toTypedArray()
            for (i in result?.indices!!) {
                list.add(result[i])
            }
            val url = list[0]!!.replace(".jpg", "zoom.jpg")
            binding?.let { Glide.with(context).load(Configs.RESOURCE_PREFIX+ url).into(it?.imgPhoto) }
        } else
            binding?.imgPhoto?.setImageResource(R.mipmap.ic_zt_empty)

        holder?.itemView?.setOnClickListener {
            if (mClickListener != null) {
                mClickListener!!.onClick(item, holder?.position)
            }
        }
    }
}