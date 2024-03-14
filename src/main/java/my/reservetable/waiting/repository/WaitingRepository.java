package my.reservetable.waiting.repository;

import my.reservetable.waiting.domain.Waiting;
import my.reservetable.waiting.domain.WaitingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface WaitingRepository extends JpaRepository<Waiting, Long> {

    //List<Waiting> findAllByRegisteredDateTimeBefore(LocalDateTime registeredDateTime);

    @Query("select count (*) from Waiting a where a.registeredDateTime < ?1 ")
    Long getRegisteredDateTimeBefore(LocalDateTime registeredDateTime);

    @Query("select count (*) from Waiting a where a.registeredDateTime < ?1 and a.waitingStatus= ?2")
    Long getRegisteredDateTimeBeforeAndWaitingReady(LocalDateTime registeredDateTime, WaitingStatus waitingStatus);

}
