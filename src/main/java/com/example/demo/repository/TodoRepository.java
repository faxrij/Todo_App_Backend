package com.example.demo.repository;

import com.example.demo.model.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {


    // SELECT * FROM TODO WHERE name = ?
    @Query("SELECT t FROM Todo t WHERE t.name=?1")
    Optional<Todo> findTodoByName(String name);

    @Query("SELECT t FROM Todo t WHERE t.id=?1")
    Optional<Todo> findTodoById(Long id);
}
