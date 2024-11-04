package carrotmoa.carrotmoa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {
    LIKE(1L, "게시물에 좋아요가 눌렸습니다."),
    COMMENT(2L, "내 게시물에 댓글이 달렸습니다."),
    REPLY(3L, "내 댓글에 답글이 달렸습니다."),
    WISHLIST(4L, "내 게시물에 찜을 했습니다.");

    private final Long typeId;
    private final String title;

}