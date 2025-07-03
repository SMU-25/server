package final_project.momeasy.global.validation.validator;

import final_project.momeasy.global.apiPayload.code.GeneralErrorCode;
import final_project.momeasy.global.validation.annoation.CheckSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class CheckSizeValidator implements ConstraintValidator<CheckSize, Integer> {
    @Override
    public boolean isValid(Integer size, ConstraintValidatorContext constraintValidatorContext) {
        if(size <= 0) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(GeneralErrorCode.NOT_VALID_SIZE.getMessage()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
