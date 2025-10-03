package ktb3.full.week04.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiErrorCode {

    // 400 Bad Request
    INVALID_INPUT("4001"),

    // 401 Unauthorized
    INVALID_CREDENTIALS("4011"),
    LOGIN_REQUIRED("4012"),

    // 403 Forbidden
    NO_PERMISSION("4031"),

    // 404 Not Found
    USER_NOT_FOUND("4041"),
    POST_NOT_FOUND("4042"),
    COMMENT_NOT_FOUND("4043"),

    // 405 Method Not Allowed
    METHOD_NOT_ALLOWED("4051"),

    // 409 Conflict
    DUPLICATED_EMAIL("4091"),
    DUPLICATED_NICKNAME("4092"),

    // 415 Unsupported Media Type
    UNSUPPORTED_MEDIA_TYPE("4151"),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR("5001"),
    ;

    private final String code;
}