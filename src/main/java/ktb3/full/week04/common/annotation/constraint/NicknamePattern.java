package ktb3.full.week04.common.annotation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Pattern(regexp = "^[가-힣a-zA-Z0-9]{1,10}$")
@ReportAsSingleViolation
@Documented
@Constraint(validatedBy = { })
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NicknamePattern {

    String message() default "올바른 닉네임 형식을 입력해주세요. (한글, 영어, 숫자만 포함하고 공백 없이 1~10자 사이여야 합니다.)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
