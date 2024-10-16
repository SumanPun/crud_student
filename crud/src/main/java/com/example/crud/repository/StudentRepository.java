package com.example.crud.repository;

import com.example.crud.dto.StudentDto;
import com.example.crud.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("Select s from Student s where s.id= ?1")
    Optional<Student> getStudentById(Integer id);

    @Query("Select s from Student s where s.fullName like :key")
    List<Student> searchByKeywords(@Param("key") String keyword);
}
