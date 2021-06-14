package uz.pdp.task1hoteldb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.DomainEvents;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1hoteldb.entity.Hotel;
import uz.pdp.task1hoteldb.repository.HotelRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping("")
    public List<Hotel> getAll() {
        List<Hotel> hotelList = hotelRepository.findAll();
        return hotelList;
    }

    @GetMapping("/{id}")
    public Hotel getOne(@PathVariable Integer id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isEmpty()) {
            return new Hotel();
        }
        return optionalHotel.get();
    }

    @PostMapping
    public String save(@RequestBody Hotel hotel) {
        boolean exists = hotelRepository.existsByName(hotel.getName());
        if (exists) {
            return "This hotel already exist";
        }

        hotelRepository.save(hotel);
        return "Hotel saved";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isEmpty()) {
            return "Hotel not found";
        }
        hotelRepository.deleteById(id);
        return "Hotel deleted";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id, @RequestBody Hotel hotel) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);

        if (optionalHotel.isEmpty()) {
            return "Hotel not found";
        }

        boolean exists = hotelRepository.existsByName(hotel.getName());
        if (!exists) {
            return "This hotel already exist";
        }

        Hotel editingHotel = optionalHotel.get();
        editingHotel.setName(hotel.getName());
        hotelRepository.save(editingHotel);
        return "Hotel edited";
    }
}
