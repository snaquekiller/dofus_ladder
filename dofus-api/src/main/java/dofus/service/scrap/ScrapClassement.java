package dofus.service.scrap;

import data.entity.PlayerSucces;
import data.entity.PlayerXp;
import data.service.PlayerSuccesPersistenceService;
import data.service.PlayerXpPersistenceService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * .
 */
@Service
@Slf4j
public class ScrapClassement extends ScrapService {


    @Inject
    private PlayerXpPersistenceService playerXpPersistenceService;

    @Inject
    private PlayerSuccesPersistenceService playerSuccesPersistenceService;


    private static String GLOBAL_LADDER_XP = "https://www.dofus.com/fr/mmorpg/communaute/ladder-temps-reel/general";
    private static String GLOBAL_LADDER_SUCCES = "https://www.dofus.com/fr/mmorpg/communaute/ladder-temps-reel/succes";

    private static final String CLASS_LADDER = "https://www.dofus.com/fr/mmorpg/communaute/ladder-temps-reel/general?breeds=%s&name=#jt_list";
    private static final String CLASS_LADDER_SUCCES = "https://www.dofus.com/fr/mmorpg/communaute/ladder-temps-reel/succes?breeds=%s&name=#jt_list";


    /**
     * Get all manga from the news page
     */
    public List<PlayerXp> scanAll() throws IOException {
        List<PlayerXp> playerXps = scanOneXp(GLOBAL_LADDER_XP);
        for (int i = 0; i < 18; i++) {
            playerXps.addAll(scanOneXp(String.format(CLASS_LADDER, i)));
        }
        List<PlayerSucces> playerSucces = scanOneSucces(GLOBAL_LADDER_SUCCES);
        for (int i = 0; i < 18; i++) {
            playerSucces.addAll(scanOneSucces(String.format(CLASS_LADDER_SUCCES, i)));
        }
        return playerXps;
    }


    /**
     * Get all manga from the news page
     */
    public List<PlayerSucces> scanOneSucces(String url) throws IOException {
        try {
            List<PlayerSucces> playerXps = new ArrayList<>();
            Connection connect = Jsoup.connect(url);
            final Document doc = addInfo(connect).get();
            Element body = doc.getElementsByClass("ak-main-content").get(0).getElementsByTag("tbody").get(0);
            Elements tr = body.getElementsByTag("tr");

            tr.forEach(element -> {
                int rank = Integer
                        .parseInt(element.getElementsByClass("ak-rank").get(0).getElementsByTag("span").text());
                String name = element.getElementsByClass("ak-name").get(0).getElementsByClass("ak-character-name").get(0).text();
                String classe = element.getElementsByTag("td").get(3).text();
                int level = Integer
                        .parseInt(element.getElementsByClass("ak-level").get(0).text());
                String serveur = element.getElementsByTag("td").get(2).text();
                long sucees = Long
                        .parseLong(element.getElementsByTag("td").get(5).text().replace(" ", ""));
                PlayerSucces playerXp = new PlayerSucces();
                playerXp.setClasse(classe);
                playerXp.setName(name);
                playerXp.setNiveau(level);
                playerXp.setServeur(serveur);
                playerXp.setSucces(sucees);
                playerXp.setNumber(rank);

                PlayerSucces save = playerSuccesPersistenceService.save(playerXp);
                playerXps.add(save);
            });
            return playerXps;
        } catch (final Exception e) {
            log.error("can't scrap the GLOBAL_LADDER_XP ={}", GLOBAL_LADDER_XP, e);
            throw e;
        }
    }

    /**
     * Get all manga from the news page
     */
    public List<PlayerXp> scanOneXp(String url) throws IOException {
        try {
            List<PlayerXp> playerXps = new ArrayList<>();
            Connection connect = Jsoup.connect(url);
            final Document doc = addInfo(connect).get();
            Element body = doc.getElementsByClass("ak-main-content").get(0).getElementsByTag("tbody").get(0);
            Elements tr = body.getElementsByTag("tr");

            tr.forEach(element -> {
                int rank = Integer
                        .parseInt(element.getElementsByClass("ak-rank").get(0).getElementsByTag("span").text());
                String name = element.getElementsByClass("ak-name").get(0).getElementsByClass("ak-character-name").get(0).text();
                String classe = element.getElementsByClass("ak-class").get(0).text();
                int level = Integer
                        .parseInt(element.getElementsByClass("ak-level").get(0).text());
                String serveur = element.getElementsByTag("td").get(4).text();
                long total = Long
                        .parseLong(element.getElementsByClass("ak-xp-total").get(0).text().replace(" ", ""));
                PlayerXp playerXp = new PlayerXp();
                playerXp.setClasse(classe);
                playerXp.setName(name);
                playerXp.setNiveau(level);
                playerXp.setServeur(serveur);
                playerXp.setXp(total);
                playerXp.setNumber(rank);

                PlayerXp save = playerXpPersistenceService.save(playerXp);
                playerXps.add(save);
            });
            return playerXps;
        } catch (final Exception e) {
            log.error("can't scrap the GLOBAL_LADDER_XP ={}", GLOBAL_LADDER_XP, e);
            throw e;
        }
    }
}
