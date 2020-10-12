package com.tubes.rentalmotor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailMotorActivity extends AppCompatActivity {

    protected Cursor cursor;
    String sMerk, sHarga, sGambar;
    DatabaseHelper dbHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_motor);

        Bundle terima = getIntent().getExtras();

        dbHelper = new DatabaseHelper(this);
        Intent intent = getIntent();

        String merk = terima.getString("merk");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from motor where merk = '" + merk + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            sMerk = cursor.getString(0);
            sHarga = cursor.getString(1);
        }

        if (sMerk.equals("Scoopy")) {
            sGambar = "scoopy";
        } else if (sMerk.equals("Vario")) {
            sGambar = "vario";
        } else if (sMerk.equals("Lexi")) {
            sGambar = "lexi";
        } else if (sMerk.equals("Piaggio")) {
            sGambar = "piaggio";
        } else if (sMerk.equals("PCX")) {
            sGambar = "pcx";
        } else if (sMerk.equals("NMAX")) {
            sGambar = "nmax";
        } else if (sMerk.equals("Motobi")) {
            sGambar = "motobi";
        } else if (sMerk.equals("Ninja")) {
            sGambar = "ninja";
        } else if (sMerk.equals("XMAX")) {
            sGambar = "xmax";
        }

        TextView tvMerk = findViewById(R.id.JMotor);
        TextView tvHarga = findViewById(R.id.JHarga);

        tvMerk.setText(sMerk);
        tvHarga.setText("Rp. " + sHarga);

        setupToolbar();

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbDetailMtr);
        toolbar.setTitle("Detail Motor");
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
