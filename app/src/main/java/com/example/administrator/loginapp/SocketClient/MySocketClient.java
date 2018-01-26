package com.example.administrator.loginapp.SocketClient;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class MySocketClient {
    //线程池
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(3);
    //客户端的socket
    private Socket mSocket;
    private boolean first_connect = true;
    private BufferedWriter mBufferedWriter = null;
    private BufferedReader mBufferedReader=null;

    private static final String TAG = "test";
    private StringBuffer stringBuffer = null;

    public void getInput(final String str) {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    if (first_connect) {
                        //连接服务器端
                        mSocket = new Socket("192.168.132.41", 1122);
                        first_connect = false;
                        mBufferedWriter = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream()));
                    }

                    mBufferedWriter.write(str + "\n");
                    mBufferedWriter.flush();

                    getInfoFromServer();

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage().toString());
                }
            }
        });


    }
    //接受服务器发送过来的消息
    public void getInfoFromServer(){

       new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   mBufferedReader=new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                   String receiveMsg;
                   while (  (receiveMsg=mBufferedReader.readLine())!=null) {

                       Log.e(TAG, "run: " + receiveMsg);
                   }

               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }).start();







    }
    //关闭各种流文件，防止内存泄漏
    public void closeFile() {
        try {

            if (mBufferedWriter != null && mSocket != null) {
                mBufferedWriter.close();
                mSocket.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //客户端断开连接，（服务器端仍在运行，反之也是）
    public void stopConnected(){
        if(mBufferedWriter!=null){
            try {
                mBufferedWriter.write("close" + "\n");
                mBufferedWriter.flush();
                Log.e(TAG, "stopConnected: run" );
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
