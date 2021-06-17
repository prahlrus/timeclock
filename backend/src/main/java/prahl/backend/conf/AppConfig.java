package prahl.backend.conf;

import prahl.backend.model.DaoObtainer;
import prahl.backend.model.DaoObtainerImpl;
import prahl.backend.resource.EmployeeResource;
import prahl.backend.service.EmployeeService;
import prahl.backend.service.EmployeeServiceImpl;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import prahl.backend.service.ShiftService;
import prahl.backend.service.ShiftServiceImpl;

@ApplicationPath("/api")
public class AppConfig extends ResourceConfig {
    public AppConfig() {
        register(CORSFilter.class);
        register(EmployeeResource.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(DaoObtainerImpl.class).to(DaoObtainer.class);
                bind(EmployeeServiceImpl.class).to(EmployeeService.class);
                bind(ShiftServiceImpl.class).to(ShiftService.class);
            }
        });
    }
}
