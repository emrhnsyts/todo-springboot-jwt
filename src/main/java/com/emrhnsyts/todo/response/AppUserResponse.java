package com.emrhnsyts.todo.response;

import com.emrhnsyts.todo.entity.AppUser;
import lombok.Data;

@Data
public class AppUserResponse {
    private Long id;
    private String username;

    public AppUserResponse(AppUser appUser) {
        this.id = appUser.getId();
        this.username = appUser.getUsername();
    }
}
