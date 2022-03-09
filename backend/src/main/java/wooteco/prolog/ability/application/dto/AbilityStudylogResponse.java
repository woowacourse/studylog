package wooteco.prolog.ability.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.ability.domain.StudylogAbility;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.domain.Studylog;

@AllArgsConstructor
@Getter
public class AbilityStudylogResponse {

    private StudylogResponse studylog;
    private List<AbilityResponse> abilities;

    public static List<AbilityStudylogResponse> listOf(List<StudylogAbility> studylogAbilities) {
        return studylogAbilities.stream()
            .collect(Collectors.groupingBy(it -> it.getStudylog()))
            .entrySet().stream()
            .map(it -> AbilityStudylogResponse.of(it.getKey(), it.getValue()))
            .collect(Collectors.toList());
    }

    public static AbilityStudylogResponse of(Studylog studylog, List<StudylogAbility> studylogAbilities) {
        StudylogResponse studylogResponse = StudylogResponse.of(studylog);
        List<AbilityResponse> abilityResponses = studylogAbilities.stream()
            .map(it -> AbilityResponse.of(it.getAbility()))
            .collect(Collectors.toList());

        return new AbilityStudylogResponse(studylogResponse, abilityResponses);
    }
}