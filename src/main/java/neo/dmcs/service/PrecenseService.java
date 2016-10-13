package neo.dmcs.service;

import neo.dmcs.repository.CustomRepository;
import neo.dmcs.model.User;
import neo.dmcs.view.precense.StudentPrecensesView;
import neo.dmcs.view.precense.TeacherPrecensesView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Mateusz Wieczorek, 28.04.16.
 */
@Service
public class PrecenseService {

    private static final Logger logger = LoggerFactory.getLogger(PrecenseService.class);

    @Autowired
    private CustomRepository customRepository;

    public List<StudentPrecensesView> getStudentPrecenses(User user) {
        List<Object[]> objects = customRepository.findStudentPrecensesByUserId(user.getId());
        return getStudentCastedResult(objects);
    }

    public List<TeacherPrecensesView> getTeacherPrecenses(User user) {
        List<Object[]> objects = customRepository.findTeacherPrecensesByUserId(user.getId());
        return getTeacherCastedResult(objects);
    }

    private List<TeacherPrecensesView> getTeacherCastedResult(List<Object[]> objects) {
        List<TeacherPrecensesView> resultList = new ArrayList<TeacherPrecensesView>();
        for (Object[] object : objects) {
            TeacherPrecensesView tpv = new TeacherPrecensesView();
            tpv.setId((Integer) object[0]);
            tpv.setSubjectName(String.valueOf(object[1]));
            tpv.setDepartmentName(String.valueOf(object[2]));
            tpv.setType(String.valueOf(object[3]));
            tpv.setQuantity((Integer) object[4]);
            resultList.add(tpv);
        }
        return resultList;
    }

    private List<StudentPrecensesView> getStudentCastedResult(List<Object[]> objects) {
        List<StudentPrecensesView> resultList = new ArrayList<StudentPrecensesView>();
        for (Object[] object : objects) {
            StudentPrecensesView spv = new StudentPrecensesView();
            spv.setSubjectName(String.valueOf(object[0]));
            spv.setDepartmentName(String.valueOf(object[1]));
            spv.setType(String.valueOf(object[2]));
            spv.setCourseId((Integer) object[3]);
            spv.setQuantity((Integer) object[4]);
            spv.setTeacherName(String.valueOf(object[5]));
            resultList.add(spv);
        }
        return resultList;
    }
}
