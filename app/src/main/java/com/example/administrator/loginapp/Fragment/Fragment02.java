package com.example.administrator.loginapp.Fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.loginapp.R;
import com.example.administrator.loginapp.SocketClient.MySocketClient;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public class Fragment02 extends BaceFragment {
    private TextView tv;
    private Button btn;
    private EditText ed;
    MySocketClient   mySocketClient = new MySocketClient();
    @Override
    protected void initData() {

//        Toast.makeText(mActivity, String.valueOf(testJava.getTestJava().getName()), Toast.LENGTH_SHORT).show();

//        Class<?> aClass=testJava.class;
//        Field[] fields=aClass.getDeclaredFields();
//        Constructor<?> constructor = null;
//        testJava testJava = null;
//        try {
//            constructor=aClass.getConstructor();
//            testJava=(testJava)constructor.newInstance();
//        } catch (java.lang.InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//
//
//        try {
//            Field fieldww=aClass.getDeclaredField("name");
//            fieldww.setAccessible(true);
//           // fieldww.set(testJava,20);
//            tv.setText(fieldww.get(testJava)+"   "+fieldww.getType().getName());
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        for(Field field:fields){
//            field.setAccessible(true);
//           // tv.setText(field.getName()+"   "+field.getType().getName());
//        }


//        try {
//            Class<?> w=getActivity().getClassLoader().loadClass("test");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        Class<?> a=aClass.getSuperclass();
//        int aa=aClass.getModifiers();




    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        tv= (TextView) rootView.findViewById(R.id.tv);
        btn= (Button) rootView.findViewById(R.id.btn_send);
        ed= (EditText) rootView.findViewById(R.id.et_receive);



btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        mySocketClient.getInput(ed.getText().toString());
//        receiveMag();
    }
});



        


    }



    @Override
    protected int getLayout() {
        return R.layout.fragment_02;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mySocketClient.closeFile();
        mySocketClient.stopConnected();
    }
}
