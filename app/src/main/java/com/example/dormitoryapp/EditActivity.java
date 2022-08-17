package com.example.dormitoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.dormitoryapp.databinding.ActivityEditBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.dormitoryapp.Constants.ROOM_UPDATE;
import static com.example.dormitoryapp.Constants.USER_LOGIN_URL;

public class EditActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

    }

    public void saveEveryting(View view) {
        Bundle resultIntent = getIntent().getExtras();
        EditText Available =findViewById(R.id.Available);
        EditText Roomtype =findViewById(R.id.Roomtype);
        int RoomNum = resultIntent.getInt("RoomNum");
        boolean Availableroom = resultIntent.getBoolean("Available");
        String type= resultIntent.getString("RoomType");
        Available.setText(Boolean.toString(Availableroom));
        Roomtype.setText(type);
        String data = "{\"room_type\":\"" + Roomtype.getText().toString() + "\", \"available\": \"" + Available.getText().toString()+"\"}";

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(()->{
            try {
                String response = RESTController.sendPut(ROOM_UPDATE+RoomNum, data);
                handler.post(()->{
                    if (!response.equals("")  && !response.equals(" ")) {
                        Intent intent = new Intent(EditActivity.this, RoomActivity.class);
                        intent.putExtra("UserInfo", response);
                        startActivity(intent);
                    } else {
                        Toast.makeText(EditActivity.this, "Errors while authenticating", Toast.LENGTH_LONG).show();
                    }
                });


            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
    }



