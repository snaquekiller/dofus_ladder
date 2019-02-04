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

import getln.data.entity.Player;
import getln.data.service.PlayerPersistenceService;
import lombok.extern.slf4j.Slf4j;

/**
 * .
 */
@Service
@Slf4j
public class ScrapClassement extends ScrapService {


    @Inject
    private PlayerPersistenceService playerPersistenceService;


    String url = "https://www.dofus.com/fr/mmorpg/communaute/ladder-temps-reel/general";


    /**
     * Get all manga from the news page
     */
    public List<Player> scanLastScanOutPage()
            throws IOException {
        try {
            List<Player> players = new ArrayList<>();
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
                        .parseLong(element.getElementsByClass("ak-xp-total").get(0).text().replace(" ",""));
                Player player = new Player();
                player.setClasse(classe);
                player.setName(name);
                player.setNiveau(level);
                player.setServeur(serveur);
                player.setXp(total);
                player.setNumber(rank);

                Player save = playerPersistenceService.save(player);
                players.add(save);
            });
            return players;
        } catch (final Exception e) {
            log.error("can't scrap the url ={}", url, e);
            throw e;
        }
    }
}
