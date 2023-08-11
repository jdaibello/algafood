package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.PaymentMethodController;
import com.algaworks.algafood.api.dto.PaymentMethodDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.PaymentMethod;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodDTOAssembler extends RepresentationModelAssemblerSupport<PaymentMethod, PaymentMethodDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public PaymentMethodDTOAssembler() {
        super(PaymentMethodController.class, PaymentMethodDTO.class);
    }

    @Override
    public PaymentMethodDTO toModel(PaymentMethod paymentMethod) {
        PaymentMethodDTO paymentMethodDTO = createModelWithId(paymentMethod.getId(), paymentMethod);
        modelMapper.map(paymentMethod, paymentMethodDTO);

        if (algaSecurity.canQueryPaymentMethods()) {
            paymentMethodDTO.add(algaLinks.linkToPaymentMethods("payment-methods"));
        }

        return paymentMethodDTO;
    }

    @Override
    public CollectionModel<PaymentMethodDTO> toCollectionModel(Iterable<? extends PaymentMethod> entities) {
        CollectionModel<PaymentMethodDTO> collectionModel = super.toCollectionModel(entities);

        if (algaSecurity.canQueryPaymentMethods()) {
            collectionModel.add(algaLinks.linkToPaymentMethods());
        }

        return collectionModel;
    }
}
