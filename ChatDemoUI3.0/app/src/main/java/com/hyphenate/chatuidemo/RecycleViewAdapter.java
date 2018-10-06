package com.hyphenate.chatuidemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RecycleViewAdapter_log";
    private List<Newpackets> dataList = new ArrayList<>();
    private final int HEAD = 0x001;
    private final int ITEM = 0x002;
    private final int FOOT = 0x003;
    private Context context;
    private itemClickListeren listeren;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEAD:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_head, parent, false));
            case ITEM:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
            case FOOT:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot, parent, false));
            default:
                return null;
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (getItemViewType(position) == HEAD) {
            ((TextView) viewHolder.getView(R.id.item_headTv)).setText("快递号:" + dataList.get(position).getId());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listeren.headItemClick(position);
                }
            });
        } else if (getItemViewType(position) == ITEM) {
            ((TextView) viewHolder.getView(R.id.item_Tv)).setText("目的地：   "+dataList.get(position).getPosition());
            ((TextView) viewHolder.getView(R.id.item_pTv)).setText("现在：   " + dataList.get(position).getPositionNow());
            ((TextView) viewHolder.getView(R.id.item_recvname)).setText("物流状态：   "+dataList.get(position).getStatus());
            ((TextView) viewHolder.getView(R.id.item_sendname)).setText("快递员手机号：   "+dataList.get(position).getCourierMobile());
            Picasso.with(context).load(dataList.get(position).getImageUrl()).into(((ImageView) viewHolder.getView(R.id.item_Iv)));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listeren.itemClick(position);
                }
            });
        } else if (getItemViewType(position) == FOOT) {
            ((Button) viewHolder.getView(R.id.item_footTv)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listeren.footTvClick(position);
                }
            });
            ((Button) viewHolder.getView(R.id.item_footbtn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listeren.footItemClick(position);
                }
            });

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listeren.footItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (dataList.get(position).getType()) {
            case 1:
                return HEAD;
            case 2:
                return ITEM;
            case 3:
                return FOOT;
            default:
                return 0;
        }
    }

    public RecycleViewAdapter(List<Newpackets> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> views = new SparseArray<>();

        public ViewHolder(View convertView) {
            super(convertView);
        }

        /**
         * 根据id获取view
         */
        public <T extends View> T getView(int viewId) {
            View view = views.get(viewId);
            if (null == view) {
                view = itemView.findViewById(viewId);
                views.put(viewId, view);
            }
            return (T) view;
        }
    }

    public interface itemClickListeren {
        void headItemClick(int position);

        void itemClick(int position);

        void footItemClick(int position);

        void footTvClick(int position);

       // void footBtnClick(int position);
    }

    public void setItemOnClick(itemClickListeren listeren) {
        this.listeren = listeren;
    }

}
