package uz.exadel.hotdeskbooking.repository.vacation;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.exadel.hotdeskbooking.dto.request.VacationDTO;

public interface VacationRepository extends JpaRepository<VacationDTO, String> {
}
