package uz.exadel.hotdeskbooking.service.base;

import uz.exadel.hotdeskbooking.dto.ResponseItem;

public interface VacationBase<T,D> {
    ResponseItem post(T t);

    ResponseItem getAll();

    ResponseItem get(D id);

    ResponseItem put(D id, T t);

    ResponseItem delete(D id);

}
