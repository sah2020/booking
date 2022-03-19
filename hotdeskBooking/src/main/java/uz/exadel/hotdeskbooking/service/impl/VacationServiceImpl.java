package uz.exadel.hotdeskbooking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.User;
import uz.exadel.hotdeskbooking.domain.Vacation;
import uz.exadel.hotdeskbooking.dto.request.VacationDTO;
import uz.exadel.hotdeskbooking.exception.NotFoundException;
import uz.exadel.hotdeskbooking.repository.UserRepository;
import uz.exadel.hotdeskbooking.repository.VacationRepository;
import uz.exadel.hotdeskbooking.response.VacancyResponse;
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
            throw new NotFoundException("api.error.user.notFound");
        }

        Vacation vacation = new Vacation();
        vacation.setVacationStart(vacationDTO.getVacationStart());
        vacation.setVacationEnd(vacationDTO.getVacationEnd());
        vacation.setUserId(vacationDTO.getUserId());
        vacation.setUser(byId.get());
        Vacation savedVacation = repository.save(vacation);
        return savedVacation.getId();
    }

    @Override
    public List<Vacation> getAll() {
        return repository.findAll();
    }

    @Override
    public Vacation get(String id) {
        Optional<Vacation> byId = repository.findById(id);
        checkVacationExistence(id); //throws an exception, if not found

        return byId.get();
    }

    @Override
    public void put(String vacationId, VacationDTO vacationDTO) {
        Optional<Vacation> findById = repository.findById(vacationId);

        checkVacationExistence(vacationId); //throws an exception, if not found

        Vacation vacation = findById.get();
        vacation.setVacationStart(vacationDTO.getVacationEnd());
        vacation.setVacationStart(vacationDTO.getVacationStart());
        repository.save(vacation);
    }

    @Override
    public void delete(String id) {
        checkVacationExistence(id);
        repository.deleteById(id);
    }

    private void checkVacationExistence(String id) {
        boolean existsById = repository.existsById(id);
        if (!existsById) {
            throw new NotFoundException(VacancyResponse.VACATION_NOT_FOUND.getMessage());
        }
    }
}

