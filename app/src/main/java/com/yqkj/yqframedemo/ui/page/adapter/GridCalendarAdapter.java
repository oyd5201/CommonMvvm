package com.yqkj.yqframedemo.ui.page.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yqkj.yqframedemo.R;
import com.yqkj.yqframedemo.data.bean.RlBean;

import java.util.List;

public class GridCalendarAdapter extends BaseAdapter {
    private Context context = null;
    private List<RlBean.RlBeans> listDay = null;

    private List<Integer> listCount = null;//默认35个数字
    private LayoutInflater inflater;
    private int week;//星期几
    private OnItemClickListener mClickListener;

    public void setClickListener(OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }
    private class Holder {

        TextView item_tex;
        TextView item_dis;
        ImageView imgDis;
        LinearLayout lyRoot;
        public TextView getItem_tex() {
            return item_tex;
        }

        public void setItem_tex(TextView item_tex) {
            this.item_tex = item_tex;
        }


    }

    //构造方法
    public GridCalendarAdapter(Context context, List<RlBean.RlBeans> data, List<Integer> listCount, int week) {
        this.context = context;
        this.listDay = data;
        this.listCount = listCount;
        this.inflater = LayoutInflater.from(context);
        this.week = week;
    }




    @Override
    public int getCount() {


        return listCount.size();

    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_rl_day, null);
            holder = new Holder();
            holder.item_tex =  view.findViewById(R.id.tv_day);
            holder.item_dis =  view.findViewById(R.id.tv_dis);
            holder.imgDis =  view.findViewById(R.id.img_dis);
            holder.lyRoot =  view.findViewById(R.id.root_layout);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }


        if (position >= week-1) {
            if ((position-week+1) < listDay.size()) {
                holder.item_tex.setText(listDay.get(position - week + 1).getLedgerDay() + "");
                String type = listDay.get(position - week + 1).getWorkStatus();
                if(listDay.get(position-week+1).isChoose())
                    holder.lyRoot.setBackgroundColor(context.getResources().getColor(R.color.color_085BB5));
                else
                    holder.lyRoot.setBackground(context.getResources().getDrawable(R.drawable.bg_036493_bold));


                holder.lyRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mClickListener != null) {
                            mClickListener.onClick(listDay.get(position - week + 1), position - week + 1);
                        }
                    }
                });


                if(listDay.get(position - week + 1).getWorkStatus()!=null) {

                    if (type.equals("1")) {
                        holder.imgDis.setVisibility(View.VISIBLE);
                        holder.imgDis.setImageResource(R.mipmap.ic_yz);
                    } else if (type.equals("-1")) {
                        holder.imgDis.setVisibility(View.VISIBLE);
                        holder.imgDis.setImageResource(R.mipmap.ic_dz);
                    } else if (type.equals("-2")) {
                        holder.imgDis.setVisibility(View.VISIBLE);
                        holder.imgDis.setImageResource(R.mipmap.ic_wz);
                    } else if (type.equals("-3")) {
                        holder.imgDis.setVisibility(View.VISIBLE);
                        holder.imgDis.setImageResource(R.mipmap.ic_bz);
                    } else {
                        holder.imgDis.setVisibility(View.GONE);
                    }
                }else {
                    holder.imgDis.setVisibility(View.GONE);
                }
            }
        }


        holder.item_dis.setText("");


        return view;
    }


    public interface OnItemClickListener {
        void onClick(RlBean.RlBeans rlBean, int position);
    }
}
