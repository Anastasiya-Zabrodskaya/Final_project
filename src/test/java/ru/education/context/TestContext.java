package ru.education.context;

import ru.education.models.UserDto;

public class TestContext {
    private UserDto currentUser;

    public UserDto getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserDto currentUser) {
        this.currentUser = currentUser;
    }
}
