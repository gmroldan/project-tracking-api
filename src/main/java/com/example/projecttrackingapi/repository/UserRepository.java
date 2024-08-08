package com.example.projecttrackingapi.repository;

import com.example.projecttrackingapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true,
            value = "SELECT u.* FROM users u " +
                    "INNER JOIN team_members tm ON tm.user_id = u.id " +
                    "INNER JOIN teams t ON t.id = tm.team_id " +
                    "WHERE t.project_id = :projectId")
    List<User> findByProjectId(Long projectId);
}
