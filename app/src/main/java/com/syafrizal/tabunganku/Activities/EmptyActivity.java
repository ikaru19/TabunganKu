package com.syafrizal.tabunganku.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.syafrizal.tabunganku.Constant;
import com.syafrizal.tabunganku.R;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
    }

    public void saveClick(View view) {
        EditText tv_saldo = findViewById(R.id.editTextSaldoAwal);
        String saldoTxt = tv_saldo.getText().toString();
        int saldo = Integer.parseInt(saldoTxt);
        SharedPreferences preferences = getSharedPreferences(Constant.USER_SP,MODE_PRIVATE);
        preferences.edit()
                .putInt(Constant.USER_MONEY,saldo)
                .apply();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
}
