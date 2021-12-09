package com.yqkj.yqframedemo.ui.page

import android.os.Bundle
import com.kunminx.architecture.data.repository.CommonListResponse
import com.kunminx.architecture.data.response.DataResult
import com.kunminx.architecture.ui.page.BaseActivity
import com.kunminx.architecture.ui.page.DataBindingConfig
import com.kunminx.architecture.utils.KvSpUtil
import com.kunminx.architecture.utils.KvSpUtil.decodeString
import com.yqkj.yqframedemo.BR
import com.yqkj.yqframedemo.R

import com.yqkj.yqframedemo.data.bean.MzZcBean
import com.yqkj.yqframedemo.data.bean.User
import com.yqkj.yqframedemo.databinding.ActivityListDetailBinding
import com.yqkj.yqframedemo.ui.model.MzZcListViewModel
import com.yqkj.yqframedemo.ui.page.adapter.ListAdapter
import java.util.ArrayList
import java.util.HashMap

class ListDetailActivity : BaseActivity() {
    private lateinit var listDetailBinding: ActivityListDetailBinding
    private lateinit var mState:MzZcListViewModel
    private lateinit var adapter: ListAdapter
    override fun initViewModel() {

        mState = getActivityScopeViewModel(MzZcListViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        adapter = ListAdapter(this)
        val dataBindingConfig = DataBindingConfig(R.layout.activity_list_detail,BR.vm,mState)
                .addBindingParam(BR.adapter,adapter);
        return dataBindingConfig
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listDetailBinding = binding as ActivityListDetailBinding
        lifecycle.addObserver(mState.urlPramsRequest)



        mState.urlPramsRequest.mzZcLiveData.observe(this, { dataResult: DataResult<CommonListResponse<MzZcBean?>> ->
            if (!dataResult.responseStatus.isSuccess) {
                return@observe
            }
            val mzZcBeanList: MutableList<MzZcBean?> = ArrayList()
            mzZcBeanList.addAll(dataResult.result.data.list)
            var users = User("sss","ss")
            users.name = "eeee"
            users.zhName = "ssss"
            mState.user.value = users
            mState.list.value =mzZcBeanList
        })

        if(mState.urlPramsRequest.mzZcLiveData.value==null)
            getTzList()

    }

    private fun getTzList(){
        val map: MutableMap<String, String?> = HashMap()
        map["organizationId"] = decodeString("jydId", "")
        map["year"] = "2021"
        map["month"] = "10"
        map["day"] = "14"
        mState.urlPramsRequest.requestMzZcList(map)
    }
}