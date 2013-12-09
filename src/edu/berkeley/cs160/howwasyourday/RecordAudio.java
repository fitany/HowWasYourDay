package edu.berkeley.cs160.howwasyourday;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RecordAudio extends Activity {
    private ImageButton mAudioRecordBtn;
    private File mRecAudioFile; // 录制的音频文件
    private File mRecAudioPath; // 录制的音频文件路徑
    private MediaRecorder mMediaRecorder;// MediaRecorder对象
    private String strTempFile = "recaudio_";// 零时文件的前缀
    public int seconds = 0;
    public int minutes = 0;
    public Timer t;
    private int count = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_record_audio);
            initRecAudioPath();
            initButton();
            
    }

    private void initButton() {
            mAudioRecordBtn = (ImageButton) findViewById(R.id.record);
            
            /* 开始按钮事件监听 */
            mAudioRecordBtn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                            /* 按钮状态 */
                    		if (count == 0) {
                    			mAudioRecordBtn.setImageResource(R.drawable.stop_icon);
                    			mAudioRecordBtn.setBackgroundColor(Color.TRANSPARENT);
                    			mHandler.sendEmptyMessage(MSG_RECORD);
                    			count++;
                    		} else {
                    			mHandler.sendEmptyMessage(MSG_STOP);
                    			count = 0;
                    		}
                    }
            });
            
            mAudioRecordBtn.setImageResource(R.drawable.record_icon);
            mAudioRecordBtn.setBackgroundColor(Color.TRANSPARENT);
    }

    private void startRecord() {
            try {
                    if (!initRecAudioPath()) {
                            return;
                    }
                    
                    /* Set the timer */
                    t = new Timer();
                    //Set the schedule function and rate
                    t.scheduleAtFixedRate(new TimerTask() {

                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    TextView tv = (TextView) findViewById(R.id.timer);
                                    tv.setText(String.valueOf(minutes)+":"+String.valueOf(seconds));
                                    seconds += 1;

                                    if(seconds == 60)
                                    {
                                        tv.setText(String.valueOf(minutes)+":"+String.valueOf(seconds));

                                        seconds=0;
                                        minutes=minutes+1;

                                    }
                                }

                            });
                        }

                    }, 0, 1000);
                    
                    
                    /* 按钮状态 */
//                    mAudioStartBtn.setEnabled(false);
//                    mAudioStopBtn.setEnabled(true);
                    
                    /* ①Initial：实例化MediaRecorder对象 */
                    mMediaRecorder = new MediaRecorder();
                    /* ②setAudioSource/setVedioSource */
                    mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
                    /*
                     * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default
                     * THREE_GPP(3gp格式，H263视频
                     * /ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
                     */
                    mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                    /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default */
                    mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                    /* ②设置输出文件的路径 */
                    try {
                            mRecAudioFile = File.createTempFile(strTempFile, ".amr",
                                            mRecAudioPath);

                    } catch (Exception e) {
                            e.printStackTrace();
                    }
                    mMediaRecorder.setOutputFile(mRecAudioFile.getAbsolutePath());
                    /* ③准备 */
                    mMediaRecorder.prepare();
                    /* ④开始 */
                    mMediaRecorder.start();
            } catch (IOException e) {
                    e.printStackTrace();
            }
    }

    private void stopRecord() {
            if (mRecAudioFile != null) {
            		/* Stop Timer */
            		t.cancel();
            		seconds = 0;
            		minutes = 0;
                    /* 按钮状态 */
//                    mAudioStartBtn.setEnabled(true);
//                    mAudioStopBtn.setEnabled(false);
                    /* ⑤停止录音 */
                    mMediaRecorder.stop();
//                    /* 将录音文件添加到List中 */
//                    addItem(mRecAudioFile);
                    /* ⑥释放MediaRecorder */
                    mMediaRecorder.release();
                    mMediaRecorder = null;
                    renderAddComment();
            }
    }
    
    private void renderAddComment() {
    	Intent i = new Intent(this, AddComment.class);
		i.putExtra("path", mRecAudioFile.getAbsolutePath());
		i.putExtra("type", "audio");
		startActivity(i);
    }

    private static final int MSG_RECORD = 0;
    private static final int MSG_STOP = 1;
    private Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                    switch (msg.what) {
                    case MSG_RECORD:
                            startRecord();
                            break;
                    case MSG_STOP:
                            stopRecord();
                            break;
                    default:
                            break;
                    }
            };
    };

    private boolean sdcardIsValid() {
            if (Environment.getExternalStorageState().equals(
                            android.os.Environment.MEDIA_MOUNTED)) {
                    return true;
            } else {
                    Toast.makeText(getBaseContext(), "没有SD卡", Toast.LENGTH_LONG).show();
            }
            return false;
    }

    private boolean initRecAudioPath() {
            if (sdcardIsValid()) {
                    String path = Environment.getExternalStorageDirectory().toString()
                                    + File.separator + "record";// 得到SD卡得路径
                    mRecAudioPath = new File(path);
                    if (!mRecAudioPath.exists()) {
                            mRecAudioPath.mkdirs();
                    }
            } else {
                    mRecAudioPath = null;
            }
            return mRecAudioPath != null;
    }
}

/* 过滤文件类型 */
class MusicFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
            return (name.endsWith(".amr"));
    }
}
