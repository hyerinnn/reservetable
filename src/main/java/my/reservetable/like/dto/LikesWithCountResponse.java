package my.reservetable.like.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikesWithCountResponse {

    private int count;
    private List<LikeResponse> likes;

    @Builder
    private LikesWithCountResponse(List<LikeResponse> likes, int count) {
        this.count = count;
        this.likes = likes;
    }

}
