package dofus.modele.request;

import java.util.Date;

import lombok.Data;

/**
 * .
 */
@Data
public class UserRequestDto {

    private String email;

    /**
     * The name of the user
     **/
    private String nom;

    /**
     * Prenom
     **/
    private String prenom;

    /**
     * the pseudo
     **/
    private String pseudo;

    private String password;

    private String role;

}
