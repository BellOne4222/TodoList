package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.TodoEntity;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
}
