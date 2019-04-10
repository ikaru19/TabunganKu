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
import android.widget.Toast;

import com.syafrizal.tabunganku.Constant;
import com.syafrizal.tabunganku.Databases.DatabaseHelper;
import com.syafrizal.tabunganku.R;

public class DetailActivity extends AppCompatActivity {
    String judul,tipe;
    int id,jumlah,uang;
    EditText txtJudul , txtJumlah;
    Spinner spinnertype;
    DatabaseHelper databaseHelper;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        judul = intent.getStringExtra("judul");
        jumlah = intent.getIntExtra("jumlah",0);
        tipe = intent.getStringExtra("tipe");
        txtJudul = findViewById(R.id.et_JudulDetail);
        txtJumlah = findViewById(R.id.et_JumlahDetail);
        spinnertype = findViewById(R.id.spinnerDetail);

        txtJudul.setText(judul);
        txtJumlah.setText(Integer.toString(jumlah));
        if (tipe.equalsIgnoreCase("pengeluaran")){
            spinnertype.setSelection(0);
        }else{
            spinnertype.setSelection(1);
        }

        databaseHelper = new DatabaseHelper(this);
        preferences = getSharedPreferences(Constant.USER_SP,MODE_PRIVATE);
        uang = preferences.getInt(Constant.USER_MONEY,0);
    }

    public void editOnClick(View view) {
        new AlertDialog.Builder(this)
                .setMessage("Apakah anda yakin ingin mengubah data ini ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        onUpdateProcess();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void deleteOnClick(View view) {
        new AlertDialog.Builder(this)
                .setMessage("Apakah anda yakin ingin menghapus data ini ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        onDeleteProcess();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void onDeleteProcess(){
        boolean result = databaseHelper.deleteData(id);
        if (result){
            if (tipe.equalsIgnoreCase("pemasukkan")){
                uang -= jumlah;
                preferences.edit()
                        .putInt(Constant.USER_MONEY,uang)
                        .apply();
            }else{
                uang += jumlah;
                preferences.edit()
                        .putInt(Constant.USER_MONEY,uang)
                        .apply();
            }
            Toast.makeText(this, "Data Dihapus", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Data tidak dapat dihapus", Toast.LENGTH_SHORT).show();
        }

    }

    public void onUpdateProcess(){
        int jmlBaru = Integer.parseInt(txtJumlah.getText().toString());
        String tipe = spinnertype.getSelectedItem().toString();
        String judul = txtJudul.getText().toString();
        //pengecekan update spinner
        if (tipe.equalsIgnoreCase(this.tipe)){
            if (tipe.equalsIgnoreCase("pemasukkan")){
                //menghapus data dan menambahkan baru
                uang -= jumlah;
                uang += jmlBaru;
            }else {
                uang += jumlah;
                uang -= jmlBaru;
            }
        }else{
            if (tipe.equalsIgnoreCase("pemasukkan")){
                //menambah dari data awal
                uang += (jmlBaru+jumlah);
            }else{
                //melakukan pengurangan 2 kali
                uang -= jmlBaru;
                uang -= jumlah;
            }
        }

        boolean result = databaseHelper.updateData(id,judul,jmlBaru,tipe);
        if (result){
           preferences.edit()
                        .putInt(Constant.USER_MONEY,uang)
                        .apply();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Data Diubah", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "Data tidak dapat diubah", Toast.LENGTH_SHORT).show();
        }
    }
}
