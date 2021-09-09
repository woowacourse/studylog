package wooteco.prolog.studylog.application.dto.report.abilityGraph;

public class AbilityResponse {

    private Long id;
    private String name;
    private Long weight;
    private Double percentage;

    private AbilityResponse() {
    }

    public AbilityResponse(Long id, String name, Long weight, Double percentage) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.percentage = percentage;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getWeight() {
        return weight;
    }

    public Double getPercentage() {
        return percentage;
    }
}
