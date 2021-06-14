package uz.pdp.task1hoteldb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task1hoteldb.entity.Hotel;
import uz.pdp.task1hoteldb.entity.Room;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    boolean existsByName(String name);
}
