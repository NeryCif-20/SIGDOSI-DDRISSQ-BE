package com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.service;

import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialCreateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialResponse;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialSearchRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.dto.BuildingMaterialUpdateRequest;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.model.BuildingMaterial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BuildingMaterialService {

    BuildingMaterialResponse get(UUID id);
    BuildingMaterialResponse create(BuildingMaterialCreateRequest request);
    BuildingMaterialResponse update(UUID id, BuildingMaterialUpdateRequest request);
    Page<BuildingMaterialResponse> getAll(BuildingMaterialSearchRequest request, Pageable pageable);
    BuildingMaterial getByIdOrThrow(UUID id);

}
