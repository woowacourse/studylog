package wooteco.studylog.log.web.controller.dto;

import java.util.Objects;

public class AuthorResponse {
    private Long authorId;
    private String nickName;
    private String image;

    public AuthorResponse(Long authorId, String nickName, String image) {
        this.authorId = authorId;
        this.nickName = nickName;
        this.image = image;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getNickName() {
        return nickName;
    }

    public String getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorResponse authorResponse = (AuthorResponse) o;
        return Objects.equals(authorId, authorResponse.authorId) && Objects.equals(nickName, authorResponse.nickName) && Objects.equals(image, authorResponse.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, nickName, image);
    }
}
