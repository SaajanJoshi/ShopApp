package com.store.storeapp.warehouse;

import com.store.storeapp.dto.WarehouseDto;
import com.store.storeapp.warehouse.entity.Warehouse;
import com.store.storeapp.warehouse.repository.WarehouseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WarehouseService {
    @Autowired
    WarehouseRepository warehouseRepository;
    public WarehouseDto getWarehouseDetail(Long warehouseId) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);
        return copyWarehouseEntityToDTO(warehouse);
    }

    private WarehouseDto copyWarehouseEntityToDTO(Optional<Warehouse> warehouse) {
        WarehouseDto warehouseDTO = new WarehouseDto();
        BeanUtils.copyProperties(warehouse.get(),warehouseDTO);
        return warehouseDTO;
    }

    public Long addWarehouse(WarehouseDto warehouseDTO) {
        Warehouse warehouse = mapWarehouseDtoToEntity(warehouseDTO);
        return warehouseRepository.save(warehouse).getId();
    }

    private Warehouse mapWarehouseDtoToEntity(WarehouseDto warehouseDTO) {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseCode(warehouseDTO.getWarehouseCode());
        warehouse.setWarehouseDesc(warehouseDTO.getWarehouseDesc());
        return warehouse;
    }

    public List<WarehouseDto> getAllWarehouse() {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        return mapWarehousesToProductDto(warehouses);
    }

    public Warehouse getWarehouseByCode(String warehouseCode){
        return warehouseRepository.findByWarehouseCode(warehouseCode);
    }

    public List<WarehouseDto> mapWarehousesToProductDto(Collection<Warehouse> warehouses) {
        List<WarehouseDto> warehouseDtos = new ArrayList<>();

        for(Warehouse warehouse:warehouses){
            WarehouseDto warehouseDTO = new WarehouseDto();
            warehouseDTO.setWarehouseCode(warehouse.getWarehouseCode());
            warehouseDTO.setWarehouseDesc(warehouse.getWarehouseDesc());
            warehouseDTO.setSelfId(warehouse.getId());
            warehouseDtos.add(warehouseDTO);
        }
        return warehouseDtos;
    }

    public void deleteWarehouse(Long warehouseId) {
        Warehouse warehouse = warehouseRepository.getById(warehouseId);
        warehouseRepository.delete(warehouse);
    }

    public List<Warehouse> getAllWarehouseByIds(Collection<Long> warehouseIds) {
       return warehouseRepository.getAllWarehouseByIds(warehouseIds);
    }

    public List<Warehouse> getAllWarehouseByCode(Collection<String> warehouseCodes){
        return warehouseRepository.findAllByWarehouseCode(warehouseCodes);
    }
    public List<Warehouse> addAllWarehouse(Collection<WarehouseDto> warehouseDtoList) {
    List<Warehouse> warehouses = mapWarehouseDtoToWarehouse(warehouseDtoList);
    return warehouseRepository.saveAll(warehouses);
    }

    private List<Warehouse> mapWarehouseDtoToWarehouse(Collection<WarehouseDto> warehouseDtoList){
        List<Warehouse> warehouses = new ArrayList<>();
        for(WarehouseDto warehouseDto:warehouseDtoList){
            warehouses.add(mapWarehouseDtoToEntity(warehouseDto));
        }
        return warehouses;
    }
}
