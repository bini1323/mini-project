package session;

import vo.EmployeeVO;

public abstract class UserSession {
    private static EmployeeVO loginUser;

    public static void setLoginUser(EmployeeVO loginUser) {
        UserSession.loginUser = loginUser;
    }

    public static long getUserId() {
        return Long.parseLong(loginUser.getEmployeeId());
    }
}
