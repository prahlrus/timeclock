package prahl.backend.service;

/** Wraps exceptions thrown by the data layer */
public class DataAccessException extends Exception {
    public DataAccessException(Exception e) {
        super("Data access exception of type " + e.getClass(), e);
    }
}
