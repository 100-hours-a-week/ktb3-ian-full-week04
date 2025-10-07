package ktb3.full.week04.common;

public abstract class Constants {

    // Session Attribute Name
    public static final String SESSION_ATTRIBUTE_NAME_LOGGED_IN_USER = "loggedInUser";

    // Message
    public static final String MESSAGE_NOT_NULL_EMAIL = "이메일을 입력해주세요.";
    public static final String MESSAGE_NOT_NULL_NICKNAME = "닉네임을 입력해주세요.";
    public static final String MESSAGE_NOT_NULL_PASSWORD = "비밀번호를 입력해주세요.";
    public static final String MESSAGE_NOT_NULL_POST_TITLE = "제목을 입력해주세요.";
    public static final String MESSAGE_NOT_NULL_POST_CONTENT = "내용을 입력해주세요.";

    public static final String MESSAGE_EMAIL_PATTERN = "올바른 이메일 주소 형식을 입력해주세요. (예: example@example.com)";
    public static final String MESSAGE_NICKNAME_PATTERN = "올바른 닉네임 형식을 입력해주세요. (한글, 영어, 숫자만 포함하고 공백 없이 1~10자 사이여야 합니다.)";
    public static final String MESSAGE_PASSWORD_PATTERN = "올바른 비밀번호 형식을 입력해주세요. (대문자, 소문자, 숫자, 특수문자를 각각 최소 1개 포함하고 8~20자 사이여야 합니다.)";
    public static final String MESSAGE_POST_TITLE_PATTERN = "제목은 1 ~ 26자로 입력해주세요.";
    public static final String MESSAGE_POST_CONTENT_PATTERN = "내용을 입력해주세요.";
}
