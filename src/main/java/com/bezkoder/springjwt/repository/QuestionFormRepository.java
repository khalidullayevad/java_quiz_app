package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.models.QuestionForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface QuestionFormRepository extends JpaRepository<QuestionForm,Long> {
    QuestionForm findByIdEquals(Long id);
    List<QuestionForm> findAllByUser_Id(Long id);
}
