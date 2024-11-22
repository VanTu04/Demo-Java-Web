package com.project.shopapp.controllers;

import com.project.shopapp.DTO.StatusDTO;
import com.project.shopapp.DTO.TableReservationDTO;
import com.project.shopapp.DTO.UserDTO;
import com.project.shopapp.enums.RESERVATION_STATUS;
import com.project.shopapp.models.TableReservation;
import com.project.shopapp.models.User;
import com.project.shopapp.services.TableReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/booktable")
@RequiredArgsConstructor
public class TableReservationController {

    private final TableReservationService tableReservationService;

    @GetMapping()
    public ResponseEntity<List<TableReservation>> tableReservationList (){
        List<TableReservation> tableReservations = tableReservationService.findAllTableReservation();
        return ResponseEntity.ok(tableReservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> tableReservationList(@PathVariable Long id) throws Exception {
        List<TableReservation> tableReservations = tableReservationService.findAllById(id);
        return ResponseEntity.ok(tableReservations);
    }

    @GetMapping("/code/{id}")
    public ResponseEntity<?> tableReservationListByCode(@PathVariable String id) throws Exception {
        List<TableReservation> tableReservation = tableReservationService.findTableReservationByCode(id);
        return ResponseEntity.ok(tableReservation);
    }

    @PostMapping()
    public ResponseEntity<?> createTableReservation (@Valid @RequestBody TableReservationDTO tableReservationDTO, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            tableReservationService.createTable(tableReservationDTO);

            return ResponseEntity.ok("Create successfully");
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()); //rule 5
        }
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id,
                                          @Valid @RequestBody StatusDTO status,
                                          BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            tableReservationService.changeStatus(id, status);
            return ResponseEntity.ok("Change successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTableReservation (@PathVariable Long id){
        try {
            TableReservation canceledReservation = tableReservationService.cancelReservation(id);
            return ResponseEntity.ok(canceledReservation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
