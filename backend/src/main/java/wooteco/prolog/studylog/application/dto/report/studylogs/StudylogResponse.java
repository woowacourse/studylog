package wooteco.prolog.studylog.application.dto.report.studylogs;

import java.time.LocalDateTime;
import java.util.List;

public class StudylogResponse {

    private Long id;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String title;
    private List<AbilityResponse> abilities;

    private StudylogResponse() {
    }

    public StudylogResponse(Long id,
                            LocalDateTime createAt,
                            LocalDateTime updateAt,
                            String title,
                            List<AbilityResponse> abilities) {
        this.id = id;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.title = title;
        this.abilities = abilities;
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

    public List<AbilityResponse> getAbilities() {
        return abilities;
    }
}
