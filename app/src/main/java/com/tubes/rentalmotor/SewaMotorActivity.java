package com.tubes.rentalmotor;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import java.util.List;

public class SewaMotorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String CHANNEL_ID = "Channel 1";
    EditText nama, alamat, no_hp, lama;
    Button selesai;

    String sNama, sAlamat, sNo, sMerk, sLama;
    int iLama, iHarga;
    double dTotal;

    private Spinner spinner;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa);

        dbHelper = new DatabaseHelper(this);

        spinner = findViewById(R.id.spinner);
        selesai = findViewById(R.id.selesaiHitung);
        nama = findViewById(R.id.eTNama);
        alamat = findViewById(R.id.eTAlamat);
        no_hp = findViewById(R.id.eTHP);
        lama = findViewById(R.id.eTLamaSewa);

        spinner.setOnItemSelectedListener(this);

        loadSpinnerData();

        selesai.setOnClickListener(v -> {
            sNama = nama.getText().toString();
            sAlamat = alamat.getText().toString();
            sNo = no_hp.getText().toString();
            sLama = lama.getText().toString();
            if (sNama.isEmpty() || sAlamat.isEmpty() || sNo.isEmpty() || sLama.isEmpty()) {
                Toast.makeText(SewaMotorActivity.this, "(*) tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }


            if (sMerk.equals("Scoopy")) {
                iHarga = 70000;
            } else if (sMerk.equals("Vario")) {
                iHarga = 70000;
            } else if (sMerk.equals("Lexi")) {
                iHarga = 750000;
            } else if (sMerk.equals("Piaggio")) {
                iHarga = 125000;
            } else if (sMerk.equals("PCX")) {
                iHarga = 140000;
            } else if (sMerk.equals("NMAX")) {
                iHarga = 140000;
            } else if (sMerk.equals("Motobi")) {
                iHarga = 200000;
            } else if (sMerk.equals("Ninja")) {
                iHarga = 250000;
            } else if (sMerk.equals("XMAX")) {
                iHarga = 300000;
            }

            iLama = Integer.parseInt(sLama);
            dTotal = (iHarga * iLama);

            SQLiteDatabase dbH = dbHelper.getWritableDatabase();
            dbH.execSQL("INSERT INTO penyewa (nama, alamat, no_hp) VALUES ('" +
                    sNama + "','" +
                    sAlamat + "','" +
                    sNo + "');");
            dbH.execSQL("INSERT INTO sewa (merk, nama, lama, total) VALUES ('" +
                    sMerk + "','" +
                    sNama + "','" +
                    iLama + "','" +
                    dTotal + "');");
            createNotificationChannel();
            addNotification();
            PenyewaActivity.m.RefreshList();
            finish();

        });

        setupToolbar();

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbSewaMotr);
        toolbar.setTitle("Sewa Motor");
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

    private void loadSpinnerData() {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List categories = db.getAllCategories();

        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView parent, View view, int position, long id) {
        sMerk = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView parent) {

    }

    private void createNotificationChannel(){
        // NotificationChannel diperlukan untuk API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Channel 1";
            String description = "This is Channel 1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void addNotification(){
        //konstruktor harus diberi CHANNEL_ID untuk API 26+
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Data Added")
                .setContentText("Data penyewa berhasil ditambah...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //Buat intent untuk tampilin notifikasi
        Intent notificationIntent = new Intent(this, SignUpActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        //Menampilkan notifikasi
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}