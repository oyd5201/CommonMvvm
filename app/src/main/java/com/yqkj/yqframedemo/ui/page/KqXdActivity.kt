package com.yqkj.yqframedemo.ui.page

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kunminx.architecture.ui.page.BaseActivity
import com.kunminx.architecture.ui.page.DataBindingConfig
import com.kunminx.architecture.utils.KvSpUtil
import com.kunminx.architecture.utils.KvSpUtil.decodeString
import com.kunminx.architecture.utils.Utils.getDayofWeek
import com.kunminx.architecture.utils.Utils.getMonthOfCounts
import com.yqkj.yqframedemo.BR
import com.yqkj.yqframedemo.R
import com.yqkj.yqframedemo.data.bean.DictionaryBean
import com.yqkj.yqframedemo.data.bean.KqXdJlBean
import com.yqkj.yqframedemo.data.bean.MzZcBean
import com.yqkj.yqframedemo.data.bean.RlBean
import com.yqkj.yqframedemo.ui.model.KqXdViewModel
import com.yqkj.yqframedemo.ui.page.adapter.GridCalendarAdapter
import com.yqkj.yqframedemo.ui.page.adapter.KqXdJlListAdapter
import com.yqkj.yqframedemo.ui.widgets.PopChooseOne
import kotlinx.android.synthetic.main.activity_kq_xd_detail.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class KqXdActivity : BaseActivity() {
    private lateinit var mstate : KqXdViewModel
    private lateinit var gridViewSim: GridCalendarAdapter
    private lateinit var kqXdJlListAdapter: KqXdJlListAdapter
    private val chooseYearList: MutableList<DictionaryBean> = ArrayList<DictionaryBean>()
    private val chooseMonthList: MutableList<DictionaryBean> = ArrayList<DictionaryBean>()
    private val xdTzBeanList: MutableList<KqXdJlBean> = ArrayList<KqXdJlBean>()
    var days: MutableList<RlBean?> = ArrayList()
    override fun initViewModel() {
        mstate = getActivityScopeViewModel(KqXdViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        kqXdJlListAdapter = KqXdJlListAdapter(this,xdTzBeanList)
        var dataBindingConfig = DataBindingConfig(R.layout.activity_kq_xd_detail,BR.vm,mstate).addBindingParam(BR.Ada,kqXdJlListAdapter)
        return dataBindingConfig
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mstate.title.set("智能台账")
        //获取年月日历
        //获取年月日历
        val date = Calendar.getInstance()
        val year = date[Calendar.YEAR].toString()
        val month = (date[Calendar.MONTH] + 1).toString()
        val day = date[Calendar.DAY_OF_MONTH].toString()

        refreshDays(year, month, true)
        mstate.year.set(year+"年")
        mstate.month.set(month+"月")

        refreshText()
        tv_year_dis.setText(year)
        tv_month_dis.setText(month)

        getXdJl(year,"01","14")


        ly_back.setOnClickListener(View.OnClickListener { finish() })

        var yearInt:Int = year.toInt()
        var monthInt:Int = month.toInt()
        for (i in 0 until (yearInt - 2018)) {
            val dictionaryBean = DictionaryBean()
            dictionaryBean.keyName = (2019 + i).toString() + "年"
            dictionaryBean.keyValue = (2019 + i).toString() + ""
            if ((2019 + i) == yearInt) {
                dictionaryBean.chooseType = "1"
            } else dictionaryBean.chooseType = "0"
            chooseYearList.add(dictionaryBean)
        }

        ly_year.setOnClickListener(View.OnClickListener {
            object : PopChooseOne(this, chooseYearList, ly_year) {
                override fun setData(holder: ChooseAdapter.ViewHolder, position: Int) {
                    val tvName: TextView = holder.getView(R.id.tv_choose)
                    tvName.text = chooseYearList[position].keyName
                    if (chooseYearList[position].chooseType.equals("1")) {
                        tvName.background = resources.getDrawable(R.drawable.bg_0470b9_bold)
                    } else {
                        tvName.background = resources.getDrawable(R.drawable.bg_036096_bold)
                    }
                }
            }.setOnItemClickListener(object : PopChooseOne.ItemClickListener {
                override fun onItemClick(obj: Any?, position: Int) {
                    tv_year.setText(chooseYearList[position].keyName)
                    tv_year_dis.setText(chooseYearList[position].keyValue)
                    for (i in chooseYearList.indices) {
                        chooseYearList[i].chooseType = "0"
                    }
                    chooseYearList[position].chooseType = "1"
                    if (yearInt == Integer.valueOf(tv_year_dis.getText().toString())) {
                        tv_month_dis.setText("1")
                        tv_month.setText("1月")
                        if (chooseMonthList.size > 1) chooseMonthList[0].chooseType = "1"
                    }
                    refreshDays(tv_year_dis.getText().toString(), tv_month_dis.getText().toString(), false)
                    refreshText()
                    getXdJl(tv_year_dis.getText().toString(), tv_month_dis.getText().toString(), "1")

                }
            })
        })


        ly_month.setOnClickListener(View.OnClickListener { //添加12个月份的数据
            val chooseYear: Int = Integer.valueOf(tv_year_dis.getText().toString())
            if (yearInt != chooseYear) {
                chooseMonthList.clear()
                for (i in 1..12) {
                    val dictionaryBeans = DictionaryBean()
                    dictionaryBeans.keyName = i.toString() + "月"
                    dictionaryBeans.keyValue = i.toString() + ""
                    if (i == Integer.valueOf(tv_month_dis.getText().toString())) {
                        dictionaryBeans.chooseType = "1"
                    } else {
                        dictionaryBeans.chooseType = "0"
                    }
                    chooseMonthList.add(dictionaryBeans)
                }
            } else {
                chooseMonthList.clear()
                for (i in 1 until monthInt + 1) {
                    val dictionaryBeans = DictionaryBean()
                    dictionaryBeans.keyName = i.toString() + "月"
                    dictionaryBeans.keyValue = i.toString() + ""
                    if (i == Integer.valueOf(tv_month_dis.getText().toString())) {
                        dictionaryBeans.chooseType = "1"
                    } else {
                        dictionaryBeans.chooseType = "0"
                    }
                    chooseMonthList.add(dictionaryBeans)
                }
            }
            object : PopChooseOne(this, chooseMonthList, ly_month) {
                override fun setData(holder: ChooseAdapter.ViewHolder, position: Int) {
                    val tvName: TextView = holder.getView(R.id.tv_choose)
                    tvName.text = chooseMonthList[position].keyName
                    if (chooseMonthList[position].chooseType.equals("1")) {
                        tvName.background = resources.getDrawable(R.drawable.bg_0470b9_bold)
                    } else {
                        tvName.background = resources.getDrawable(R.drawable.bg_036096_bold)
                    }
                }
            }.setOnItemClickListener(object : PopChooseOne.ItemClickListener {
                override fun onItemClick(obj: Any?, position: Int) {
                    tv_month.setText(chooseMonthList[position].keyName)
                    tv_month_dis.setText(chooseMonthList[position].keyValue)
                    for (i in chooseMonthList.indices) {
                        chooseMonthList[i].chooseType = "0"
                    }
                    chooseMonthList[position].chooseType = "1"
                    refreshDays(tv_year_dis.getText().toString(), tv_month_dis.getText().toString(), false)
                    refreshText()
                    getXdJl(tv_year_dis.getText().toString(), tv_month_dis.getText().toString(), "1")

                }
            })
        })
    }

    //刷新日历 isFirst 默认显示当天 切换月份显示一号
    private fun refreshDays(year: String, month: String, isFirst: Boolean) {

        val dayCount: Int = getMonthOfCounts(Integer.valueOf(year), Integer.valueOf(month))
        val days: MutableList<RlBean.RlBeans?> = ArrayList()
        val count: MutableList<Int?> = ArrayList()


        //每个月的一号
        val nyr = "$year-$month-01"


        //添加Item到网格中
        gd_rl.setNumColumns(7)
        if (getDayofWeek(nyr) > 5 && dayCount > 30) { //判断每个月第一号是否在星期四之后表格要多一行并且月份天数大于30天就需要多画一行表格
            for (i in 1..42) {
                count.add(i)
            }
            gd_rl.setColumnWidth(6)
        } else {
            for (i in 1..35) {
                count.add(i)
            }
            gd_rl.setColumnWidth(5)
        }
        gridViewSim = GridCalendarAdapter(this, days, count, getDayofWeek(nyr))
        gridViewSim.setClickListener(object : GridCalendarAdapter.OnItemClickListener {
            override fun onClick(rlBean: RlBean.RlBeans, position: Int) {

                rlBean.isChoose = true
                for (i in days.indices) {
                    if (i != position) days[i]!!.isChoose = false
                }
                getXdJl(tv_year_dis.getText().toString(), tv_month_dis.getText().toString(), rlBean.ledgerDay.toString() + "")

                gridViewSim.notifyDataSetChanged()
            }
        })
        gd_rl.setAdapter(gridViewSim)

        val map: MutableMap<String, String?> = HashMap()
        map["organizationId"] = decodeString("jydId", "")
        map["year"] = year
        map["month"] = month
        map["ledgerType"] = "4"
        mstate.urlPramsRequest.rlBeanLiveData.observe(this, androidx.lifecycle.Observer {
            var dataResult = it.result
            if(!dataResult.isSuccess) {
                return@Observer
            }
            days.addAll(dataResult.data.ledgerCalendar)
            gridViewSim.notifyDataSetChanged()

        })
        mstate.urlPramsRequest.requestRlBeanList(map)

    }

    private fun getXdJl(year: String, month: String, day: String) {
        var month = month
        var day = day
        val map: MutableMap<String, String?> = HashMap()
        map["organizationId"] = decodeString("jydId", "")
        if (Integer.valueOf(month) < 10) month = "0$month"
        if (Integer.valueOf(day) < 10) day = "0$day"
        map["year"] = year
        map["month"] = month
        map["day"] = day
        mstate.urlPramsRequest.kqXdLiveData.observe(this, androidx.lifecycle.Observer {
            var dataResult = it.result
            if(!it.responseStatus.isSuccess) {
                return@Observer
            }

            mstate.list.setValue(dataResult.data)



        })
        mstate.urlPramsRequest.requestKqXdList(map)

    }


    private fun refreshText() {
        tv_wc_title.setText(tv_year.getText().toString() + tv_month.getText().toString() + "台账记录")
        tv_tz_wc.setText("正常完成")
        tv_wwc_ts.setText(tv_month.getText().toString() + "未完成")
        tv_wc_qk_title.setText(tv_year.getText().toString() + tv_month.getText().toString() + "台账日历")
    }
}