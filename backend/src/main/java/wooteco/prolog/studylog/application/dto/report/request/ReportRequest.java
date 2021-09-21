package wooteco.prolog.studylog.application.dto.report.request;

import java.util.List;
import wooteco.prolog.studylog.application.dto.report.request.abilitigraph.GraphRequest;
import wooteco.prolog.studylog.application.dto.report.request.studylog.StudylogRequest;

public class ReportRequest {
    private String title;
    private String description;
    private GraphRequest abilityGraph;
    private List<StudylogRequest>studylogs;
    private Boolean represent;

    private ReportRequest() {
    }

    public ReportRequest(String title,
                         String description,
                         GraphRequest abilityGraph,
                         List<StudylogRequest> studylogs,
                         Boolean represent) {
        this.title = title;
        this.description = description;
        this.abilityGraph = abilityGraph;
        this.studylogs = studylogs;
        this.represent = represent;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public GraphRequest getAbilityGraph() {
        return abilityGraph;
    }

    public List<StudylogRequest> getStudylogs() {
        return studylogs;
    }

    public Boolean isRepresent() {
        return represent;
    }
}
