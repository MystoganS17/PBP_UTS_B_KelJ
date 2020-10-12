package com.tubes.rentalmotor;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import static android.R.layout.simple_list_item_1;

public class PenyewaActivity extends AppCompatActivity {

    private String CHANNEL_ID = "Channel 1";
    String[] daftar;
    ListView ListView1;
    protected Cursor cursor;
    DatabaseHelper dbcenter;
    @SuppressLint("StaticFieldLeak")
    public static PenyewaActivity m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyewa);

        Button tambah = findViewById(R.id.tambahPenyewa);

        tambah.setOnClickListener(v -> {
            Intent p = new Intent(PenyewaActivity.this, SewaMotorActivity.class);
            startActivity(p);
        });

        m = this;
        dbcenter = new DatabaseHelper(this);

        RefreshList();
        setupToolbar();

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbPenyewa);
        toolbar.setTitle("Daftar Penyewa");
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

    public void RefreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM penyewa", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            daftar[i] = cursor.getString(0);
        }

        ListView1 = findViewById(R.id.listView1);
        ListView1.setAdapter(new ArrayAdapter(this, simple_list_item_1, daftar));
        ListView1.setSelected(true);
        ListView1.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
            final String selection = daftar[arg2];
            final CharSequence[] dialogitem = {"Lihat Data", "Hapus Data"};
            AlertDialog.Builder builder = new AlertDialog.Builder(PenyewaActivity.this);
            builder.setTitle("Pilihan");
            builder.setItems(dialogitem, (dialog, item) -> {
                switch (item) {
                    case 0: {
                        Intent i = new Intent(PenyewaActivity.this, DetailPenyewaActivity.class);
                        i.putExtra("nama", selection);
                        startActivity(i);
                        break;
                    }
                    case 1: {
                        SQLiteDatabase db1 = dbcenter.getWritableDatabase();
                        db1.execSQL("DELETE FROM penyewa where nama = '" + selection + "'");
                        db1.execSQL("DELETE FROM sewa where nama = '" + selection + "'");
                        RefreshList();
                        createNotificationChannel();
                        addNotification();
                        break;
                    }
                }
            });
            builder.create().show();
        });
        ((ArrayAdapter) ListView1.getAdapter()).notifyDataSetInvalidated();

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
                .setContentTitle("Data Deleted")
                .setContentText("Data penyewa berhasil dihapus...")
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