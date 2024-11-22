package com.project.shopapp.repositories;

import com.project.shopapp.models.TableReservation;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableReservationRepository extends JpaRepository<TableReservation, Long> {
    List<TableReservation> findByCustomer(User user);

    boolean existsByReservationCode(String reservationCode);

    @Query("SELECT tr FROM TableReservation tr WHERE tr.reservationCode LIKE %:code%")
    List<TableReservation> findByReservationCodeLike(@Param("code") String code);
}
