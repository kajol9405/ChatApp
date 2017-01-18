package com.example.kajol.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        final EditText etName = (EditText) findViewById(R.id.etName);

        Button bSaveName = (Button) findViewById(R.id.bSaveName);
        bSaveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NameActivity.this, ChatsActivity.class);
                intent.putExtra("name", etName.getText().toString());
                startActivity(intent);
            }
        });
    }

}


