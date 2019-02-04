package dofus.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.querydsl.core.types.dsl.BooleanExpression;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dofus.modele.PlayerGrowthDto;
import dofus.modele.TemporalData;
import getln.data.entity.Player;
import getln.data.entity.QPlayer;
import getln.data.service.PlayerPersistenceService;

/**
 * .
 */
@CrossOrigin
@RestController
public class DofusController {

    @Inject
    private PlayerPersistenceService playerPersistenceService;


    /**
     * List reviewers.
     *
     * @return the reviewers response
     */
    @RequestMapping(value = "/player", method = RequestMethod.GET)
    public PlayerGrowthDto player(
            @RequestParam(required = true) final String name,
            @RequestParam(required = false) @Max(1000) final Date start,
            @RequestParam(required = false) final Date end,
            @RequestParam(required = false, defaultValue = "1") @Min(1) final int page,
            @RequestParam(required = false, defaultValue = "20") @Max(1000) final int limit,
            @RequestParam(required = false, defaultValue = "name") final String sort,
            @RequestParam(required = false, defaultValue = "asc") final String order
    ) {
        // @formatter:on

        BooleanExpression predicate = QPlayer.player.name.equalsIgnoreCase(name);
        if (start != null && end != null) {
            predicate = predicate
                    .and(QPlayer.player.creationDate.after(start).and(QPlayer.player.creationDate.before(end)));
        }
        PageRequest pageRequest = PageRequest.of(page - 1, limit, Direction.fromString(order), sort);
        Page<Player> all = playerPersistenceService.findAll(predicate, pageRequest);

        List<Player> content = new ArrayList<>(all.getContent());
        content.sort(Comparator.comparing(Player::getCreationDate));

        Player player = content.get(0);

        PlayerGrowthDto playerGrowthDto = new PlayerGrowthDto();
        playerGrowthDto.setClasse(player.getClasse());
        playerGrowthDto.setCreationDate(player.getCreationDate());
        playerGrowthDto.setName(player.getName());
        playerGrowthDto.setNiveau(player.getNiveau());
        playerGrowthDto.setServeur(player.getServeur());
        playerGrowthDto.setNumber(player.getNumber());

        HashMap<Date, TemporalData> xpDate = new LinkedHashMap<>();
        AtomicLong lastXp = new AtomicLong(0);
        content.forEach(player1 -> {
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

    /**
     * List reviewers.
     *
     * @return the reviewers response
     */
    @RequestMapping(value = "/chapter", method = RequestMethod.GET)
    public String listRevdsqdiewers() {
        // @formatter:on

        return "pascoucocu";
    }

}
