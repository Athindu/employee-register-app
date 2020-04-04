package com.example.employeeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper empDB;
    EditText editID, editName, editAddress, editAge, editPosition;
    SQLiteDatabase database;
    Button submit;
    boolean isInserted;
    Button view;
    Button update;
    boolean isUpdated;
    Button delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        empDB = new DatabaseHelper(this);

        editID = findViewById(R.id.idEdit);
        editName = findViewById(R.id.nameEdit);
        editAddress = findViewById(R.id.addressEdit);
        editAge = findViewById(R.id.ageEdit);
        editPosition = findViewById(R.id.postionEdit);
        submit = findViewById(R.id.button);
        update = findViewById(R.id.buttonUpdate);
        delete = findViewById(R.id.buttonDelete);
        view = findViewById(R.id.buttonView);
        showData();
        submitData();
        updateData();
        deleteData();
        viewData();
    }

    public void submitData() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = empDB.getWritableDatabase();
                String eID = editID.getText().toString();
                String eName = editName.getText().toString();
                String eAddress = editAddress.getText().toString();
                String eAge = editAge.getText().toString();
                String ePosition = editPosition.getText().toString();

                System.out.println("1");

                if (eID.equals("") || eName.equals("") || eAddress.equals("") || eAge.equals("") || ePosition.equals("") ) {
                    System.out.println("2");
                    Toast.makeText(MainActivity.this, "Please fill all the details", Toast.LENGTH_LONG).show();
                    System.out.println("3");
                }
                else {
                    System.out.println("5");
                    isInserted = empDB.insertData(eID, eName, eAddress, eAge, ePosition);
                    if (isInserted ==true)
                        Toast.makeText(MainActivity.this, "Data recorded successfully...", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity.this, "Data insertion is unsuccessful...\n Duplicate data entry for ID...", Toast.LENGTH_LONG).show();
                    }
                editID.setText("");
                editName.setText("");
                editAddress.setText("");
                editAge.setText("");
                editPosition.setText("");
                }
        });
    }

    public void showData(){
        Cursor cursor = empDB.retrieveData();
        if (cursor.getCount() == 0){
            showMessage("Error", "nothing found" );
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            buffer.append("Id: " + cursor.getString(0) + "\n");
            buffer.append("Name: " + cursor.getString(1) + "\n");
            buffer.append("Address: " + cursor.getString(2) + "\n");
            buffer.append("Age: " + cursor.getString(3) + "\n");
            buffer.append("Position: " + cursor.getString(4) + "\n\n\n");
        }

        showMessage("Employee List",buffer.toString());
    }



    public void showMessage( String title, String message ){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }


    public void updateData(){
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = empDB.getWritableDatabase();
                String upID = editID.getText().toString();
                String upName = editName.getText().toString();
                String upAddress = editAddress.getText().toString();
                String upAge = editAge.getText().toString();
                String upPosition = editPosition.getText().toString();

                if (upID.equals("") || upName.equals("") || upAddress.equals("") || upAge.equals("") || upPosition.equals("") ) {
                    Toast.makeText(MainActivity.this, "Please fill all the details", Toast.LENGTH_LONG).show();
                }
                else {
                    isUpdated = empDB.updateData(upID, upName, upAddress, upAge, upPosition);
                    if (isUpdated == true)
                        Toast.makeText(MainActivity.this, "Data updated successfully...", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity.this, "Data is not updated...", Toast.LENGTH_LONG).show();
                }
                editID.setText("");
                editName.setText("");
                editAddress.setText("");
                editAge.setText("");
                editPosition.setText("");
            }
        });
    }


    public void deleteData(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteID = editID.getText().toString();
                Integer delRows = empDB.deleteData(deleteID);

                if (deleteID.equals("")){
                    Toast.makeText(MainActivity.this, "Enter employee id of the employee need to be deleted", Toast.LENGTH_LONG).show();
                }
                else {
                    if (delRows > 0) {
                        Toast.makeText(MainActivity.this, "Data is deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Data is not deleted...\nEmployee not found", Toast.LENGTH_LONG).show();
                    }
                }
                editID.setText("");
                editName.setText("");
                editAddress.setText("");
                editAge.setText("");
                editPosition.setText("");
            }
        });
    }


    public void viewData(){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = empDB.retrieveData();
                if (cursor.getCount() == 0){
                    showMessage("Error", "nothing found" );
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    buffer.append("Id: " + cursor.getString(0) + "\n");
                    buffer.append("Name: " + cursor.getString(1) + "\n");
                    buffer.append("Address: " + cursor.getString(2) + "\n");
                    buffer.append("Age: " + cursor.getString(3) + "\n");
                    buffer.append("Position: " + cursor.getString(4) + "\n\n\n");
                }

                showMessage("Employee List",buffer.toString());
            }
        });
    }

}


