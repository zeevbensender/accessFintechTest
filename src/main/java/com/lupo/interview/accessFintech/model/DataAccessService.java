package com.lupo.interview.accessFintech.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DataAccessService {
    private static final Logger LOG = LoggerFactory.getLogger(DataAccessService.class);
    private final StockRepository repo;
    private final boolean cleanUponRestart;

    public DataAccessService(@Autowired StockRepository repo, @Value("${server.clean.db.upon.restart}") boolean cleanUponRestart) {
        this.repo = repo;
        this.cleanUponRestart = cleanUponRestart;
    }

    @PostConstruct
    public void init() {
        if(cleanUponRestart)
            repo.deleteAll();
    }

    public void saveOrUpdateStock(Stock stock) {
            Optional<Stock> optional = getStock(stock.getName());
            if(!optional.isPresent()) {
                LOG.info("About to save new  stock data: {}: price {})",
                        stock.getName(), stock.getPrice());
                repo.save(stock);
            } else if(stock.getPrice() < optional.get().getPrice()) {
                LOG.info("About to update stock: {} (old price: {}, new price {})",
                        stock.getName(), optional.get().getPrice(), stock.getPrice());
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
