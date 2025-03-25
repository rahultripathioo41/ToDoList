package com.practice.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import com.practice.entites.Task;

@Component
public interface TaskDao extends CrudRepository<Task, Integer>{
	
	@Query(value="select * from task where user_id=:userId order by priority desc",nativeQuery = true)
	public List<Task> findByUser(@Param("userId") int userId);
	
}
