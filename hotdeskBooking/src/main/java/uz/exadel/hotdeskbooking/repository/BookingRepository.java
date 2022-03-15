package uz.exadel.hotdeskbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.exadel.hotdeskbooking.domain.Booking;

import java.util.Date;
import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, String> {
    @Query(value = "SELECT b.* FROM booking b" +
            "    INNER JOIN workplace wrk on b.workplace_id=wrk.id" +
            "    INNER JOIN map m ON wrk.map_id=m.id" +
            "    INNER JOIN office o on o.id=m.office_id" +
            "   WHERE o.id=?1 AND (b.start_date BETWEEN ?2 AND ?3)" +
            "  OR (b.end_date BETWEEN ?2 AND ?3)" +
            "  AND b.active=true", nativeQuery = true)
    List<Booking> findAllByWorkplace_Map_OfficeIdAndStartDateAndEndDateAndActiveTrue(String workplace_map_officeId, Date startDate, Date endDate);

    @Query(value = "SELECT b.* FROM booking b" +
            "    INNER JOIN workplace wrk on b.workplace_id=wrk.id" +
            "    INNER JOIN map m ON wrk.map_id=m.id" +
            "    INNER JOIN office o on o.id=m.office_id" +
            "   WHERE o.id=?1 AND b.active=true", nativeQuery = true)
    List<Booking> findAllByWorkplace_Map_OfficeIdAndActiveTrue(String workplace_map_officeId);

    @Query(value = "SELECT b.* FROM booking b" +
            "    INNER JOIN workplace wrk on b.workplace_id=wrk.id" +
            "    INNER JOIN map m ON wrk.map_id=m.id" +
            "    INNER JOIN office o on o.id=m.office_id" +
            "   WHERE o.id=?1 AND b.start_date > ?2 AND b.active=true", nativeQuery = true)
    List<Booking> findAllByWorkplace_Map_OfficeIdAndStartDateAndActiveTrue(String workplace_map_officeId, Date startDate);

    @Query(value = "SELECT b.* FROM booking b" +
            "    INNER JOIN workplace wrk on b.workplace_id=wrk.id" +
            "    INNER JOIN map m ON wrk.map_id=m.id" +
            "    INNER JOIN office o on o.id=m.office_id" +
            "   WHERE o.id=?1 AND b.start_date < ?2 AND b.active=true", nativeQuery = true)
    List<Booking> findAllByWorkplace_Map_OfficeIdAndEndDateAndActiveTrue(String workplace_map_officeId, Date endDate);

    Booking findFirstByWorkplaceIdAndStartDateAndUserIdAndActiveFalse(String workplaceId, Date startDate, String userId);

    List<Booking> findAllByWorkplaceIdAndStartDateInAndUserIdAndActiveFalse(String workplaceId, List<Date> startDate, String userId);

    @Query(value = "SELECT * FROM booking " +
            "WHERE workplace_id=?1 AND (start_date BETWEEN ?2 AND ?3) OR (end_date BETWEEN ?2 AND ?3) AND active=true",
            nativeQuery = true)
    List<Booking> findAllByWorkplaceIdAndStartDateAndEndDateAndActiveTrue(String workplaceId, Date startDate, Date endDate);

    List<Booking> findAllByIdIn(List<String> id);

    List<Booking> findAllByUserIdAndActiveTrue(String userId);

    List<Booking> findAllByActiveTrue();
}
