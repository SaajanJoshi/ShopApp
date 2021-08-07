package com.store.storeapp.billling_report;

import com.store.storeapp.billed_product.BilledProductService;
import com.store.storeapp.billling_report.entity.BillingReport;
import com.store.storeapp.billling_report.repository.BillingReportRepository;
import com.store.storeapp.dto.BillDto;
import com.store.storeapp.dto.BillProductDto;
import com.store.storeapp.dto.BillReportDto;
import com.store.storeapp.product.ProductService;
import com.store.storeapp.product.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BillingService {
    @Autowired
    ProductService productService;
    @Autowired
    BillingReportRepository billingReportRepository;
    @Autowired
    BilledProductService billedProductService;
    private Logger logger = LoggerFactory.getLogger(BillingService.class);
    public void addBill(BillDto billDto) {
        BillingReport billingReport = mapBillDtoToBillingReport(billDto);
        billingReport = billingReportRepository.save(billingReport);
        addBillProduct(billingReport.getId(), billDto.getProductDtoList());
    }

    private void addBillProduct(Long id, List<BillProductDto> productDtoList) {
        billedProductService.addBilledProduct(id, productDtoList);
    }

    private BillingReport mapBillDtoToBillingReport(BillDto billDto) {
        BillingReport billingReport = new BillingReport();

        billingReport.setCustomerName(billDto.getCustomerName());
        billingReport.setDiscountPercentage(billDto.getDiscount());

        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal totalQuantity = BigDecimal.ZERO;
        List<Long> prodId =  billDto.getProductDtoList()
                .stream()
                .filter(b -> b.getPrice() == null || b.getPrice().equals(BigDecimal.ZERO))
                .map(BillProductDto::getProdId)
                .collect(Collectors.toList());

        List<Product> productList = new ArrayList<>();

        if (prodId != null || !prodId.isEmpty()) {
            productList = productService.getAllWithProdids(prodId);
        }

        Map<Long, BigDecimal> prodToPriceMap = productList
                .stream()
                .collect(Collectors.toMap(Product::getId, Product::getProductSellingPrice));

        for (BillProductDto billProductDto : billDto.getProductDtoList()) {
            BigDecimal price = billProductDto.getPrice() != null || billProductDto.getPrice().equals(BigDecimal.ZERO)
                    ? billProductDto.getPrice() : prodToPriceMap.get(billProductDto.getProdId());
            totalPrice = totalPrice.add(price);
            totalQuantity = totalQuantity.add(billProductDto.getQuantity());
            billProductDto.setPrice(price);
        }
        billingReport.setTotalPrice(totalPrice);
        billingReport.setTotalQuantity(totalQuantity);

        return billingReport;
    }

    public List<BillReportDto> getReport(Map<String,Date> requestDateParam) {
        List<BillingReport> billingReports;
        if(!requestDateParam.isEmpty()){
            Date fromDate = requestDateParam.get("fromDate");
            Date toDate = requestDateParam.get("toDate");
            billingReports = billingReportRepository.getReports(fromDate,toDate);
        } else {
            billingReports = billingReportRepository.findAll();
        }

        return mapBillingReportToDto(billingReports);
    }

    private List<BillReportDto> mapBillingReportToDto(List<BillingReport> billingReports) {
        List<BillReportDto> billReportDtos = new ArrayList<>();

        for(BillingReport billingReport:billingReports){
            BillReportDto billReportDto = new BillReportDto();
            billReportDto.setSelfId(billingReport.getId());
            billReportDto.setTotalPrice(billingReport.getTotalPrice());
            billReportDto.setDiscount(billingReport.getDiscountPercentage());
            billReportDto.setCustomerName(billingReport.getCustomerName());
            billReportDto.setTotalQuantity(billingReport.getTotalQuantity());
            billReportDtos.add(billReportDto);
        }
        return  billReportDtos;
    }
}
