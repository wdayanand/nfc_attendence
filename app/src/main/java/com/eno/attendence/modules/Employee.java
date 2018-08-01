package com.eno.attendence.modules;

import android.os.Parcelable;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

import static com.eno.attendence.database.DataBaseConstants.*;

@Table(name = TABLE_EMPLOYEE)
public class Employee extends Model implements Serializable {
    @Column(name = COL_EMP_ID,unique = true, onUniqueConflict = Column.ConflictAction.REPLACE,onUpdate = Column.ForeignKeyAction.CASCADE)
    String empId;

    @Column(name = COL_EMP_NAME)
    String name;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Employee getEmployeeById(final String userId) {

        return new Select()
                .from(Employee.class)
                .where(COL_EMP_ID + " = ?",
                        userId)
                .executeSingle();
    }

    public static List<Employee> getAllEmployee() {

        return new Select()
                .from(Employee.class)
                .execute();
    }
    public List<EmpEntry> getEntryList() {
        try {
            return (new Select()).from(EmpEntry.class).where(Cache.getTableName(EmpEntry.class) + "." + "employee" + "=?", new Object[]{this.getId()}).execute();

        } catch (Exception e) {
            return null;
        }
    }
}
