package final_project.momeasy.global.validation.annoation;

import final_project.momeasy.global.validation.validator.CheckCursorValidator;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CheckCursorValidator.class )
@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(hidden = true)
public @interface CheckCursor {
    String message() default "Invalid cursor";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
