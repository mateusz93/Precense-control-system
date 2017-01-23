package neo.dmcs.statistic;

import neo.dmcs.enums.UserType;
import neo.dmcs.model.Grade;
import neo.dmcs.model.StudentCourse;
import neo.dmcs.model.StudentPrecense;
import neo.dmcs.model.User;
import neo.dmcs.repository.GradeRepository;
import neo.dmcs.repository.StudentCourseRepository;
import neo.dmcs.repository.StudentPrecenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private StudentPrecenseRepository studentPrecenseRepository;

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

    @RequestMapping(value = "/globalPresenceAverage", method = RequestMethod.GET, produces = "application/json")
    public String globalPresenceAverage(HttpSession httpSession) {
        User user = getUserFromSession(httpSession);
        List<StudentPrecense> studentPrecenses = studentPrecenseRepository.findByStudent(user);
        long absent = studentPrecenses.stream().filter(s -> s.getStatus().equalsIgnoreCase("Nieobecny")).count();
        long present = studentPrecenses.stream().filter(s -> s.getStatus().equalsIgnoreCase("Obecny")).count();
        long late = studentPrecenses.stream().filter(s -> s.getStatus().equalsIgnoreCase("Spozniony")).count();

        return "    {\n" +
                "        \"cols\": [\n" +
                "        {\"id\":\"\",\"label\":\"Topping\",\"pattern\":\"\",\"type\":\"string\"},\n" +
                "        {\"id\":\"\",\"label\":\"Slices\",\"pattern\":\"\",\"type\":\"number\"}\n" +
                "      ],\n" +
                "        \"rows\": [\n" +
                "        {\"c\":[{\"v\":\"Nieobecnosci\",\"f\":null},{\"v\":" + absent + ",\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Obecnosci\",\"f\":null},{\"v\":" + present + ",\"f\":null}]},\n" +
                "        {\"c\":[{\"v\":\"Spoznienia\",\"f\":null},{\"v\":" + late + ",\"f\":null}]},\n" +
                "      ]\n" +
                "    }";
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

    @RequestMapping(value = "/subjectGradesAverage", method = RequestMethod.GET, produces = "application/json")
    public String subjectGradesAverage(HttpSession httpSession) {
        User user = getUserFromSession(httpSession);
        List<StudentCourse> studentCourses = studentCourseRepository.findByStudent(user);
        List<SubjectGradeStat> subjectGradeStats = new ArrayList<>();
        for (StudentCourse studentCourse : studentCourses) {
            SubjectGradeStat subjectGradeStat = new SubjectGradeStat();
            List<Grade> grades = gradeRepository.findByTeacherCourse(studentCourse.getTeacherCourse());
            subjectGradeStat.setName(studentCourse.getTeacherCourse().getSubject().getName());
            subjectGradeStat.setAverage(grades.stream().mapToInt(Grade::getValue).average().getAsDouble());
            double roundedValue = new BigDecimal(String.valueOf(subjectGradeStat.getAverage())).setScale(2, RoundingMode.HALF_UP).doubleValue();
            subjectGradeStat.setAverage(roundedValue);
            subjectGradeStats.add(subjectGradeStat);
        }
        String result = "[['Name', 'Average', {role: 'style'}],";

        for(SubjectGradeStat subjectGradeStat : subjectGradeStats) {
            result += "['" + subjectGradeStat.getName() + "', " + subjectGradeStat.getAverage() + ", " +
                    subjectGradeStat.getColor() + "],";
        }
        result = result.substring(0, result.length()-1);
        return result +=  "]";
    }

    private class SubjectGradeStat {

        private String name;
        private double average;
        private final String color = "#76A7FA";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getAverage() {
            return average;
        }

        public void setAverage(double average) {
            this.average = average;
        }

        public String getColor() {
            return color;
        }
    }

}
