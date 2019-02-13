package dofus.modele;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * .
 */
@Data
public class TopPlayerDto {

    Date start;

    Date end;

    List<TopPlayerDto> topPlayers;
}
