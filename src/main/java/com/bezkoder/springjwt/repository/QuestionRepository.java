package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Question;
import com.bezkoder.springjwt.models.QuestionForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findAllByQuestionForm_Id(Long id);
   Question findByIdEquals(Long id);
}
