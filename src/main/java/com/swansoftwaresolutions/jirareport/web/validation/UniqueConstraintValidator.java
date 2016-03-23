package com.swansoftwaresolutions.jirareport.web.validation;

import com.swansoftwaresolutions.jirareport.core.dto.user.UserDto;
import com.swansoftwaresolutions.jirareport.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Vladimir Martynyuk
 */
@Component
public class UniqueConstraintValidator implements ConstraintValidator<Unique, String> {

    Class<?> aClass;
    String fieldName;
    Unique unique;

    @Autowired
    UserService userService;

    @Override
    public void initialize(Unique unique) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        System.out.println(unique.property());
        System.out.println(unique.value());
        aClass = unique.value();
        this.unique = unique;
        fieldName = unique.property();
    }

    @Override
    @Transactional
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        UserDto customerList = userService.retrieveByUsername(s);
        if(customerList!=null){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("User already exist").addConstraintViolation();
            return false;
        }
        else
            return true;
    }
}
