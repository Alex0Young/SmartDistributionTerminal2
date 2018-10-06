package com.hyphenate.chatuidemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PacketsList extends AppCompatActivity {
    private static final String TAG = "PacketsList";
    private RecyclerView mRecyclerView;
    private List<Packets> packets= new ArrayList<>();
    private List<Newpackets> newpackets = new ArrayList<>();
    //private String datas = "[{\"packetid\":\"123456789012345\",\"senddata\":\"https://img.alicdn.com/imgextra/i1/TB1AsbTOXXXXXcwXFXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg\",\"packetstate\":\"运送中\",\"nowlocation\":\"北京市小店\",\"destination\":\"北京市邮电大学\",\"recvtelphone\":\"111111111\",\"company\":\"顺丰查水表快递公司\",\"sendname\":\"王小二\",\"recvname\":\"李小四\",\"sendtelphone\":\"22222222\"},{\"packetid\":\"123456789012322\",\"senddata\":\"https://img.alicdn.com/bao/uploaded/i1/TB1Vrz8NXXXXXbkXVXXO6i5.VXX_112353.jpg_430x430q90.jpg\",\"packetstate\":\"运送中\",\"nowlocation\":\"北京市\",\"destination\":\"北京市西土城\",\"recvtelphone\":\"111111111\",\"company\":\"中通\",\"sendname\":\"王小二\",\"recvname\":\"李四\",\"sendtelphone\":\"222222222\"},{\"packetid\":\"123456789012000\",\"senddata\":\"https://img.alicdn.com/imgextra/i2/1714128138/TB2tEZKbY1K.eBjSszbXXcTHpXa_!!1714128138.jpg_430x430q90.jpg\",\"packetstate\":\"运送中\",\"nowlocation\":\"天津市\",\"destination\":\"北京市西土城\",\"recvtelphone\":\"111111111\",\"company\":\"圆通\",\"sendname\":\"王小二\",\"recvname\":\"张大\",\"sendtelphone\":\"11111\"},{\"packetid\":\"123456789012345\",\"senddata\":\"https://img.alicdn.com/imgextra/i1/TB1AsbTOXXXXXcwXFXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg\",\"packetstate\":\"运送中\",\"nowlocation\":\"北京市小店\",\"destination\":\"北京市邮电大学\",\"recvtelphone\":\"111111111\",\"company\":\"顺丰查水表快递公司\",\"sendname\":\"王小二\",\"recvname\":\"李小四\",\"sendtelphone\":\"22222222\"},{\"packetid\":\"123456789012322\",\"senddata\":\"https://img.alicdn.com/bao/uploaded/i1/TB1Vrz8NXXXXXbkXVXXO6i5.VXX_112353.jpg_430x430q90.jpg\",\"packetstate\":\"运送中\",\"nowlocation\":\"北京市\",\"destination\":\"北京市西土城\",\"recvtelphone\":\"111111111\",\"company\":\"中通\",\"sendname\":\"王小二\",\"recvname\":\"李四\",\"sendtelphone\":\"222222222\"},{\"packetid\":\"123456789012000\",\"senddata\":\"https://img.alicdn.com/imgextra/i2/1714128138/TB2tEZKbY1K.eBjSszbXXcTHpXa_!!1714128138.jpg_430x430q90.jpg\",\"packetstate\":\"运送中\",\"nowlocation\":\"天津市\",\"destination\":\"北京市西土城\",\"recvtelphone\":\"111111111\",\"company\":\"圆通\",\"sendname\":\"王小二\",\"recvname\":\"张大\",\"sendtelphone\":\"11111\"}]";
    private String datas;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x111:
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private RecycleViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packet_list);
        Intent intent = getIntent();
        datas = intent.getStringExtra("data");
        Log.d(TAG, "onCreate: "+datas);
        packets.addAll(GsonUtil.jsonToList(datas.toString(), Packets.class));
        //对数据源进行拆分 这里的NewDate里面的
        // 1 表示是商品的头部标题
        // 2 表示是商品的item的布局
        // 3 表示的是底部的item的布局
        Log.d(TAG, "onCreate: "+packets.size());
        for (int i = 0; i < packets.size(); i++) {
            newpackets.add(new Newpackets(1, packets.get(i).getId(), "", "", "","","","",""));

            newpackets.add(new Newpackets(2, "",packets.get(i).getStatus(),packets.get(i).getImageUrl(),packets.get(i).getPosition(),packets.get(i).getPositionNow(),packets.get(i).getUserMobile(),packets.get(i).getUserName(),packets.get(i).getCourierMobile()));

            newpackets.add(new Newpackets(3, "", "", "", "","","","",""));
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_packet);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST)); //添加分割线
        adapter = new RecycleViewAdapter(newpackets, this);
        mRecyclerView.setAdapter(adapter);
        //适配器的接口回调
        adapter.setItemOnClick(new RecycleViewAdapter.itemClickListeren() {
            @Override
            public void headItemClick(int position) {
                Toast.makeText(PacketsList.this, newpackets.get(position).getId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void itemClick(int position) {
                Toast.makeText(PacketsList.this, newpackets.get(position).getPositionNow(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void footItemClick(int position) {
                Toast.makeText(PacketsList.this, "签收", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void footTvClick(int position) {
                Toast.makeText(PacketsList.this,"已签收",Toast.LENGTH_SHORT).show();
            }
        });
        //模拟下拉加载更多 5s加载第二页数据
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < packets.size(); i++) {
                    newpackets.add(new Newpackets(1, packets.get(i).getId(), "", "", "","","","",""));

                    newpackets.add(new Newpackets(2, "",packets.get(i).getStatus(),packets.get(i).getImageUrl(),packets.get(i).getPosition(),packets.get(i).getPositionNow(),packets.get(i).getUserMobile(),packets.get(i).getUserName(),packets.get(i).getCourierMobile()));

                    newpackets.add(new Newpackets(3, "", "", "", "","","","",""));
                }
                mHandler.sendEmptyMessage(0x111);
            }
        }, 5000);
    }

}
