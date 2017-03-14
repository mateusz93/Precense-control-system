package neo.dmcs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.repository.GradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Mateusz Wieczorek on 11/6/16.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class GradeService {

    private GradeRepository gradeRepository;

}
