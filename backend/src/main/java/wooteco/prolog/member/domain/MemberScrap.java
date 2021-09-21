package wooteco.prolog.member.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.studylog.domain.Studylog;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberScrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studylog_id", nullable = false)
    private Studylog scrapStudylog;

    public MemberScrap(Member member, Studylog scrapStudylog) {
        this(null, member, scrapStudylog);
    }

    public MemberScrap(Long id, Member member, Studylog scrapStudylog) {
        this.id = id;
        this.member = member;
        this.scrapStudylog = scrapStudylog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberScrap)) {
            return false;
        }
        MemberScrap memberScrap = (MemberScrap) o;
        return Objects.equals(member.getId(), memberScrap.member.getId()) &&
            Objects.equals(scrapStudylog.getId(), memberScrap.getScrapStudylog().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(member.getId(), scrapStudylog.getId());
    }
}