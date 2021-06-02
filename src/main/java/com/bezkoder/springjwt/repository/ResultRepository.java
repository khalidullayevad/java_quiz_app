package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ResultRepository extends JpaRepository<Result,Long> {
    List<Result> findAllByPessedBy_Id(Long id);
    List<Result> findAllByQuestionForm_Id(Long id);
}
