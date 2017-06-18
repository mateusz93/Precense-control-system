package neo.dmcs.bpm;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Mateusz Wieczorek on 18.06.2017.
 */
@Data
@Builder
public class Message implements Serializable {

    private String value;
    private String type;
}
