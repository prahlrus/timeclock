package prahl.backend.service;

import org.glassfish.jersey.spi.Contract;
import prahl.backend.model.Employee;
import prahl.backend.model.Shift;

import java.util.Date;

@Contract
public interface ShiftService {
    Shift startShift(Employee employee, Date startTime) throws DataAccessException;
    Shift endShift(Employee employee, Date endTime) throws DataAccessException;
}
