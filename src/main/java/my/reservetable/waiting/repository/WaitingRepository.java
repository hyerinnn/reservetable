package my.reservetable.waiting.repository;

import my.reservetable.waiting.domain.Waiting;
import my.reservetable.waiting.domain.WaitingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WaitingRepository extends JpaRepository<Waiting, Long> {

    //List<Waiting> findAllByRegisteredDateTimeBefore(LocalDateTime registeredDateTime);


    // 특정시간(웨이팅 생성시간)보다 먼저 생성된 웨이팅 수 조회
    @Query("select count (*) from Waiting a where a.registeredDateTime < ?1 ")
    Long getRegisteredDateTimeBefore(LocalDateTime registeredDateTime);

    @Query("select count (*) from Waiting a where a.registeredDateTime < ?1 and a.waitingStatus= ?2")
    Long getRegisteredDateTimeBeforeAndWaitingReady(LocalDateTime registeredDateTime, WaitingStatus waitingStatus);


    // 오늘 생성된 웨이팅이면서 상태가 READY인 데이터 중, 특정 웨이팅생성 시간보다 먼저 생성된 웨이팅 목록 조회
    @Query("SELECT w FROM Waiting w WHERE w.waitingStatus = :status AND w.registeredDateTime < :beforeDateTime " +
            "AND DATE(w.registeredDateTime) = CURRENT_DATE ORDER BY w.registeredDateTime DESC")
    List<Waiting> findAllReadyBeforeDateTime(
            @Param("status") WaitingStatus status,
            @Param("beforeDateTime") LocalDateTime beforeDateTime);


    @Query("SELECT COUNT(w) FROM Waiting w WHERE w.waitingStatus = :status AND DATE(w.registeredDateTime) = :today")
    Long countByStatusAndToday(@Param("status") WaitingStatus status, @Param("today") LocalDate today);

}
