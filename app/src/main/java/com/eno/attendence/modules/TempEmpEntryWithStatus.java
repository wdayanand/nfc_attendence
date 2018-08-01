package com.eno.attendence.modules;

import java.io.Serializable;

public class TempEmpEntryWithStatus implements Serializable{
    EmpEntry empEntry;
    boolean loggedIn;

    public EmpEntry getEmpEntry() {
        return empEntry;
    }

    public void setEmpEntry(EmpEntry empEntry) {
        this.empEntry = empEntry;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
