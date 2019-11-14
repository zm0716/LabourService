package com.agilefinger.labourservice.widget.voice;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.Time;

import com.agilefinger.labourservice.utils.PictureUtils;

import top.defaults.logger.Logger;

public class EaseVoiceRecorder {
    MediaRecorder recorder;
    static final String PREFIX = "voice";
    static final String EXTENSION = ".wav";//.m4a
    private boolean isRecording = false;
    private long startTime;
    private String voiceFilePath = null;
    private String voiceFileName = null;
    private File file;
    private Handler handler;
    private int mTime;  //开始录音计时，计时；（在reset()中置空） 单位为毫秒

    private static final int MSG_CURRENT_TIME = 0x111;     //当前语音时长
    private static final int MSG_COUNT_DOWN_DONE = 0x113;    //录音倒计时结束
    private static final int MSG_COUNT_DOWN = 0x112;     //最后10s倒计时

    public EaseVoiceRecorder(Handler handler) {
        this.handler = handler;
    }

    /**
     * start recording to the file
     */
    public String startRecording(Context appContext) {
        PictureUtils.mkdirMyPetRootDirectory();
        file = null;
        try {
            // need to create recorder every time, otherwise, will got exception
            // from setOutputFile when try to reuse
            if (recorder != null) {
                recorder.release();
                recorder = null;
            }
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            recorder.setAudioChannels(1); // MONO
            recorder.setAudioSamplingRate(44100); // 8000Hz
            recorder.setAudioEncodingBitRate(96000); // seems if change this to
            voiceFileName = getVoiceFileName("");
            voiceFilePath = PictureUtils.getMyPetRootDirectory() + voiceFileName;
            file = new File(voiceFilePath);
            recorder.setOutputFile(file.getAbsolutePath());
            recorder.prepare();
            isRecording = true;
            recorder.start();
        } catch (IOException e) {
            Logger.e("voice", "prepare() failed");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isRecording) {
                        android.os.Message msg = new android.os.Message();
                        msg.arg1 = recorder.getMaxAmplitude() * 4 / 0x7FFF;
                        int seconds = (int) (new Date().getTime() - startTime) / 1000;
                        if (seconds == 60) {
                            msg.arg2 = MSG_COUNT_DOWN_DONE;
                        } else if (seconds >= 50) {
                            msg.arg2 = MSG_COUNT_DOWN;
                            msg.obj = 60 - seconds;
                        } else {
                            msg.arg2 = MSG_CURRENT_TIME;
                            msg.obj = seconds;
                        }
                        handler.sendMessage(msg);
                        SystemClock.sleep(100);
                    }
                } catch (Exception e) {
                    // from the crash report website, found one NPE crash from
                    // one android 4.0.4 htc phone
                    // maybe handler is null for some reason
                    Logger.e("voice", e.toString());
                }
            }
        }).start();
        startTime = new Date().getTime();
        Logger.d("voice", "start voice recording to file:" + file.getAbsolutePath());
        return file == null ? null : file.getAbsolutePath();
    }


    /**
     * stop the recoding
     *
     * @return seconds of the voice recorded
     */

    public void discardRecording() {
        if (recorder != null) {
            try {
                recorder.stop();
                recorder.release();
                recorder = null;
                if (file != null && file.exists() && !file.isDirectory()) {
                    file.delete();
                }
            } catch (IllegalStateException e) {
            } catch (RuntimeException e) {
            }
            isRecording = false;
        }
    }

    public int stopRecoding() {
        if (recorder != null) {
            mTime = 0;
            isRecording = false;
            recorder.stop();
            recorder.release();
            recorder = null;

            if (file == null || !file.exists() || !file.isFile()) {
                return EMError.FILE_INVALID;
            }
            if (file.length() == 0) {
                file.delete();
                return EMError.FILE_INVALID;
            }
            int seconds = (int) (new Date().getTime() - startTime) / 1000;
            Logger.d("voice", "voice recording finished. seconds:" + seconds + " file length:" + file.length());
            return seconds;
        }
        return 0;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (recorder != null) {
            recorder.release();
        }
    }

    private String getVoiceFileName(String uid) {
        Time now = new Time();
        now.setToNow();
        return uid + now.toString().substring(0, 15) + EXTENSION;
    }

    public boolean isRecording() {
        return isRecording;
    }


    public String getVoiceFilePath() {
        return voiceFilePath;
    }

    public String getVoiceFileName() {
        return voiceFileName;
    }


}
