package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.exadel.hotdeskbooking.domain.Booking;

import java.util.Date;
import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findAllByWorkplace_Map_OfficeIdAndStartDateInAndActiveTrue(String workplace_map_officeId, List<Date> startDate);

    @Query(value = "SELECT b.* FROM booking b" +
            "    INNER JOIN workplace wrk on b.workplace_id=wrk.id" +
            "    INNER JOIN map m ON wrk.map_id=m.id" +
            "    INNER JOIN office o on o.id=m.office_id" +
            "   WHERE o.id=?1 AND (b.start_date>=?2 AND b.start_date<=?3)" +
            "  OR (b.end_date>=?2 AND b.end_date<?3)" +
            "  AND b.active=true", nativeQuery = true)
    List<Booking> findAllByWorkplace_Map_OfficeIdAndStartDateAndEndDateAndActiveTrue(String workplace_map_officeId, Date startDate, Date endDate);

    Booking findFirstByWorkplaceIdAndStartDateAndUserIdAndActiveFalse(String workplaceId, Date startDate, String userId);

    List<Booking> findAllByWorkplaceIdAndStartDateInAndUserIdAndActiveFalse(String workplaceId, List<Date> startDate, String userId);
}
