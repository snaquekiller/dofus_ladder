package dofus.configuration;

import data.entity.EntityData;
import data.service.DataRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Base JPA configuration.
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = {EntityData.class, DataRepository.class})
@EntityScan(basePackageClasses = {EntityData.class, DataRepository.class})
public abstract class JpaConfiguration {

}
