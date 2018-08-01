package com.eno.attendence.database;

import android.database.Cursor;

import com.activeandroid.Cache;
import com.eno.attendence.modules.Employee;

import java.util.ArrayList;
import java.util.List;

import static com.eno.attendence.database.DataBaseConstants.*;

public class DataBaseHelper {
    public static List<Employee> getAllEmployee() {
        List<Employee> list = new ArrayList<>();
        String sql = "select age("+COL_END_TIME+", "+COL_START_TIME+") from " + TABLE_EMP_ENTRY;
        Cursor cursor = Cache.openDatabase().rawQuery(sql, null);

        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            do {
                list.add(parseSingleEmployee(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
       /* return new Select()
                .from(Employee.class)
                .execute();*/
        return list;
    }

    private static Employee parseSingleEmployee(Cursor cursor) {
        Employee cd = new Employee();


        return cd;

    }

}
