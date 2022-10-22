package wooteco.prolog.roadmap.application.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.roadmap.Keyword;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KeywordResponse {

    private Long keywordId;
    private String name;
    private int order;
    private Long parentKeywordId;
    private String description;
    private List<KeywordResponse> childrenKeywords;

    public KeywordResponse(final Long keywordId, final String name, final int order, final Long parentKeywordId,
                           final String description, final List<KeywordResponse> childrenKeywords) {
        this.keywordId = keywordId;
        this.name = name;
        this.order = order;
        this.parentKeywordId = parentKeywordId;
        this.description = description;
        this.childrenKeywords = childrenKeywords;
    }

    public static KeywordResponse createResponse(final Keyword keyword) {
        if (keyword == null) {
            return null;
        }
        return new KeywordResponse(
            keyword.getId(),
            keyword.getName(),
            keyword.getOrdinal(),
            keyword.getParentIdOrNull(),
            keyword.getDescription(),
            createKeywordChildren(keyword.getChildren()));
    }

    private static List<KeywordResponse> createKeywordChildren(final List<Keyword> children) {
        List<KeywordResponse> keywords = new ArrayList<>();
        for (Keyword keyword : children) {
            keywords.add(createResponse(keyword));
        }
        return keywords;
    }
}
