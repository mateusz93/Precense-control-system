package neo.dmcs.statistic;

import neo.dmcs.enums.UserType;
import neo.dmcs.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static neo.dmcs.util.UserUtils.getUserFromSession;
import static neo.dmcs.util.UserUtils.isNotLogged;

/**
 * @Author Mateusz Wieczorek on 11/5/16.
 */
@Transactional
@RestController
@RequestMapping("/stats")
public class StatsController {

    private final Logger logger = LoggerFactory.getLogger(StatsController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView save(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        if (UserType.Student.toString().equals(user.getType())) {
            mvc.setViewName("stats/studentStats");
        } else {
            mvc.setViewName("stats/teacherStats");
        }
        return mvc;
    }

    @RequestMapping(value = "/someData", method = RequestMethod.GET, produces = "application/json")
    public String someData(HttpSession httpSession) {
        return "    {\n" +
                "        \"cols\": [\n" +
                "        {\"id\":\"\",\"label\":\"Topping\",\"pattern\":\"\",\"type\":\"string\"},\n" +
                "        {\"id\":\"\",\"label\":\"Slices\",\"pattern\":\"\",\"type\":\"number\"}\n" +
                "      ],\n" +
                "        \"rows\": [\n" +
                "        {\"c\":[{\"v\":\"Mushrooms\",\"f\":null},{\"v\":3,\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Onions\",\"f\":null},{\"v\":1,\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Olives\",\"f\":null},{\"v\":1,\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Zucchini\",\"f\":null},{\"v\":1,\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Pepperoni\",\"f\":null},{\"v\":2,\"f\":null}]}\n" +
                "      ]\n" +
                "    }";
    }

    @RequestMapping(value = "/precenses", method = RequestMethod.GET, produces = "application/json")
    public String precenses(HttpSession httpSession) {
        return "    {\n" +
                "        \"cols\": [\n" +
                "        {\"id\":\"\",\"label\":\"Topping\",\"pattern\":\"\",\"type\":\"string\"},\n" +
                "        {\"id\":\"\",\"label\":\"Slices\",\"pattern\":\"\",\"type\":\"number\"}\n" +
                "      ],\n" +
                "        \"rows\": [\n" +
                "        {\"c\":[{\"v\":\"Dupa\",\"f\":null},{\"v\":5,\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Oczko\",\"f\":null},{\"v\":1,\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Elo\",\"f\":null},{\"v\":1,\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Matka\",\"f\":null},{\"v\":2,\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Ojciec\",\"f\":null},{\"v\":3,\"f\":null}]}\n" +
                "      ]\n" +
                "    }";
    }

}
