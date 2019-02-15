/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dofus;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import dofus.service.scrap.ScrapClassement;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nicolas
 */
@Slf4j
@Service
public class ScheduledDofus {


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Inject
    private ScrapClassement scrapClassement;


    @Scheduled(cron = "${cron.task.dofus}")
    public void newMangaCron() throws IOException {
        log.info("begin task dofus");
        this.scrapClassement.scanAll();
        log.info("end task dofus");
    }

}
