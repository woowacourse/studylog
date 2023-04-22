package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.studylog.application.dto.TagRequest;
import wooteco.prolog.studylog.application.dto.TagResponse;
import wooteco.prolog.studylog.domain.StudylogTag;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.Tags;
import wooteco.prolog.studylog.domain.repository.TagRepository;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    private static final TagRequest JAVA_TAG_REQUEST = new TagRequest("자바");
    private static final TagRequest COLLECTION_TAG_REQUEST = new TagRequest("컬렉션");

    @Mock
    private StudylogTagService studylogTagService;
    @Mock
    private TagRepository tagRepository;
    @InjectMocks
    private TagService tagService;

    @Test
    void Tags를_찾거나_없으면_생성하는_기능_테스트() {
        final List<TagRequest> tagRequests = Arrays.asList(JAVA_TAG_REQUEST, COLLECTION_TAG_REQUEST);

        when(tagRepository.findByNameValueIn(anyList()))
            .then(this::getFirstElementTags);

        final Tags tags = tagService.findOrCreate(tagRequests);

        assertAll(
            () -> verify(tagRepository)
                .saveAll(tags.getList().subList(1, 2)),
            () -> assertThat(tags.getList())
                .extracting(Tag::getName)
                .contains(JAVA_TAG_REQUEST.getName(), COLLECTION_TAG_REQUEST.getName())
        );
    }

    private List<Tag> getFirstElementTags(InvocationOnMock invocation) {
        final List<Tag> existTags = new ArrayList<>();
        final List<String> argument = invocation.getArgument(0);
        final Tag existTag = new Tag(argument.get(0));
        existTags.add(existTag);
        return existTags;
    }

    @Test
    void 스터디로그에_쓰인_모든_태그들을_찾는_기능_테스트() {
        final StudylogTag studylog1 = new StudylogTag(null, new Tag(1L, "스프링"));
        final StudylogTag studylog2 = new StudylogTag(null, new Tag(2L, "자바"));
        final StudylogTag studylog3 = new StudylogTag(null, new Tag(1L, "스프링"));

        doReturn(Arrays.asList(studylog1, studylog2, studylog3))
            .when(studylogTagService).findAll();

        final List<TagResponse> foundTags = tagService.findTagsIncludedInStudylogs();

        assertThat(foundTags)
            .extracting(TagResponse::getName)
            .contains("자바", "스프링");
    }
}
