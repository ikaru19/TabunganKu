package com.syafrizal.tabunganku.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.syafrizal.tabunganku.Constant;
import com.syafrizal.tabunganku.Databases.DatabaseHelper;
import com.syafrizal.tabunganku.R;

public class TambahActivity extends AppCompatActivity {
    EditText et_Judul;
    EditText et_Jumlah;
    Spinner  spinner_Tipe;
    DatabaseHelper databaseHelper;
    String strTotal;
    private SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        et_Judul = findViewById(R.id.editTextJudul);
        et_Jumlah = findViewById(R.id.editTextJumlah);
        spinner_Tipe = findViewById(R.id.spinner_tipe);
        preferences = getSharedPreferences(Constant.USER_SP,MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(this);
    }

    public void saveOnClick(View view) {
        new AlertDialog.Builder(this)
                .setMessage("Apakah anda yakin ingin menyimpan data ini ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        saveProgress();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void saveProgress(){
        String judul = et_Judul.getText().toString();
        String value = et_Jumlah.getText().toString();
        if (judul.matches("") || value.matches("")) {
            Toast.makeText(this, "Tolong lakukan pengecekan data", Toast.LENGTH_SHORT).show();
        }else {
            int jumlah = Integer.parseInt(value);
            String tipe = String.valueOf(spinner_Tipe.getSelectedItem());
            int total = preferences.getInt(Constant.USER_MONEY, 0);
            boolean result = databaseHelper.insertData(judul, jumlah, tipe);
            if (result) {
                Intent intent = new Intent(this, MainActivity.class);
                if (tipe.equalsIgnoreCase("pemasukkan")) {
                    total += jumlah;
                    preferences.edit()
                            .putInt(Constant.USER_MONEY, total)
                            .apply();
                } else {
                    total -= jumlah;
                    preferences.edit()
                            .putInt(Constant.USER_MONEY, total)
                            .apply();
                }
                startActivity(intent);
                Toast.makeText(this, "Data telah dimasukkan", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Data tidak dapat dimasukkan", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
