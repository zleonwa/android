package com.example.dami.mybook1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etTitle;
    EditText etAuthor;
    EditText etContent;
    final String dbName = "book_db";
    final String tableName = "book_tb";

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etAuthor = (EditText) findViewById(R.id.etAuthor);
        etContent = (EditText) findViewById(R.id.etContent);

        Button btnSave = (Button) findViewById(R.id.btnSave);

        settingDatabase();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etTitle.getText() == null || etTitle.getText().toString().isEmpty() ||
                        etAuthor.getText() == null || etAuthor.getText().toString().isEmpty() ||
                        etContent.getText() == null || etContent.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "모든 항목을 입력해주세요", Toast.LENGTH_LONG).show();
                else{
                    int result = insertRecordParam(etTitle.getText().toString(), etAuthor.getText().toString(), etContent.getText().toString());
                    if(result>0) {
                        Toast.makeText(getApplicationContext(), "저장 완료", Toast.LENGTH_LONG).show();
                    }
                    else Toast.makeText(getApplicationContext(), "저장 실패", Toast.LENGTH_LONG).show();

                    etTitle.setText("");
                    etAuthor.setText("");
                    etContent.setText("");
                }
            }
        });
    }

    public int insertRecordParam(String title, String author, String content){
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("author", author);
        contentValues.put("content", content);

        return (int)db.insert(tableName, null, contentValues);
    }

    public boolean settingDatabase(){
        try{
            db = openOrCreateDatabase(dbName, MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS "+ tableName + "("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "title text,"
                    + "author text,"
                    + "content text"
                    + ")");
            return true;
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "테이블 생성 오류", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
        return false;
    }
}
