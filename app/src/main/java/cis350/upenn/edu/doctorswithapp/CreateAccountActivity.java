package cis350.upenn.edu.doctorswithapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;

import cis350.upenn.edu.doctorswithapp.processor.Processor;
import cis350.upenn.edu.doctorswithapp.processor.FileProcessor;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Spinner gender = findViewById(R.id.gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genders, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter);
    }

    public void onCreateButtonClick(View view){
        EditText username = (EditText)findViewById(R.id.username);
        String user = username.getText().toString();

        EditText password1 = (EditText)findViewById(R.id.password1);
        String pass1 = password1.getText().toString();

        EditText password2 = (EditText)findViewById(R.id.password2);
        String pass2 = password2.getText().toString();

        EditText name = (EditText)findViewById(R.id.name);
        String n = name.getText().toString();

        EditText age = (EditText)findViewById(R.id.age);
        int a = Integer.parseInt(age.getText().toString());

        Spinner gender = (Spinner) findViewById(R.id.gender);
        String g = gender.getSelectedItem().toString();

        EditText doctor = (EditText)findViewById(R.id.doctor);
        String doc = doctor.getText().toString();

        boolean correct = true;
        if(MainActivity.processor.usernameTaken(user)){
            username.setText("");
            TextView username_error = (TextView)findViewById(R.id.username_error);
            username_error.setText("Username " + user + " is already taken.");
            correct = false;
        }
        else{
            TextView username_error = (TextView)findViewById(R.id.username_error);
            username_error.setText(" ");
        }
        if(!pass1.equals(pass2)){
            TextView password_error = (TextView)findViewById(R.id.password_error);
            password_error.setText("The passwords are not the same.");
            correct = false;
        }
        else{
            TextView password_error = (TextView)findViewById(R.id.password_error);
            password_error.setText(" ");
        }
        if(a >= 150){
            TextView age_error = (TextView)findViewById(R.id.age_error);
            age_error.setText("Your age is too great.");
            correct = false;
        }
        else if(a < 16){
            TextView age_error = (TextView)findViewById(R.id.age_error);
            age_error.setText("You are a minor. You cannot create an account.");
            correct = false;
        }
        else{
            TextView age_error = (TextView)findViewById(R.id.age_error);
            age_error.setText(" ");
        }
        if(!MainActivity.processor.isDoc(doc)){
            TextView doctor_error = (TextView)findViewById(R.id.doctor_error);
            doctor_error.setText(doc + " is not a registered doctor.");
            correct = false;
        }
        else{
            TextView doctor_error = (TextView)findViewById(R.id.doctor_error);
            doctor_error.setText(" ");
        }

        if(correct){
            MainActivity.processor.createNewAccount(user, pass1, n, a, g, doc);

            Intent i = new Intent(this, HomePageActivity.class);
            i.putExtra("USERNAME", user);
            startActivity(i);
        }
    }

    public void onCancelButtonClick(View view){
        Intent i = new Intent();
        finish();
    }
}
