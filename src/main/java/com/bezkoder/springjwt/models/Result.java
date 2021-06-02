package com.bezkoder.springjwt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "t_result")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private QuestionForm questionForm;

    @ManyToOne(fetch = FetchType.EAGER)
    private User pessedBy;

    @Column(name = "score")
    private int score;

    @Column(name = "countOfQuestions")
    private int countOfQuestions;


    @Column(name = "passedDate")
    private Date addedDate;





    @Column(name = "correct")
    private boolean correct;

}
