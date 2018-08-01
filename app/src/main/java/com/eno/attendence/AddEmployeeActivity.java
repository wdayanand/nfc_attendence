package com.eno.attendence;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eno.attendence.modules.Employee;

public class AddEmployeeActivity extends AppCompatActivity {
    EditText edtEmployeeId, edtEmployeeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtEmployeeName = findViewById(R.id.edtEmployeeName);
        edtEmployeeId = findViewById(R.id.edtEmployeeId);

        Button fab = (Button) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if (TextUtils.isEmpty(edtEmployeeId.getText().toString())) {
                        Snackbar.make(view, "Please enter employee id", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else if (TextUtils.isEmpty(edtEmployeeName.getText().toString())) {
                        Snackbar.make(view, "Please enter employee name", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    } else {
                        Employee emp2 = Employee.getEmployeeById(edtEmployeeId.getText().toString());
                        if (emp2 == null) {
                            emp2 = new Employee();
                            emp2.setName(edtEmployeeName.getText().toString());
                            emp2.setEmpId(edtEmployeeId.getText().toString());
                            emp2.save();
                            Snackbar.make(view, "Employee added successfully", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {

                            Snackbar.make(view, "Employee already exist", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                    }
                } catch (Exception e) {
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
