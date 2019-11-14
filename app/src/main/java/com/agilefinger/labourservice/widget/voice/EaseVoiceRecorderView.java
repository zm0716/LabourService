package com.agilefinger.labourservice.widget.voice;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.PowerManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agilefinger.labourservice.R;

/**
 * Voice recorder view
 */
public class EaseVoiceRecorderView extends RelativeLayout {
    protected Context context;
    protected LayoutInflater inflater;
    protected Drawable[] micImages;
    protected EaseVoiceRecorder voiceRecorder;
    protected PowerManager.WakeLock wakeLock;
    protected ImageView micImage, countDownImage;
    protected TextView recordingHint;
    protected TextView recordingSeconds;
    private static final int MSG_CURRENT_TIME = 0x111;     //当前语音时长
    private static final int MSG_COUNT_DOWN_DONE = 0x113;    //录音倒计时结束
    private static final int MSG_COUNT_DOWN = 0x112;     //最后10s倒计时
    private boolean isLastTenSeconds = false;
    private EaseVoiceRecorderCallback mRecorderCallback;
    protected Handler micImageHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.arg2) {
                case MSG_CURRENT_TIME:
                    if (isLastTenSeconds) {
                        micImage.setVisibility(View.VISIBLE);
                        recordingSeconds.setVisibility(View.VISIBLE);
                        countDownImage.setVisibility(View.INVISIBLE);
                    }
                    isLastTenSeconds = false;
                    recordingSeconds.setText(msg.obj + "s");
                    break;
                case MSG_COUNT_DOWN://最后10s倒计时
                    if (!isLastTenSeconds) {
                        micImage.setVisibility(View.INVISIBLE);
                        recordingSeconds.setVisibility(View.INVISIBLE);
                        countDownImage.setVisibility(VISIBLE);
                    }
                    isLastTenSeconds = true;
                    int id = context.getResources().getIdentifier("n_" + msg.obj, "mipmap", context.getPackageName());
                    countDownImage.setImageResource(id);
                    break;
                case MSG_COUNT_DOWN_DONE:
                    //到时间了
                    int length = stopRecoding();
                    if (length > 0) {
                        if (mRecorderCallback != null) {
                            mRecorderCallback.onVoiceRecordComplete(getVoiceFilePath(), length);
                        }
                    } else if (length == EMError.FILE_INVALID) {
                        Toast.makeText(context, R.string.Recording_without_permission, Toast.LENGTH_SHORT).show();
                    }/* else {
                        Toast.makeText(context, R.string.The_recording_time_is_too_short, Toast.LENGTH_SHORT).show();
                    }*/
                    break;
            }
            // change image
            micImage.setImageDrawable(micImages[msg.arg1]);
        }
    };

    public EaseVoiceRecorderView(Context context) {
        super(context);
        init(context);
    }

    public EaseVoiceRecorderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EaseVoiceRecorderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.ease_widget_voice_recorder, this);

        micImage = (ImageView) findViewById(R.id.mic_image);
        countDownImage = (ImageView) findViewById(R.id.count_down_image);
        recordingHint = (TextView) findViewById(R.id.recording_hint);
        recordingSeconds = (TextView) findViewById(R.id.recording_seconds);
        voiceRecorder = new EaseVoiceRecorder(micImageHandler);

        // animation resources, used for recording
        micImages = new Drawable[]{getResources().getDrawable(R.drawable.ease_record_animate_01),
                getResources().getDrawable(R.drawable.ease_record_animate_02),
                getResources().getDrawable(R.drawable.ease_record_animate_03),
                getResources().getDrawable(R.drawable.ease_record_animate_04),
                getResources().getDrawable(R.drawable.ease_record_animate_05),};

        wakeLock = ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).newWakeLock(
                PowerManager.SCREEN_DIM_WAKE_LOCK, "demo");
    }


    /**
     * on speak button touched
     *
     * @param v
     * @param event
     */
    public boolean onPressToSpeakBtnTouch(View v, MotionEvent event, EaseVoiceRecorderCallback recorderCallback) {
        mRecorderCallback = recorderCallback;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
              //  try {
                    EaseChatRowVoicePlayer voicePlayer = EaseChatRowVoicePlayer.getInstance(context);
                    if (voicePlayer.isPlaying())
                        voicePlayer.stop();
                    v.setPressed(true);
                    startRecording();
              /*  } catch (Exception e) {
                    v.setPressed(false);
                }*/
                return true;
            case MotionEvent.ACTION_MOVE:
                if (event.getY() < 0) {
                    showReleaseToCancelHint();
                } else {
                    showMoveUpToCancelHint();
                }
                return true;
            case MotionEvent.ACTION_UP:
                v.setPressed(false);
                if (event.getY() < 0) {
                    // discard the recorded audio.
                    discardRecording();
                } else {
                    // stop recording and send voice file
                    try {
                        int length = stopRecoding();
                        if (length > 0) {
                            if (recorderCallback != null) {
                                recorderCallback.onVoiceRecordComplete(getVoiceFilePath(), length);
                            }
                        } else if (length == EMError.FILE_INVALID) {
                            Toast.makeText(context, R.string.Recording_without_permission, Toast.LENGTH_SHORT).show();
                        }/*else {
                            Toast.makeText(context, R.string.The_recording_time_is_too_short, Toast.LENGTH_SHORT).show();
                        }*/
                    } catch (Exception e) {
                        e.printStackTrace();
                        //Toast.makeText(context, R.string.send_failure_please, Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            default:
                discardRecording();
                return false;
        }
    }


    public interface EaseVoiceRecorderCallback {
        /**
         * on voice record complete
         *
         * @param voiceFilePath   录音完毕后的文件路径
         * @param voiceTimeLength 录音时长
         */
        void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength);
    }

    public void startRecording() {
        if (!EaseCommonUtils.isSdcardExist()) {
            Toast.makeText(context, R.string.Send_voice_need_sdcard_support, Toast.LENGTH_SHORT).show();
            return;
        }


       // try {
            wakeLock.acquire();
            this.setVisibility(View.VISIBLE);
            recordingHint.setText(context.getString(R.string.move_up_to_cancel));
            recordingHint.setBackgroundColor(Color.TRANSPARENT);
            voiceRecorder.startRecording(context);
       /* } catch (Exception e) {
            e.printStackTrace();
            if (wakeLock.isHeld())
                wakeLock.release();
            if (voiceRecorder != null)
                voiceRecorder.discardRecording();
            this.setVisibility(View.INVISIBLE);
            Toast.makeText(context, R.string.recoding_fail, Toast.LENGTH_SHORT).show();
            return;
        }*/
    }

    public void showReleaseToCancelHint() {
        recordingHint.setText(context.getString(R.string.release_to_cancel));
        recordingHint.setBackgroundResource(R.drawable.ease_recording_text_hint_bg);
    }

    public void showMoveUpToCancelHint() {
        recordingHint.setText(context.getString(R.string.move_up_to_cancel));
        recordingHint.setBackgroundColor(Color.TRANSPARENT);
    }

    public void discardRecording() {
        if (wakeLock.isHeld())
            wakeLock.release();
        try {
            // stop recording
            if (voiceRecorder.isRecording()) {
                voiceRecorder.discardRecording();
                this.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
        }
    }

    public int stopRecoding() {
        this.setVisibility(View.INVISIBLE);
        if (wakeLock.isHeld())
            wakeLock.release();
        return voiceRecorder.stopRecoding();
    }

    public String getVoiceFilePath() {
        return voiceRecorder.getVoiceFilePath();
    }

    public String getVoiceFileName() {
        return voiceRecorder.getVoiceFileName();
    }

    public boolean isRecording() {
        return voiceRecorder.isRecording();
    }

}
