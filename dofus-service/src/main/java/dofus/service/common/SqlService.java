package dofus.service.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import org.springframework.context.annotation.ComponentScan;

import getln.data.DofusData;

/**
 * .
 */
@Target(ElementType.TYPE)
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
@ComponentScan(basePackageClasses = SqlService.class)
@DofusData
public @interface SqlService {

}
