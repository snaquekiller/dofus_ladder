package dofus.modele;

import java.util.Date;

import lombok.Data;

/**
 * .
 */

@Data
public class PlayerTemporalData {

    private String classe;

    private Date oldDate;

    private String name;

    private int position = 0;

    private int positionAugmentation = 0;

    private int level;

    private int levelAugmentation;

    private long xp;

    private long xpAugmentation;

}
