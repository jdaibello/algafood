package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.PaymentMethodDTO;
import com.algaworks.algafood.domain.model.PaymentMethod;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentMethodDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PaymentMethodDTO toModel(PaymentMethod paymentMethod) {
        return modelMapper.map(paymentMethod, PaymentMethodDTO.class);
    }

    public List<PaymentMethodDTO> toCollectionModel(Collection<PaymentMethod> paymentMethods) {
        return paymentMethods.stream().map(paymentMethod -> toModel(paymentMethod)).collect(Collectors.toList());
    }
}
