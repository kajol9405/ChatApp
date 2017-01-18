package com.example.kajol.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatsActivity extends AppCompatActivity {


    Handler hn = new Handler();
    Runnable rr;
    private Context context = ChatsActivity.this;
    private Pubnub pubnub;
    private ChatsAdapter adapter;
    private RecyclerView rcView;
    private LinearLayoutManager layoutManager;
    private String name;
    private EditText etMessage;
    private String message;
    private Gson gson;
    private JSONObject messageObject;
    private ArrayList<String> chatMessageList;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        i = 0;
        name = getIntent().getStringExtra("name");
        chatMessageList = new ArrayList<>();
        gson = new Gson();


        pubnub = new Pubnub("pub-c-7497b5ea-f13c-4b4e-a511-11ec11d46849", "sub-c-f6e538a4-da2e-11e6-9211-02ee2ddab7fe");
        try {

            pubnub.subscribe("android_club", new Callback() {
                @Override
                public void successCallback(String channel, Object message) {
                    super.successCallback(channel, message);
                    chatMessageList.add(message.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            } else {
                                adapter = new ChatsAdapter(context, chatMessageList, name);
                                rcView = (RecyclerView) findViewById(R.id.chatsRecyclerView);
                                rcView.setAdapter(adapter);
                            }
                        }
                    });
                    Log.d("successCallback", "message " + message);
                }

            });
        } catch (PubnubException e) {
            e.printStackTrace();
        }

        Button bSend = (Button) findViewById(R.id.sendMessage);
        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etMessage = (EditText) findViewById(R.id.chatsBox);
                message = etMessage.getText().toString();

                if (message.length() != 0) {
                    message = gson.toJson(new Message(name, message, System.currentTimeMillis() / 1000L));
                    try {
                        messageObject = new JSONObject(message);
                    } catch (JSONException je) {
                        Log.d("kajol", je.toString());
                    }

                    pubnub.publish("android_club", messageObject, new Callback() {
                        @Override
                        public void successCallback(String channel, Object message) {
                            super.successCallback(channel, message);
                            Log.d("successCallback", "message " + message);
                        }

                        @Override
                        public void errorCallback(String channel, PubnubError error) {
                            super.errorCallback(channel, error);
                            Log.d("errorCallback", "error " + error);
                        }
                    });
                } else {
                    Toast.makeText(context, "Please enter message", Toast.LENGTH_SHORT).show();
                }

                etMessage.setText("");
                if (i == 0) {
                    hn.postDelayed(rr, 60 * 1000);
                    i++;
                }


            }
        });


        layoutManager = new LinearLayoutManager(this);


        adapter = new ChatsAdapter(context, chatMessageList, name);
        rcView = (RecyclerView) findViewById(R.id.chatsRecyclerView);
        rcView.setLayoutManager(layoutManager);
        rcView.setItemAnimator(new DefaultItemAnimator());

        rcView.addItemDecoration(new DividerItemDecoration(this, null));
        rcView.setAdapter(adapter);
        rcView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });


        rr = new Runnable() {
            @Override
            public void run() {
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                hn.postDelayed(rr, 60 * 1000);
            }
        };
    }
}
