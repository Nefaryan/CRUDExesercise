package co.develhope.crudEntity.controller;

import co.develhope.crudEntity.entities.Car;
import co.develhope.crudEntity.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @PostMapping //post car
    public Car createCar(@RequestBody Car car){
        Car cars = carRepository.saveAndFlush(car);
        return cars;
    }

    @GetMapping() // Gett all
    public List<Car> findAllCar(){
        List<Car> cars = carRepository.findAll();
        return cars;
    }

    @GetMapping("/{id}")
    public Car getById(@PathVariable long id){
        boolean existsById = carRepository.existsById(id);
        if(existsById){
            Car car = carRepository.getReferenceById(id);
            return car;
        }
        return new Car();
    }

    @PutMapping("/{id}") //Put by Id
    public Car updateCarById(@PathVariable Long id, @RequestBody Car car){
        car.setId(id);
        Car car0 = carRepository.getReferenceById(id);
        boolean existsById = carRepository.existsById(id);
        if(existsById){
            return car;
        }else{
            return new Car();
        }

    }

    @DeleteMapping("/{id}") //Delete By Id
    public void deleteCarById(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response){
        boolean existsById = carRepository.existsById(id);
        if(existsById){
            carRepository.deleteById(id);
        }else{
            System.out.println(HttpStatus.CONFLICT);
        }try {
            response.sendError(409,"CONFLICT");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("")// Delete all
    public void deleteAllCar(){
        carRepository.deleteAll();
    }
}
