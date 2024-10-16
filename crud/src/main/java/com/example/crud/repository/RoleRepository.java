package com.example.crud.repository;

import com.example.crud.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("Select r from Role r where r.name = ?1")
    Role findByName(String name);
}
