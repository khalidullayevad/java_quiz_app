package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.Question;
import com.bezkoder.springjwt.models.QuestionForm;
import com.bezkoder.springjwt.models.Result;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.services.QuestionService;
import com.bezkoder.springjwt.services.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class QuestionCantroller {
    @Autowired
    private QuestionService questionService;
    @Autowired
    UserRepository userRepository;



    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/allQuizzes")
    public ResponseEntity<?> getAllCards(){

        List<QuestionForm> quizzes = questionService.getAllQuestionForms();
        for (QuestionForm q: quizzes) {
            System.out.println(q.getUser().getUsername());
        }
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/allPassedTests")
    public ResponseEntity<?> getAllMyPassedTest(){
        User users=getUserData();
        List<Result> results = questionService.myPassedTest(users.getId());
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/allCreatedTests")
    public ResponseEntity<?> getAllCreateTest(){
        User users=getUserData();
        List<QuestionForm> questionForms = questionService.getAllQuestionFormsByUserId(users.getId());
        return new ResponseEntity<>(questionForms, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/getTest/{id}")
    public ResponseEntity<?> getForm(@PathVariable Long id) {
        System.out.println("GET CARD");
        QuestionForm questionForm = questionService.getOneForm(id);

        if (questionForm != null) {
            return new ResponseEntity<>(questionForm, HttpStatus.OK);
        }
        System.out.println("GET CARD 2");
        return null;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/getQuestion/{id}")
    public ResponseEntity<?> getQuestion(@PathVariable Long id) {
        System.out.println("GET CARD");
      Question question =questionService.getOneQuestion(id);

        if (question != null) {
            return new ResponseEntity<>(question, HttpStatus.OK);
        }
        System.out.println("GET CARD 2");
        return null;
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/getQuestions/{test_id}")
    public ResponseEntity<?> getQuestions (@PathVariable Long test_id){
        System.out.println("GET TASKS");
        List<Question> questions = questionService.getQuestionByFormRandom(test_id);
//        System.out.println("DSD"+questions.get(1).getQuestionForm().getTitle());
        System.out.println("GET TASKS 2");
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/getResultByForm/{test_id}")
    public ResponseEntity<?> getResurtByForm (@PathVariable Long test_id){
        System.out.println("GET TASKS");
        List<Result> results = questionService.byMyTest(test_id);

        System.out.println("GET TASKS 2");
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/getQuestionsAll/{test_id}")
    public ResponseEntity<?> getQuestionsAll (@PathVariable Long test_id){
        System.out.println("GET TASKS");
        List<Question> questions = questionService.getQuestionByForm(test_id);
//        System.out.println("DSD"+questions.get(1).getQuestionForm().getTitle());
        System.out.println("GET TASKS 2");
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
    private  User getUserData(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
        User users=userRepository.findByUsername(userDetails.getUsername()).get();
        return users;
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/addResult")
    public ResponseEntity<?> addCardTask (@RequestBody Result result){
        Long qfId = result.getId();
        User users=getUserData();

        result.setPessedBy(users);
        QuestionForm questionForm = questionService.getOneForm(qfId);
        result.setQuestionForm(questionForm);
        Date currentSqlDate = new Date(System.currentTimeMillis());
        result.setAddedDate(currentSqlDate);
        result.setId(null);
        questionService.saveResult(result);

        return ResponseEntity.ok(result);

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/addQuestionForm")
    public ResponseEntity<?> addQuestionForm (@RequestBody QuestionForm questionForm){

        User users=getUserData();
        questionForm.setUser(users);
        Date currentSqlDate = new Date(System.currentTimeMillis());
        questionForm.setAddedDate(currentSqlDate);
        questionService.saveQuestionForm(questionForm);
        System.out.println("I AM HERE 2 ");
        return ResponseEntity.ok(questionForm);

    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/editQuestion")
    public ResponseEntity<?> editQuestion (@RequestBody Question question){
        System.out.println(question.getId() +"ujkl");
        Question secondQuestion = questionService.getOneQuestion(question.getId());
        System.out.println(secondQuestion.getQuestionForm().getTitle());
        question.setQuestionForm(secondQuestion.getQuestionForm());
        System.out.println(question.getQuestionForm().getTitle()+"dfghjm,");
        Date currentSqlDate = new Date(System.currentTimeMillis());
        question.setAddedDate(currentSqlDate);
        questionService.saveQuestion(question);
        System.out.println("I AM HERE 2 ");
        return ResponseEntity.ok(question);

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/addQuestion")
    public ResponseEntity<?> addQuestion (@RequestBody Question question){
        Long id = question.getId();
        question.setId(null);
        QuestionForm questionForm = questionService.getOneForm(id);
        question.setQuestionForm(questionForm);
        Date currentSqlDate = new Date(System.currentTimeMillis());
        question.setAddedDate(currentSqlDate);
        questionService.saveQuestion(question);
        System.out.println("I AM HERE 2 ");
        return ResponseEntity.ok(question);

    }
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(value = "/deleteForm/{id}")
    public ResponseEntity<?> addCardTask(@PathVariable Long id){
        System.out.println("DELETE");


        QuestionForm questionForm = questionService.getOneForm(id);
        if (questionForm!=null) {
            questionService.deleteForm(id);
        }

        System.out.println("DELETE 11 ");
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(value = "/deleteQuestion/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id){
        System.out.println("DELETE");


       Question question = questionService.getOneQuestion(id);

        if (question!=null) {
            questionService.deleteQuestion(id);
        }

        System.out.println("DELETE 11 ");
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
