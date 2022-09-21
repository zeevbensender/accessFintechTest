package com.lupo.interview.accessFintech.web;

import com.lupo.interview.accessFintech.model.DataAccessService;
import com.lupo.interview.accessFintech.model.Stock;
import com.lupo.interview.accessFintech.model.StockNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainConnector {
    private static final Logger LOG = LoggerFactory.getLogger(MainConnector.class);
    private final DataAccessService dataAccessService;


    public MainConnector(@Autowired DataAccessService dataAccessService) {
        this.dataAccessService = dataAccessService;
    }

    @GetMapping("/hello")
    public String hello(){
        return "HELLO";
    }

    @GetMapping("/lowest")
    public ResponseEntity<String> getLowestPrice(@RequestParam String stockId) {
        try {
            double price = dataAccessService.getStockPrice(stockId);
            return new ResponseEntity<>(String.valueOf(price), HttpStatus.OK);
        } catch (StockNotFoundException e) {
            String msg = String.format("Stock %s not found", stockId);
            LOG.warn(msg);
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Stock>> getAll() {
        return new ResponseEntity<>(dataAccessService.getAll(), HttpStatus.OK);
    }
}
