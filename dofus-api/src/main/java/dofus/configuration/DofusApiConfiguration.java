package dofus.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import dofus.GetLnJob;
import dofus.controller.ApiControllerInterface;
import dofus.service.ApiService;
import data.DofusData;
import dofus.service.common.SqlService;

//@formatter:off
@Configuration
@Import({JpaConfiguration.class, NukeServletApiConfiguration.class
})
@ComponentScan(basePackageClasses = {
        GetLnJob.class,
        ApiControllerInterface.class,
        SqlService.class
})
@DofusData
@SqlService
@ApiService
//@formatter:on
public class DofusApiConfiguration {

}
