package com.store.storeapp.billed_product;

import com.store.storeapp.billed_product.entity.BilledProduct;
import com.store.storeapp.billed_product.repository.BilledProductRepository;
import com.store.storeapp.dto.BillProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BilledProductService {
    @Autowired
    BilledProductRepository billedProductRepository;
    public void addBilledProduct(Long id, List<BillProductDto> productDtoList) {
        List<BilledProduct> billedProducts = mapBillProductDtoToBilledProduct(id,productDtoList);
        billedProductRepository.saveAll(billedProducts);
    }

    private List<BilledProduct> mapBillProductDtoToBilledProduct(Long id, List<BillProductDto> productDtoList) {
        List<BilledProduct> billedProducts = new ArrayList<>();

        for(BillProductDto billProductDto:productDtoList){
            BilledProduct billedProduct = new BilledProduct();
            billedProduct.setProdId(billProductDto.getProdId());
            billedProduct.setBillingReportId(id);
            billedProduct.setWarehouseId(billProductDto.getWarehouseId());
            billedProduct.setPrice(billProductDto.getPrice());
            billedProduct.setUnit(billedProduct.getUnit());
            billedProduct.setQuantity(billedProduct.getQuantity());
            billedProducts.add(billedProduct);
        }
        return billedProducts;
    }
}
