package neo.dmcs.model;

import neo.dmcs.dao.SubjectDao;
import neo.dmcs.dao.TeacherCourseDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @Author Mateusz Wieczorek, 16.05.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring-test-config.xml"})
public class DatabaseTest {

    @Autowired
    private TeacherCourseDao teacherCourseDao;
    @Autowired
    private SubjectDao subjectDao;

    @Test
    public void test() {
        Subject subject = subjectDao.findById(1);

        List<TeacherCourse> teacherCourseList = teacherCourseDao.findBySubject(subject);
        assertThat(true, is(true));

    }
}
