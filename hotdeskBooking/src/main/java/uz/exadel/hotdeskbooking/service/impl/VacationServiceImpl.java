package uz.exadel.hotdeskbooking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.User;
import uz.exadel.hotdeskbooking.domain.Vacation;
import uz.exadel.hotdeskbooking.dto.request.VacationDTO;
import uz.exadel.hotdeskbooking.exception.BadRequestException;
import uz.exadel.hotdeskbooking.exception.ConflictException;
import uz.exadel.hotdeskbooking.repository.UserRepository;
import uz.exadel.hotdeskbooking.repository.VacationRepository;
import uz.exadel.hotdeskbooking.response.ResponseMessage;
import uz.exadel.hotdeskbooking.service.VacationService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class VacationServiceImpl implements VacationService {

    private final VacationRepository repository;
    private final UserRepository userRepository;

    @Override
    public String post(VacationDTO vacationDTO) {
        Optional<User> byId = userRepository.findById(vacationDTO.getUserId());
        if (byId.isEmpty()) {
            throw new ConflictException(ResponseMessage.USER_NOT_FOUND.getMessage());
        }

        Vacation vacation = new Vacation();
        vacation.setVacationStart(vacationDTO.getVacationStart());
        vacation.setVacationEnd(vacationDTO.getVacationEnd());
        vacation.setUserId(vacationDTO.getUserId());
        Vacation savedVacation = repository.save(vacation);
        return savedVacation.getId();
    }

    @Override
    public List<Vacation> getAll() {
        return repository.findAll();
    }

    @Override
    public Vacation get(String id) {
        checkVacationExistence(id); //throws an exception, if not found
        Vacation vacation = repository.findFirstById(id);
        return vacation;
    }

    @Override
    public String put(String vacationId, VacationDTO vacationDTO) {
        checkVacationExistence(vacationId); //throws an exception, if not found
        Vacation vacation = repository.findFirstById(vacationId);

        vacation.setVacationStart(vacationDTO.getVacationEnd());
        vacation.setVacationStart(vacationDTO.getVacationStart());
        repository.save(vacation);
        return ResponseMessage.VACATION_UPDATED.getMessage();
    }

    @Override
    public String delete(String id) {
        checkVacationExistence(id);
        repository.deleteById(id);
        return ResponseMessage.VACATION_DELETED.getMessage();
    }

    private void checkVacationExistence(String id) {
        if (id == null) {
            throw new BadRequestException(ResponseMessage.REQUEST_BODY_NULL.getMessage());
        }
        boolean existsById = repository.existsById(id);
        if (!existsById) {
            throw new ConflictException(ResponseMessage.VACATION_NOT_FOUND.getMessage());
        }
    }
}

