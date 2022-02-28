package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.exadel.hotdeskbooking.domain.ReportDomain;

public interface ReportRepository extends JpaRepository<ReportDomain, String> {
}
