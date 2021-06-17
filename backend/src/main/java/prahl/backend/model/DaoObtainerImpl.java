package prahl.backend.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.jvnet.hk2.annotations.Service;

import java.sql.SQLException;
import java.util.Date;

@Service
public class DaoObtainerImpl implements DaoObtainer {
    private JdbcPooledConnectionSource connectionSource;
    private Dao<Employee,Long> employeeDao;
    private Dao<Shift,Long> shiftDao;

    private boolean populated = false;

    public DaoObtainerImpl() throws SQLException {
        this.connectionSource = new JdbcPooledConnectionSource("jdbc:h2:mem:TimeClockDB");
        this.employeeDao = DaoManager.createDao(this.connectionSource, Employee.class);
        this.shiftDao = DaoManager.createDao(this.connectionSource, Shift.class);
        populateTestData();
    }

    @Override
    public Dao<Employee, Long> obtainEmployeeDao() {
        return this.employeeDao;
    }

    @Override
    public Dao<Shift, Long> obtainShiftDao() {
        return this.shiftDao;
    }

    private void populateTestData() throws SQLException {
        if (!this.populated) {
            this.populated = true;

            TableUtils.dropTable(this.connectionSource, Employee.class, true);
            TableUtils.createTableIfNotExists(this.connectionSource, Employee.class);

            TableUtils.dropTable(this.connectionSource, Shift.class, true);
            TableUtils.createTableIfNotExists(this.connectionSource, Shift.class);

            Employee e1 = new Employee();
            e1.setName("Edward Ershter");
            employeeDao.create(e1);

            Employee e2 = new Employee();
            e2.setName("Steven Tsweiter");
            employeeDao.create(e2);

            Shift s11 = new Shift();
            s11.setEmployee(e1);
            s11.setStartTime(new Date(2021,6,16,9,1));
            s11.setEndTime(new Date(2021,6,16,16,56));
            shiftDao.create(s11);

            Shift s12 = new Shift();
            s12.setEmployee(e1);
            s12.setStartTime(new Date(2021,6,17,8,59));
            shiftDao.create(s12);

            Shift s21 = new Shift();
            s21.setEmployee(e2);
            s21.setStartTime(new Date(2021,6,16,8,57));
            s21.setEndTime(new Date(2021,6,16,17,13));
            shiftDao.create(s21);
        }
    }
}
