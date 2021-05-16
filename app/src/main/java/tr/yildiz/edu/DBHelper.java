package tr.yildiz.edu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context){
        super(context, "Login.db", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create Table users(_id Integer primary key, username Text, password Text, image String)");
        myDB.execSQL("create Table questions(_id Integer primary key,user_id Integer, question Text, A Text, B Text, C Text, D Text, E Text, " +
                "answer Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion){
        myDB.execSQL("drop Table if exists users");
    }


    public Boolean insertData(String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password",password);
        contentValues.put("image", (String) null);
        long result = myDB.insert("users", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean insertDataWithİmage(String username, String password, Uri uri) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password",password);
        contentValues.put("image", storeImage(uri));
        long result = myDB.insert("users", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean checkusername(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ?", new String[] {username});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public Boolean checkusernamePassword(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public Integer getUserId(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select _id from users where username = ?", new String[] {username});
        cursor.moveToFirst();
        Integer id =  cursor.getInt(0);
        return id;
    }

    public Boolean insertQuestion(Integer id, String question, String a, String b, String c, String d, String e, String answer) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", id);
        contentValues.put("question",question);
        contentValues.put("A",a);
        contentValues.put("B",b);
        contentValues.put("C",c);
        contentValues.put("D",d);
        contentValues.put("E",e);
        contentValues.put("answer",answer);
        long result = myDB.insert("questions", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    Cursor readAllData(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("select * from questions where user_id = ?", new String[] {username});
        }
        return cursor;
    }

    Integer updateData(String question, String a, String b, String c, String d, String e, String answer, String index){
        Log.d("sez", "fkfdlkfdkl" + answer);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("question", question);
        cv.put("A", a);
        cv.put("B", b);
        cv.put("C", c);
        cv.put("D", d);
        cv.put("E", e);
        cv.put("answer", answer);
        int result = db.update("questions", cv, "_id = ?", new String[]{index});

        return result;
    }

    long deleteOneRow(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("questions", "_id = ?", new String[]{id});
        return result;
    }

    public String storeImage(Uri uri){
        String s = uri.toString();

        return s;
    }

    public String getImage(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select image from users where username = ?", new String[] {username});
        cursor.moveToFirst();
        String img =  cursor.getString(0);
        return img;

    }

}
