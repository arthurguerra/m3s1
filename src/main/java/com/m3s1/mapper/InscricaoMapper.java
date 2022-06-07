package com.m3s1.mapper;

import com.m3s1.dto.InscricaoReqDTO;
import com.m3s1.dto.InscricaoRespDTO;
import com.m3s1.model.Inscricao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InscricaoMapper {

    InscricaoMapper INSTANCE = Mappers.getMapper(InscricaoMapper.class);

    @Mapping(source = "aluno.matricula", target = "matriculaAluno")
    @Mapping(source = "curso.codigo", target = "codigoCurso")
    InscricaoReqDTO toRequest(Inscricao model);

    @Mapping(source = "matriculaAluno", target = "aluno.matricula")
    @Mapping(source = "codigoCurso", target = "curso.codigo")
    Inscricao toModel(InscricaoReqDTO request);

    @Mapping(source = "aluno.matricula", target = "matriculaAluno")
    @Mapping(source = "curso.codigo", target = "codigoCurso")
    InscricaoRespDTO toResponse(Inscricao model);

    @Mapping(source = "matriculaAluno", target = "aluno.matricula")
    @Mapping(source = "codigoCurso", target = "curso.codigo")
    Inscricao toModel(InscricaoRespDTO response);

}
