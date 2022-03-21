package uz.exadel.hotdeskbooking.repository.vacation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.exadel.hotdeskbooking.domain.Vacation;

import java.util.Date;
import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, String> {
    @Query(value = "SELECT * FROM vacation WHERE user_id=?1 AND (start_date BETWEEN ?2 AND ?3) OR (end_date BETWEEN ?2 AND ?3)",
            nativeQuery = true)
    List<Vacation> findAllByUserIdAndStartDateAndEndDate(String userId, Date startDate, Date endDate);
}
