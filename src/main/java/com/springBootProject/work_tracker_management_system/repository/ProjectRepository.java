package com.springBootProject.work_tracker_management_system.repository;

import com.springBootProject.work_tracker_management_system.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

}
