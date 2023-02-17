package com.example.chatgpt;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView welocme;
    ImageButton send;
    EditText  messegebox;
    List<Messsege> messsegeList;
    MessageAdapter messageAdapter;
    public static final MediaType JSON  = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //BINDING
        messsegeList = new ArrayList<>();
        recyclerView = findViewById(R.id.RecyclerView);
        welocme = findViewById(R.id.welcome);
        send = findViewById(R.id.SendBtn);
        messegebox = findViewById(R.id.MessageBox);
        //SET_UP_RECYCLERVIEW
        messageAdapter = new MessageAdapter(messsegeList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        //CLICK
        send.setOnClickListener(view -> {
            String question = messegebox.getText().toString().trim();
            AddToChat(question,Messsege.SENT_BY_ME);
            messegebox.setText("");
            callAPI(question);
            welocme.setVisibility(View.GONE);
        });
    }
    //FUN
    void AddToChat(String Message, String SentBy){
        runOnUiThread(() -> {
            boolean add = messsegeList.add(new Messsege(Message, SentBy));
            messageAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
        });
    }
    //FUN
    void AddResponse(String response){
        messsegeList.remove(messsegeList.size()-1);
        AddToChat(response,Messsege.SENT_BY_BOT);
    }
        //FUN
    void callAPI (String question){
        messsegeList.add(new Messsege("Typing...",Messsege.SENT_BY_BOT));
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model","text-davinci-003");
            jsonBody.put("prompt",question);
            jsonBody.put("max_tokens",4000);
            jsonBody.put("temperature",0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    AddResponse("Failed to load response due to "+e.getMessage());
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()){
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(response.body().string());
                                JSONArray jsonArray = jsonObject.getJSONArray("choices");
                            String result =     jsonArray.getJSONObject(0).getString("text");
                            AddResponse(result.trim());
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }else{
                        assert response.body() != null;
                        AddResponse("Failed to load response due to "+response.body());
                    }
            }
        });
    }

}