package uz.pdp.task1hoteldb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import uz.pdp.task1hoteldb.entity.Hotel;
import uz.pdp.task1hoteldb.entity.Room;
import uz.pdp.task1hoteldb.paylaod.RoomDto;
import uz.pdp.task1hoteldb.repository.HotelRepository;
import uz.pdp.task1hoteldb.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public List<Room> getAll() {
        List<Room> roomList = roomRepository.findAll();
        return roomList;
    }

    @GetMapping("/forHotel/{id}")
    public Page<Room> getAllByHotelId(@PathVariable Integer id, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Room> hotelPage = roomRepository.findAllByHotelId(id, pageable);
        return hotelPage;
    }

    @GetMapping("/{id}")
    public Room getOne(@PathVariable Integer id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isEmpty()) {
            return new Room();
        }
        return optionalRoom.get();
    }

    @PostMapping
    public String save(@RequestBody RoomDto roomDto) {
        Room room = new Room();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());

        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());

        if (optionalHotel.isEmpty()) {
            return "Hotel not found";
        }
        boolean exists = roomRepository.existsByNumber(roomDto.getNumber());
        if (exists) {
            return "Room number already exist";
        }

        room.setHotel(optionalHotel.get());
        roomRepository.save(room);
        return "Room saved";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isEmpty()) {
            return "Room not found";
        }
        hotelRepository.deleteById(id);
        return "Room deleted";
    }


    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id, @RequestBody RoomDto roomDto) {
        Optional<Room> optionalRoom = roomRepository.findById(id);

        if (optionalRoom.isEmpty()) {
            return "Room not found";
        }
        boolean exists = roomRepository.existsByNumber(roomDto.getNumber());
        if (exists) {
            return "Room number already exist";
        }

        Room room = optionalRoom.get();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());

        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());

        if (optionalHotel.isEmpty()) {
            return "Hotel not found";
        }

        room.setHotel(optionalHotel.get());
        roomRepository.save(room);
        return "Room edited";
    }
}
