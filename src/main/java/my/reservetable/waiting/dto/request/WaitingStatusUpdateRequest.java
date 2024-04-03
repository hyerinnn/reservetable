package my.reservetable.waiting.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.waiting.domain.WaitingStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WaitingStatusUpdateRequest {

    @NotBlank(message = "waitingId는 필수입니다.")
    private Long waitingId;

    @NotBlank(message = "waitingStatus는 필수입니다.")
    private WaitingStatus waitingStatus;

    @Builder
    private WaitingStatusUpdateRequest(Long waitingId, WaitingStatus waitingStatus) {
        this.waitingId = waitingId;
        this.waitingStatus = waitingStatus;
    }
}
