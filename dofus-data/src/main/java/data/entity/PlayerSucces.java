package data.entity;


import data.commons.AbstractJpaEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * .
 */
@Entity
@Table(name = "dofus_succes", schema = "LN")
@lombok.Data
public class PlayerSucces extends AbstractJpaEntity<Long> {

    private int number = 0;

    private String name;

    private String classe;

    private int niveau;

    private String serveur;

    private long succes;

    private Date creationDate = new Date();

    public PlayerSucces() {
    }


}
