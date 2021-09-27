package wooteco.prolog.studylog.application.dto.report.response.studylogs;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.List;
import wooteco.prolog.studylog.domain.report.studylog.ReportedStudylog;

public class StudylogResponse {

    private Long id;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String title;
    private List<StudylogAbilityResponse> abilities;

    private StudylogResponse() {
    }

    public StudylogResponse(Long id,
                            LocalDateTime createAt,
                            LocalDateTime updateAt,
                            String title,
                            List<StudylogAbilityResponse> abilities) {
        this.id = id;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.title = title;
        this.abilities = abilities;
    }

    public static StudylogResponse from(ReportedStudylog reportedStudylog) {
        List<StudylogAbilityResponse> abilities = reportedStudylog.getAbilities().stream()
            .map(StudylogAbilityResponse::from)
            .collect(toList());

        return new StudylogResponse(
            reportedStudylog.getId(),
            reportedStudylog.getCreatedAt(),
            reportedStudylog.getUpdatedAt(),
            reportedStudylog.getTitle(),
            abilities
        );
    }

    private static List<StudylogAbilityResponse> extractAbiliites(ReportedStudylog reportedStudylog) {
        return reportedStudylog.getAbilities().stream()
            .map(StudylogAbilityResponse::from)
            .collect(toList());
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public String getTitle() {
        return title;
    }

    public List<StudylogAbilityResponse> getAbilities() {
        return abilities;
    }
}
