package dofus;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import dofus.configuration.GetLnConfiguration;

/**
 * @author Nicolas
 */
@Configuration
@Import({GetLnConfiguration.class})
@SpringBootApplication
@EnableScheduling
public class DofusLadder {


    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) throws IOException {
        SpringApplication.run(DofusLadder.class);
    }

}
