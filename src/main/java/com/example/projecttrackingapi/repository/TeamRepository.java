package com.example.projecttrackingapi.repository;

import com.example.projecttrackingapi.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
