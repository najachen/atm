package android.naja.com.atm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/8/15.
 */
public class MyDBHelper extends SQLiteOpenHelper {
    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //下方括號內的程式，可先在筆記本中打好SQL語法，再貼到括號內，android studio會自動加上跳脫字元"\"
        db.execSQL("CREATE TABLE \"main\".\"exp\" (\"_id\" INTEGER PRIMARY KEY NOT NULL, \"cdate\" DATETIME NOT NULL, \"info\" VARCHAR, \"amount\" INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
