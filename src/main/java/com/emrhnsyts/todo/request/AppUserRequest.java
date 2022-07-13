package com.emrhnsyts.todo.request;

import com.emrhnsyts.todo.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserRequest {
    @Length(min = 5, max = 15, message = "Username length must be between 5 and 15.")
    @NotBlank(message = "Username field can not be null.")
    private String username;
    @Length(min = 5, max = 15, message = "Password length must be between 5 and 15.")
    @NotBlank(message = "Password field can not be null.")
    private String password;

}
