package neo.dmcs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.UserType;
import neo.dmcs.model.Grade;
import neo.dmcs.model.StudentCourse;
import neo.dmcs.model.StudentPresence;
import neo.dmcs.model.User;
import neo.dmcs.repository.GradeRepository;
import neo.dmcs.repository.StudentCourseRepository;
import neo.dmcs.repository.StudentPresenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static neo.dmcs.util.Const.*;

/**
 * @author Mateusz Wieczorek on 06.04.2017.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class StatisticService {

    private final GradeRepository gradeRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final StudentPresenceRepository studentPresenceRepository;

    public ModelAndView prepareView(User user) {
        ModelAndView mvc = new ModelAndView();
        if (UserType.STUDENT.name().equalsIgnoreCase(user.getType().name())) {
            mvc.setViewName("stats/studentStats");
        } else {
            mvc.setViewName("stats/teacherStats");
        }
        return mvc;
    }

    public String calculateGlobalPresenceAverage(User user) {
        List<StudentPresence> studentPresences = studentPresenceRepository.findByStudent(user);
        long absent = getAbsentCountByStatus(studentPresences, ABSENT);
        long present = getAbsentCountByStatus(studentPresences, PRESENT);
        long late = getAbsentCountByStatus(studentPresences, BELATED);
        return getGlobalPresenceAverageStatsAsString(absent, present, late);
    }

    public String calculateFinalGradesAverage(User user) {
        List<Grade> grades = gradeRepository.findByUser(user);
        grades = getFinalGrades(grades);
        long sixGrade = getGradeCountByValue(grades, 6);
        long fiveGrade = getGradeCountByValue(grades, 5);
        long fourGrade = getGradeCountByValue(grades, 4);
        long threeGrade = getGradeCountByValue(grades, 3);
        long twoGrade = getGradeCountByValue(grades, 2);
        long oneGrade = getGradeCountByValue(grades, 1);
        return getFinalGradeAverageStatsAsString(sixGrade, fiveGrade, fourGrade, threeGrade, twoGrade, oneGrade);
    }

    private String getFinalGradeAverageStatsAsString(long sixGrade, long fiveGrade, long fourGrade, long threeGrade, long twoGrade, long oneGrade) {
        return getGradeAverageAsString(sixGrade, fiveGrade, fourGrade, threeGrade, twoGrade, oneGrade);
    }

    public String calculateGlobalGradesAverage(User user) {
        List<Grade> grades = gradeRepository.findByUser(user);
        long sixGrade = getGradeCountByValue(grades, 6);
        long fiveGrade = getGradeCountByValue(grades, 5);
        long fourGrade = getGradeCountByValue(grades, 4);
        long threeGrade = getGradeCountByValue(grades, 3);
        long twoGrade = getGradeCountByValue(grades, 2);
        long oneGrade = getGradeCountByValue(grades, 1);
        return getGradeAverageAsString(sixGrade, fiveGrade, fourGrade, threeGrade, twoGrade, oneGrade);
    }

    private String getGlobalPresenceAverageStatsAsString(long absent, long present, long late) {
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

    private String getGradeAverageAsString(long sixGrade, long fiveGrade, long fourGrade, long threeGrade, long twoGrade, long oneGrade) {
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

    private List<Grade> getFinalGrades(List<Grade> grades) {
        return grades
                .stream()
                .filter(Grade::isFinalGrade)
                .collect(Collectors.toList());
    }

    private long getGradeCountByValue(List<Grade> grades, int value) {
        return grades
                .stream()
                .filter(grade -> grade.getValue() == value)
                .count();
    }

    private long getAbsentCountByStatus(List<StudentPresence> studentPresences, String status) {
        return studentPresences
                .stream()
                .filter(s -> s.getStatus().equalsIgnoreCase(status))
                .count();
    }

    public String calculateSubjectGradesAverage(User user) {
        List<StudentCourse> studentCourses = studentCourseRepository.findByStudent(user);
        List<SubjectGradeStat> subjectGradeStats = new ArrayList<>();
        for (StudentCourse studentCourse : studentCourses) {
            SubjectGradeStat subjectGradeStat = new SubjectGradeStat();
            List<Grade> grades = gradeRepository.findByTeacherCourse(studentCourse.getTeacherCourse());
            subjectGradeStat.setName(studentCourse.getTeacherCourse().getSubject().getName());
            subjectGradeStat.setAverage(grades
                    .stream()
                    .mapToInt(Grade::getValue)
                    .average()
                    .getAsDouble());
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

        double getAverage() {
            return average;
        }

        void setAverage(double average) {
            this.average = average;
        }

        String getColor() {
            return color;
        }
    }
}
