package com.vgowda.hasura.chinook.repositories;

import com.vgowda.hasura.chinook.models.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
