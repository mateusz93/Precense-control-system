package neo.dmcs.view.precense;

import lombok.Data;

/**
 * @Author Mateusz Wieczorek, 18.05.16.
 */
@Data
public class CheckPresenceView {

    private String ID;
    private String firstName;
    private String lastName;
    private String precenseStatus;
}
