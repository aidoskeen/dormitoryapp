package com.example.dormitoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.dormitoryapp.models.Dormitory;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.dormitoryapp.Constants.*;

public class DormitoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dormitory);
    fillList();

    }
    public void fillList(){
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            try {
                String response = null;

                response = RESTController.sendGet(DORMITORY_ALL_URL);

                String finalResponse = response;
                handler.post(() -> {
                    if (!finalResponse.equals("") && !finalResponse.equals("Error")) {
                        //Uzpildysiu sarasa pagal db duomenis
                        //Zinau, kad gausiu JSON formatu

                        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                                .create();
                        Type projectListType = new TypeToken<List<Dormitory>>() {
                        }.getType();
                        final List<Dormitory> projectListFromJson = builder.fromJson(finalResponse, projectListType);

                        ListView dormList = findViewById(R.id.dormList);
                        ArrayAdapter<Dormitory> arrayAdapter = new ArrayAdapter<>(DormitoryActivity.this, android.R.layout.simple_list_item_1, projectListFromJson);
                        dormList.setAdapter(arrayAdapter);

                        dormList.setOnItemClickListener(((parent, view, position, id) -> {
                            Toast.makeText(DormitoryActivity.this, "Showing Dorm " + projectListFromJson.get(position).getDorm_num() + " data", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(DormitoryActivity.this, RoomActivity.class);
                            intent.putExtra("DormNum", projectListFromJson.get(position).getDorm_num());
                            startActivity(intent);
                        }));
                    }

                });


            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
    public void addDormitory(View view) {
        EditText addressField =findViewById(R.id.addressField);


        String data = "{\"address\":\"" + addressField.getText().toString() + "\"}";

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(()->{
            try {
                String response = RESTController.sendPost(DORMITORY_ADDITION, data);
                handler.post(()->{
                    if (!response.equals("")  && !response.equals(" ")) {
                        fillList();
                    }
                });


            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public void deleteDorm(View view) {
        EditText dormIdField=findViewById(R.id.dormIdField);
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(()->{
            try {
                String response = RESTController.sendDelete(DORMITORY_DELETE+dormIdField.getText().toString());
                handler.post(()->{
                    if (!response.equals("")  && !response.equals(" ")) {
                        fillList();
                    }
                });


            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}