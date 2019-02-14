package dofus.modele.mapper;

import data.entity.PlayerXp;
import dofus.modele.PlayerTemporalData;

public class PlayerTemporalDataMapper {

    public static PlayerTemporalData map(PlayerXp player, long oldXp, int oldPosition, int oldLevel) {

        PlayerTemporalData temporalData = new PlayerTemporalData();
        temporalData.setClasse(player.getClasse());
        temporalData.setName(player.getName());

        temporalData.setLevel(player.getLevel());
        temporalData.setLevelAugmentation(player.getLevel() - oldLevel);

        temporalData.setXp(player.getXp());
        temporalData.setXpAugmentation(player.getXp() - oldXp);

        temporalData.setPosition(player.getPosition());
        temporalData.setPositionAugmentation(oldPosition - player.getPosition());

        return temporalData;
    }

}
