package my.reservetable.waiting.service;

import lombok.RequiredArgsConstructor;
import my.reservetable.exception.NotFoundEntityException;
import my.reservetable.waiting.domain.Waiting;
import my.reservetable.waiting.dto.request.WaitingStatusUpdateRequest;
import my.reservetable.waiting.dto.response.WaitingResponse;
import my.reservetable.waiting.repository.WaitingRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaitingService {

    private final WaitingRepository waitingRepository;

    public WaitingResponse changeWaitingStatus(WaitingStatusUpdateRequest request) {
        Waiting waiting = waitingRepository.findById(request.getWaitingId())
                .orElseThrow(() -> new NotFoundEntityException("웨이팅 정보를 찾을 수 없습니다."));
        waiting.changeWaitingStatus(request.getWaitingStatus());
        return WaitingResponse.toDto(waiting);
    }
}
