package getln.data.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import getln.data.commons.AbstractJpaEntity;

/**
 * .
 */
@Entity
@Table(name = "dofus_player", schema = "LN")
@lombok.Data
public class Player extends AbstractJpaEntity<Long> {

    private int number = 0;

    private String name;

    private String classe;

    private int niveau;

    private String serveur;

    private long xp;

    private Date creationDate = new Date();

    public Player() {
    }


}
