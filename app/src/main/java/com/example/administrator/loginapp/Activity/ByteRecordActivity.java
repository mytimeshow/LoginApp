package com.example.administrator.loginapp.Activity;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.loginapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ByteRecordActivity extends BaseActivity {
    private TextView tv_text1;
    private Button btn_say1;
    private Button btn_play1;
    private volatile boolean mIsRecord;
    private ExecutorService mExecutorService= Executors.newSingleThreadExecutor();
    private File mfile;
    private long startTime,stopTime;
    private static final int SIZE=2048;
    private byte[] mbuffer=new byte[SIZE];
    private boolean mIsPlaying;
    private AudioTrack mAudioTrack;


    private FileOutputStream fileOutputStream;
    private AudioRecord mAudioRecord;


    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    } ;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_byte_record;
    }
    @Override
    protected void init() {
        initViews();
    }

    @Override
    protected void initListener() {
        btn_say1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecord();
            }
        });
        //播放录音
        btn_play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPlay(mfile);
            }
        });

    }


    private void initViews() {
        tv_text1= (TextView) findViewById(R.id.tv_text1);
        btn_say1= (Button) findViewById(R.id.btn_say1);
        btn_play1= (Button) findViewById(R.id.btn_play1);
    }
    private void startRecord() {

        if(mIsRecord){
            btn_say1.setText("按下说话");
            mIsRecord=false;


        }else {
            btn_say1.setText("正在说话");
            mIsRecord=true;
            mExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    if(!doStart()){
                        recordFail();
                    }
                }


            });
        }



    }
    //录音失败
    private void recordFail() {


        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ByteRecordActivity.this, "录音失败", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private boolean doStart() {
        //创建录音文件
        mfile=new File(Environment.getExternalStorageDirectory()+"/Record/"+System.currentTimeMillis()+".pcm");
        mfile.getParentFile().mkdir();
        try {
            mfile.createNewFile();


            //创建文件输出流
            fileOutputStream=new FileOutputStream(mfile);
            //配置AudioRecord
            int audioSource= MediaRecorder.AudioSource.MIC;
            int sampleRate=44100;
            int channelConfig= AudioFormat.CHANNEL_IN_DEFAULT;
            int audioFormat=AudioFormat.ENCODING_PCM_16BIT;
            //计算AudioRecord内部buffer最小的大小
            int minBufferSize=AudioRecord.getMinBufferSize(sampleRate,channelConfig,audioFormat);

                mAudioRecord=new AudioRecord(audioSource,sampleRate,channelConfig,audioFormat,Math.max(minBufferSize,SIZE));
            //开始录音
            mAudioRecord.startRecording();
            //记录录音时间
            startTime=System.currentTimeMillis();
            //循环读取数据，用于统计时长
            while (mIsRecord){
                int read=mAudioRecord.read(mbuffer,0,SIZE);
                if(read>0){
                    fileOutputStream.write(mbuffer,0,read);
                }else {
                    return false;
                }
            }

            //退出循环，停止录音，释放资源

            return doStop();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            if(mAudioRecord!=null){
                mAudioRecord.release();
            }
        }




    }


    //停止录音
    private boolean doStop() {
        try{

            mAudioRecord.stop();
            mAudioRecord.release();
            mAudioRecord=null;
            fileOutputStream.close();
            stopTime=System.currentTimeMillis();
            final int second= (int) ((stopTime-startTime)/1000);
            if(second>3){
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_text1.setText("录音成功"+second+"秒");
                    }
                });
            }

        }catch (RuntimeException |IOException e){
            e.printStackTrace();
            return false;
        }




        return true;
    }


    private void doPlay(final File m) {
        //检查当前播放状态，防止重复播放
        if(mfile!=null && !mIsPlaying){
            Log.e( "toPlay",":00" );
            mIsPlaying=true;
            mExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                 toPlay(m);
                }
            });
        }


    }

    private void toPlay(File m) {
        Log.e( "toPlay",": 1" );
        //扬声器播放,音乐类型播放
        int streamType= AudioManager.STREAM_MUSIC;
        //采样频率
        int sampleRate=44100;
        Log.e( "toPlay",": 2" );
        //录音时采用单声道，播放也是
        int channelConfig=AudioFormat.CHANNEL_IN_DEFAULT;
        Log.e( "toPlay",":3" );
        //dddd
        int audioFormat=AudioFormat.ENCODING_PCM_16BIT;
        Log.e( "toPlay",": 4" );
        //流模式
        int mode= AudioTrack.MODE_STREAM;
        Log.e( "toPlay",": 5" );
        //计算最小buffer大小
        int minBufferSize=AudioTrack.getMinBufferSize(sampleRate,channelConfig,audioFormat);
        Log.e( "toPlay",": 6" );

        Log.e( "toPlay",": 7" );
        FileInputStream mFileInputStream=null;
        Log.e( "toPlay",":8" );
        try {
            Log.e( "toPlay",":9" );
            mAudioTrack=new AudioTrack(streamType,sampleRate,channelConfig,audioFormat,
                    Math.max(minBufferSize,SIZE),mode);
            mFileInputStream=new FileInputStream(m);
            int read;
            while ((read=mFileInputStream.read(mbuffer))>0){
                int ret=mAudioTrack.write(mbuffer,0,read);

                mAudioTrack.play();

                Log.e( "toPlay",":15" );
                switch (ret){
                    case AudioTrack.ERROR_INVALID_OPERATION:
                    case AudioTrack.ERROR_BAD_VALUE:
                    case AudioManager.ERROR_DEAD_OBJECT:
                        playFail();
                        Log.e( "toPlay",":10" );
                        return;
                    default:
                        break;
                }
            }

        }catch (RuntimeException  e){
            Log.e( "toPlay",":11" );
            playFail();
            e.printStackTrace();
            Log.e("myerror", e.toString() );

        } catch (FileNotFoundException e) {
            Log.e( "toPlay",":12" );
            playFail();
            e.printStackTrace();
        } catch (IOException e) {
            Log.e( "toPlay",":13" );
            playFail();
            e.printStackTrace();
        } finally {
            Log.e( "toPlay",":14" );
            mIsPlaying=false;
            if(mFileInputStream!=null){
                closeFileInputStream(mFileInputStream);
            }
            if(mAudioTrack!=null){
                mAudioTrack.stop();
                mAudioTrack.release();
                mAudioTrack=null;
            }
        }
    }

    private void playFail() {
        mfile=null;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ByteRecordActivity.this, "播放失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void closeFileInputStream(FileInputStream mFileInputStream) {
        try {
            mFileInputStream.close();
            mFileInputStream=null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExecutorService.shutdownNow();

    }

}
