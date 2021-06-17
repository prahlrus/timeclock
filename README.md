# Simple Timeclock Application

This is a simple implementation of an Employee time clock using a Jersey servlet as a backend and a React frontend. The backend exposes the following API endpoints:

 - GET /api/employees: returns a list of employees in the database.
 - GET /api/employees/{id}: returns details for a particular employee.
 - GET /api/employees/{id}/shifts: returns all of the shifts associated with an employee.
 - POST /api/employees/{id}/start: creates a shift, starting now, for the employee if they do not have an active shift currently and returns it if successful.
 - POST /api/employees/{id}/end: ends the current shift of an employee, if they have an active shift, and returns the updated shift data if successful.

To run the backend, `cd backend/` and `mvn clean package` to create the WAR. Then deploy the WAR to a container of your choice (tested with Apache Tomcat 10.6).

# Current State

To be honest, a lot of work remains to be done here:

 - React frontend not yet implemented.
 - GET /api/employees/{id}/shifts causes an unidetified 500 error.
 - POST /api/employees/{id}/start and /end never return a shift object. Because the shift retrieval itself is not working, it is unclear whether they are behaving correctly otherwise.
 - Currently we always drop the tables of the database and re-populate them when connecting to the database. This is for testing purposes only, when the other APIs are working (in particular, if APIs to create employees are added) the data will be allowed to persist.

