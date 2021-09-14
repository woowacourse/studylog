package wooteco.prolog.studylog.domain.report.abilitygraph;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import wooteco.prolog.studylog.domain.ablity.Ability;
import wooteco.prolog.studylog.domain.report.Report;
import wooteco.prolog.studylog.domain.report.common.Updatable;

@Entity
@AllArgsConstructor
public class ReportedAbility implements Updatable<ReportedAbility> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ability_id", nullable = false)
    private Ability ability;

    @Column(nullable = false)
    private Long weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    protected ReportedAbility() {
    }

    public ReportedAbility(Ability ability, Long weight) {
        this(null, ability, weight, null);
    }

    public ReportedAbility(Ability ability, Long weight, Report report) {
        this(null, ability, weight, report);
    }

    public void appendTo(Report report) {
        this.report = report;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return ability.getName();
    }

    public Long getWeight() {
        return weight;
    }

    public Boolean isParent() {
        return ability.isParent();
    }

    @Override
    public void update(ReportedAbility ability) {
        this.ability = ability.ability;
        this.weight = ability.weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportedAbility)) {
            return false;
        }
        ReportedAbility that = (ReportedAbility) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
