package wooteco.prolog.studylog.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wooteco.prolog.common.BaseEntity;
import wooteco.prolog.studylog.application.dto.StudylogDocumentResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.StudylogDocumentRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogSearchCondition;

@Profile({"local", "test"})
@Service
public class FakeStudylogDocumentService extends AbstractStudylogDocumentService {

    public FakeStudylogDocumentService(
        StudylogDocumentRepository studylogDocumentRepository,
        StudylogRepository studylogRepository) {
        super(studylogDocumentRepository, studylogRepository);
    }

    @Override
    public StudylogDocumentResponse findBySearchKeyword(String keyword, List<Long> tags, List<Long> missions,
                                                        List<Long> levels, List<String> usernames,
                                                        LocalDate start, LocalDate end, Pageable pageable) {
        final Page<Studylog> studylogs = studylogRepository.search(
            makeSearchCondition(keyword.toLowerCase(), tags, missions, levels, usernames, start, end), pageable);

        final List<Long> studylogIds = studylogs.stream()
            .map(BaseEntity::getId)
            .collect(Collectors.toList());

        return StudylogDocumentResponse.of(studylogIds,
                                            studylogs.getTotalElements(),
                                            studylogs.getTotalPages(),
                                            studylogs.getNumber());
    }

    private StudylogSearchCondition makeSearchCondition(String keyword, List<Long> tags, List<Long> missions,
                                                       List<Long> levels, List<String> usernames,
                                                       LocalDate start, LocalDate end
    ) {
        List<String> keywords = new ArrayList<>();
        if (Objects.nonNull(keyword)) {
            keywords = preprocess(keyword);
        }

        return StudylogSearchCondition.builder()
            .keywords(keywords)
            .levels(levels)
            .missions(missions)
            .tags(tags)
            .usernames(usernames)
            .startDate(start)
            .endDate(end)
            .build();
    }
}
