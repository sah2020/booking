package uz.exadel.hotdeskbooking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.exadel.hotdeskbooking.domain.Vacation;
import uz.exadel.hotdeskbooking.dto.response.ResponseItem;
import uz.exadel.hotdeskbooking.dto.request.VacationDTO;
import uz.exadel.hotdeskbooking.exception.RestException;
import uz.exadel.hotdeskbooking.repository.vacation.VacationRepository;
import uz.exadel.hotdeskbooking.service.base.VacationBase;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
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
        Optional<Vacation> byId = repository.findById(id);
        return byId.map(ResponseItem::new).orElseGet(() -> new ResponseItem("Not found", HttpStatus.NOT_FOUND.value()));
    }

    @Transactional
    @Override
    public ResponseItem put(String id, VacationDTO vacationDTO) {
        Optional<Vacation> findById = repository.findById(id);

        if (findById.isEmpty())
            throw new RestException("Requested vacation doesn’t exist", HttpStatus.NOT_FOUND.value());

        Vacation vacation = findById.get();
        vacation.setVacationStart(vacationDTO.getVacationEnd());
        vacation.setVacationStart(vacationDTO.getVacationStart());
        return new ResponseItem("OK", HttpStatus.OK.value());
    }

    @Transactional
    @Override
    public ResponseItem delete(String id) {

        boolean existsById = repository.existsById(id);
        if (!existsById)
            throw new RestException("Requested vacation doesn’t exist", HttpStatus.NOT_FOUND.value());
        repository.deleteById(id);
        return null;
    }
}
