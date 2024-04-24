package my.reservetable.waiting.service;

import lombok.RequiredArgsConstructor;
import my.reservetable.exception.NotFoundEntityException;
import my.reservetable.waiting.domain.Waiting;
import my.reservetable.waiting.domain.WaitingStatus;
import my.reservetable.waiting.dto.response.WaitingResponse;
import my.reservetable.waiting.repository.WaitingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WaitingService {

    private final WaitingRepository waitingRepository;

    @Transactional
    public WaitingResponse changeWaitingStatus(Long waitingId, WaitingStatus status) {
        //TODO : 지난 웨이팅, 입장완료 등의 상태는 변경하면 안되므로, 현재 입장순서 웨이팅에 대해서만 특정 변경할 수 있도록 제한을 걸어야함

        Waiting waiting = waitingRepository.findById(waitingId)
                .orElseThrow(() -> new NotFoundEntityException("웨이팅 정보를 찾을 수 없습니다."));
        waiting.changeWaitingStatus(status);
        return WaitingResponse.toDto(waiting);
    }

    @Transactional
    public void 미루기(Long waitingId){
        Waiting waiting = waitingRepository.findById(waitingId)
                .orElseThrow(() -> new NotFoundEntityException("웨이팅 정보를 찾을 수 없습니다."));

        // TODO : 웨이팅 미루기 구현
        // 현재 진행중인 웨이팅인지 확인
        // 웨이팅 미루기
        // 포인트 차감
    }
}
