package dofus.modele;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class PasswordDto {

    @NotEmpty
    private String oldPassword;

    @NotEmpty
    private String token;

    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
    message="Minimum eight characters, at least one letter, one number and one special character")
    private String newPassword;
}