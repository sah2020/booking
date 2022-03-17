package uz.exadel.hotdeskbooking.service;

import uz.exadel.hotdeskbooking.dto.response.ResponseItem;

public interface VacationBase<T,D> {
    ResponseItem post(T t);

    ResponseItem getAll();

    ResponseItem get(D id);

    ResponseItem put(D id, T t);

    ResponseItem delete(D id);

}
