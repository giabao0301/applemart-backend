package com.applemart.auth.user;
import com.applemart.auth.user.role.Role;
import com.applemart.auth.user.role.RoleDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private String fullName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String profileImageUrl;
    private Set<RoleDTO> roles;
}
