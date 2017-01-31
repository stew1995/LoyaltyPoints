package com.stewart.loyaltypoints;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stewart.loyaltypoints.models.User;

public class DetailsActivity extends AppCompatActivity {

    private Button btnAcStart;
    private DatabaseReference mDatabase;
    private EditText etAcFirstName, etAcLirstName, etAcStudentID, etAcAnswer;
    private Spinner spAcYear, spAcQuestion;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        btnAcStart = (Button) findViewById(R.id.btnAcStart);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        etAcFirstName = (EditText) findViewById(R.id.etAcFirstName);
        etAcLirstName = (EditText) findViewById(R.id.etAcLastName);
        etAcStudentID = (EditText) findViewById(R.id.etAcStudentID);
        etAcAnswer= (EditText) findViewById(R.id.etAcAnswer);
        spAcYear = (Spinner) findViewById(R.id.spAcYear);
        spAcQuestion = (Spinner) findViewById(R.id.spAcQuestion);




        btnAcStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeUser();
                startActivity(new Intent(DetailsActivity.this, MainScreenActivity.class));
            }
        });
    }


    private void writeUser() {


        String fName = etAcFirstName.getText().toString().trim();
        String lName = etAcLirstName.getText().toString().trim();
        String Year = spAcYear.getSelectedItem().toString().trim();
        Long studentID = Long.valueOf(etAcStudentID.getText().toString().trim());
        String Answer = etAcAnswer.getText().toString().trim();
        int Points = 0;
        //Need to include the spinner
        //getSelectedItem is the method to get the required field from the dropdown menu
        String Question = spAcQuestion.getSelectedItem().toString().trim();

        User user = new User(fName, lName, Year, studentID, Question, Answer, Points);

        mDatabase.child(userID).setValue(user);
    }
}
