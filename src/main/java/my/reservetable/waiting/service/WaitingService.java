package my.reservetable.waiting.service;

import lombok.RequiredArgsConstructor;
import my.reservetable.exception.NotFoundEntityException;
import my.reservetable.point.domain.PointType;
import my.reservetable.point.service.PointService;
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
    private final PointService pointService;

    @Transactional
    public WaitingResponse changeWaitingStatus(Long waitingId, WaitingStatus status) {

        Waiting waiting = waitingRepository.findById(waitingId)
                .orElseThrow(() -> new NotFoundEntityException("웨이팅 정보를 찾을 수 없습니다."));
        checkWaitingStatusForChange(waiting);
        waiting.changeWaitingStatus(status);
        return WaitingResponse.toDto(waiting);
    }

    @Transactional
    public void postponeWaiting(Long waitingId){
        Waiting waiting = waitingRepository.findById(waitingId)
                .orElseThrow(() -> new NotFoundEntityException("웨이팅 정보를 찾을 수 없습니다."));

        checkWaitingStatusForChange(waiting);
        // 웨이팅 미루기
        // TODO : 웨이팅 로직 수정필요

        // 포인트차감
        pointService.subtractPoint(waiting.getMember().getId() ,PointType.POST_PONE.getPoint());
    }

    private void checkWaitingStatusForChange(Waiting waiting){
        //현재 입장대기 웨이팅에 대해서만 변경가능
        if(!(waiting.getWaitingStatus()).equals(WaitingStatus.READY)){
            throw new IllegalArgumentException("입장대기 상태의 웨이팅만 변경 할 수 있습니다.");
        }
    }
}
