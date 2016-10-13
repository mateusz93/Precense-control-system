package neo.dmcs.model;

import neo.dmcs.repository.SubjectRepository;
import neo.dmcs.repository.TeacherCourseRepository;
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
    private TeacherCourseRepository teacherCourseRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    public void test() {
        Subject subject = subjectRepository.findOne(1);

        List<TeacherCourse> teacherCourseList = teacherCourseRepository.findBySubject(subject);
        assertThat(true, is(true));

    }
}
