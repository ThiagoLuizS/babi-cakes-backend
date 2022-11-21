package br.com.babicakesbackend.models.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper<T, View, Form> {

    View entityToView(T t);
    T formToEntity(Form form);
    Form viewToForm(View view);
    Form entityToForm(T t);
}
