package com.bezkoder.springjwt.services.impl;

import com.bezkoder.springjwt.models.Question;
import com.bezkoder.springjwt.models.QuestionForm;
import com.bezkoder.springjwt.models.Result;
import com.bezkoder.springjwt.repository.QuestionFormRepository;
import com.bezkoder.springjwt.repository.QuestionRepository;
import com.bezkoder.springjwt.repository.ResultRepository;
import com.bezkoder.springjwt.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@Service
public class QuestionImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private QuestionFormRepository questionFormRepository;
    @Override
    public List<QuestionForm> getAllQuestionForms() {
        return questionFormRepository.findAll();
    }



    @Override
    public List<QuestionForm> getAllQuestionFormsByUserId(Long id) {
        return questionFormRepository.findAllByUser_Id(id);
    }

    @Override
    public List<Question> getQuestionByFormRandom(Long id) {
        List<Question> allQues = questionRepository.findAllByQuestionForm_Id(id);
        if (allQues.size()>0) {
            int count = allQues.size();
            if(allQues.get(0).getQuestionForm().getCount()<=allQues.size()) {

                count = allQues.get(0).getQuestionForm().getCount();
            }
                List<Question> qList = new ArrayList<Question>();
                Random random = new Random();
                for (int i = 0; i < count; i++) {
                    int rand = random.nextInt(allQues.size());
                    qList.add(allQues.get(rand));
                    allQues.remove(rand);
                }

                for (int i = 0; i < count; i++) {
                    System.out.println(qList.get(i).getQuestionText());
                }
                return qList;
            }

         return null;
    }

    @Override
    public List<Question> getQuestionByForm(Long id) {
        return questionRepository.findAllByQuestionForm_Id(id);
    }

    @Override
    public void deleteForm(Long id) {
        List<Result> allResult = resultRepository.findAllByQuestionForm_Id(id);

        if(allResult.size()!=0){
            System.out.println("I AM hero! Result!! ");

               for(int i=0; i<allResult.size();i++){
                   resultRepository.deleteById(allResult.get(i).getId());
               }
                System.out.println("I am here Result 3");

        }

        List<Question> allQues = questionRepository.findAllByQuestionForm_Id(id);
        if(allQues!=null){
            System.out.println("I AM hero!!! ");
            for(int i =0; i<allQues.size();i++) {
                System.out.println("I am here 2");
                questionRepository.deleteById(allQues.get(i).getId());
                System.out.println("I am here 3");
            }
        }

        questionFormRepository.deleteById(id);


    }
    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
    @Override
    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }
    @Override
    public QuestionForm getOneForm(Long id) {
        return questionFormRepository.findByIdEquals(id);
    }

    @Override
    public Question getOneQuestion(Long id) {
        return questionRepository.findByIdEquals(id);
    }

    @Override
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public QuestionForm saveQuestionForm(QuestionForm questionForm) {
        return questionFormRepository.save(questionForm);
    }

    @Override
    public Result saveResult(Result result) {
        return resultRepository.save(result);
    }

    @Override
    public List<Result> myPassedTest(Long id) {
        return resultRepository.findAllByPessedBy_Id(id);
    }

    @Override
    public List<Result> byMyTest(Long id) {
        return resultRepository.findAllByQuestionForm_Id(id);
    }
}
