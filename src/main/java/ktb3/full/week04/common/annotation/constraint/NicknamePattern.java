package ktb3.full.week04.common.annotation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

import static ktb3.full.week04.common.Constants.MESSAGE_NICKNAME_PATTERN;

@Pattern(regexp = "^[가-힣a-zA-Z0-9]{1,10}$")
@ReportAsSingleViolation
@Documented
@Constraint(validatedBy = { })
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NicknamePattern {

    String message() default MESSAGE_NICKNAME_PATTERN;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
