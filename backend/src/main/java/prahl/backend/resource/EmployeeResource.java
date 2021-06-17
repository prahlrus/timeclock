package prahl.backend.resource;

import jakarta.ws.rs.*;
import prahl.backend.service.DataAccessException;
import prahl.backend.model.Employee; The MIT License (MIT)

Copyright © 2021 <copyright holders>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

import prahl.backend.model.Shift;
import prahl.backend.service.EmployeeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import prahl.backend.service.ShiftService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/employees")
public class EmployeeResource {
    private EmployeeService employeeService;
    private ShiftService shiftService;

    @Inject
    public EmployeeResource(EmployeeService employeeService, ShiftService shiftService) {
        this.employeeService = employeeService;
        this.shiftService = shiftService;
    }

    private static Response dataAccessException(DataAccessException e) {
        return Response
                .serverError()
                .header("Message", e.getMessage())
                .header("Description",e.getStackTrace())
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        try {
            List<Employee> employees = employeeService.all();
            if (employees != null) {
                return Response.ok()
                        .entity(employees)
                        .build();
            } else {
                return Response
                        .status(Response.Status.NO_CONTENT)
                        .build();
            }
        } catch (DataAccessException e) {
            return dataAccessException(e);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@PathParam("id") long id) {
        try {
            Employee employee = employeeService.read(id);
            if (employee != null) {
                return Response.ok()
                        .entity(employee)
                        .build();
            } else {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .build();
            }
        }  catch (DataAccessException e) {
            return dataAccessException(e);
        }
    }

    @GET
    @Path("/{id}/shifts")
    public Response getEmployeeShifts(@PathParam("id") long id) {
        try {
            Employee employee = employeeService.read(id);
            if (employee == null) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .build();
            }

            List<Shift> shifts = new ArrayList<>();
            shifts.addAll(employee.getShifts());

            return Response
                    .ok(shifts)
                    .build();
        }  catch (DataAccessException e) {
            return dataAccessException(e);
        }
    }

    @POST
    @Path("/{id}/start")
    @Produces(MediaType.APPLICATION_JSON)
    public Response startShift(@PathParam("id") long id) {
        try {
            Date now = new Date(System.currentTimeMillis());
            Employee employee = employeeService.read(id);
            if (employee == null) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .build();
            }

            Shift shift = shiftService.startShift(employee, now);
            // indicate a failure with a null data object in the response
            return Response
                    .ok(shift)
                    .build();
        }  catch (DataAccessException e) {
            return dataAccessException(e);
        }
    }

    @POST
    @Path("/{id}/end")
    @Produces(MediaType.APPLICATION_JSON)
    public Response endShift(@PathParam("id") long id) {
        try {
            Date now = new Date(System.currentTimeMillis());
            Employee employee = employeeService.read(id);
            if (employee == null) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .build();
            }

            Shift shift = shiftService.endShift(employee, now);
            // indicate a failure with a null data object in the response
            return Response
                    .ok(shift)
                    .build();
        }  catch (DataAccessException e) {
            return dataAccessException(e);
        }
    }
}