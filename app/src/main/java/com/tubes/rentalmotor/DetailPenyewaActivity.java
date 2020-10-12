package com.tubes.rentalmotor;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailPenyewaActivity extends AppCompatActivity {

    String sNama, sAlamat, sHP, sMerk, sHarga;
    int iLama, iTotal;
    double dTotal;

    protected Cursor cursor;
    DatabaseHelper dbHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penyewa);

        dbHelper = new DatabaseHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from penyewa, motor, sewa where penyewa.nama = sewa.nama AND motor.merk = sewa.merk AND penyewa.nama = '" + getIntent().getStringExtra("nama") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            sNama = cursor.getString(0);
            sAlamat = cursor.getString(1);
            sHP = cursor.getString(2);
            sMerk = cursor.getString(3);
            sHarga = cursor.getString(4);
            iLama = cursor.getInt(7);
            dTotal = cursor.getDouble(8);
        }

        TextView tvNama = findViewById(R.id.HNama);
        TextView tvAlamat = findViewById(R.id.HAlamat);
        TextView tvHP = findViewById(R.id.HTelp);

        TextView tvMerk = findViewById(R.id.HMerk);
        TextView tvHarga = findViewById(R.id.HHarga);

        TextView tvLama = findViewById(R.id.HLamaSewa);
        TextView tvTotal = findViewById(R.id.HTotal);

        tvNama.setText("     " + sNama);
        tvAlamat.setText("     " + sAlamat);
        tvHP.setText("     " + sHP);

        tvMerk.setText("     " + sMerk);
        tvHarga.setText("     Rp. " + sHarga);

        tvLama.setText("     " + iLama + " hari");
        iTotal = (int) dTotal;
        tvTotal.setText("     Rp. " + iTotal);

        setupToolbar();

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.DetailPenyewa);
        toolbar.setTitle("Detail Penyewa");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
