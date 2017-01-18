package com.example.kajol.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.CommentsViewHolder> {
    String message;
    Gson gson;
    private ArrayList<String> chatMessageList;
    private Context context;
    private LayoutInflater inflater;
    private CommentsViewHolder myViewHolder;
    private View view;
    private String timestamp;
    private String mName;

    public ChatsAdapter(Context context, ArrayList<String> chatMessageList, String mName) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.chatMessageList = chatMessageList;
        inflater = LayoutInflater.from(context);
        gson = new Gson();
        this.mName = mName;
    }


    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = inflater.inflate(R.layout.fragment_comments, parent, false);
        myViewHolder = new CommentsViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {


        message = chatMessageList.get(position);
        Message messageObject = gson.fromJson(message, Message.class);

        timestamp = GeneralHelper.time_ago(messageObject.getTimestamp());
        holder.chatsMessage.setText(GetUsername(messageObject.getUsername()) + " : " + messageObject.getMessage());
        holder.chatsTime.setText(Html.fromHtml("<i> " + timestamp + "</i>"));
    }

    private String GetUsername(String username) {
        if (username.equalsIgnoreCase(mName))
            return "Me";
        return username;
    }

    @Override
    public int getItemCount() {

        return chatMessageList.size();
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder {

        private TextView chatsMessage;
        private TextView chatsTime;

        public CommentsViewHolder(View view) {
            super(view);
            chatsMessage = (TextView) view.findViewById(R.id.chatsMessage);
            chatsTime = (TextView) view.findViewById(R.id.chatsTime);

        }
    }

}
