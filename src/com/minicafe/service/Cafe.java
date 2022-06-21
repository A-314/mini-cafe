package com.minicafe.service;

import com.minicafe.job.CashboxStatsJob;
import com.minicafe.job.WinnerJob;
import com.minicafe.model.Order;
import com.minicafe.job.BuyerStatsJob;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import static com.minicafe.util.CafeConst.*;

public class Cafe extends Thread {

    private final ScheduledExecutorService executorService;
    private final List<Buyer> buyers;
    private final List<Cashbox> cashboxes;
    private final BlockingQueue<Order> allOrders;

    public Cafe(int buyersNumber, int cashboxesNumber){
        this.executorService = Executors.newScheduledThreadPool(3);
        this.allOrders = new ArrayBlockingQueue<>(10);
        this.buyers = createBuyers(buyersNumber);
        this.cashboxes = createCashboxes(cashboxesNumber);
        initShutdownHook();
    }

    @Override
    public void run(){
        buyers.forEach(buyer -> new Thread(buyer).start());
        cashboxes.forEach(cashbox -> new Thread(cashbox).start());

        var buyerStatsPath = Path.of("resources", "buers-stats.com.minicafe.csv");
        executorService.scheduleAtFixedRate(new BuyerStatsJob(buyers, buyerStatsPath), BUYER_STATS_JOB_PERIOD, BUYER_STATS_JOB_PERIOD, TimeUnit.SECONDS);

        var cashboxStatsPath = Path.of("resources", "cashboxes-stats.com.minicafe.csv");
        executorService.scheduleAtFixedRate(new CashboxStatsJob(cashboxes, cashboxStatsPath), CASHBOX_STATS_JOB_PERIOD, CASHBOX_STATS_JOB_PERIOD, TimeUnit.SECONDS);

        executorService.scheduleAtFixedRate(new WinnerJob(buyerStatsPath, cashboxStatsPath), WINNER_JOB_PERIOD,WINNER_JOB_PERIOD, TimeUnit.SECONDS);
    }

    private List<Buyer> createBuyers(int buyersNumber) {
        return IntStream.range(0,buyersNumber)
                .mapToObj(i -> new Buyer(allOrders))
                .toList();
    }

    private void initShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown initiated...");
            executorService.shutdownNow();
            System.out.println("Shutdown finished successfully");
        }));
    }

    private List<Cashbox> createCashboxes(int cashboxesNumber) {
        return IntStream.range(0,cashboxesNumber)
                .mapToObj(i -> new Cashbox(allOrders))
                .toList();
    }
}
