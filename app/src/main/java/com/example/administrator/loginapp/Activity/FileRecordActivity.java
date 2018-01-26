package com.example.administrator.loginapp.Activity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.loginapp.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class FileRecordActivity extends BaseActivity {
    private LinearLayout speeking_view;
    private ImageView voice_ling1;
    private ImageView voice_ling2;
    private ImageView voice_ling3;
    private ImageView voice_ling4;
    private TextView tv_text;
    private Button btn_say;
    private Button btn_play;
    private ScheduledExecutorService mExecutorService= Executors.newSingleThreadScheduledExecutor();
    //录音文件
    private File mfile;
    //录音类
    private MediaRecorder mMediaRecorder;
    private long startTime,stopTime;
    private volatile boolean mIsPlaying;
    private MediaPlayer mMediaPlayer;
    private boolean mIsRecording;
    //MedioRecord.getAmplitude返回的最大值是32767
    private static final int MAX_AMPLITUDE=32767;
    private static final int AMPLITUDE_LEVEL=8;
    private Random mRandom=new Random(System.currentTimeMillis());
    List<ImageView> listIma=new ArrayList<>();


   private Handler mHandler=new Handler(){
       @Override
       public void handleMessage(Message msg) {

       }
   } ;



    @Override
    protected int getLayoutRes() {
        return R.layout.activity_file_record;
    }
    @Override
    protected void init() {
        initViews();

    }

    private void initViews() {
        tv_text= (TextView) findViewById(R.id.tv_text);
        btn_say= (Button) findViewById(R.id.btn_say);
        btn_play= (Button) findViewById(R.id.btn_play);
        speeking_view= (LinearLayout) findViewById(R.id.speeking_view);
        voice_ling1= (ImageView) findViewById(R.id.img_line1);
        voice_ling2= (ImageView) findViewById(R.id.img_line2);
        voice_ling3= (ImageView) findViewById(R.id.img_line3);
        voice_ling4= (ImageView) findViewById(R.id.img_line4);
        listIma.add(voice_ling4);
        listIma.add(voice_ling3);
        listIma.add(voice_ling2);
        listIma.add(voice_ling1);
    }

    @Override
    protected void initListener() {

        btn_say.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //开始录音
                        startRecord();
                        break;
                    case MotionEvent.ACTION_UP:
                        //停止录音
                        stopRecord();
                        break;
                    case MotionEvent.ACTION_CANCEL:

                        break;
                    default:
                        break;
                }

                return true;
            }


        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //播放录音
                playRecord(mfile);
            }
        });
    }



    private void startRecord() {
        mIsRecording=true;
        btn_say.setText("正在说话");
        speeking_view.setVisibility(View.VISIBLE);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                //释放之前的录音
                releaseRecord();
                //录音失败
                if(!doStart()){
                    recordFail();
                }
            }
        });
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                //获取声音的大小
                getRecordAmplitude();
            }
        });
    }

    private void getRecordAmplitude() {
        if(mMediaRecorder==null){
            return;
        }
        int amplitude;
        try {
            amplitude=mMediaRecorder.getMaxAmplitude();
            Log.e( "refreshAudioAmplitude: ", String.valueOf(amplitude));

        }catch (RuntimeException e){
            e.printStackTrace();
            amplitude=mRandom.nextInt(MAX_AMPLITUDE);

        }


        //把音量化为4个等级
        final int level=amplitude/(MAX_AMPLITUDE/AMPLITUDE_LEVEL);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                refreshAudioAmplitude(level);
            }
        });
        //如果仍在录音，隔5毫秒获取音量大小
        if(mIsRecording){
            mExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    getRecordAmplitude();
                }
            },50, TimeUnit.MILLISECONDS );
        }
    }

    private void refreshAudioAmplitude(int level) {
        Log.e( "refreshAudioAmplitude: ", String.valueOf(level));

        for(int i=0;i<listIma.size();i++){
            listIma.get(i).setVisibility(i<level?View.VISIBLE:View.INVISIBLE);
        }
    }

    //录音失败
    private void recordFail() {
        mIsRecording=false;
        mfile=null;
        speeking_view.setVisibility(View.GONE);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FileRecordActivity.this, "录音失败", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private boolean doStart() {
        mMediaRecorder=new MediaRecorder();
        mfile=new File(Environment.getExternalStorageDirectory()+"/Record/"+System.currentTimeMillis()+".m4a");
        mfile.getParentFile().mkdir();
        try {


            mfile.createNewFile();
            //从麦克风采集
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //保存文件为mp4格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            //所以安卓都支持的采样频率
            mMediaRecorder.setAudioSamplingRate(44100);
            //通用编码格式
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            //音质较好的频率
            mMediaRecorder.setAudioEncodingBitRate(96000);
            //设置文件位置
            mMediaRecorder.setOutputFile(mfile.getAbsolutePath());
            //开始录音
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            //记录录音的时间
            startTime=System.currentTimeMillis();

        } catch (IOException |RuntimeException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    private void stopRecord() {
        mIsRecording=false;
        speeking_view.setVisibility(View.GONE);
        btn_say.setText("按下说话");
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                //执行停止录音的逻辑
                if(!doStop()){
                    recordFail();
                }
                //释放之前的录音
                releaseRecord();
            }
        });

    }
//停止录音
    private boolean doStop() {
        try{
            mMediaRecorder.stop();
            stopTime=System.currentTimeMillis();
            final int second= (int) ((stopTime-startTime)/1000);
            if(second>3){
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_text.setText("录音成功"+second+"秒");
                    }
                });
            }

        }catch (RuntimeException e){
            e.printStackTrace();
            return false;
        }



        return true;
    }
//释放录音
    private void releaseRecord() {
        //检查录音类不为空
        if(mMediaRecorder!=null){
            mMediaRecorder.release();
            mMediaRecorder=null;
        }
    }
    private void playRecord(final File mfile) {
        //检查当前播放状态，防止重复播放
        if(mfile!=null && !mIsPlaying){
            mIsPlaying=true;
            mExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                   doPlay(mfile);
                }
            });
        }
    }

    private void doPlay(File mfile) {
        mMediaPlayer=new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(mfile.getPath());
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlay();
                }
            });
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    playFail();
                    stopPlay();
                    return true;
                }
            });

            mMediaPlayer.setVolume(1,1);
            mMediaPlayer.setLooping(false);
            mMediaPlayer.prepare();
            mMediaPlayer.start();


        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            playFail();
            stopPlay();
        }

    }

    private void playFail() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FileRecordActivity.this, "播放失败", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExecutorService.shutdownNow();
        releaseRecord();
        stopPlay();
    }

    private void stopPlay() {
        mIsPlaying=false;
        if(mMediaPlayer!=null){
            mMediaPlayer.setOnCompletionListener(null);
            mMediaPlayer.setOnErrorListener(null);
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer=null;
        }

    }
}
