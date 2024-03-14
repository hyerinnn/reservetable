package my.reservetable.waiting.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WaitingStatus {

    READY("입장대기"),
    VISITED("입장완료"),
    CANCEL("웨이팅취소"),
    NOSHOW("노쇼");

    private final String text;
}
