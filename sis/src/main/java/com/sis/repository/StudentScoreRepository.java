package com.sis.repository;

import com.sis.entity.StudentScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentScoreRepository extends JpaRepository<StudentScore, Integer> {

    @Query("""
            SELECT ss
            FROM StudentScore ss
            JOIN FETCH ss.student st
            JOIN FETCH ss.subject su
            ORDER BY st.studentId DESC, su.subjectId ASC, ss.studentScoreId DESC
            """)
    List<StudentScore> findAllWithStudentAndSubject();
}

