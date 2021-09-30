package wooteco.prolog.report.application;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.application.dto.ability.AbilityCreateRequest;
import wooteco.prolog.report.application.dto.ability.AbilityResponse;
import wooteco.prolog.report.application.dto.ability.AbilityUpdateRequest;
import wooteco.prolog.report.domain.ablity.Ability;
import wooteco.prolog.report.domain.ablity.repository.AbilityRepository;
import wooteco.prolog.report.exception.AbilityNotFoundException;

@Service
@Transactional(readOnly = true)
public class AbilityService {

    private final AbilityRepository abilityRepository;

    public AbilityService(AbilityRepository abilityRepository) {
        this.abilityRepository = abilityRepository;
    }

    @Transactional
    public void createAbility(Member member, AbilityCreateRequest request) {
        Ability ability = extractAbility(member, request);

        abilityRepository.save(ability);
    }

    private Ability extractAbility(Member member, AbilityCreateRequest request) {
        String name = request.getName();
        String description = request.getDescription();
        String color = request.getColor();
        Long parentId = request.getParent();

        if (Objects.isNull(parentId)) {
            return Ability.parent(name, description, color, member);
        }

        Ability parent = findAbilityById(parentId);
        return Ability.child(name, description, color, parent, member);
    }

    public List<AbilityResponse> findAbilitiesByMember(Member member) {
        List<Ability> abilities = abilityRepository.findByMember(member);

        return AbilityResponse.of(abilities);
    }

    public List<AbilityResponse> findParentAbilitiesByMember(Member member) {
        List<Ability> parentAbilities = abilityRepository.findByMemberAndParentIsNull(member);

        return AbilityResponse.of(parentAbilities);
    }

    public List<AbilityResponse> findAbilitiesByMemberId(Long memberId) {
        List<Ability> abilities = abilityRepository.findByMemberId(memberId);

        return AbilityResponse.of(abilities);
    }

    @Transactional
    public List<AbilityResponse> updateAbility(Member member, AbilityUpdateRequest request) {
        Ability legacyAbility = findAbilityByIdAndMember(request.getId(), member);
        Ability updateAbility = request.toEntity();

        legacyAbility.update(updateAbility);

        return findAbilitiesByMember(member);
    }

    @Transactional
    public void deleteAbility(Member member, Long abilityId) {
        Ability ability = findAbilityByIdAndMember(abilityId, member);
        ability.validateDeletable();

        ability.deleteRelationshipWithParent();
        abilityRepository.delete(ability);
    }

    private Ability findAbilityById(Long parentId) {
        return abilityRepository.findById(parentId)
            .orElseThrow(AbilityNotFoundException::new);
    }

    private Ability findAbilityByIdAndMember(Long abilityId, Member member) {
        return abilityRepository.findByIdAndMember(abilityId, member)
            .orElseThrow(AbilityNotFoundException::new);
    }
}
