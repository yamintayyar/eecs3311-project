package com.team.servicebooking.model.user;

import java.util.UUID;

public interface User {
    UUID user_id = null;
    String name = "";
    String email = "";
    String password = "";

    boolean login();

    boolean logout();
}
