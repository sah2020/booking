package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.domain.Vacation;
import uz.exadel.hotdeskbooking.dto.request.VacationDTO;

import java.util.List;

public interface VacationService {
    String post(VacationDTO vacationDto);

    List<Vacation> getAll();

    Vacation get(String vacationId);

    String put(String id, VacationDTO vacationDTO);

    String delete(String vacationId);
}
