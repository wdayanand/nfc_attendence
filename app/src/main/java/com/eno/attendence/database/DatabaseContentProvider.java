package com.eno.attendence.database;

import com.activeandroid.Configuration;
import com.activeandroid.content.ContentProvider;
import com.eno.attendence.modules.EmpEntry;
import com.eno.attendence.modules.Employee;

/**
 * Created by Anita on 15/5/18.
 */

public class DatabaseContentProvider extends ContentProvider {

    @Override
    protected Configuration getConfiguration() {
        if (getContext() != null) {
            Configuration.Builder builder = new Configuration.Builder(getContext());
            builder.addModelClass(Employee.class);
            builder.addModelClass(EmpEntry.class);
            return builder.create();
        }

        return null;
    }
}