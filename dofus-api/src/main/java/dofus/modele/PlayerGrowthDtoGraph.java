package dofus.modele;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import lombok.Data;

/**
 * .
 */
@Data
public class PlayerGrowthDtoGraph {

    List<PlayerDto> player;

    HashMap<Date, HashMap<String, Long>> dateGraph;
}
