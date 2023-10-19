package com.mazagao.mazagao.mapper;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    private static ModelMapper modelMapper = new ModelMapper();

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return modelMapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> originList, Class<D> destination) {
        return originList.stream()
                .map(o -> modelMapper.map(o, destination))
                .collect(Collectors.toList());
    }
}
