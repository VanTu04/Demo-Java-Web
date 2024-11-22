package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.StatusDTO;
import com.project.shopapp.DTO.TableReservationDTO;
import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.customexceptions.InvalidParamException;
import com.project.shopapp.enums.RESERVATION_STATUS;
import com.project.shopapp.models.TableReservation;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.TableReservationRepository;
import com.project.shopapp.services.TableReservationService;
import com.project.shopapp.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TableReservationImpl implements TableReservationService {

    private final TableReservationRepository tableReservationRepository;
    private final UserService userService;

    @Override
    public List<TableReservation> findAllTableReservation() {
        return tableReservationRepository.findAll();
    }

    @Override
    public void createTable(TableReservationDTO tableReservationDTO) throws Exception {
        User existingUser = userService.findUserById(tableReservationDTO.getCustomerId());
        //loai bo giay
        LocalDateTime localDate = tableReservationDTO.getReservationTime().truncatedTo(ChronoUnit.MINUTES);
        //lay ra gio hien tai
        LocalDateTime currentTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        //so sanh
        if(localDate.isBefore(currentTime)) {
            throw new Exception("Reservation time cannot be in the past.");
        }
        TableReservation tableReservation = TableReservation.builder()
                .customerName(tableReservationDTO.getCustomerName())
                .guestCount(tableReservationDTO.getGuestCount())
                .status(RESERVATION_STATUS.PENDING)
                .reservationTime(localDate.toString())
                .reservationCode(generateUniqueReservationCode())
                .customer(existingUser)
                .build();
        tableReservationRepository.save(tableReservation);
    }

    public String generateUniqueReservationCode() {
        String reservationCode;
        do {
            reservationCode = UUID.randomUUID().toString().substring(0, 8); // Sinh mã mới
        } while (tableReservationRepository.existsByReservationCode(reservationCode)); // Kiểm tra sự trùng lặp
        return reservationCode;
    }

    @Override
    public TableReservation cancelReservation(Long id) throws DataNotFoundException, InvalidParamException {
        TableReservation reservation = tableReservationRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Reservation with id " + id + " not found."));

        if(reservation.getStatus().equals(RESERVATION_STATUS.CONFIRMED) || reservation.getStatus().equals(RESERVATION_STATUS.SUCCESS)) {
            throw new InvalidParamException("Table reservation can not be cancelled.");
        }
        if(reservation.getStatus().equals(RESERVATION_STATUS.CANCELLED)) {
            throw new InvalidParamException("Table reservation has been cancelled.");
        }
        reservation.setStatus(RESERVATION_STATUS.CANCELLED);
        return tableReservationRepository.save(reservation);
    }

    @Override
    public TableReservation changeStatus(Long id, StatusDTO status) throws InvalidParamException {
        try{
            TableReservation existingTableReservation = tableReservationRepository.findById(id)
                    .orElseThrow(() -> new DataNotFoundException("Table reservation not found"));
            RESERVATION_STATUS statusConvert = RESERVATION_STATUS.valueOf(status.getStatus());
            existingTableReservation.setStatus(statusConvert);
            return tableReservationRepository.save(existingTableReservation);
        }
        catch (IllegalArgumentException e){
            throw new InvalidParamException("Status incorrect");
        }
    }

    @Override
    public List<TableReservation> findAllById(Long id) throws Exception {
        User existingUser = userService.findUserById(id);
        List<TableReservation> tableReservations = tableReservationRepository.findByCustomer(existingUser);
        return tableReservations;
    }

    @Override
    public List<TableReservation> findTableReservationByCode(String id) {
        return tableReservationRepository.findByReservationCodeLike(id);
    }
}
