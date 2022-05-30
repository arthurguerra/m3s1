package com.m3s1.mapper;

import com.m3s1.dto.CursoDTO;
import com.m3s1.model.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CursoMapper {

    CursoMapper INSTANCE = Mappers.getMapper(CursoMapper.class);

    Curso toModel(CursoDTO dto);

    CursoDTO toDTO(Curso model);
}
