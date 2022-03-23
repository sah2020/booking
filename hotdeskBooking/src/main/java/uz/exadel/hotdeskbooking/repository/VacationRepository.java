package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.exadel.hotdeskbooking.domain.Vacation;

import java.util.Date;
import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, String> {
    @Query(value = "SELECT * FROM vacation WHERE user_id=?1 AND (vacation_start BETWEEN ?2 AND ?3) OR (vacation_end BETWEEN ?2 AND ?3)",
            nativeQuery = true)
    List<Vacation> findAllByUserIdAndStartDateAndEndDate(String userId, Date startDate, Date endDate);

    Vacation findFirstById(String id);
}
