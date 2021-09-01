package wooteco.prolog.tag.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.membertag.domain.MemberTag;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberTagResponse {

    @JsonUnwrapped
    private TagResponse tagResponse;
    private int count;

    public static MemberTagResponse of(MemberTag memberTag) {
        return new MemberTagResponse(TagResponse.of(memberTag.getTag()), memberTag.getCount());
    }

    public static List<MemberTagResponse> asListFrom(List<MemberTag> memberTags, int postCount) {
        List<MemberTagResponse> memberTagResponses = new ArrayList<>();
        final TagResponse allTagResponse = new TagResponse(0L, "ALL");
        memberTagResponses.add(new MemberTagResponse(allTagResponse, postCount));
        memberTagResponses.addAll(memberTags.stream()
            .map(MemberTagResponse::of)
            .collect(Collectors.toList()));
        return memberTagResponses;
    }
}
