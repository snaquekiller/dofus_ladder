package dofus.service;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

import org.springframework.context.annotation.ComponentScan;

/**
 * .
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
@ComponentScan(basePackageClasses = ApiService.class)
public @interface ApiService {

}
