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

package com.yqkj.yqframedemo.ui.page.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.yqkj.yqframedemo.data.bean.MzZcBean;

/**
 * Create by oyd at 2021/11/22
 */
public class DiffUtils {

    private DiffUtil.ItemCallback<MzZcBean> mLibraryInfoItemCallback;

    private DiffUtils() {
    }

    private static final DiffUtils S_DIFF_UTILS = new DiffUtils();

    public static DiffUtils getInstance() {
        return S_DIFF_UTILS;
    }

    public DiffUtil.ItemCallback<MzZcBean> getLibraryInfoItemCallback() {
        if (mLibraryInfoItemCallback == null) {
            mLibraryInfoItemCallback = new DiffUtil.ItemCallback<MzZcBean>() {
                @Override
                public boolean areItemsTheSame(@NonNull MzZcBean oldItem, @NonNull MzZcBean newItem) {
                    return oldItem.equals(newItem);
                }

                @Override
                public boolean areContentsTheSame(@NonNull MzZcBean oldItem, @NonNull MzZcBean newItem) {
                    return oldItem.getCreatTime().equals(newItem.getCreatTime());
                }
            };
        }
        return mLibraryInfoItemCallback;
    }

}
