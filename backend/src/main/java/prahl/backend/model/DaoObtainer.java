package prahl.backend.model;

import com.j256.ormlite.dao.Dao;
import org.glassfish.jersey.spi.Contract;
import prahl.backend.model.Employee;
import prahl.backend.model.Shift;

@Contract
public interface DaoObtainer {
    Dao<Employee,Long> obtainEmployeeDao();
    Dao<Shift,Long> obtainShiftDao();
}
