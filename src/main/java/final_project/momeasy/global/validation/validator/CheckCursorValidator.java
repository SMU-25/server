package final_project.momeasy.global.validation.validator;

import final_project.momeasy.global.apiPayload.code.GeneralErrorCode;
import final_project.momeasy.global.validation.annoation.CheckCursor;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class CheckCursorValidator implements ConstraintValidator<CheckCursor, Long> {
    @Override
    public boolean isValid(Long cursor, ConstraintValidatorContext constraintValidatorContext) {
        if(cursor < 0){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(GeneralErrorCode.NOT_VALID_CURSOR.getMessage()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
