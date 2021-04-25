package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
//membuat class ini dengan mewarisi AppcCompatActivity
public class MainActivity extends AppCompatActivity {

    String[] daftar; //membuat array string daftar
    ListView ListView01; //memanggil fungsi list
    Menu menu; //memanggil fungsi menu
    protected Cursor cursor; //memanggil fungsi cursor
    DataHelper dbcenter;//memanggil kelas datahelper
    public static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //membuat fungsi onCreate
        super.onCreate(savedInstanceState);
        //mengatur tampilan awal aplikasi
        setContentView(R.layout.activity_main);
        //memberikan fungsi pada button dengan id button2
        Button btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent inte = new Intent(MainActivity.this, Biodata.class);
                startActivity(inte);
            }
        });
        ma = this;
        dbcenter = new DataHelper(this);
        RefreshList();
    }

    public void RefreshList() {
        //membuat fungsi refreshlist yang digunakakn untuk menampilkan data dari data base kedalam list view
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1).toString();
        }
        ListView01 = (ListView) findViewById(R.id.listView1);
        ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        ListView01.setSelected(true);
        ListView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                //membuat fungsi onItemclik yang di gunakan untuk memberikan option ketika item pada list view di klik
                final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();
                final CharSequence[] dialogitem = {"View Biodata", "Update Biodata", "Delete Biodata"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Option");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                //memberikan option view data
                                Intent i = new Intent(getApplicationContext(), ViewBiodata.class);
                                i.putExtra("nama", selection);
                                startActivity(i);
                                break;
                            case 1:
                                //memebrikan option update data
                                Intent in = new Intent(getApplicationContext(), UpdateBiodata.class);
                                in.putExtra("nama", selection);
                                startActivity(in);
                                break;
                            case 2:
                                //memberikan option delete data
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("delete from biodata where nama = '" + selection + "'");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter) ListView01.getAdapter()).notifyDataSetInvalidated();
    }
}