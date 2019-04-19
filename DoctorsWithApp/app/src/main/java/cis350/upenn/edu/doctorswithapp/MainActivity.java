package cis350.upenn.edu.doctorswithapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.util.Log;

import cis350.upenn.edu.doctorswithapp.processor.Processor;
import cis350.upenn.edu.doctorswithapp.processor.FileProcessor;
import cis350.upenn.edu.doctorswithapp.processor.WebProcessor;

public class MainActivity extends AppCompatActivity {
    public static Processor processor;
    public static Context context;
    public static final int HomePageActivity_ID = 1;
    public static final int CreateAccountActivity_ID = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        processor = new FileProcessor();
    }

    public void onLoginButtonClick(View view){
        EditText login_id = (EditText)findViewById(R.id.login_id);

        String id = login_id.getText().toString();

        EditText login_pass = (EditText)findViewById(R.id.login_pass);

        String pass = login_pass.getText().toString();

        if(processor.usernameTaken(id)){
            if(processor.correctLogin(id, pass)){
                Intent i = new Intent(this, HomePageActivity.class);
                i.putExtra("USERNAME", id);
                startActivity(i);
            }
            else{
                TextView error = (TextView)findViewById(R.id.error);
                error.setText("Password is incorrect.");
            }
        }
        else{
            TextView error = (TextView)findViewById(R.id.error);
            error.setText("Username is not associated to an existing account.");
        }
    }

    public void onCreateButtonClick(View view){
        Intent i = new Intent(this, CreateAccountActivity.class);
        startActivity(i);
    }
}
