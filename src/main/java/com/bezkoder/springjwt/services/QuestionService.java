package com.bezkoder.springjwt.services;

import com.bezkoder.springjwt.models.Question;
import com.bezkoder.springjwt.models.QuestionForm;
import com.bezkoder.springjwt.models.Result;

import java.util.List;

public interface QuestionService {
    List<QuestionForm> getAllQuestionForms();

    List<QuestionForm> getAllQuestionFormsByUserId(Long id);
    List<Question> getQuestionByFormRandom(Long id);
    List<Question> getQuestionByForm(Long id);
    void deleteForm(Long id);
    void deleteQuestion(Long id);
    void deleteResult(Long id);
    QuestionForm getOneForm(Long id);
    Question getOneQuestion (Long id);
    Question saveQuestion(Question question);
    QuestionForm saveQuestionForm(QuestionForm questionForm);
    Result saveResult( Result result);
    List <Result> myPassedTest(Long id);
    List <Result> byMyTest(Long id);
}
