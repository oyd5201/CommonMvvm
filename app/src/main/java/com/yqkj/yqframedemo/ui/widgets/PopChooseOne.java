package com.yqkj.yqframedemo.ui.widgets;

import static com.kunminx.architecture.utils.DisplayUtils.dp2px;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yqkj.yqframedemo.R;
import com.yqkj.yqframedemo.data.bean.DictionaryBean;

import java.util.List;


public abstract class PopChooseOne extends PopupWindow {
    private List<DictionaryBean> beanList;
    private Context mContext;
    private LayoutInflater mInflater;
    private Activity mActivity;
    private ChooseAdapter chooseAdapter;
    private RecyclerView rvChoose;
    private View mView;
    public PopChooseOne(Context context, List<DictionaryBean> beanList){
        this.beanList = beanList;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mActivity= (Activity) context;
        init();
    }
    public PopChooseOne(Context context, List<DictionaryBean> beanList, View view){
        this.beanList = beanList;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mActivity= (Activity) context;
        mView = view;
        init();
    }

    private void init(){
        View popView = mInflater.inflate(R.layout.pop_choose_type, null);
        setContentView(popView);
        //设置PopupWindow宽高
        //设置PopupWindow宽高

        setWidth(dp2px(200));
        setHeight(dp2px(160));

        setOutsideTouchable(true);
        rvChoose = popView.findViewById(R.id.rv_choose);

        chooseAdapter = new ChooseAdapter(mContext,beanList);

        rvChoose.setLayoutManager(new GridLayoutManager(mContext,2));
        rvChoose.setAdapter(chooseAdapter);

        showAsDropDown(mView);
    }

    public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ViewHolder> {

        private Context context;
        private List<DictionaryBean> list_data;


        public ChooseAdapter(Context context, List<DictionaryBean> list_datas) {
            this.context = context;
            this.list_data = list_datas;
        }

        public void nodfiyData(List<DictionaryBean> list) {
            if (list != null) {
                this.list_data.clear();
                this.list_data.addAll(list);
            }
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_choose_one, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            setData(holder,position);

            //条目点击事件
            if (mItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.onItemClick(list_data.get(position), position);
                        dismiss();
                    }
                });
            }


            //长按点击事件
            if (mItemLoogClickListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return mItemLoogClickListener.onItemLongClick(list_data.get(position), position);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return list_data.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            ViewHolder(View view) {
                super(view);

            }

            public <T extends View> T getView(int id) {
                return (T) itemView.findViewById(id);
            }
        }

        //使用接口回调点击事件
        private  ItemClickListener mItemClickListener;

        public void setOnItemClickListener(ItemClickListener itemClickListener) {
            this.mItemClickListener = itemClickListener;
        }

        //使用接口回调点击事件
        private ItemLongClickListener mItemLoogClickListener;

        public void setOnItemLongClickListener(ItemLongClickListener itemLongClickListener) {
            this.mItemLoogClickListener = itemLongClickListener;
        }

        /**
         * Created by Administrator on 2017/5/28.
         * 点击事件接口
         */


    }
    public interface ItemClickListener {
        void onItemClick(Object obj, int position);
    }

    /**
     * Created by Administrator on 2017/5/28.
     * 长按点击事件接口
     */

    public interface ItemLongClickListener {
        boolean onItemLongClick(Object obj, int position);
    }

    public abstract void setData(ChooseAdapter.ViewHolder holder, int position);

    /**
     * 刷新数据
     * @param list
     */
    public void nodfiyData(List<DictionaryBean> list){
        if(chooseAdapter!=null){
            chooseAdapter.nodfiyData(list);
        }
    }

    /**
     * 点击事件
     * @param itemClickListener
     */
    public void setOnItemClickListener(ItemClickListener itemClickListener){
        if(chooseAdapter!=null){
            chooseAdapter.setOnItemClickListener(itemClickListener);
        }
    }

    /**
     * 长按事件
     * @param itemLongClickListener
     */
    public void setOnItemLongClickListener(ItemLongClickListener itemLongClickListener){
        if(chooseAdapter!=null){
            chooseAdapter.setOnItemLongClickListener(itemLongClickListener);
        }
    }
    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if(Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }
    /**
     * 设置PopupWindow的位置
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }
}
