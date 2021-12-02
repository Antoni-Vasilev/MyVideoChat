package com.truestart.myvideochat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

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
    }

    private void createOrJoinRoom(String roomName) {
        try {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL("https://meet.jit.si"))
                    .setRoom(roomName)
                    .setAudioMuted(false)
                    .setVideoMuted(false)
                    .setWelcomePageEnabled(false)
                    .build();
            JitsiMeetActivity.launch(this, options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}