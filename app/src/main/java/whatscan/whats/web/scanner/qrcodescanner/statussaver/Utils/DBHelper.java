package whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.QRScan;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "qr_database.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE qr_scans (id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS qr_scans");
        onCreate(db);
    }

    public void deleteAllQRCodes(SQLiteDatabase db, int id) {
        db.execSQL("DELETE FROM qr_scans WHERE id='" + id + "'");

    }

    public List<QRScan> getAllQRCodes() {
        List<QRScan> qrCodes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM qr_scans", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex("id"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String timestampString = cursor.getString(cursor.getColumnIndex("timestamp"));
                Date timestamp = null;
                try {
                    timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestampString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                QRScan qrScan = new QRScan(id, content, timestamp);
                qrCodes.add(qrScan);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return qrCodes;
    }


}

