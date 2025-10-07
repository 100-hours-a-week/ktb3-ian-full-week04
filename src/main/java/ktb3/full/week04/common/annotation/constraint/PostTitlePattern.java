package ktb3.full.week04.common.annotation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

import static ktb3.full.week04.common.Constants.MESSAGE_POST_TITLE_PATTERN;

@Pattern(regexp = "^(?=.*\\S).{1,26}$")
@ReportAsSingleViolation
@Documented
@Constraint(validatedBy = { })
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PostTitlePattern {

    String message() default MESSAGE_POST_TITLE_PATTERN;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
