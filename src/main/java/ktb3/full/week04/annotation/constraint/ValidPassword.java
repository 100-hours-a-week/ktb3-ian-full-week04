package ktb3.full.week04.annotation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+]).{8,20}$")
@ReportAsSingleViolation
@Constraint(validatedBy = {})
@Documented
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "올바른 비밀번호 형식을 입력해주세요. (대문자, 소문자, 숫자, 특수문자를 각각 최소 1개 포함하고 8~20자 사이여야 합니다.)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}