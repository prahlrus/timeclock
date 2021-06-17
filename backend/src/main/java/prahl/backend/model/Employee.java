package prahl.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "employees")
public class Employee {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(canBeNull = false)
    private String name;

    @ForeignCollectionField(eager = true)
    @JsonBackReference
    private ForeignCollection<Shift> shifts;

    public Employee() {}

    // getters and setters

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ForeignCollection getShifts() { return shifts; }
    public void setShifts(ForeignCollection shifts) { this.shifts = shifts; }

    public Shift findCurrentShift(Employee employee) {
        for (Shift s : this.shifts) {
            if (s.getEndTime() != null)
                return s;
        }
        return null;
    }
}
