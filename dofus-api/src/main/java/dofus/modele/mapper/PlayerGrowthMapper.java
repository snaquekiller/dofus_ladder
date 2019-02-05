package dofus.modele.mapper;

import data.entity.PlayerXp;
import dofus.modele.PlayerGrowthDto;
import dofus.modele.TemporalData;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class PlayerGrowthMapper {

    public static PlayerGrowthDto map(List<PlayerXp> playerXps) {
        PlayerXp playerXp = playerXps.get(0);
        PlayerGrowthDto playerGrowthDto = new PlayerGrowthDto();
        playerGrowthDto.setClasse(playerXp.getClasse());
        playerGrowthDto.setCreationDate(playerXp.getCreationDate());
        playerGrowthDto.setName(playerXp.getName());
        playerGrowthDto.setNiveau(playerXp.getNiveau());
        playerGrowthDto.setServeur(playerXp.getServeur());
        playerGrowthDto.setNumber(playerXp.getNumber());

        HashMap<Date, TemporalData> xpDate = new LinkedHashMap<>();
        AtomicLong lastXp = new AtomicLong(0);
        playerXps.forEach(player1 -> {
            TemporalData temporalData = new TemporalData();
            temporalData.setClasse(player1.getClasse());
            temporalData.setNiveau(player1.getNiveau());
            long xp = player1.getXp();
            temporalData.setXp(xp);
            temporalData.setNumber(player1.getNumber());
            temporalData.setAugmentation(xp - lastXp.get());
            lastXp.set(xp);
            xpDate.put(player1.getCreationDate(), temporalData);
        });
        playerGrowthDto.setXpDate(xpDate);

        return playerGrowthDto;
    }

}
