package uz.exadel.hotdeskbooking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import uz.exadel.hotdeskbooking.dto.ResponseItem;
import uz.exadel.hotdeskbooking.dto.request.VacationDTO;
import uz.exadel.hotdeskbooking.exception.RestException;
import uz.exadel.hotdeskbooking.repository.vacation.VacationRepository;
import uz.exadel.hotdeskbooking.service.base.VacationBase;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
public class VacationServiceImpl implements VacationBase<VacationDTO, String> {

    private final VacationRepository repository;

    @Transactional
    @Override
    public ResponseItem post(VacationDTO vacationDTO) {
        VacationDTO save = repository.save(vacationDTO);
        return new ResponseItem("OK", HttpStatus.OK.value());
    }

    @Transactional
    @Override
    public ResponseItem getAll() {
        repository.findAll();
        return new ResponseItem("Vacations found", HttpStatus.FOUND.value());
    }

    @Transactional
    @Override
    public ResponseItem get(String id) {
        Optional<VacationDTO> byId = repository.findById(id);
        if (byId.isPresent()) {
            return new ResponseItem(byId.get());
        }
        return new ResponseItem("Not found", HttpStatus.NOT_FOUND.value());
    }

    @Transactional
    @Override
    public ResponseItem put(String id, VacationDTO vacation) {
        Optional<VacationDTO> findById = repository.findById(id);

        if (findById.isEmpty())
            throw new RestException("Requested vacation doesn’t exist", HttpStatus.NOT_FOUND);

        VacationDTO vacationDTO = findById.get();
        vacationDTO.setVacationStart(vacation.getVacationEnd());
        vacationDTO.setVacationStart(vacation.getVacationStart());
        return new ResponseItem("OK", HttpStatus.OK.value());
    }

    @Transactional
    @Override
    public ResponseItem delete(String id) {

        boolean existsById = repository.existsById(id);
        if (!existsById)
            throw new RestException("Requested vacation doesn’t exist", HttpStatus.NOT_FOUND);
        repository.deleteById(id);
        return null;
    }
}
