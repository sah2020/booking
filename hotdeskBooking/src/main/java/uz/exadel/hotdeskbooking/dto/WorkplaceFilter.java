package uz.exadel.hotdeskbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import uz.exadel.hotdeskbooking.domain.Map;
import uz.exadel.hotdeskbooking.domain.Workplace;
import uz.exadel.hotdeskbooking.enums.WorkplaceTypeEnum;

import javax.persistence.criteria.*;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkplaceFilter implements Specification<Workplace> {
    private String workplaceNumber;
    private WorkplaceTypeEnum type;
    private Boolean nextToWindow;
    private Boolean hasPC;
    private Boolean hasMonitor;
    private Boolean hasKeyboard;
    private Boolean hasMouse;
    private Boolean hasHeadset;
    private Integer floor;
    private Boolean kitchen;
    private Boolean confRoom;
    private String officeId;

    @Override
    public Predicate toPredicate(Root<Workplace> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Join<Workplace, Map> mapRoot = root.join("map");
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (officeId != null){
            predicates.add(cb.equal(mapRoot.get("officeId"), officeId));
        }

        if (workplaceNumber != null) {
            predicates.add(cb.equal(root.get("workplaceNumber"), workplaceNumber));
        }

        if (type != null) {
            predicates.add(cb.equal(root.get("type"), type));
        }

        if (nextToWindow != null) {
            predicates.add(cb.equal(root.get("nextToWindow"), nextToWindow));
        }

        if (hasPC != null) {
            predicates.add(cb.equal(root.get("hasPC"), hasPC));
        }

        if (hasMonitor != null) {
            predicates.add(cb.equal(root.get("hasMonitor"), hasMonitor));
        }

        if (hasKeyboard != null) {
            predicates.add(cb.equal(root.get("hasKeyboard"), hasKeyboard));
        }

        if (hasHeadset != null) {
            predicates.add(cb.equal(root.get("hasHeadSet"), hasHeadset));
        }

        if (hasMouse != null) {
            predicates.add(cb.equal(root.get("hasMouse"), hasMouse));
        }

        if (floor != null) {
            predicates.add(cb.equal(mapRoot.get("floor"), floor));
        }

        if (kitchen != null) {
            predicates.add(cb.equal(mapRoot.get("kitchen"), kitchen));
        }

        if (confRoom != null) {
            predicates.add(cb.equal(mapRoot.get("confRoom"), confRoom));
        }

        return predicates.size() <= 0 ? null : cb.and(predicates.toArray(new Predicate[0]));
    }
}
