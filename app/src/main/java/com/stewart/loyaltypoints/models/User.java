package com.stewart.loyaltypoints.models;

import static android.os.Build.ID;

/**
 * Created by stewart on 16/01/2017.
 */

public class User {

    private String fName;
    private String lName;
    private String Year;
    private Long studentID;
    private String Question;
    private String Answer;
    private Long Points;

    public User() {
    }

    public User(String fName, String lName, String Year, Long studentID, String Question, String Answer, Long points) {
        this.fName = fName;
        this.lName = lName;
        this.Year = Year;
        this.studentID = studentID;
        this.Question = Question;
        this.Answer = Answer;
        this.Points = points;

    }

    public Long getPoints() {
        return Points;
    }

}
