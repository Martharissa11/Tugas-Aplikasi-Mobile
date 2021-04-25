package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateBiodata extends AppCompatActivity {
//menampilkan kelas update biodata dengan mewarisi Appcompactactivity
    protected Cursor cursor;
    //memanggil fungsi cursor
    DataHelper dbHelper;
    //memanggil kelas data helper
    Button ton1, ton2;
    //memanggil fungsi button
    EditText text1, text2, text3, text4, text5;
    //memanggil fungsi edit text

    @Override
    //membuat function onCreate yang berguna untuk menampilkan data dari database
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_biodata);
        dbHelper= new DataHelper(this);
        text1 = (EditText) findViewById(R.id.editText1);
        text2 = (EditText) findViewById(R.id.editText2);
        text3 = (EditText) findViewById(R.id.editText3);
        text4 = (EditText) findViewById(R.id.editText4);
        text5 = (EditText) findViewById(R.id.editText5);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata WHERE nama = '" +
                getIntent().getStringExtra("nama") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            text1.setText(cursor.getString(0).toString());
            text2.setText(cursor.getString(1).toString());
            text3.setText(cursor.getString(2).toString());
            text4.setText(cursor.getString(3).toString());
            text5.setText(cursor.getString(4).toString());
        }
        //untuk memberikan fungsi pada button simpan
        ton1 = (Button) findViewById(R.id.button1);
        //untuk memberikan fungsi pada button back
        ton2 = (Button) findViewById(R.id.button2);
        // daftarkan even onClickpadabtnSimpan
        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            //membuat function onClick yang berguna untuk melakukan update data kedalam database
            public void onClick(View arg0) {
// TODO Auto-generated method stub
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("update biodata set nama='"+
                        text2.getText().toString() +"', tgl='" +
                        text3.getText().toString()+"', jk='"+
                        text4.getText().toString() +"', alamat='" +
                        text5.getText().toString() + "' where no='" +
                        text1.getText().toString()+"'");
                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
                MainActivity.ma.RefreshList();
                finish();
            }
        });
        ton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
// TODO Auto-generated method stub
                finish();
            }
        });
    }
}