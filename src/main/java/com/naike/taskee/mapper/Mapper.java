package com.naike.taskee.mapper;

public interface Mapper <E, D> {
    public E mapToEntity(D dto);
    public D mapToDTO(E entity);
}
