package com.example.retrofit.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.retrofit.Entity.User;
import com.example.retrofit.R;

public class UserDetailActivity extends AppCompatActivity {
    TextView tv_id, tv_fullname, tv_email;
    ImageView img_avatar;
    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        User user = (User) getIntent().getSerializableExtra("user");
        tv_id = findViewById(R.id.tv_id);
        tv_fullname = findViewById(R.id.tv_fullname);
        tv_email = findViewById(R.id.tv_email);
        img_avatar = findViewById(R.id.img_avatar);

        tv_id.setText(String.valueOf(user.getId()));
        tv_fullname.setText(String.format("%s %s", user.getFirst_name(), user.getLast_name()));
        tv_email.setText(user.getEmail());
        Glide.with(this).load(user.getAvatar()).into(img_avatar);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> finish());

    }
}