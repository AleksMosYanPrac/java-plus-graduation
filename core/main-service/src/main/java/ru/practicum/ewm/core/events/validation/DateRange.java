package ru.practicum.ewm.core.events.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface DateRange {

    String message() default "Start Date of range can't be after End Date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}