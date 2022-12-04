package jp.co.kazono.tap_to_silent;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotificationManager notificationManager = getNotificationManager();
        showDoNotDisturbAuthSettingPage(notificationManager);
        if (notificationManager.isNotificationPolicyAccessGranted()) {
            intoSilentMode();
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private void showDoNotDisturbAuthSettingPage(NotificationManager notificationManager) {
        // must ask permission to access notification policy if be able to set ringer mode to silent.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(
                android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        }
    }

    private void intoSilentMode() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        try {
            // audioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, 0);
            // audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            // Toast.makeText(this, "Enable Silent Mode.", Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            e.printStackTrace();
            Toast.makeText(this, "ERROR: Please contact to developer.", Toast.LENGTH_LONG).show();
        }
    }
}
