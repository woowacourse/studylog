package wooteco.prolog.badge.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.badge.domain.BadgeType;
import wooteco.prolog.studylog.domain.repository.BadgeRepository;

@ExtendWith(MockitoExtension.class)
class ComplimentKingBadgeCreatorTest {

    @Mock
    private BadgeRepository badgeRepository;

    private ComplimentKingBadgeCreator complimentKingBadgeCreator;

    @BeforeEach
    void init() {
        complimentKingBadgeCreator =
            new ComplimentKingBadgeCreator(badgeRepository, Arrays.asList(1L, 2L));
    }

    @DisplayName("create() : 일정 session동안 칭찬 개수가 15개 초과 시 칭찬 뱃지를 만들 수 있다")
    @Test
    void test_create() {
        //given
        final String userName = "urrr";
        final int complimentKingUpperCriteria = 15;

        //when
        when(badgeRepository.countLikesByUsernameDuringSessions(any(), any()))
            .thenReturn(complimentKingUpperCriteria);

        Optional<BadgeType> badgeType = complimentKingBadgeCreator.create(userName);

        //then
        assertThat(badgeType).isPresent();
        assertThat(badgeType.get()).isEqualTo(BadgeType.COMPLIMENT_KING);
    }

    @DisplayName("create() : 일정 session동안 칭찬 개수가 15개 미만일 시 칭찬 뱃지를 만들 수 없다")
    @Test
    void test_create_exception() {
        //given
        final String username = "judy";
        final int complimentKingLowerCriteria = 14;

        //when
        when(badgeRepository.countLikesByUsernameDuringSessions(any(), any()))
            .thenReturn(complimentKingLowerCriteria);

        Optional<BadgeType> badgeType = complimentKingBadgeCreator.create(username);

        //then
        assertThat(badgeType).isEmpty();
    }
}
