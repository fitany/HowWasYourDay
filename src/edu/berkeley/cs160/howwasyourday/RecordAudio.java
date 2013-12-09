package edu.berkeley.cs160.howwasyourday;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class RecordAudio extends Activity {
	private Button mAudioStartBtn;
    private Button mAudioStopBtn;
    private File mRecAudioFile; // 录制的音频文件
    private File mRecAudioPath; // 录制的音频文件路徑
    private MediaRecorder mMediaRecorder;// MediaRecorder对象
    private String strTempFile = "recaudio_";// 零时文件的前缀
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_record_audio);
            initRecAudioPath();
            initButton();
    }

    private void initButton() {
            mAudioStartBtn = (Button) findViewById(R.id.mediarecorder1_AudioStartBtn);
            mAudioStopBtn = (Button) findViewById(R.id.mediarecorder1_AudioStopBtn);

            /* 开始按钮事件监听 */
            mAudioStartBtn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                            /* 按钮状态 */
                            mAudioStartBtn.setEnabled(false);
                            mAudioStopBtn.setEnabled(true);
                            mHandler.sendEmptyMessage(MSG_RECORD);
                    }
            });
            /* 停止按钮事件监听 */
            mAudioStopBtn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                            /* 按钮状态 */
                            mAudioStartBtn.setEnabled(true);
                            mAudioStopBtn.setEnabled(false);
                            mHandler.sendEmptyMessage(MSG_STOP);
                    }
            });

            /* 按钮状态 */
            mAudioStartBtn.setEnabled(true);
            mAudioStopBtn.setEnabled(false);
    }

    private void startRecord() {
            try {
                    if (!initRecAudioPath()) {
                            return;
                    }
                    /* 按钮状态 */
                    mAudioStartBtn.setEnabled(false);
                    mAudioStopBtn.setEnabled(true);
                    
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
                    /* 按钮状态 */
                    mAudioStartBtn.setEnabled(true);
                    mAudioStopBtn.setEnabled(false);
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
		i.putExtra("photo", mRecAudioFile.getAbsolutePath());
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

//    /* 播放录音文件 */
//    private void playMusic(File file) {
//            Intent intent = new Intent();
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setAction(android.content.Intent.ACTION_VIEW);
//            /* 设置文件类型 */
//            intent.setDataAndType(Uri.fromFile(file), "audio");
//            startActivity(intent);
//    }

//    private void setOnItemClickListener() {
//            mList.setOnItemClickListener(new OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> l, View v, int position,
//                                    long id) {
//                            List<Map<String, Object>> listdata = (List<Map<String, Object>>) mList.getTag();
//                            Map<String, Object> map = listdata.get(position);
//                            String name = (String) map.get("text");
//                            /* 得到被点击的文件 */
//                            File playfile = new File(mRecAudioPath.getAbsolutePath()
//                                            + File.separator + name);
//                            /* 播放 */
//                            playMusic(playfile);
//                    }
//            });
//    }

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
