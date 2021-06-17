package prahl.backend.service;

import prahl.backend.model.Employee;
import org.glassfish.jersey.spi.Contract;

import java.util.List;

@Contract
public interface EmployeeService {
    List<Employee> all() throws DataAccessException;
    Employee read(long id) throws DataAccessException;
}
