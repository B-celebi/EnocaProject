package com.enoca.cartApi.common.utilities.abstracts;

import org.modelmapper.ModelMapper;

public interface ModelMapperBusiness {
    ModelMapper forRequest();
    ModelMapper forResponse();
}
