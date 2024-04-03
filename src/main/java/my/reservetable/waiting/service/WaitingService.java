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

    //public WaitingResponse changeWaitingStatus(WaitingStatusUpdateRequest request) {
    @Transactional
    public WaitingResponse changeWaitingStatus(Long waitingId, WaitingStatus status) {
        Waiting waiting = waitingRepository.findById(waitingId)
                .orElseThrow(() -> new NotFoundEntityException("웨이팅 정보를 찾을 수 없습니다."));
        waiting.changeWaitingStatus(status);
        return WaitingResponse.toDto(waiting);
    }
}
