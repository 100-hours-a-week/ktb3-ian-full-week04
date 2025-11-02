package ktb3.full.community.util;

import ktb3.full.community.common.Constants;
import ktb3.full.community.domain.entity.User;

public class AccountValidator {

    public static Long getUserId(User user) {
        return (user == null || user.isDeleted()) ? null : user.getId();
    }

    public static String getAuthorName(User user) {
        return (user == null || user.isDeleted()) ? Constants.DELETED_AUTHOR : user.getNickname();
    }
}
