package my.reservetable.waiting.service;

import lombok.RequiredArgsConstructor;
import my.reservetable.waiting.domain.WaitingStatus;
import my.reservetable.waiting.dto.response.WaitingOwnerResponse;
import my.reservetable.waiting.repository.WaitingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WaitingByOwnerService {

    private final WaitingRepository waitingRepository;

    public int getCountNowWaitingsByShopId(Long shopId) {
        return waitingRepository.getCountNowWaitingsByShopId(shopId, WaitingStatus.READY);
    }

    public List<WaitingOwnerResponse> getNowWaitingsByShopId(Long shopId) {
        return waitingRepository.getNowWaitingsByShopId(shopId, WaitingStatus.READY)
                .stream()
                .map(WaitingOwnerResponse::toDto)
                .collect(Collectors.toList());
    }

    // 가게별 전체 웨이팅 목록 조회 (TODO : 검색 필터링 필요(queryDSL 적용시 개발예정))
    public List<WaitingOwnerResponse> getWaitingByShopId(Long shopId) {
        return waitingRepository.getListAllWaitingByShopId(shopId)
                .stream()
                .map(WaitingOwnerResponse::toDto)
                .collect(Collectors.toList());
    }

}
