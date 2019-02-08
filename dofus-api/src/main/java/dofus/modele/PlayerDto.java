package dofus.modele;

import java.util.Date;

import lombok.Data;

/**
 * .
 */
@Data
public class PlayerDto {

    private int number = 0;

    private String name;

    private String classe;

    private int niveau;

    private String serveur;

    private Date creationDate = new Date();

    private Long xp;
}
