package uz.pdp.task1hoteldb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task1hoteldb.entity.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {
    boolean existsByNumber(Integer number);

    Page<Room> findAllByHotelId(Integer hotel_id, Pageable pageable);
}
