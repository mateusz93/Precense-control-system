package neo.dmcs.statistic;

import neo.dmcs.enums.UserType;
import neo.dmcs.model.Grade;
import neo.dmcs.model.User;
import neo.dmcs.repository.GradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private GradeRepository gradeRepository;

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

    @RequestMapping(value = "/finalGradesAverage", method = RequestMethod.GET, produces = "application/json")
    public String finalGradesAverage(HttpSession httpSession) {
        User user = getUserFromSession(httpSession);
        List<Grade> grades = gradeRepository.findByUser(user);
        grades = grades.stream().filter(grade -> grade.isFinalGrade()).collect(Collectors.toList());
        long sixGrade = grades.stream().filter(grade -> grade.getValue() == 6).count();
        long fiveGrade = grades.stream().filter(grade -> grade.getValue() == 5).count();
        long fourGrade = grades.stream().filter(grade -> grade.getValue() == 4).count();
        long threeGrade = grades.stream().filter(grade -> grade.getValue() == 3).count();
        long twoGrade = grades.stream().filter(grade -> grade.getValue() == 2).count();
        long oneGrade = grades.stream().filter(grade -> grade.getValue() == 1).count();

        return "    {\n" +
                "        \"cols\": [\n" +
                "        {\"id\":\"\",\"label\":\"Topping\",\"pattern\":\"\",\"type\":\"string\"},\n" +
                "        {\"id\":\"\",\"label\":\"Slices\",\"pattern\":\"\",\"type\":\"number\"}\n" +
                "      ],\n" +
                "        \"rows\": [\n" +
                "        {\"c\":[{\"v\":\"Liczba szostek\",\"f\":null},{\"v\":" + sixGrade + ",\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Liczba piatek\",\"f\":null},{\"v\":" + fiveGrade + ",\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Liczba czworek\",\"f\":null},{\"v\":" + fourGrade + ",\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Liczba trojek\",\"f\":null},{\"v\":" + threeGrade + ",\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Liczba dwojek\",\"f\":null},{\"v\":" + twoGrade + ",\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Liczba jedynek\",\"f\":null},{\"v\":" + oneGrade + ",\"f\":null}]}\n" +
                "      ]\n" +
                "    }";
    }

    @RequestMapping(value = "/globalGradesAverage", method = RequestMethod.GET, produces = "application/json")
    public String globalGradesAverage(HttpSession httpSession) {
        User user = getUserFromSession(httpSession);
        List<Grade> grades = gradeRepository.findByUser(user);
        long sixGrade = grades.stream().filter(grade -> grade.getValue() == 6).count();
        long fiveGrade = grades.stream().filter(grade -> grade.getValue() == 5).count();
        long fourGrade = grades.stream().filter(grade -> grade.getValue() == 4).count();
        long threeGrade = grades.stream().filter(grade -> grade.getValue() == 3).count();
        long twoGrade = grades.stream().filter(grade -> grade.getValue() == 2).count();
        long oneGrade = grades.stream().filter(grade -> grade.getValue() == 1).count();

        return "    {\n" +
                "        \"cols\": [\n" +
                "        {\"id\":\"\",\"label\":\"Topping\",\"pattern\":\"\",\"type\":\"string\"},\n" +
                "        {\"id\":\"\",\"label\":\"Slices\",\"pattern\":\"\",\"type\":\"number\"}\n" +
                "      ],\n" +
                "        \"rows\": [\n" +
                "        {\"c\":[{\"v\":\"Liczba szostek\",\"f\":null},{\"v\":" + sixGrade + ",\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Liczba piatek\",\"f\":null},{\"v\":" + fiveGrade + ",\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Liczba czworek\",\"f\":null},{\"v\":" + fourGrade + ",\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Liczba trojek\",\"f\":null},{\"v\":" + threeGrade + ",\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Liczba dwojek\",\"f\":null},{\"v\":" + twoGrade + ",\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Liczba jedynek\",\"f\":null},{\"v\":" + oneGrade + ",\"f\":null}]}\n" +
                "      ]\n" +
                "    }";
    }

}
