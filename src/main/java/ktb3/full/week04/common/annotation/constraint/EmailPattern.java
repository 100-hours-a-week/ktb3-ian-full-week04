package ktb3.full.week04.common.annotation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
@ReportAsSingleViolation
@Documented
@Constraint(validatedBy = { })
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailPattern {

    String message() default "올바른 이메일 주소 형식을 입력해주세요. (예: example@example.com)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
