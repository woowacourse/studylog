package wooteco.studylog.log.dao;

import org.springframework.stereotype.Repository;
import wooteco.studylog.log.StudyLog;
import wooteco.studylog.log.domain.Category;

import java.util.List;

@Repository
public class StudyLogDao {
    public List<StudyLog> findAll() {
        return null;
    }

    public StudyLog insert(StudyLog studyLog) {
        // TODO : DB에 로그를 삽입한다.
        return null;
    }

    public StudyLog findById(Long logId) {
        // TODO : DB에서 로그 하나를 찾아온다.
        String sql = "SELECT * FROM WHERE logId = ?";
        return null;
    }

    public List<Category> findAllCategories() {
        // TODO : DB에서 카테고리 목록을 불러온다.
        return null;
    }
}
