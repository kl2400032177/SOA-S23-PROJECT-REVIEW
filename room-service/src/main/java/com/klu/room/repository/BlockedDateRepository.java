package com.klu.room.repository;

import com.klu.room.entity.BlockedDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BlockedDateRepository extends JpaRepository<BlockedDate, Long> {

    // Overlap check: existing block overlaps requested range if
    // existing.checkIn < requested.checkOut AND existing.checkOut > requested.checkIn
    @Query("SELECT b FROM BlockedDate b WHERE b.roomId = :roomId " +
           "AND b.checkIn < :checkOut AND b.checkOut > :checkIn")
    List<BlockedDate> findOverlaps(@Param("roomId") Long roomId,
                                     @Param("checkIn") LocalDate checkIn,
                                     @Param("checkOut") LocalDate checkOut);

    void deleteByBookingId(Long bookingId);

}