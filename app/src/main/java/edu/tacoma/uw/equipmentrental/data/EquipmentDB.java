package edu.tacoma.uw.equipmentrental.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import edu.tacoma.uw.equipmentrental.R;
import model.Equipment;

public class EquipmentDB {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Equipment.db";

    private EquipmentDBHelper mEquipmentDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public EquipmentDB(Context context) {
        mEquipmentDBHelper = new EquipmentDBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mEquipmentDBHelper.getWritableDatabase();
    }

    /**
     * Inserts equipment into the local sqlite table. Returns true if successful, false otherwise.
     * @param equipmentname
     * @param shortDesc
     * @param price
     * @param email
     * @return true or false
     */
    public boolean insertEquipment(String equipmentname, String shortDesc, String price, String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("equipmentname", equipmentname);
        contentValues.put("shortDesc", shortDesc);
        contentValues.put("price", price);
        contentValues.put("Email", email);

        long rowId = mSQLiteDatabase.insert("Equipment", null, contentValues);
        return rowId != -1;
    }

    /**
     * Delete all the data from Equipment
     */
    public void deleteEquipment() {
        mSQLiteDatabase.delete("Equipment", null, null);
    }

    /**
     * Returns the list of equipment from the local Equipment table.
     * @return list
     */
    public List<Equipment> getEquipment() {

        String[] columns = {
                "equipmentname", "shortDesc", "price", "Email"
        };

        Cursor c = mSQLiteDatabase.query(
                "Equipment",  // The table to query
                columns,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        c.moveToFirst();
        List<Equipment> list = new ArrayList<Equipment>();
        for (int i=0; i<c.getCount(); i++) {
            String equipmentname = c.getString(0);
            String shortDesc = c.getString(1);
            String price = c.getString(2);
            String email = c.getString(3);
            Equipment equipment = new Equipment(equipmentname, shortDesc, price, email);
            list.add(equipment);
            c.moveToNext();
        }

        return list;
    }




    class EquipmentDBHelper extends SQLiteOpenHelper {

        private final String CREATE_EQUIPMENT_SQL;

        private final String DROP_EQUIPMENT_SQL;



        public EquipmentDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            CREATE_EQUIPMENT_SQL = context.getString(R.string.CREATE_EQUIPMENT_SQL);
            DROP_EQUIPMENT_SQL = context.getString(R.string.DROP_EQUIPMENT_SQL);

        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_EQUIPMENT_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_EQUIPMENT_SQL);
            onCreate(sqLiteDatabase);
        }
    }
}
