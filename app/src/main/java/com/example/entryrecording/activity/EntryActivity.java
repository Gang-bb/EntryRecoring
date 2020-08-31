package com.example.entryrecording.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.czt.mp3recorder.MP3Recorder;
import com.example.entryrecording.R;
import com.example.entryrecording.bean.Entry;
import com.example.entryrecording.bean.Sound;
import com.example.entryrecording.mydao.entryDao;
import com.example.entryrecording.mydao.soundDao;
import com.example.entryrecording.utils.DateUtil;
import com.example.entryrecording.utils.ShakeAnimatorUtil;
import com.example.entryrecording.utils.ToastUtils;
import com.example.entryrecording.widget.MyToolBar;
import com.piterwilson.audio.MP3RadioStreamDelegate;
import com.piterwilson.audio.MP3RadioStreamPlayer;
import com.shuyu.waveview.AudioPlayer;
import com.shuyu.waveview.AudioWaveView;
import com.shuyu.waveview.FileUtils;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EntryActivity extends AppCompatActivity implements MP3RadioStreamDelegate {

    static int position;
    List<Entry> entries;
    Entry entry;

    MP3Recorder mRecorder;
    AudioPlayer audioPlayer;

    MP3RadioStreamPlayer player = new MP3RadioStreamPlayer();

    static String filePath;

    boolean mIsRecord = false;

    boolean mIsPlay = false;

    Sound sound = new Sound();

    String beginTime;
    String endTime;
    soundDao soundDao;
    entryDao entryDao;
    @BindView(R.id.btn_delete)
    Button btnDelete;

    private Handler mHandler = new Handler();

    private boolean isPlaying = false;
    private boolean playeEnd = false;
    private boolean isShow = false;


    @BindView(R.id.btn_Chinese)
    Button btnChinese;
    @BindView(R.id.btn_English)
    Button btnEnglish;
    @BindView(R.id.t_English)
    TextView tEnglish;
    @BindView(R.id.t_Chinese)
    TextView tChinese;
    @BindView(R.id.audioWave_play)
    AudioWaveView audioWavePlay;
    @BindView(R.id.audioWave)
    AudioWaveView audioWave;
    @BindView(R.id.btn_before)
    Button btnBefore;
    @BindView(R.id.btn_record_sound)
    Button btnRecordSound;
    @BindView(R.id.btn_record_end)
    Button btnRecordEnd;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.toolbar)
    MyToolBar toolbar;
    @BindView(R.id.file_name_text_view)
    TextView fileNameTextView;
    @BindView(R.id.seekbar)
    SeekBar seekbar;
    @BindView(R.id.current_progress_text_view)
    TextView currentProgressTextView;
    @BindView(R.id.fab_play)
    FloatingActionButton fabPlay;
    @BindView(R.id.file_length_text_view)
    TextView fileLengthTextView;
    @BindView(R.id.Layout_play)
    LinearLayout LayoutPlay;
    @BindView(R.id.btn_pause)
    Button btnPause;
    @BindView(R.id.btn_showSound)
    Button btnShowSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        ButterKnife.bind(this);
        checkPermission();
        position = (int) getIntent().getIntExtra("key_position", 0);
        entries = (List<Entry>) getIntent().getSerializableExtra("key_entries");
        entry = (Entry) getIntent().getSerializableExtra("key_entry");
        ToastUtils.show(this, entry.getE_id());


        showdata();
        initaudioPlayer();
        // setSeekbar();
    }

    //事件监听
    @OnClick({R.id.btn_English, R.id.btn_Chinese, R.id.btn_next, R.id.btn_before, R.id.btn_record_sound, R.id.btn_record_end, R.id.fab_play, R.id.btn_pause, R.id.btn_showSound,R.id.btn_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_English:
                toEnglish();
                break;
            case R.id.btn_Chinese:
                toChinese();
                break;
            case R.id.btn_before:
                toBefore();
                break;
            case R.id.btn_next:
                toNext();
                break;
            case R.id.btn_record_sound:
                record();
                break;
            case R.id.btn_record_end:
                resolveStopRecord();
                break;
            case R.id.fab_play:
                onPlay(isPlaying);
                isPlaying = !isPlaying;
                break;
            case R.id.btn_pause:
                resolvePause();
                break;
            case R.id.btn_showSound:
                showSound(isShow);
                isShow = !isShow;
                break;
            case R.id.btn_delete:
                delrteEntry();
                ToastUtils.show(view.getContext(),"删除");
                break;
        }
    }

    //删除词条
    public void delrteEntry() {
        new TDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_delete_entry)
                .setWidth(600)  //设置弹窗宽度(px)
                .setHeight(800)  //设置弹窗高度(px)
                .setScreenWidthAspect(this, 0.8f)   //设置弹窗宽度(参数aspect为屏幕宽度比例 0 - 1f)
                .setScreenHeightAspect(this, 0.3f)  //设置弹窗高度(参数aspect为屏幕宽度比例 0 - 1f)
                .setGravity(Gravity.CENTER)     //设置弹窗展示位置\
                .setTag("DialogTest")   //设置Tag
                .setDimAmount(0.5f)     //设置弹窗背景透明度(0-1f)
                .setCancelableOutside(true)     //弹窗在界面外是否可以点击取消
                .addOnClickListener(R.id.btn_sure, R.id.btn_cancel)   //添加进行点击控件的id
                .setOnBindViewListener(new OnBindViewListener() {
                    @Override
                    public void bindView(BindViewHolder viewHolder) {
                    }
                })
                .setOnViewClickListener(new OnViewClickListener() {     //View控件点击事件回调
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.btn_sure:

                                ToastUtils.show(view.getContext(), "删除111");
                                break;
                            case R.id.btn_cancel:
                                break;
                        }
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        return false;
                    }
                })
                .create()   //创建TDialog
                .show();    //展示


    }

    //删除词条
    private void deteleEntry(){
        entry.setE_spare1("0");//0表示删除状态
        entryDao = new entryDao();
        entryDao.detele(entry);
    }

    //配置seekbar
    public void setSeekbar() {
        ColorFilter filter = new LightingColorFilter
                (getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary));
        seekbar.getProgressDrawable().setColorFilter(filter);
        seekbar.getThumb().setColorFilter(filter);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (player != null && fromUser) {
                    player.seekTo(progress);
                    mHandler.removeCallbacks(mRunnable);

                    long minutes = TimeUnit.MILLISECONDS.toMinutes((int) player.getCurPosition());
                    long seconds = TimeUnit.MILLISECONDS.toSeconds((int) player.getCurPosition())
                            - TimeUnit.MINUTES.toSeconds(minutes);
                    currentProgressTextView.setText(String.format("%02d:%02d", minutes, seconds));

                    updateSeekBar();

                } else if (player == null && fromUser) {
                    // prepareMediaPlayerFromPoint(progress);
                    updateSeekBar();
                }
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    //updating mSeekBar
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (player != null) {

                long mCurrentPosition = player.getCurPosition();
                seekbar.setProgress((int) mCurrentPosition);

                long minutes = TimeUnit.MILLISECONDS.toMinutes(mCurrentPosition);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(mCurrentPosition)
                        - TimeUnit.MINUTES.toSeconds(minutes);
                currentProgressTextView.setText(String.format("%02d:%02d", minutes, seconds));

                updateSeekBar();
            }
        }
    };

    private void updateSeekBar() {
        mHandler.postDelayed(mRunnable, 1000);
    }


//    @Override
//    public void onPause() {
//        super.onPause();
//        if (mIsRecord) {
//            resolveStopRecord();
//        }
//        if (mIsPlay) {
//            audioPlayer.pause();
//            audioPlayer.stop();
//        }
//    }

    //控制暂停和开始
    private void onPlay(boolean isPlaying) {
//        if (playeEnd) {
//            stop();
//            //playBtn.setText("暂停");
//            fabPlay.setImageResource(R.drawable.ic_media_pause);
//            seekbar.setEnabled(true);
//            resolvePlayRecord();
//            return;
//        }
//
//        if (player.isPause()) {
//            // playBtn.setText("暂停");
//            fabPlay.setImageResource(R.drawable.ic_media_pause);
//            player.setPause(false);
//            seekbar.setEnabled(false);
//        } else {
//            // playBtn.setText("播放");
//            fabPlay.setImageResource(R.drawable.ic_media_play);
//            player.setPause(true);
//            seekbar.setEnabled(true);
//        }

        if (!isPlaying) {
            fabPlay.setImageResource(R.drawable.ic_media_pause);
            resolvePlayRecord(); //start from beginning
        } else {
            fabPlay.setImageResource(R.drawable.ic_media_play);
            player.setPause(true);
        }
    }


    /**
     * 播放
     */
    private void resolvePlayRecord() {
//        if (TextUtils.isEmpty(filePath) || !new File(filePath).exists()) {
//            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        audioPlayer.playUrl(filePath);
        audioWave.setVisibility(View.GONE);
        audioWavePlay.setVisibility(View.VISIBLE);

        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
        player = new MP3RadioStreamPlayer();
        //player.setUrlString(this, true, "http://www.stephaniequinn.com/Music/Commercial%20DEMO%20-%2005.mp3");
        if (!sound.getS_path().equals("")) {
            player.setUrlString(sound.getS_path());
            player.setDelegate(this);

            int size = getScreenWidth(this) / dip2px(this, 1);//控件默认的间隔是1
            player.setDataList(audioWavePlay.getRecList(), size);
            //player.setWaveSpeed(1100);
            //mRecorder.setDataList(audioWave.getRecList(), size);
            //player.setStartWaveTime(5000);
            //audioWave.setDrawBase(false);
            audioWavePlay.setBaseRecorder(player);
            audioWavePlay.startView();
            try {
                seekbar.setMax((int) player.getDuration());
                player.play();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.show(this, "读取文件异常，请重新录音！");
            noSoundUI();
        }

    }


    /**
     * 录音异常
     */
    private void resolveError() {

        FileUtils.deleteFile(filePath);
        filePath = "";
        if (mRecorder != null && mRecorder.isRecording()) {
            mRecorder.stop();
            audioWave.stopView();
        }
    }

    private void checkPermission() {
        // 检查权限是否获取（android6.0及以上系统可能默认关闭权限，且没提示）
        PackageManager pm = getPackageManager();
        boolean permission_readStorage = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.READ_EXTERNAL_STORAGE", "packageName"));
        boolean permission_writeStorage = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", "packageName"));
        boolean permission_caremera = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.RECORD_AUDIO", "packageName"));

        if (!(permission_readStorage && permission_writeStorage && permission_caremera)) {
            ActivityCompat.requestPermissions(EntryActivity.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
            }, 0x01);
        }
    }


    /**
     * 开始录音
     */
    public void record() {
        startSoundUI();
        filePath = FileUtils.getAppPath();
        File file = new File(filePath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Toast.makeText(this, "创建文件失败", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        int offset = dip2px(this, 1);
        filePath = FileUtils.getAppPath() + entry.getE_id() + ".mp3";
        mRecorder = new MP3Recorder(new File(filePath));
        int size = getScreenWidth(this) / offset;//控件默认的间隔是1
        mRecorder.setDataList(audioWave.getRecList(), size);

        mRecorder.setErrorHandler(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == MP3Recorder.ERROR_TYPE) {
                    resolveError();
                }
            }
        });

        try {
            mRecorder.start();
            audioWave.startView();
            beginTime = DateUtil.getTime();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "录音出现异常", Toast.LENGTH_SHORT).show();
            resolveError();
            return;
        }
        mIsRecord = true;


    }

    /**
     * 停止录音
     */
    private void resolveStopRecord() {
        endSoundUI();
        if (mRecorder != null && mRecorder.isRecording()) {
            mRecorder.setPause(false);
            mRecorder.stop();
            audioWave.stopView();
            endTime = DateUtil.getTime();
            addtoSound();
        }
        mIsRecord = false;
    }

    /**
     * 暂停录音
     */
    private void resolvePause() {
        if (!mIsRecord)
            return;
        pauseSoundUI();
        if (mRecorder.isPause()) {
            pauseSoundUI();
            audioWave.setPause(false);
            mRecorder.setPause(false);
            btnPause.setText("暂停");
        } else {
            audioWave.setPause(true);
            mRecorder.setPause(true);
            btnPause.setText("继续");
        }
    }

    private void stop() {
        player.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        audioWavePlay.stopView();
        stop();
    }

    //添加录音信息到数据库
    public void addtoSound() {
        if (!beginTime.equals("") && !endTime.equals("")) {
            int timesound = Integer.parseInt(endTime) - Integer.parseInt(beginTime);
            sound = new Sound();
            sound.setS_id("sound_" + entry.getE_id());
            sound.setS_entryid(entry.getE_id());
            sound.setS_filetype("mp3");
            sound.setS_path(filePath);
            sound.setS_baseid(entry.getE_baseid());
            sound.setS_name("词条" + entry.getE_id() + ".mp3");
            sound.setS_createtime(DateUtil.getStringDate());
            sound.setS_time(timesound + "");
            soundDao = new soundDao();
            soundDao.insertSound(sound);
        } else {
            ToastUtils.show(this, "录音出现问题，请重新录音");
            noSoundUI();
        }

    }

    //打开播放视图
    public void showSound(boolean isShow) {
        if (!isShow) {
            soundDao = new soundDao();
            List<Sound> sounds = soundDao.findby(entry.getE_id());
            if (sounds != null) {
                sound = sounds.get(0);
                reShowSoundUI();
            } else {
                ToastUtils.show(this, "打开文件异常，请重新录音");
                noSoundUI();
            }
        } else {
            stopShowSoundUI();
        }


    }


    //初始化audioPlayer
    public void initaudioPlayer() {
        audioPlayer = new AudioPlayer(this, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case AudioPlayer.HANDLER_CUR_TIME://更新的时间
                        // curPosition = (int) msg.obj;
                        break;
                    case AudioPlayer.HANDLER_COMPLETE://播放结束
                        mIsPlay = false;
                        break;
                    case AudioPlayer.HANDLER_PREPARED://播放开始
                        // duration = (int) msg.obj;
                        break;
                    case AudioPlayer.HANDLER_ERROR://播放错误
                        // resolveResetPlay();
                        break;
                }

            }
        });

    }


    //初始化数据
    public void showdata() {
        soundDao = new soundDao();
        sound = soundDao.findbyid("sound_" + entry.getE_id());
        if (sound != null) {
            havaSoundUI();
        } else {
            noSoundUI();
        }
        tChinese.setText(entry.getE_content());
        tEnglish.setText(entry.getE_english());
    }


    //上一条
    private void toBefore() {
        position = position - 1;
        if (position >= 0 && position < entries.size()) {
            entry = entries.get(position);
            tChinese.setText(entry.getE_content());
            tEnglish.setText(entry.getE_english());
            toChinese();
            stopShowSoundUI();
        } else {
            ToastUtils.show(this, "这里是第一个了哦~");
        }

    }
    private  void toDelete(){
        position = position + 1;
        if (position >= 0 && position < entries.size()) {
            entry = entries.get(position);
            showdata();
            toChinese();
            stopShowSoundUI();
        } else {
            ToastUtils.show(this, "这里是最后一个了哦~");
        }
    }

    //下一条
    private void toNext() {
        position = position + 1;
        if (position >= 0 && position < entries.size()) {
            entry = entries.get(position);
            showdata();
            toChinese();
            stopShowSoundUI();
        } else {
            ToastUtils.show(this, "这里是最后一个了哦~");
        }


    }


    //显示中文
    private void toChinese() {
        tChinese.setVisibility(View.VISIBLE);
        tEnglish.setVisibility(View.GONE);
    }


    //显示英文
    private void toEnglish() {
        tEnglish.setVisibility(View.VISIBLE);
        tChinese.setVisibility(View.GONE);
    }

    //没有录音的UI
    private void noSoundUI() {
        btnRecordSound.setEnabled(true);
        btnRecordEnd.setEnabled(false);
        btnPause.setEnabled(false);
        btnShowSound.setEnabled(false);
        LayoutPlay.setVisibility(View.GONE);
    }

    //有录音的UI
    private void havaSoundUI() {
        btnRecordSound.setEnabled(false);
        btnRecordEnd.setEnabled(false);
        btnPause.setEnabled(false);
        btnShowSound.setEnabled(true);
        LayoutPlay.setVisibility(View.GONE);
    }

    //录音开始时的UI
    private void startSoundUI() {
        btnRecordSound.setEnabled(false);
        btnRecordEnd.setEnabled(true);
        btnPause.setEnabled(true);
        btnShowSound.setEnabled(false);
    }

    //录音暂停的UI
    private void pauseSoundUI() {
        btnRecordSound.setEnabled(false);
        btnRecordEnd.setEnabled(true);
        btnPause.setEnabled(true);
        btnShowSound.setEnabled(false);
    }

    //录音结束的UI
    private void endSoundUI() {
        btnRecordSound.setEnabled(false);
        btnRecordEnd.setEnabled(false);
        btnPause.setEnabled(false);
        btnShowSound.setEnabled(true);
    }

    //打开播放视图UI
    private void reShowSoundUI() {
        fileNameTextView.setText(sound.getS_name());
        LayoutPlay.setVisibility(View.VISIBLE);
        btnShowSound.setText("关闭录音");
    }

    //关闭播放视图UI
    private void stopShowSoundUI() {
        LayoutPlay.setVisibility(View.GONE);
        btnShowSound.setText("打开录音");
    }


    /**
     * 获取屏幕的宽度px
     *
     * @param context 上下文
     * @return 屏幕宽px
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
        return outMetrics.widthPixels;
    }

    /**
     * 获取屏幕的高度px
     *
     * @param context 上下文
     * @return 屏幕高px
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
        return outMetrics.heightPixels;
    }

    /**
     * dip转为PX
     */
    public static int dip2px(Context context, float dipValue) {
        float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * fontScale + 0.5f);
    }


    @Override
    public void onRadioPlayerPlaybackStarted(final MP3RadioStreamPlayer player) {

//        this.runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                playeEnd = false;
//                //playBtn.setEnabled(true);
//                seekbar.setMax((int) player.getDuration());
//                seekbar.setEnabled(true);
//            }
//        });

    }

    @Override
    public void onRadioPlayerStopped(MP3RadioStreamPlayer player) {

        this.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                fabPlay.setImageResource(R.drawable.ic_media_play);
                audioWavePlay.stopView();
                isPlaying = false;
            }
        });

    }

    @Override
    public void onRadioPlayerError(MP3RadioStreamPlayer player) {

//        this.runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                playeEnd = false;
//                //playBtn.setEnabled(true);
//                seekbar.setEnabled(false);
//            }
//        });

    }

    @Override
    public void onRadioPlayerBuffering(MP3RadioStreamPlayer player) {
//        this.runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                //playBtn.setEnabled(false);
//                seekbar.setEnabled(false);
//            }
//        });
//
//    }


    }
}

