package dofus.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RestController;

import dofus.service.scrap.ScrapClassement;
import lombok.extern.slf4j.Slf4j;

/**
 * .
 */
@Slf4j
@RestController
public class TestController {


    @Inject
    private ScrapClassement scrapClassement;


//    @RequestMapping(value = "/test/dofus", method = RequestMethod.GET)
//    public List<PlayerXp> dofus(
//            @RequestParam(required = false, defaultValue = "0") final String filePath) throws Exception {
//        log.info("split image ={}", filePath);
//        return scrapClassement.scanLastScanOutPage();
//    }


}
