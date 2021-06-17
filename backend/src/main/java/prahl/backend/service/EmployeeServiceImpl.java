package prahl.backend.service;

import com.j256.ormlite.dao.Dao;
import prahl.backend.model.DaoObtainer;
import prahl.backend.model.Employee;
import jakarta.inject.Inject;
import org.jvnet.hk2.annotations.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private Dao<Employee,Long> employeeDao;

    @Inject
    public EmployeeServiceImpl(DaoObtainer obtainer) {
        this.employeeDao = obtainer.obtainEmployeeDao();
    }

    @Override
    public List<Employee> all() throws DataAccessException {
        try {
            return this.employeeDao.queryForAll();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Employee read(long id) throws DataAccessException {
        try {
            return this.employeeDao.queryForId(id);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
