package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.exadel.hotdeskbooking.domain.Booking;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findAllByWorkplace_Map_OfficeIdAndStartDateAndActiveTrue(String workplace_map_officeId, Date startDate);

    List<Booking> findAllByWorkplace_Map_OfficeIdAndStartDateAndEndDateAndActiveTrue(String workplace_map_officeId, Date startDate, Date endDate);
}
