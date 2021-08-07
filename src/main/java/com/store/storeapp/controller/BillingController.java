package com.store.storeapp.controller;

import com.store.storeapp.Api.Api;
import com.store.storeapp.billling_report.BillingService;
import com.store.storeapp.dto.BillDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(Api.ReportUrl)
public class BillingController {
    @Autowired
    BillingService billingService;

    @PostMapping("/addBill")
    public HttpEntity addBill(@RequestBody BillDto billDto){
        billingService.addBill(billDto);
        return new ResponseEntity(true, HttpStatus.OK);
    }

    @GetMapping("/getReport")
    public HttpEntity getReport(@RequestParam(required = false) Map<String, Date> requestDateParam){
        return new ResponseEntity(billingService.getReport(requestDateParam),HttpStatus.OK);
    }
}
