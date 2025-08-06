package com.spring.luispa.todo_list.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.spring.luispa.todo_list.dtos.TaskCreateRequest;
import com.spring.luispa.todo_list.dtos.TaskReponse;
import com.spring.luispa.todo_list.dtos.TaskUpdateRequest;
import com.spring.luispa.todo_list.entities.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    // DTO -> Entidad (creación)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    Task toEntity(TaskCreateRequest task);

    // DTO -> Entidad (actualización)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(TaskUpdateRequest dto, @MappingTarget Task entity);

    // Entidad -> DTO (respuesta)
    TaskReponse toResponse(Task task);
}
