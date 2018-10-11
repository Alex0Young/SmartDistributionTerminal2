package com.hyphenate.chatuidemo.ui;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chatuidemo.Constant;
import com.hyphenate.chatuidemo.GsonUtil;
import com.hyphenate.chatuidemo.HttpUtil;
import com.hyphenate.chatuidemo.MyDecoration;
import com.hyphenate.chatuidemo.Newpackets;
import com.hyphenate.chatuidemo.Packets;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.RecycleViewAdapter;
import com.hyphenate.chatuidemo.db.MyDataBaseHelper;
import com.hyphenate.easeui.EaseConstant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class PacketsFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "PacketsFragment1";
    private RecyclerView mRecyclerView;
    private List<Packets> packets= new ArrayList<>();
    private List<Newpackets> newpackets = new ArrayList<>();
    //private String datas = "[{\"packetid\":\"123456789012345\",\"senddata\":\"https://img.alicdn.com/imgextra/i1/TB1AsbTOXXXXXcwXFXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg\",\"packetstate\":\"运送中\",\"nowlocation\":\"北京市小店\",\"destination\":\"北京市邮电大学\",\"recvtelphone\":\"111111111\",\"company\":\"顺丰查水表快递公司\",\"sendname\":\"王小二\",\"recvname\":\"李小四\",\"sendtelphone\":\"22222222\"},{\"packetid\":\"123456789012322\",\"senddata\":\"https://img.alicdn.com/bao/uploaded/i1/TB1Vrz8NXXXXXbkXVXXO6i5.VXX_112353.jpg_430x430q90.jpg\",\"packetstate\":\"运送中\",\"nowlocation\":\"北京市\",\"destination\":\"北京市西土城\",\"recvtelphone\":\"111111111\",\"company\":\"中通\",\"sendname\":\"王小二\",\"recvname\":\"李四\",\"sendtelphone\":\"222222222\"},{\"packetid\":\"123456789012000\",\"senddata\":\"https://img.alicdn.com/imgextra/i2/1714128138/TB2tEZKbY1K.eBjSszbXXcTHpXa_!!1714128138.jpg_430x430q90.jpg\",\"packetstate\":\"运送中\",\"nowlocation\":\"天津市\",\"destination\":\"北京市西土城\",\"recvtelphone\":\"111111111\",\"company\":\"圆通\",\"sendname\":\"王小二\",\"recvname\":\"张大\",\"sendtelphone\":\"11111\"},{\"packetid\":\"123456789012345\",\"senddata\":\"https://img.alicdn.com/imgextra/i1/TB1AsbTOXXXXXcwXFXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg\",\"packetstate\":\"运送中\",\"nowlocation\":\"北京市小店\",\"destination\":\"北京市邮电大学\",\"recvtelphone\":\"111111111\",\"company\":\"顺丰查水表快递公司\",\"sendname\":\"王小二\",\"recvname\":\"李小四\",\"sendtelphone\":\"22222222\"},{\"packetid\":\"123456789012322\",\"senddata\":\"https://img.alicdn.com/bao/uploaded/i1/TB1Vrz8NXXXXXbkXVXXO6i5.VXX_112353.jpg_430x430q90.jpg\",\"packetstate\":\"运送中\",\"nowlocation\":\"北京市\",\"destination\":\"北京市西土城\",\"recvtelphone\":\"111111111\",\"company\":\"中通\",\"sendname\":\"王小二\",\"recvname\":\"李四\",\"sendtelphone\":\"222222222\"},{\"packetid\":\"123456789012000\",\"senddata\":\"https://img.alicdn.com/imgextra/i2/1714128138/TB2tEZKbY1K.eBjSszbXXcTHpXa_!!1714128138.jpg_430x430q90.jpg\",\"packetstate\":\"运送中\",\"nowlocation\":\"天津市\",\"destination\":\"北京市西土城\",\"recvtelphone\":\"111111111\",\"company\":\"圆通\",\"sendname\":\"王小二\",\"recvname\":\"张大\",\"sendtelphone\":\"11111\"}]";
    private String data= "[{\"id\":\"1234567891234155\",\"status\":\"配送中\",\"imageUrl\":\"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3275357620,145457536&fm=26&gp=0.jpg\",\"positionNow\":\"北京市顺丰配送中心\",\"position\":\"北京市西城区西土城10号北京邮电大学一单元10-3\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"test222\"},{\"id\":\"123456789123456\",\"status\":\"配送中\",\"imageUrl\":\"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3275357620,145457536&fm=26&gp=0.jpg\",\"positionNow\":\"北京市顺丰配送中心\",\"position\":\"北京市西城区西土城10号北京邮电大学一单元10-3\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"test222\"},{\"id\":\"456789132456700\",\"status\":\"配送中\",\"imageUrl\":\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539160498&di=06b32abd0f074d516f\",\"positionNow\":\"北京市\",\"position\":\"北京市东城区故宫博物馆\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"test222\"},{\"id\":\"456789132456711\",\"status\":\"运送中\",\"imageUrl\":\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539160498&di=06b32abd0f074d516f\",\"positionNow\":\"北京市\",\"position\":\"北京市东城区故宫博物馆\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"test222\"},{\"id\":\"456789132456744\",\"status\":\"运送中\",\"imageUrl\":\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539160498&di=06b32abd0f074d516f\",\"positionNow\":\"北京市\",\"position\":\"北京市东城区故宫博物馆\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"test222\"},{\"id\":\"456789132456777\",\"status\":\"运送中\",\"imageUrl\":\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539160498&di=06b32abd0f074d516f\",\"positionNow\":\"北京市\",\"position\":\"北京市东城区故宫博物馆\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"test222\"},{\"id\":\"456789132456789\",\"status\":\"配送中\",\"imageUrl\":\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539160498&di=06b32abd0f074d516f\",\"positionNow\":\"北京市\",\"position\":\"北京市东城区故宫博物馆\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"test222\"},{\"id\":\"7894561324879000\",\"status\":\"配送中\",\"imageUrl\":\"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3275357620,145457536&fm=26&gp=0.jpg\",\"positionNow\":\"北京市通州区\",\"position\":\"北京市邮电大学西土城\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"test222\"},{\"id\":\"7894561324879844\",\"status\":\"配送中\",\"imageUrl\":\"https://ss2.bdstatic.co/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3275357620,145457536&fm=26&gp=0.jpg\",\"positionNow\":\"北京市通州区\",\"position\":\"北京市邮电大学西土城\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"李三\"},{\"id\":\"789456132487985\",\"status\":\"配送中\",\"imageUrl\":\"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3275357620,145457536&fm=26&gp=0.jpg\",\"positionNow\":\"北京市通州区\",\"position\":\"北京市邮电大学西土城\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"test222\"}]";
    private View view;
    private String chatUsername;
    private ProgressDialog progressDialog;

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

    private ImageView edit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.packet_list, container, false);
     /*   String address = "http://39.107.119.70/smart/get_package.php";
        String postinfo = "11114141313";
        getPackage(address, postinfo);
        */
//        data = getFragmentManager().findFragmentById(R.id.fragment_container).getArguments().getString("DATA");
        packets.addAll(GsonUtil.jsonToList(data.toString(), Packets.class));
        initRecycle();

        Log.d(TAG, "onCreateView: 444");
        return view;
    }

    private void initRecycle(){
        edit = (ImageView) view.findViewById(R.id.right_image);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewNoticeActivity.class);
                startActivity(intent);
            }
        });

        Log.d(TAG, "onCreate: "+packets.size());
        for (int i = 0; i < packets.size(); i++) {
            newpackets.add(new Newpackets(1, packets.get(i).getId(), "", "", "","","","",""));

            newpackets.add(new Newpackets(2, "",packets.get(i).getStatus(),packets.get(i).getImageUrl(),packets.get(i).getPosition(),packets.get(i).getPositionNow(),packets.get(i).getUserMobile(),packets.get(i).getUserName(),packets.get(i).getCourierMobile()));

            newpackets.add(new Newpackets(3, "", "", "", "","","",packets.get(i).getUserName(),""));
        }
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_packet);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new MyDecoration(getContext(), MyDecoration.VERTICAL_LIST)); //添加分割线
        adapter= new RecycleViewAdapter(newpackets, getActivity());
        mRecyclerView.setAdapter(adapter);
        adapter.setItemOnClick(new RecycleViewAdapter.itemClickListeren() {
            @Override
            public void headItemClick(int position) {
                Toast.makeText(getActivity(), newpackets.get(position).getId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void itemClick(int position) {
                Toast.makeText(getActivity(), newpackets.get(position).getUserName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void footItemClick(int position) {
                Toast.makeText(getActivity(), "联系收件人"+newpackets.get(position).getUserName(), Toast.LENGTH_SHORT).show();
                chatUsername = newpackets.get(position).getUserName();
                Log.d(TAG, "footItemClick: "+chatUsername);
                startChat(position);
            }

            @Override
            public void footTvClick(int position) {
                //boolean flag = true;
                sendNotice(position);
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
        Log.d(TAG, "initRecycle: 11");
    }

    private void getPackage(String address, String postinfo){
        showProgressDialog();
        Log.d(TAG, "getPackage: 222");
        //packets = "[{\"id\":\"1234567891234155\",\"status\":\"配送中\",\"imageUrl\":\"https:\\/\\/ss2.bdstatic.com\\/70cFvnSh_Q1YnxGkpoWK1HF6hhy\\/it\\/u=3275357620,145457536&fm=26&gp=0.jpg\",\"positionNow\":\"北京市顺丰配送中心\",\"position\":\"北京市西城区西土城10号北京邮电大学一单元10-3\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"李三\"},{\"id\":\"123456789123456\",\"status\":\"配送中\",\"imageUrl\":\"https:\\/\\/ss2.bdstatic.com\\/70cFvnSh_Q1YnxGkpoWK1HF6hhy\\/it\\/u=3275357620,145457536&fm=26&gp=0.jpg\",\"positionNow\":\"北京市顺丰配送中心\",\"position\":\"北京市西城区西土城10号北京邮电大学一单元10-3\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"李三\"},{\"id\":\"456789132456700\",\"status\":\"配送中\",\"imageUrl\":\"https:\\/\\/timgsa.baidu.com\\/timg?image&quality=80&size=b9999_10000&sec=1539160498&di=06b32abd0f074d516f\",\"positionNow\":\"北京市\",\"position\":\"北京市东城区故宫博物馆\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"李三\"},{\"id\":\"456789132456711\",\"status\":\"运送中\",\"imageUrl\":\"https:\\/\\/timgsa.baidu.com\\/timg?image&quality=80&size=b9999_10000&sec=1539160498&di=06b32abd0f074d516f\",\"positionNow\":\"北京市\",\"position\":\"北京市东城区故宫博物馆\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"李三\"},{\"id\":\"456789132456744\",\"status\":\"运送中\",\"imageUrl\":\"https:\\/\\/timgsa.baidu.com\\/timg?image&quality=80&size=b9999_10000&sec=1539160498&di=06b32abd0f074d516f\",\"positionNow\":\"北京市\",\"position\":\"北京市东城区故宫博物馆\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"李三\"},{\"id\":\"456789132456777\",\"status\":\"运送中\",\"imageUrl\":\"https:\\/\\/timgsa.baidu.com\\/timg?image&quality=80&size=b9999_10000&sec=1539160498&di=06b32abd0f074d516f\",\"positionNow\":\"北京市\",\"position\":\"北京市东城区故宫博物馆\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"李三\"},{\"id\":\"456789132456789\",\"status\":\"配送中\",\"imageUrl\":\"https:\\/\\/timgsa.baidu.com\\/timg?image&quality=80&size=b9999_10000&sec=1539160498&di=06b32abd0f074d516f\",\"positionNow\":\"北京市\",\"position\":\"北京市东城区故宫博物馆\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"李三\"},{\"id\":\"7894561324879000\",\"status\":\"配送中\",\"imageUrl\":\"https:\\/\\/ss2.bdstatic.com\\/70cFvnSh_Q1YnxGkpoWK1HF6hhy\\/it\\/u=3275357620,145457536&fm=26&gp=0.jpg\",\"positionNow\":\"北京市通州区\",\"position\":\"北京市邮电大学西土城\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"李三\"},{\"id\":\"7894561324879844\",\"status\":\"配送中\",\"imageUrl\":\"https:\\/\\/ss2.bdstatic.com\\/70cFvnSh_Q1YnxGkpoWK1HF6hhy\\/it\\/u=3275357620,145457536&fm=26&gp=0.jpg\",\"positionNow\":\"北京市通州区\",\"position\":\"北京市邮电大学西土城\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"李三\"},{\"id\":\"789456132487985\",\"status\":\"配送中\",\"imageUrl\":\"https:\\/\\/ss2.bdstatic.com\\/70cFvnSh_Q1YnxGkpoWK1HF6hhy\\/it\\/u=3275357620,145457536&fm=26&gp=0.jpg\",\"positionNow\":\"北京市通州区\",\"position\":\"北京市邮电大学西土城\",\"userMobile\":\"18813141413\",\"courierMobile\":\"11114141313\",\"userName\":\"李三\"}]";
        HttpUtil.postOkHttpRequest(address, postinfo, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String datas = response.body().string();
                Log.d(TAG, "onResponse: "+datas);
                packets.addAll(GsonUtil.jsonToList(datas.toString(), Packets.class));
                Log.d(TAG, "onCreate: "+packets.size());
                for (int i = 0; i < packets.size(); i++) {
                    newpackets.add(new Newpackets(1, packets.get(i).getId(), "", "", "","","","",""));

                    newpackets.add(new Newpackets(2, "",packets.get(i).getStatus(),packets.get(i).getImageUrl(),packets.get(i).getPosition(),packets.get(i).getPositionNow(),packets.get(i).getUserMobile(),packets.get(i).getUserName(),packets.get(i).getCourierMobile()));

                    newpackets.add(new Newpackets(3, "", "", "", "","","","",""));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: http333");
            }
        });

        closeProgressDialog();
    }

    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    private void sendNotice(int position){
        MyDataBaseHelper dbHelper= new MyDataBaseHelper(getContext(),"Notice.db",null,2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("NOTICE",null,null,null,null,null,null);
        String content;
        if(cursor.moveToLast()){
            content = cursor.getString(cursor.getColumnIndex("notice"));//notice
            Log.d(TAG, "sendNotice: "+content);
            String toChatUsername = packets.get(position).getUserName();
            Log.i("alex", "send: "+toChatUsername);
            EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
            //如果是群聊，设置chattype，默认是单聊
            //发送消息
            EMClient.getInstance().chatManager().sendMessage(message);

            message.setMessageStatusCallback(new EMCallBack() {
                @Override public void onSuccess() {
                    // 消息发送成功，打印下日志，正常操作应该去刷新ui
                    Log.i("SendNotice", "send message on success");
                    //Looper.prepare();
                    //Toast.makeText(getActivity(),"发送提醒消息成功",Toast.LENGTH_SHORT).show();
                    // Looper.loop();
                    new Thread() {
                        public void run() {
                            Message msg = new Message();
                            msg.obj = "取件提醒发送成功";
                            // 把消息发送到主线程，在主线程里现实Toast
                            handler.sendMessage(msg);
                        };
                    }.start();

                }

                @Override public void onError(int i, String s) {
                    // 消息发送失败，打印下失败的信息，正常操作应该去刷新ui
                    Log.i("SendNotice", "send message on error " + i + " - " + s);
                    //Toast.makeText(getContext(), "发送提醒消息失败",Toast.LENGTH_SHORT).show();
                    new Thread() {
                        public void run() {
                            Message msg = new Message();
                            msg.obj = "取件提醒发送失败";
                            // 把消息发送到主线程，在主线程里现实Toast
                            handler.sendMessage(msg);
                        };
                    }.start();
                }

                @Override public void onProgress(int i, String s) {
                    // 消息发送进度，一般只有在发送图片和文件等消息才会有回调，txt不回调
                }
            });
        }
        else {
            new Thread() {
                public void run() {
                    Message msg = new Message();
                    msg.obj = "未编辑提醒内容";
                    // 把消息发送到主线程，在主线程里现实Toast
                    handler.sendMessage(msg);
                };
            }.start();
        }

    }

    private void startChat(int position){
        //EMConversation conversation = conversationListView.getItem(position);
        //String username = conversation.conversationId();
        Log.d(TAG, "startChat: "+chatUsername);
        Intent intent = new Intent(getActivity(), ChatActivity.class);

        // it's single chat
        intent.putExtra(Constant.EXTRA_USER_ID, chatUsername);
        startActivity(intent);
    }

    // Handler内部类，它的引用在子线程中被使用，发送mesage，被handlerMesage方法接收
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            String str = (String) msg.obj;
            Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
        };
    };

}
