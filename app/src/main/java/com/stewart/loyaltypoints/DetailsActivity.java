package com.stewart.loyaltypoints;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stewart.loyaltypoints.models.User;

import static com.google.android.gms.analytics.internal.zzy.a;
import static com.google.android.gms.analytics.internal.zzy.e;

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
                startActivity(new Intent(DetailsActivity.this, NavigationActivity.class));
            }
        });
    }

//If the user writes nothing a toast needs to come up
    private void writeUser() {
        String fName = etAcFirstName.getText().toString().trim();
        String lName = etAcLirstName.getText().toString().trim();
        String Year = spAcYear.getSelectedItem().toString().trim();
        Long studentID = Long.valueOf(etAcStudentID.getText().toString().trim());
        String Answer = etAcAnswer.getText().toString().trim();
        //Need to include the spinner
        //getSelectedItem is the method to get the required field from the dropdown menu
        String Question = spAcQuestion.getSelectedItem().toString().trim();
        Long points = Long.valueOf(0);

        if(!TextUtils.isEmpty(fName) || !TextUtils.isEmpty(lName) || !studentID.equals(null) || !TextUtils.isEmpty(Answer)||
            spAcQuestion.getSelectedItem().toString() !="Choose a Security Question:") {
            User user = new User(fName, lName, Year, studentID, Question, Answer, points);

            mDatabase.child(userID).setValue(user);
        } else {
            Toast.makeText(this, "Enter your account details to continue", Toast.LENGTH_SHORT).show();
            return;
        }



    }

    private boolean isEmpty(EditText text) {
        if (text.getText().toString().isEmpty()) {
             return false;
         }

        return true;
    }
}
