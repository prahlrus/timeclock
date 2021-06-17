package prahl.backend.service;

import com.j256.ormlite.dao.Dao;
import jakarta.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import prahl.backend.model.DaoObtainer;
import prahl.backend.model.Employee;
import prahl.backend.model.Shift;

import java.sql.SQLException;
import java.util.Date;

@Service
public class ShiftServiceImpl implements ShiftService {
    private Dao<Shift,Long> shiftDao;

    @Inject
    public ShiftServiceImpl(DaoObtainer obtainer) {
        this.shiftDao = obtainer.obtainShiftDao();
    }

    @Override
    public Shift startShift(Employee employee, Date startTime) throws DataAccessException {
        try {
            // do not allow a shift to start if one is currently open
            Shift current = employee.findCurrentShift(employee);
            if (current != null)
                return null;

            Shift shift = new Shift();
            if (shiftDao.create(shift) == 1)
                return shift;
            else
                return null;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Shift endShift(Employee employee, Date endTime) throws DataAccessException {
        try {
            // it is only possible to end a shift if an employee has one open
            Shift current = employee.findCurrentShift(employee);
            if (current == null)
                return null;

            current.setEndTime(endTime);
            // return null to indicate failure to save the new data
            if (this.shiftDao.update(current) != 1)
                return current;
            else
                return null;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
