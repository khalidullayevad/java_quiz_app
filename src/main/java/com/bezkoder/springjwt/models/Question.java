package com.bezkoder.springjwt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "t_question")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "optionA")
    private String optionA;

    @Column(name = "optionB")
    private String optionB;

    @Column(name = "optionC")
    private String optionC;

    @Column(name = "answer")
    private int answer;

    @ManyToOne( fetch = FetchType.EAGER)
    private QuestionForm questionForm;

    @Column(name = "addedDate")
    private Date addedDate;


}
