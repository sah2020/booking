package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.domain.Vacation;
import uz.exadel.hotdeskbooking.dto.request.VacationDTO;
import uz.exadel.hotdeskbooking.dto.response.ResponseItem;
import uz.exadel.hotdeskbooking.response.success.CreatedResponse;
import uz.exadel.hotdeskbooking.response.success.OkResponse;

import javax.transaction.Transactional;
import java.util.List;

public interface VacationService {
    String post(VacationDTO vacationDto);

    List<Vacation> getAll();

    Vacation get(String vacationId);

    void put(String id, VacationDTO vacationDTO);

    void delete(String vacationId);
}
