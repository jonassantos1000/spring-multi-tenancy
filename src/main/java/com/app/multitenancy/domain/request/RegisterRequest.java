package com.app.multitenancy.domain.request;

import com.app.multitenancy.domain.Tenant;
import com.app.multitenancy.domain.UserRole;

public record RegisterRequest(String username, String password, String name, String email, UserRole role, Tenant tenant) {

}
