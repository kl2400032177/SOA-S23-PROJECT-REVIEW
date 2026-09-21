package com.klu.room.service;

import com.klu.room.dto.BlockDateRequest;
import com.klu.room.dto.RoomRequest;
import com.klu.room.entity.BlockedDate;
import com.klu.room.entity.Room;
import com.klu.room.repository.BlockedDateRepository;
import com.klu.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final BlockedDateRepository blockedDateRepository;

    public Room createRoom(RoomRequest request) {
        Room room = new Room();
        room.setPropertyId(request.getPropertyId());
        room.setPropertyName(request.getPropertyName());
        room.setRoomType(request.getRoomType());
        room.setPricePerNight(request.getPricePerNight());
        room.setCapacity(request.getCapacity());
        return roomRepository.save(room);
    }

    public List<Room> getRoomsByProperty(Long propertyId) {
        return roomRepository.findByPropertyId(propertyId);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoom(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + id));
    }

    public boolean isAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        if (!checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("checkOut must be after checkIn");
        }
        getRoom(roomId);
        List<BlockedDate> overlaps = blockedDateRepository.findOverlaps(roomId, checkIn, checkOut);
        return overlaps.isEmpty();
    }

    public void blockDates(BlockDateRequest request) {
        if (!isAvailable(request.getRoomId(), request.getCheckIn(), request.getCheckOut())) {
            throw new IllegalArgumentException("Room is not available for the requested dates");
        }
        BlockedDate block = new BlockedDate();
        block.setRoomId(request.getRoomId());
        block.setCheckIn(request.getCheckIn());
        block.setCheckOut(request.getCheckOut());
        block.setBookingId(request.getBookingId());
        blockedDateRepository.save(block);
    }

    public void releaseDates(Long bookingId) {
        blockedDateRepository.deleteByBookingId(bookingId);
    }

}