/***************************************************************************
 *
 * This file is part of the 'NDEF Tools for Android' project at
 * http://code.google.com/p/ndef-tools-for-android/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ****************************************************************************/

package com.eno.attendence;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.eno.attendence.modules.EmpEntry;
import com.eno.attendence.modules.Employee;
import com.eno.attendence.modules.TempEmpEntryWithStatus;
import com.skyfishjy.library.RippleBackground;

import org.ndeftools.Message;
import org.ndeftools.MimeRecord;
import org.ndeftools.Record;
import org.ndeftools.externaltype.ExternalTypeRecord;
import org.ndeftools.wellknown.TextRecord;

/**
 * Activity demonstrating the default implementation of the abstract reader activity.
 * <p>
 * The activity lists the records of any detected NDEF message and displays some toast messages for various events.
 *
 * @author Thomas Rorvik Skjolberg
 */

public class NfcReaderActivity extends org.ndeftools.util.activity.NfcReaderActivity {

    private static final String TAG = NfcReaderActivity.class.getName();

    protected Message message;
    RippleBackground rippleBackground;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.reader);

        // lets start detecting NDEF message using foreground mode
        setDetecting(true);
        rippleBackground = (RippleBackground) findViewById(R.id.content);
        rippleBackground.startRippleAnimation();
    }

    /**
     * An NDEF message was read and parsed. This method prints its contents to log and then shows its contents in the GUI.
     *
     * @param message the message
     */

    @Override
    public void readNdefMessage(Message message) {

        this.message = message;

        // process message

        // show in log
        // iterate through all records in message
        Log.d(TAG, "Found " + message.size() + " NDEF records");


        for (int k = 0; k < message.size(); k++) {
            Record record = message.get(k);

            Log.d(TAG, "Record " + k + " type " + record.getClass().getSimpleName());

            if (record instanceof TextRecord) {
                TextRecord data = (TextRecord) record;
                Employee emp = Employee.getEmployeeById(data.getText());

                if (emp == null) {
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                } else {
                    TempEmpEntryWithStatus tempEmpEntryWithStatus = EmpEntry.isEmployeeAlreadyLoggedIn(emp.getId());
                    if (tempEmpEntryWithStatus.isLoggedIn()) {
                        EmpEntry empEntry = tempEmpEntryWithStatus.getEmpEntry();
                        empEntry.setEndTime(System.currentTimeMillis());
                        empEntry.save();
                    } else {
                        EmpEntry empEntry = new EmpEntry();
                        empEntry.setStartTime(System.currentTimeMillis());
                        empEntry.setEmployee(emp);
                        empEntry.save();
                    }

                    Toast.makeText(this, !tempEmpEntryWithStatus.isLoggedIn() ? "User In" : "User Out", Toast.LENGTH_SHORT).show();

                }
            } else {
                Toast.makeText(this, "Invalid Tag", Toast.LENGTH_SHORT).show();
            }


        }


    }


    /**
     * An empty NDEF message was read.
     */

    @Override
    protected void readEmptyNdefMessage() {
        toast(getString(R.string.readEmptyMessage));


    }

    /**
     * Something was read via NFC, but it was not an NDEF message.
     * <p>
     * Handling this situation is out of scope of this project.
     */

    @Override
    protected void readNonNdefMessage() {
        toast(getString(R.string.readNonNDEFMessage));

    }

    /**
     * NFC feature was found and is currently enabled
     */

    @Override
    protected void onNfcStateEnabled() {
        toast(getString(R.string.nfcAvailableEnabled));
    }

    /**
     * NFC feature was found but is currently disabled
     */

    @Override
    protected void onNfcStateDisabled() {
        toast(getString(R.string.nfcAvailableDisabled));
    }

    /**
     * NFC setting changed since last check. For example, the user enabled NFC in the wireless settings.
     */

    @Override
    protected void onNfcStateChange(boolean enabled) {
        if (enabled) {
            toast(getString(R.string.nfcAvailableEnabled));
        } else {
            toast(getString(R.string.nfcAvailableDisabled));
        }
    }

    /**
     * This device does not have NFC hardware
     */

    @Override
    protected void onNfcFeatureNotFound() {
        toast(getString(R.string.noNfcMessage));
    }


    public void toast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    @Override
    protected void onTagLost() {
        toast(getString(R.string.tagLost));
    }

}
