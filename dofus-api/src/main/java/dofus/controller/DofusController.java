package dofus.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import data.entity.PlayerXp;
import data.entity.QPlayerXp;
import data.service.PlayerXpPersistenceService;
import dofus.modele.PlayerGrowthDto;
import dofus.modele.mapper.PlayerGrowthMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * .
 */
@CrossOrigin
@RestController
public class DofusController {

    @Inject
    private PlayerXpPersistenceService playerXpPersistenceService;


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

        BooleanExpression predicate = QPlayerXp.playerXp.name.equalsIgnoreCase(name);
        if (start != null && end != null) {
            predicate = predicate
                    .and(QPlayerXp.playerXp.creationDate.after(start).and(QPlayerXp.playerXp.creationDate.before(end)));
        }
        PageRequest pageRequest = PageRequest.of(page - 1, limit, Direction.fromString(order), sort);
        Page<PlayerXp> all = playerXpPersistenceService.findAll(predicate, pageRequest);

        List<PlayerXp> content = new ArrayList<>(all.getContent());
        content.sort(Comparator.comparing(PlayerXp::getCreationDate));

        return PlayerGrowthMapper.map(content);
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
