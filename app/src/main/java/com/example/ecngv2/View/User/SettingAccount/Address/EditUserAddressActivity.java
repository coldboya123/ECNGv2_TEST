package com.example.ecngv2.View.User.SettingAccount.Address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecngv2.Model.Object.City;
import com.example.ecngv2.Model.Object.District;
import com.example.ecngv2.Model.Object.UserAddress;
import com.example.ecngv2.Model.Object.Ward;
import com.example.ecngv2.Model.User.Address.ModelAddress;
import com.example.ecngv2.R;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EditUserAddressActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_back;
    AutoCompleteTextView txtCity, txtDistric, txtWard;
    TextInputEditText txtName, txtPhone, txtAddress;
    MaterialCheckBox checkBox;
    AppCompatButton btn_save;
    List<City> listCity = new ArrayList<>();
    List<District> listDistrict = new ArrayList<>();
    List<Ward> listWard = new ArrayList<>();
    ModelAddress modelAddress = new ModelAddress();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_address);

        Window window = getWindow();
        @SuppressLint("UseCompatLoadingForDrawables") Drawable background = getDrawable(R.drawable.appbar_background);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(android.R.color.transparent));
//        window.setNavigationBarColor(getColor(android.R.color.transparent));
        window.setBackgroundDrawable(background);

        init();

        btn_back.setOnClickListener(this);

        txtName.setText(getIntent().getStringExtra("name"));
        txtPhone.setText(getIntent().getStringExtra("phone"));

        String address = getIntent().getStringExtra("address");
        String[] arrAddress = address.split(",");
        txtCity.setText(arrAddress[3].trim());
        txtDistric.setText(arrAddress[2].trim());
        txtWard.setText(arrAddress[1].trim());
        txtAddress.setText(arrAddress[0].trim());

        if (getIntent().getBooleanExtra("default", false)){
            checkBox.setChecked(true);
        }

        List<String> arrCity = new ArrayList<>();
        listCity = modelAddress.getCity("https://vapi.vnappmob.com/api/province/");
        for (int i=0; i<listCity.size(); i++){
            arrCity.add(listCity.get(i).getName());
        }

        txtCity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrCity));
        txtDistric.setOnClickListener(this);
        txtWard.setOnClickListener(this);

        btn_save.setOnClickListener(this);
    }

    private void init(){
        btn_back = findViewById(R.id.user_address_edit_btn_back);
        txtName = findViewById(R.id.user_address_edit_txtname);
        txtPhone = findViewById(R.id.user_address_edit_txtphone);
        txtCity = findViewById(R.id.user_address_edit_txtcity);
        txtDistric = findViewById(R.id.user_address_edit_txtdistric);
        txtWard = findViewById(R.id.user_address_edit_txtward);
        txtAddress = findViewById(R.id.user_address_edit_txtaddress);
        checkBox = findViewById(R.id.user_address_edit_checkbox);
        btn_save = findViewById(R.id.user_address_edit_btn_save);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_address_edit_btn_back:
            case R.id.user_address_edit_btn_save:
                finish();
                break;
            case R.id.user_address_edit_txtdistric:
                String id_city = "";
                for (int i=0; i<listCity.size(); i++){
                    if (listCity.get(i).getName().equals(txtCity.getText().toString())){
                        id_city = listCity.get(i).getId();
                        break;
                    }
                }
                if (!id_city.isEmpty()) {
                    List<String> arrDistrict = new ArrayList<>();
                    listDistrict = modelAddress.getDistrict("https://vapi.vnappmob.com/api/province/district/" + id_city);
                    for (int i = 0; i < listDistrict.size(); i++) {
                        arrDistrict.add(listDistrict.get(i).getName());
                    }
                    txtDistric.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrDistrict));
                } else Toast.makeText(this, "Vui lòng chọn Tỉnh/Thành", Toast.LENGTH_SHORT).show();
                break;
            case R.id.user_address_edit_txtward:
                if (listDistrict.isEmpty()){
                    Toast.makeText(this, "Vui lòng chọn Quận/Huyện", Toast.LENGTH_SHORT).show();
                } else {
                    String id_district = "";
                    for (int i=0; i<listDistrict.size(); i++){
                        if (listDistrict.get(i).getName().equals(txtDistric.getText().toString())){
                            id_district = listDistrict.get(i).getId();
                            break;
                        }
                    }
                    if (id_district.isEmpty()){
                        Toast.makeText(this, "Vui lòng chọn Quận/Huyện", Toast.LENGTH_SHORT).show();
                    } else {
                        List<String> arrWard = new ArrayList<>();
                        listWard = modelAddress.getWard("https://vapi.vnappmob.com/api/province/ward/" + id_district);
                        for (int i = 0; i < listWard.size(); i++) {
                            arrWard.add(listWard.get(i).getName());
                        }
                        txtWard.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrWard));
                    }
                }
                break;
        }
    }
}