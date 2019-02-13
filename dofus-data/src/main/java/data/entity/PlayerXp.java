package data.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import data.commons.AbstractJpaEntity;


/**
 * .
 */
@Entity
@Table(name = "dofus_player", schema = "LN")
@lombok.Data
public class PlayerXp extends AbstractJpaEntity<Long> {

    @Column(name="number")
    private int position = 0;

    private String name;

    private String classe;

    @Column(name="niveau")
    private int level;

    private String serveur;

    private long xp;

    private Date creationDate = new Date();

    public PlayerXp() {
    }


}
