package ru.practicum.ewm.core.events.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = EventFutureValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
public @interface EventFuture {

    String message() default "Event date should be in future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}