package com.byteshaper.cheatsheets.springbootrest.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates that a given number has the value 42.
 *
 */
public class FourtyTwoValidator implements ConstraintValidator<FourtyTwo, Integer> {

	@Override
	public void initialize(FourtyTwo constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		return value != null && value == 42;
	}

}
