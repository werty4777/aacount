package com.aemw.microserviceaccount.User.Infrastructure.api.models;

import com.aemw.microserviceaccount.User.Domain.customer;
import com.aemw.microserviceaccount.User.Domain.login;

public class    userAndProfile {
    public customer profile;
    public login user;

    public customer getProfile() {
        return profile;
    }

    public void setProfile(customer profile) {
        this.profile = profile;
    }

    public login getUser() {
        return user;
    }

    public void setUser(login user) {
        this.user = user;
    }
}
