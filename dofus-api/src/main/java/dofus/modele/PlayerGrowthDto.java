package dofus.modele;

import java.util.Date;
import java.util.HashMap;

import lombok.Data;

/**
 * .
 */
@Data
public class PlayerGrowthDto {

    private int number = 0;

    private String name;

    private String classe;

    private int niveau;

    private String serveur;

    private Date creationDate = new Date();

    private HashMap<Date, TemporalData> xpDate;
}
