package com.weimao.imgod.gk.weimaoautoshare;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import okhttp3.Call;

/**
 * 微猫理财
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SPLIT_CHAR_ENG = ",";//英语分隔符
    public static final String SPLICT_CHAR_CHINESE = "，";//汉语分隔符
    public static final String SHARE_URL = "http://www.qihumoney.com/tinycat/tinycat/front/customer/sharing?customer.id=";
    private EditText etxt_ids;
    private Button btn_start;
    private NestedScrollView nsv_main;
    private TextView txt_content;

    public static final int MSG_TYPE = 0x00;
    private boolean isShareing = false;//是不是正在分享
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_TYPE:
                    if (isShareing) {
                        if (shareCount > 20) {
                            isShareing = false;
                            btn_start.setText("开始");
                            Toast.makeText(MainActivity.this, "今日分享已达到最大次数", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        shareId(idArray[sharePosition]);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        nsv_main.fullScroll(NestedScrollView.FOCUS_DOWN);
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        btn_start.setOnClickListener(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        etxt_ids = (EditText) findViewById(R.id.etxt_ids);
        btn_start = (Button) findViewById(R.id.btn_start);
        nsv_main = (NestedScrollView) findViewById(R.id.nsv_main);
        txt_content = (TextView) findViewById(R.id.txt_content);
        String spContent = SPUtils.getString(MainActivity.this, SP_KEY);
        etxt_ids.setText(spContent);
        etxt_ids.setSelection(spContent.length());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                btnClcik();
                break;
        }
    }

    private String[] idArray;
    public static final String SP_KEY = "ids";//存储到手机本地.以供明天进来直接开刷.不用再复制粘贴了

    /**
     * 按钮点击事件
     */
    private void btnClcik() {
        String ids = etxt_ids.getText().toString();
        if (TextUtils.isEmpty(ids)) {
            Toast.makeText(MainActivity.this, getString(R.string.warn_title), Toast.LENGTH_SHORT).show();
            return;
        }

        if (ids.contains(SPLIT_CHAR_ENG) && ids.contains(SPLICT_CHAR_CHINESE)) {
            Toast.makeText(MainActivity.this, R.string.eng_char_and_chinese_char, Toast.LENGTH_SHORT).show();
            return;
        }

        if (ids.contains(SPLIT_CHAR_ENG)) {
            idArray = ids.split(SPLIT_CHAR_ENG);
        } else {
            idArray = ids.split(SPLICT_CHAR_CHINESE);
        }
        String btnContent = btn_start.getText().toString();
        SPUtils.put(MainActivity.this, SP_KEY, ids);
        if (btnContent.equals(getResources().getString(R.string.start))) {
            isShareing = true;
            btn_start.setText(R.string.pause);
            handler.sendEmptyMessage(MSG_TYPE);
        } else {
            isShareing = false;
            btn_start.setText(R.string.start);
        }
    }

    private int shareCount = 0;//分享遍历的次数
    private int sharePosition = 0;//此次遍历分享到的位置
    private RequestCall requestCall;

    /**
     * 分享成功之后,进行获取积分
     *
     * @param id
     */
    private void shareId(String id) {
        requestCall = OkHttpUtils.get().url(SHARE_URL + id).build();
        requestCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("onError", "onError:" + e.getMessage());
                String content = txt_content.getText().toString() + "\n第" + (shareCount + 1) + "次遍历分享,id为:" + idArray[sharePosition] + "结果:分享失败";
                setWarnTextAndSetValue(content);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("onResponse", "onResponse:" + response);
                String content = txt_content.getText().toString() + "\n第" + (shareCount + 1) + "次遍历分享,id:" + idArray[sharePosition] + "结果:" + response;
                setWarnTextAndSetValue(content);
            }
        });
    }

    public static final int DELAY_TIME = 1000;//延迟时间

    /**
     * 设置文本内容以及设置一些下标
     *
     * @param content
     */
    private void setWarnTextAndSetValue(String content) {
        txt_content.setText(content);
        nsv_main.fullScroll(NestedScrollView.FOCUS_DOWN);//手动移动到底部
        sharePosition++;
        if (sharePosition >= idArray.length) {
            shareCount++;
            sharePosition = 0;
        }
        handler.sendEmptyMessageDelayed(MSG_TYPE, DELAY_TIME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != requestCall) {
            requestCall.cancel();
        }
    }
}
