package dofus.modele.mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import data.entity.PlayerXp;
import dofus.modele.PlayerGrowthDto;
import dofus.modele.PlayerTemporalData;

public class PlayerGrowthMapper {

    public static PlayerGrowthDto map(List<PlayerXp> playerXps) {
        PlayerXp playerXp = playerXps.get(0);
        PlayerGrowthDto playerGrowthDto = new PlayerGrowthDto();
        playerGrowthDto.setClasse(playerXp.getClasse());
        playerGrowthDto.setCreationDate(playerXp.getCreationDate());
        playerGrowthDto.setName(playerXp.getName());
        playerGrowthDto.setNiveau(playerXp.getLevel());
        playerGrowthDto.setServeur(playerXp.getServeur());
        playerGrowthDto.setNumber(playerXp.getPosition());

        HashMap<Date, PlayerTemporalData> xpDate = new LinkedHashMap<>();
        AtomicLong lastXp = new AtomicLong(0);
        AtomicInteger oldPosition = new AtomicInteger(0);
        AtomicInteger oldLevel = new AtomicInteger(0);
        playerXps.forEach(player1 -> {
            PlayerTemporalData temporalData = PlayerTemporalDataMapper
                    .map(player1, lastXp.get(), oldPosition.get(), oldLevel.get());

            lastXp.set(player1.getXp());
            oldPosition.set(player1.getPosition());
            oldLevel.set(player1.getLevel());
            xpDate.put(player1.getCreationDate(), temporalData);
        });
        playerGrowthDto.setXpDate(xpDate);

        return playerGrowthDto;
    }

}
