package dofus.service.scrap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import data.entity.PlayerSucces;
import data.entity.PlayerXp;
import data.service.PlayerSuccesPersistenceService;
import data.service.PlayerXpPersistenceService;
import lombok.extern.slf4j.Slf4j;


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


    private static final String GLOBAL_LADDER_XP = "https://www.dofus.com/fr/mmorpg/communaute/ladder-temps-reel/general?page=%s";
    private static final String GLOBAL_LADDER_SUCCES = "https://www.dofus.com/fr/mmorpg/communaute/ladder-temps-reel/succes?page=%s";

    private static final String CLASS_LADDER = "https://www.dofus.com/fr/mmorpg/communaute/ladder-temps-reel/general?breeds=%s&name=#jt_list&page=%s";
    private static final String CLASS_LADDER_SUCCES = "https://www.dofus.com/fr/mmorpg/communaute/ladder-temps-reel/succes?breeds=%s&name=#jt_list&page=%s";


    /**
     * Get all manga from the news page
     */
    public List<PlayerXp> scanAll() throws IOException {
        List<PlayerXp> playerXps = new ArrayList<>();
        for (int j = 1; j < 20; j++) {
            playerXps = scanOneXp(String.format(GLOBAL_LADDER_XP, j));
//            for (int i = 1; i < 18; i++) {
//                playerXps.addAll(scanOneXp(String.format(CLASS_LADDER, i, j)));
//            }

            List<PlayerSucces> playerSucces = scanOneSucces(String.format(GLOBAL_LADDER_SUCCES, j));
//            for (int i = 1; i < 18; i++) {
//                playerSucces.addAll(scanOneSucces(String.format(CLASS_LADDER_SUCCES, i, j)));
//            }
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
                String name = element.getElementsByClass("ak-name").get(0).getElementsByClass("ak-character-name")
                        .get(0).text();
                String classe = element.getElementsByTag("td").get(3).text();
                String text = element.getElementsByClass("ak-level").get(0).text();
                int level = text.contains("Omega") ? Integer
                        .parseInt(text.replaceAll("[^\\d.]", "")) + 200 : Integer
                        .parseInt(text);
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
            log.error("can't scrap the GLOBAL_LADDER_XP ={}", url, e);
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
                String name = element.getElementsByClass("ak-name").get(0).getElementsByClass("ak-character-name")
                        .get(0).text();
                String classe = element.getElementsByClass("ak-class").get(0).text();
                int level = Integer
                        .parseInt(element.getElementsByClass("ak-level").get(0).text());
                String serveur = element.getElementsByTag("td").get(4).text();
                long total = Long
                        .parseLong(element.getElementsByClass("ak-xp-total").get(0).text().replace(" ", ""));
                PlayerXp playerXp = new PlayerXp();
                playerXp.setClasse(classe);
                playerXp.setName(name);
                playerXp.setLevel(level);
                playerXp.setServeur(serveur);
                playerXp.setXp(total);
                playerXp.setPosition(rank);

                PlayerXp save = playerXpPersistenceService.save(playerXp);
                playerXps.add(save);
            });
            return playerXps;
        } catch (final Exception e) {
            log.error("can't scrap the GLOBAL_LADDER_XP ={}", url, e);
            throw e;
        }
    }
}
