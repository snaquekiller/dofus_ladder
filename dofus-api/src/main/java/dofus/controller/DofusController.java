package dofus.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.entity.PlayerXp;
import data.entity.QPlayerXp;
import data.service.PlayerXpPersistenceService;
import dofus.modele.PlayerGrowthDto;
import dofus.modele.PlayerTemporalData;
import dofus.modele.mapper.PlayerGrowthMapper;
import dofus.modele.mapper.PlayerTemporalDataMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * .
 */
@CrossOrigin
@RestController
@Slf4j
public class DofusController {

    @Inject
    private PlayerXpPersistenceService playerXpPersistenceService;

    @PersistenceContext
    private EntityManager mainEntityManager;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:00");


    private static final int INTEVAL_POINT = 200;

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
            @RequestParam(required = false, defaultValue = "desc") final String order
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
        Collections.reverse(content);
        return PlayerGrowthMapper.map(content);
    }

    //    /**
    //     * List reviewers.
    //     *
    //     * @return the reviewers response
    //     */
    //    @RequestMapping(value = "/player/compare", method = RequestMethod.GET)
    //    public List<PlayerGrowthDto> playerCompare(
    //            @RequestParam(required = true) final String name,
    //            @RequestParam(required = false) @Max(1000) final Date start,
    //            @RequestParam(required = false) final Date end,
    //            @RequestParam(required = false, defaultValue = "1") @Min(1) final int page,
    //            @RequestParam(required = false, defaultValue = "5") @Min(1) final int compare,
    //            @RequestParam(required = false, defaultValue = "10000") @Max(10000) final int limit,
    //            @RequestParam(required = false, defaultValue = "name") final String sort,
    //            @RequestParam(required = false, defaultValue = "desc") final String order
    //    ) {
    //        // @formatter:on
    //
    //        BooleanExpression predicate = QPlayerXp.playerXp.name.equalsIgnoreCase(name);
    //        if (start != null && end != null) {
    //            predicate = predicate
    //                    .and(QPlayerXp.playerXp.creationDate.after(start).and(QPlayerXp.playerXp.creationDate.before(end)));
    //        } else {
    //            predicate = predicate
    //                    .and(QPlayerXp.playerXp.creationDate.after(DateTime.now().minusDays(1).toDate())
    //                            .and(QPlayerXp.playerXp.creationDate.before(new Date())));
    //        }
    //
    //        //Get the player and the postion depending of the end date.
    //        PageRequest pageRequest = PageRequest.of(0, 1, Direction.fromString("desc"), "creationDate");
    //        Page<PlayerXp> last = playerXpPersistenceService.findAll(predicate, pageRequest);
    //        if (last.getTotalElements() <= 0) {
    //            return null;
    //        }
    //        PlayerXp currentPlayer = last.getContent().get(0);
    //
    //        //Get the name of the player you want compare
    //        int position = currentPlayer.getPosition();
    //        Iterable<PlayerXp> allPlayer = playerXpPersistenceService.findAll(
    //                QPlayerXp.playerXp.position.between(position - compare, position + compare)
    //                        .and(QPlayerXp.playerXp.creationDate.eq(currentPlayer.getCreationDate())));
    //        List<String> names = Iterables.toStream(allPlayer).map(PlayerXp::getName).collect(Collectors.toList());
    //        names.add(currentPlayer.getName());
    //
    //        predicate = QPlayerXp.playerXp.name.in(names);
    //        if (start != null && end != null) {
    //            predicate = predicate
    //                    .and(QPlayerXp.playerXp.creationDate.after(start).and(QPlayerXp.playerXp.creationDate.before(end)));
    //        } else {
    //            predicate = predicate
    //                    .and(QPlayerXp.playerXp.creationDate.after(DateTime.now().minusDays(5).toDate())
    //                            .and(QPlayerXp.playerXp.creationDate.before(new Date())));
    //        }
    //        pageRequest = PageRequest.of(page - 1, limit * compare, Direction.fromString(order), sort);
    //        Page<PlayerXp> allData = playerXpPersistenceService.findAll(predicate, pageRequest);
    //
    //        HashMap<String, List<PlayerXp>> stringListHashMap = new HashMap<>();
    //        List<PlayerXp> content = new ArrayList<>(allData.getContent());
    //        content.forEach(playerXp1 -> {
    //            List<PlayerXp> playerXps = stringListHashMap.get(playerXp1.getName());
    //            if (playerXps == null) {
    //                List<PlayerXp> newPlayers = new ArrayList<>();
    //                newPlayers.add(playerXp1);
    //                stringListHashMap.put(playerXp1.getName(), newPlayers);
    //            } else {
    //                playerXps.add(playerXp1);
    //            }
    //        });
    //
    //        List<PlayerGrowthDto> playerGrowths = new ArrayList<>();
    //        stringListHashMap.forEach((s, playerXps) -> {
    //            playerXps.sort(Comparator.comparing(PlayerXp::getCreationDate));
    //            Collections.reverse(playerXps);
    //            playerGrowths.add(PlayerGrowthMapper.map(playerXps));
    //        });
    //
    //        return playerGrowths;
    //    }


    @RequestMapping(value = "/player/compare/graph", method = RequestMethod.GET)
    public List<HashMap<String, String>> playerCompareGraph(
            @RequestParam(required = true) final String name,
            @RequestParam(required = false) final Date start,
            @RequestParam(required = false) final Date end,
            @RequestParam(required = false, defaultValue = "1") @Min(1) final int page,
            @RequestParam(required = false, defaultValue = "2") @Min(1) @Max(5) final int compare,
            @RequestParam(required = false, defaultValue = "10000") @Max(10000) final int limit,
            @RequestParam(required = false, defaultValue = "name") final String sort,
            @RequestParam(required = false, defaultValue = "desc") final String order
    ) {
        // @formatter:on

        //Get the player and the postion depending of the end date.
        Date startDate = start != null ? start : DateTime.now().minusDays(20).toDate();
        Date endDate = end != null ? end : new Date();
        BooleanExpression predicate = QPlayerXp.playerXp.name.equalsIgnoreCase(name)
                .and(QPlayerXp.playerXp.creationDate.after(startDate)
                        .and(QPlayerXp.playerXp.creationDate.before(endDate)));

        PageRequest pageRequest = PageRequest.of(0, 1, Direction.fromString("desc"), "creationDate");
        Page<PlayerXp> last = playerXpPersistenceService.findAll(predicate, pageRequest);
        if (last.getTotalElements() <= 0) {
            return null;
        }
        PlayerXp currentPlayer = last.getContent().get(0);

        //Get the name of the player you want compare
        int position = currentPlayer.getPosition();
        predicate =
                QPlayerXp.playerXp.position.between(position - compare, position + compare)
                        .and(QPlayerXp.playerXp.creationDate
                                .between(new DateTime(currentPlayer.getCreationDate()).minusMinutes(4).toDate(),
                                        new DateTime(currentPlayer.getCreationDate()).plusMinutes(4).toDate()));
        final JPAQuery<PlayerXp> query = new JPAQuery<PlayerXp>(mainEntityManager);
        List<String> playerName = query.select(QPlayerXp.playerXp.name).distinct().from(QPlayerXp.playerXp)
                .where(predicate)
                .groupBy(QPlayerXp.playerXp.name).limit(compare * 2 + 1)
                .orderBy(QPlayerXp.playerXp.xp.desc()).fetch();

        log.info("found these names{}", playerName);

        // Search for all player
        predicate = QPlayerXp.playerXp.name.in(playerName);

        // Create the interval
        long hours = (endDate.getTime() - startDate.getTime()) / 1000 / 60 / 60;
        long nbPoint = hours > INTEVAL_POINT ? INTEVAL_POINT : hours;
        int hoursInterval =  Double.valueOf(Math.ceil((double)hours / (double)nbPoint)).intValue();
        Date startDateWith0Min = new DateTime(startDate).withMinuteOfHour(0).toDate();
        BooleanExpression datePredicate = QPlayerXp.playerXp.creationDate.after(startDateWith0Min)
                .and(QPlayerXp.playerXp.creationDate.before(new DateTime(startDateWith0Min).plusMinutes(59).toDate()));
        DateTime dateTime = new DateTime(startDateWith0Min).plusHours(hoursInterval);
        for (int i = 1; i < nbPoint; i++) {
            datePredicate = datePredicate
                    .or(QPlayerXp.playerXp.creationDate.between(new DateTime(dateTime).toDate(),
                            new DateTime(dateTime).plusMinutes(59).toDate()));
            dateTime = dateTime.plusHours(hoursInterval);
        }

        final JPAQuery<PlayerXp> queryData = new JPAQuery<>(mainEntityManager);
        List<PlayerXp> allData = queryData.select(QPlayerXp.playerXp).from(QPlayerXp.playerXp)
                .where(predicate.and(datePredicate))
                .groupBy(QPlayerXp.playerXp.creationDate.dayOfYear(),QPlayerXp.playerXp.creationDate.hour(), QPlayerXp.playerXp.name)
//                .limit(toIntExact(nbPoint) * (compare*2+1))
                .orderBy(QPlayerXp.playerXp.creationDate.desc()).fetch();

        //Create graph data from all player data
        allData.sort(Comparator.comparing(PlayerXp::getCreationDate));
        Collections.reverse(allData);
        HashMap<String, HashMap<String, String>> objectObjectHashMap = new HashMap<>();
        allData.forEach(playerXp1 -> {
            String creationDate = dateFormat.format(playerXp1.getCreationDate());
            HashMap<String, String> playerXps = objectObjectHashMap.get(creationDate);
            if (playerXps == null) {
                HashMap<String, String> newPlayers = new HashMap<>();
                newPlayers.put(playerXp1.getName(), Long.toString(playerXp1.getXp()));
                objectObjectHashMap.put(creationDate, newPlayers);
            } else {
                playerXps.put(playerXp1.getName(), Long.toString(playerXp1.getXp()));
            }
        });

        List<HashMap<String, String>> lastGraphData = new ArrayList<>();
        objectObjectHashMap.forEach((date, stringLongHashMap) -> {
            stringLongHashMap.put("name", date);
            lastGraphData.add(stringLongHashMap);
        });

        lastGraphData.sort((o1, o2) -> {
            try {
                return dateFormat.parse(o1.get("name")).compareTo(dateFormat.parse(o2.get("name")));
            } catch (ParseException e) {
                log.error("error parsing date", e);
                return -1;
            }
        });
        return lastGraphData;
    }


    /**
     * List reviewers.
     *
     * @return the reviewers response
     */
    @RequestMapping(value = "/top100", method = RequestMethod.GET)
    public List<PlayerTemporalData> top(@RequestParam(required = false) final Date start,
            @RequestParam(required = false) final Date end) {
        // @formatter:on

        DateTime endDate = new DateTime();
        if (end != null) {
            endDate = new DateTime(end);
        }
        BooleanExpression predicate =
                QPlayerXp.playerXp.creationDate.after(endDate.minusMinutes(4).toDate())
                        .and(QPlayerXp.playerXp.creationDate.before(endDate.toDate()));
        log.info("Found these PLayer for the top100 = {}", predicate.toString());
        final JPAQuery<PlayerXp> query = new JPAQuery<PlayerXp>(mainEntityManager);
        List<PlayerXp> top100 = query.select(QPlayerXp.playerXp).from(QPlayerXp.playerXp).where(predicate)
                .groupBy(QPlayerXp.playerXp.name).limit(100)
                .orderBy(QPlayerXp.playerXp.xp.desc(), QPlayerXp.playerXp.creationDate.desc()).fetch();

        log.info("Found these PLayer for the top100 = {}", top100);

        DateTime startDate = new DateTime().minusDays(2);
        if (start != null) {
            startDate = new DateTime(start);
        }
        BooleanExpression oldDate =
                QPlayerXp.playerXp.creationDate.after(startDate.minusMinutes(30).toDate())
                        .and(QPlayerXp.playerXp.creationDate.before(startDate.toDate()))
                        .and(QPlayerXp.playerXp.name.in(top100.stream().map(PlayerXp::getName).collect(
                                Collectors.toList())));

        final JPAQuery<PlayerXp> queryOld = new JPAQuery<>(mainEntityManager);
        List<PlayerXp> top100Old = queryOld.select(QPlayerXp.playerXp).from(QPlayerXp.playerXp).where(oldDate)
                .groupBy(QPlayerXp.playerXp.name).limit(100)
                .orderBy(QPlayerXp.playerXp.xp.desc(), QPlayerXp.playerXp.creationDate.desc()).fetch();
        List<PlayerTemporalData> temporalDatas = new LinkedList<>();
        top100.forEach(playerXp -> {

            Optional<PlayerXp> first = top100Old.stream()
                    .filter(playerXp1 -> playerXp.getName().equals(playerXp1.getName())).findFirst();
            if (first.isPresent()) {
                PlayerXp playerXp1 = first.get();
                log.info("Found the player for the top100 = {} and his old= {}", playerXp, playerXp1);
                temporalDatas.add(PlayerTemporalDataMapper
                        .map(playerXp, playerXp1.getXp(), playerXp1.getPosition(), playerXp1.getLevel()));
            }
        });
        return temporalDatas;
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
