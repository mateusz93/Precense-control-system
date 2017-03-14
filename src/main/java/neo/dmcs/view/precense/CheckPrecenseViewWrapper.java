package neo.dmcs.view.precense;

import lombok.Data;

import java.util.List;

/**
 * @Author Mateusz Wieczorek, 19.05.16.
 */
@Data
public class CheckPrecenseViewWrapper {

    private List<CheckPrecenseView> students;
}
