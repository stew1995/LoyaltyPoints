package com.stewart.loyaltypoints;

import static android.os.Build.ID;

/**
 * Created by stewart on 16/01/2017.
 */

public class User {

    public String fName;
    public String lName;
    public String Year;
    public Long studentID;
    public String Question;
    public String Answer;
    public int Points;

    public User() {
    }

    public User(String fName, String lName, String Year, Long studentID, String Question, String Answer, int Points) {
        this.fName = fName;
        this.lName = lName;
        this.Year = Year;
        this.studentID = studentID;
        this.Question = Question;
        this.Answer = Answer;
        this.Points = 0;

    }
}
