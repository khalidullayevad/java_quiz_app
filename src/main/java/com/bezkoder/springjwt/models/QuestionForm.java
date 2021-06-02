package com.bezkoder.springjwt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_question_form")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "count") //Count of rondamize question
    private int count;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;


    @Column(name = "addedDate")
    private Date addedDate;



}
