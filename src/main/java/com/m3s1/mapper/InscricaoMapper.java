package com.m3s1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InscricaoMapper {

    InscricaoMapper INSTANCE = Mappers.getMapper(InscricaoMapper.class);


}
