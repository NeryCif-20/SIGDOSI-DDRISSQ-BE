package com.ddrissq.sigdosi.healthcarenetwork.riss.service;

import com.ddrissq.sigdosi.common.exception.EntityAlreadyExistsException;
import com.ddrissq.sigdosi.common.exception.EntityNotFoundException;
import com.ddrissq.sigdosi.common.message.service.MessageService;
import com.ddrissq.sigdosi.common.service.util.PatchHelper;
import com.ddrissq.sigdosi.healthcarenetwork.dms.model.Dms;
import com.ddrissq.sigdosi.healthcarenetwork.dms.service.DmsService;
import com.ddrissq.sigdosi.healthcarenetwork.riss.constant.RissErrorMessageKeys;
import com.ddrissq.sigdosi.healthcarenetwork.riss.dto.RissCreateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.riss.dto.RissResponse;
import com.ddrissq.sigdosi.healthcarenetwork.riss.dto.RissSearchRequest;
import com.ddrissq.sigdosi.healthcarenetwork.riss.dto.RissUpdateRequest;
import com.ddrissq.sigdosi.healthcarenetwork.riss.mapper.RissMapper;
import com.ddrissq.sigdosi.healthcarenetwork.riss.model.Riss;
import com.ddrissq.sigdosi.healthcarenetwork.riss.repository.RissRepository;
import com.ddrissq.sigdosi.healthcarenetwork.riss.specification.RissSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class RissServiceImpl implements RissService {

    private final RissRepository repository;
    private final RissMapper mapper;
    private final DmsService dmsService;
    private final MessageService messageService;

    @Override
    public RissResponse get(UUID id) {
        return null;
    }

    @Override
    public RissResponse create(RissCreateRequest request) {
        Dms dms = dmsService.getByIdOrThrow(request.dms());
        validateUniqueDmsName(dms.getId(), request.name());
        Riss riss = mapper.toRiss(request);
        riss.setDms(dms);
        Riss savedRiss = repository.save(riss);
        return mapper.toResponse(savedRiss);
    }

    @Override
    public RissResponse update(UUID id, RissUpdateRequest request) {
        Riss riss = getByIdOrThrow(id);
        Dms dms = PatchHelper.resolveEntity(
                request.dms(),
                riss.getDms(),
                dmsService::getByIdOrThrow);
        String name = PatchHelper.resolveValue(
                request.name(), riss.getName());
        validateUniqueDmsName(dms.getId(), name, id);
        mapper.updateRiss(request, riss);
        riss.setDms(dms);
        return mapper.toResponse(riss);
    }

    @Override
    public Page<RissResponse> getAll(RissSearchRequest request, Pageable pageable) {
        Specification<Riss> spec = Specification.allOf(
                Specification.anyOf(
                        RissSpecification.hasName(request.q()),
                        RissSpecification.hasDmsName(request.q())));
        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public Riss getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageService.getMessage(
                                RissErrorMessageKeys.NOT_FOUND)));
    }

    private void validateUniqueDmsName(UUID dms, String name) {
        validateUniqueDmsName(dms, name, null);
    }

    private void validateUniqueDmsName(UUID dms, String name, UUID id) {
        String capitalizedName = StringUtils.capitalize(name.trim());
        boolean exists = id == null
                ? repository.existsByDmsIdAndName(dms, capitalizedName)
                : repository.existsByDmsIdAndNameAndIdNot(dms, capitalizedName, id);
        if (exists) {
            throw new EntityAlreadyExistsException(
                    messageService.getMessage(
                            RissErrorMessageKeys.ALREADY_EXISTS));
        }
    }

}
