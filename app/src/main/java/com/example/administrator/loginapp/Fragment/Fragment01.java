package com.example.administrator.loginapp.Fragment;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.loginapp.Activity.testJava;
import com.example.administrator.loginapp.R;
import com.example.administrator.loginapp.Views.FlowLayout;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public class Fragment01 extends BaceFragment {
    private FlowLayout mFlowLayout;
    private EditText mEdit;
    private Button btn_commit;
    private WebView webView;
    private Button btn_useJs;
    private Button btn_useJs_result;

    @Override
    protected void initData() {
        testJava a=new testJava();
        a.setName("www");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        webView= (WebView) rootView.findViewById(R.id.circle_img);
        btn_useJs= (Button) rootView.findViewById(R.id.btn_useJs);
        mFlowLayout= (FlowLayout) rootView.findViewById(R.id.flow_layout);
        mEdit= (EditText) rootView.findViewById(R.id.edt_text);
        btn_commit= (Button) rootView.findViewById(R.id.btn_commit);
        btn_useJs_result= (Button) rootView.findViewById(R.id.btn_useJs_result);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setUseWideViewPort(true);

        webView.addJavascriptInterface(new JSBridge(),"android");
        webView.loadUrl("file:///android_asset/page.html");
      //java 调用js代码
        btn_useJs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:sum(6,6)");
            }
        });
        btn_useJs_result.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                webView.evaluateJavascript("result()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Toast.makeText(mActivity,value, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                String text=mEdit.getText().toString();
                TextView textView=new TextView(v.getContext());
                textView.setText(text);
                textView.setTextColor(Color.BLUE);
                //Drawable drawable=getResources().getDrawable(R.drawable.bg_circle_white,null);
                textView.setBackgroundResource(R.drawable.bg_oval);

                ViewGroup.LayoutParams l= (ViewGroup.MarginLayoutParams) textView.getLayoutParams();

              //  l.setMargins(Utils.px2dip(v.getContext(),20),0,0,0);
               // textView.setLayoutParams(l);
                mFlowLayout.addView(textView);

            }
        });

//            String[] aa={"dd","cc"};
//        MyUser myUser1=new MyUser();
//        myUser1.setNick("jack");ttps
//        MyUser myUser2=new MyUser();
//        myUser2.setNick("jonh");
//        final List<MyUser> myUserList=new ArrayList<>();
//        myUserList.add(myUser1);
//        myUserList.add(myUser2);
//
//        Observable.from(myUserList)
//                .filter(new Func1<MyUser, Boolean>() {
//                    @Override
//                    public Boolean call(MyUser myUser) {
//                        return myUser!=null;
//                    }
//                })
//                .flatMap(new Func1<MyUser, Observable<MyUser>>() {
//                    @Override
//                    public Observable<MyUser> call(MyUser myUser) {
//                        return null;
//                    }
//                }).subscribe(new Subscriber<MyUser>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//
//            }
//
//            @Override
//            public void onNext(MyUser myUser) {
//                Toast.makeText(mActivity, myUser.getNick(), Toast.LENGTH_SHORT).show();
//
//            }
//        });


    }
    public class  JSBridge{
        @JavascriptInterface
        public void showToast(String toast){
            Toast.makeText(mActivity, toast, Toast.LENGTH_SHORT).show();
            Log.e("webView","l am webview");
        }
    }




    @Override
    protected int getLayout() {
        return R.layout.bace_fragment;
    }
}
