package hiberspring.util;

import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;
@Component

public class ValidationUtilsImpl implements ValidationUtil{
    private final Validator validator;

    public ValidationUtilsImpl() {
        this.validator = Validation.buildDefaultValidatorFactory().
                getValidator();
    }
    @Override
    public <E> boolean isValid(E entity) {
        return this.validator.validate(entity).isEmpty();
    }
}
