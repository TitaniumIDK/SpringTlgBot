package org.example.springtlgbot.repository;

import org.example.springtlgbot.entity.Employee;
import org.example.springtlgbot.entity.Schedule;
import org.example.springtlgbot.enums.BusynessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findByEmp(Employee emp);

    @Modifying
    @Query("UPDATE Schedule s SET " +
            "s.firstThird = CASE WHEN :choosedPart = 1 THEN :newBusynessType ELSE s.firstThird END, " +
            "s.secondThird = CASE WHEN :choosedPart = 2 THEN :newBusynessType ELSE s.secondThird END, " +
            "s.thirdThird = CASE WHEN :choosedPart = 3 THEN :newBusynessType ELSE s.thirdThird END " +
            "WHERE s.emp = :emp AND s.workDate = :datein")
    int updateBusynessType(@Param("emp") Employee employee,
                           @Param("datein") LocalDate date,
                           @Param("newBusynessType") BusynessType busynessType,
                           @Param("choosedPart") Integer thirdPart);
}
