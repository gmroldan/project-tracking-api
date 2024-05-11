package com.example.projecttrackingapi.repository;

import com.example.projecttrackingapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    Page<User> findAll(Pageable pageable);
}
