package com.truestart.myvideochat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {

    Button join;
    EditText groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        join = findViewById(R.id.join);
        groupName = findViewById(R.id.roomName);
        join.setOnClickListener(v -> createOrJoinRoom(groupName.getText().toString().trim()));
        loadRoomName();
    }

    private void loadRoomName() {
        SharedPreferences sharedPreferences = getSharedPreferences("App", MODE_PRIVATE);
        groupName.setText(sharedPreferences.getString("roomName", null));
    }

    @SuppressLint("GetInstance")
    private void createOrJoinRoom(String roomName) {
        saveRoomName(roomName);

        roomName = Arrays.toString(roomName.getBytes());

        try {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL("https://meet.jit.si"))
                    .setRoom(roomName)
                    .setWelcomePageEnabled(true)
                    .setAudioMuted(true)
                    .setVideoMuted(true)
                    .setWelcomePageEnabled(false)
                    .build();
            JitsiMeetActivity.launch(this, options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("CommitPrefEdits")
    private void saveRoomName(String roomName) {
        SharedPreferences.Editor sharedPreferences = getSharedPreferences("App", MODE_PRIVATE).edit();
        sharedPreferences.putString("roomName", roomName);
        sharedPreferences.apply();
    }
}