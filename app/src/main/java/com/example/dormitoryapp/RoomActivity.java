package com.example.dormitoryapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.dormitoryapp.models.Room;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.dormitoryapp.Constants.ROOMS_BY_DORMITORY;

public class RoomActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Bundle resultIntent = getIntent().getExtras();
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        int DormNum = resultIntent.getInt("DormNum");

        executor.execute(() -> {
            //atitinka doInBackground
            try {
                String response = RESTController.sendGet(ROOMS_BY_DORMITORY + DormNum);
                handler.post(() -> {
                    if (!response.equals("") && !response.equals("")) {
                        Gson builder = new GsonBuilder().create();
                        Type projectListType = new TypeToken<List<Room>>() {
                        }.getType();
                        final List<Room> roomListFromJson = builder.fromJson(response, projectListType);

                        ListView roomList = findViewById(R.id.roomList);
                        ArrayAdapter<Room> arrayAdapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_list_item_1, roomListFromJson);
                        roomList.setAdapter(arrayAdapter);
                        roomList.setOnItemClickListener(((parent, view, position, id) -> {
                            Toast.makeText(RoomActivity.this, "Showing room " + roomListFromJson.get(position).getRoom_num() + " data", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(RoomActivity.this, EditActivity.class);
                            intent.putExtra("RoomNum", roomListFromJson.get(position).getRoom_num());
                            intent.putExtra("Available", roomListFromJson.get(position).isAvailable());
                            intent.putExtra("RoomType", roomListFromJson.get(position).getRoom_type());
                            startActivity(intent);
                        }));

                    }
                });

       /* Projects projects = new Projects();
        projects.execute();*/

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
