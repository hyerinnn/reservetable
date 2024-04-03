package my.reservetable.waiting.service;

import lombok.RequiredArgsConstructor;
import my.reservetable.waiting.domain.WaitingStatus;
import my.reservetable.waiting.dto.response.WaitingResponse;
import my.reservetable.waiting.repository.WaitingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WaitingByOwnerService {

    private final WaitingRepository waitingRepository;

    // 현재 남은 웨이팅 수 조회
    public int getCountNowWaitingsByShopId(Long shopId) {
        return waitingRepository.getCountNowWaitingsByShopId(shopId, WaitingStatus.READY);
    }

    // 현재 남은 웨이팅 목록 조회
    public List<WaitingResponse> getNowWaitingsByShopId(Long shopId) {
        return waitingRepository.getNowWaitingsByShopId(shopId, WaitingStatus.READY)
                .stream()
                .map(WaitingResponse::toDto)
                .collect(Collectors.toList());
    }

    // 가게별 전체 웨이팅 목록 조회 (TODO : 검색 필터링 필요)
    public List<WaitingResponse> getWaitingByShopId(Long shopId) {
        return waitingRepository.getListAllWaitingByShopId(shopId)
                .stream()
                .map(WaitingResponse::toDto)
                .collect(Collectors.toList());
    }

}
