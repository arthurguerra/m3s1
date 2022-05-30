package com.m3s1.mapper;

import com.m3s1.dto.AlunoDTO;
import com.m3s1.model.Aluno;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AlunoMapper {

    AlunoMapper INSTANCE = Mappers.getMapper(AlunoMapper.class);

    AlunoDTO toDTO(Aluno model);

    Aluno toModel(AlunoDTO dto);
}
