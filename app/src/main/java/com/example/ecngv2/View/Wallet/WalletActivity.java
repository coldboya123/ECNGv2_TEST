package com.example.ecngv2.View.Wallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.ecngv2.Adapter.RCV_Wallet_Adapter;
import com.example.ecngv2.Model.Object.Cash;
import com.example.ecngv2.R;

import java.util.ArrayList;
import java.util.List;

public class WalletActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Cash> list;
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        Window window = getWindow();
        @SuppressLint("UseCompatLoadingForDrawables") Drawable background = getDrawable(R.drawable.appbar_background);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(android.R.color.transparent));
//        window.setNavigationBarColor(getColor(android.R.color.transparent));
        window.setBackgroundDrawable(background);

        init();

        btn_back.setOnClickListener(v -> finish());

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(new RCV_Wallet_Adapter(this, list));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private void init() {
        btn_back = findViewById(R.id.btn_back);
        recyclerView = findViewById(R.id.rcv_today);
        list = new ArrayList<>();
        list.add(new Cash("123ECNG", "01/04/2020", -1500000));
        list.add(new Cash("123ECNG", "01/04/2020", 10500000));
        list.add(new Cash("123ECNG", "01/04/2020", -1500000));
        list.add(new Cash("123ECNG", "01/04/2020", 5000000));

    }
}