/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yqkj.yqframedemo.ui.page.adapter

import android.content.Context
import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter
import com.yqkj.yqframedemo.data.bean.MzZcBean
import com.yqkj.yqframedemo.R
import com.yqkj.yqframedemo.ui.page.adapter.DiffUtils
import androidx.recyclerview.widget.RecyclerView
import com.yqkj.yqframedemo.databinding.AdapterMzZcBinding

/**
 * Create by oyd at 2021/11/23
 */
class ListAdapter(context: Context?) : SimpleDataBindingAdapter<MzZcBean?, AdapterMzZcBinding?>(context, R.layout.adapter_mz_zc, DiffUtils.getInstance().libraryInfoItemCallback) {

    override fun onBindItem(binding: AdapterMzZcBinding?, item: MzZcBean?, holder: RecyclerView.ViewHolder?) {
        binding?.info = item
    }
}