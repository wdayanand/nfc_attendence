package com.eno.attendence.modules;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import static com.eno.attendence.database.DataBaseConstants.*;

@Table(name = TABLE_EMP_ENTRY)
public class EmpEntry extends Model implements Serializable {
    @Column(name = COL_START_TIME)
    long startTime;
    @Column(name = COL_END_TIME)
    long endTime;

    @Column(name = COL_EMP, onDelete = Column.ForeignKeyAction.CASCADE)
    Employee employee;



    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public static List<EmpEntry> getEntryByOfEmployee(final long employeeId) {

        return new Select()
                .from(EmpEntry.class)
                .where(COL_EMP + " = ?",
                        employeeId)
                .execute();
    }

    public static TempEmpEntryWithStatus isEmployeeAlreadyLoggedIn(final long employeeId) {

        TempEmpEntryWithStatus tempEmpEntryWithStatus = new TempEmpEntryWithStatus();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        EmpEntry data = new Select()
                .from(EmpEntry.class).orderBy(COL_START_TIME+" DESC")
                .where(COL_EMP + " = ? and "+COL_START_TIME+" >= ?",
                        employeeId, cal.getTimeInMillis())
                .executeSingle();
        tempEmpEntryWithStatus.setEmpEntry(data);
        if (data == null || data.getStartTime() <= 0) {
            tempEmpEntryWithStatus.setLoggedIn(false);
        } else if (data.getEndTime() <= 0) {
            tempEmpEntryWithStatus.setLoggedIn(true);

        } else {
            tempEmpEntryWithStatus.setLoggedIn(false);

        }
        return tempEmpEntryWithStatus;

    }

    public static List<EmpEntry> getAllEntries() {

        return new Select()
                .from(EmpEntry.class)
                .execute();
    }
}
