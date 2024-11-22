package com.project.shopapp.services;

import com.project.shopapp.DTO.StatusDTO;
import com.project.shopapp.DTO.TableReservationDTO;
import com.project.shopapp.customexceptions.InvalidParamException;
import com.project.shopapp.enums.RESERVATION_STATUS;
import com.project.shopapp.models.TableReservation;
import jakarta.validation.Valid;

import java.util.List;

public interface TableReservationService {

    List<TableReservation> findAllTableReservation();

    void createTable(TableReservationDTO tableReservationDTO) throws Exception;

    TableReservation cancelReservation(Long id) throws Exception;

    TableReservation changeStatus(Long id, StatusDTO status) throws InvalidParamException;

    List<TableReservation> findAllById(Long id) throws Exception;

    List<TableReservation> findTableReservationByCode(String id);
}
