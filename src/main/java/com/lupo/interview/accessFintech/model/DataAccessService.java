package com.lupo.interview.accessFintech.model;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataAccessService {
    private static final Logger LOG = LoggerFactory.getLogger(DataAccessService.class);
    private final StockRepository repo;
    Gson gson = new Gson();

    public DataAccessService(@Autowired StockRepository repo) {
        this.repo = repo;
    }

    public void saveOrUpdateStock(Stock stock) {
            Stock oldStock = getStock(stock.getName()).get();
            if(oldStock == null || stock.getPrice() < oldStock.getPrice()) {
                LOG.info("About to update stock: {} (old price: {}, new price {})",
                        stock.getName(), oldStock.getPrice(), stock.getPrice());
                repo.save(stock);
            }
    }

    private Optional<Stock> getStock(String stockId) {
        return repo.findById(stockId);
    }

    public double getStockPrice(String stockId) throws StockNotFoundException {
        return getStock(stockId)
                .orElseThrow(StockNotFoundException::new)
                .getPrice();
    }

    public List<Stock> getAll() {
        List<Stock> res = new ArrayList<>();
        repo.findAll().forEach(res::add);
        return res;
    }
}