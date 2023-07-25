package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PaymentMethodDTOAssembler;
import com.algaworks.algafood.api.assembler.PaymentMethodInputDisassembler;
import com.algaworks.algafood.api.dto.PaymentMethodDTO;
import com.algaworks.algafood.api.dto.input.PaymentMethodInput;
import com.algaworks.algafood.api.helper.ResourceUriHelper;
import com.algaworks.algafood.api.openapi.controller.PaymentMethodControllerOpenApi;
import com.algaworks.algafood.domain.model.PaymentMethod;
import com.algaworks.algafood.domain.repository.PaymentMethodRepository;
import com.algaworks.algafood.domain.service.PaymentMethodService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentMethodController implements PaymentMethodControllerOpenApi {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private PaymentMethodService service;

    @Autowired
    private PaymentMethodDTOAssembler paymentMethodDTOAssembler;

    @Autowired
    private PaymentMethodInputDisassembler paymentMethodInputDisassembler;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaymentMethodDTO>> fetchAll(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String etag = "0";
        OffsetDateTime lastUpdateDate = paymentMethodRepository.getLastUpdateDate();

        if (lastUpdateDate != null) {
            etag = String.valueOf(lastUpdateDate.toEpochSecond());
        }

        if (request.checkNotModified(etag)) {
            return null;
        }

        List<PaymentMethodDTO> paymentMethodsDTO = paymentMethodDTOAssembler
                .toCollectionModel(paymentMethodRepository.findAll());

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic()).eTag(etag)
                .body(paymentMethodsDTO);
    }

    @Override
    @GetMapping(value = "/{paymentMethodId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentMethodDTO> find(
            @ApiParam(value = "ID da forma de pagamento", example = "1") @PathVariable Long paymentMethodId,
            ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String etag = "0";
        OffsetDateTime updateDate = paymentMethodRepository.getUpdateDateById(paymentMethodId);

        if (updateDate != null) {
            etag = String.valueOf(updateDate.toEpochSecond());
        }

        if (request.checkNotModified(etag)) {
            return null;
        }

        PaymentMethod paymentMethod = service.findOrFail(paymentMethodId);
        PaymentMethodDTO paymentMethodDTO = paymentMethodDTOAssembler.toModel(paymentMethod);

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic()).eTag(etag)
                .body(paymentMethodDTO);
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentMethodDTO add(@RequestBody @Valid PaymentMethodInput paymentMethodInput) {
        PaymentMethod paymentMethod = paymentMethodInputDisassembler.toDomainObject(paymentMethodInput);
        paymentMethod = service.save(paymentMethod);

        PaymentMethodDTO paymentMethodDTO = paymentMethodDTOAssembler.toModel(paymentMethod);
        ResourceUriHelper.addUriInResponseHeader(paymentMethodDTO.getId());

        return paymentMethodDTO;
    }

    @Override
    @PutMapping(value = "/{paymentMethodId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentMethodDTO update(
            @ApiParam(value = "ID da forma de pagamento", example = "1") @PathVariable Long paymentMethodId,
            @RequestBody @Valid PaymentMethodInput paymentMethodInput) {
        PaymentMethod currentPaymentMethod = service.findOrFail(paymentMethodId);
        paymentMethodInputDisassembler.copyToDomainObject(paymentMethodInput, currentPaymentMethod);
        currentPaymentMethod = service.save(currentPaymentMethod);

        return paymentMethodDTOAssembler.toModel(currentPaymentMethod);
    }

    @Override
    @DeleteMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @ApiParam(value = "ID da forma de pagamento", example = "1") @PathVariable Long paymentMethodId) {
        service.delete(paymentMethodId);
    }
}
