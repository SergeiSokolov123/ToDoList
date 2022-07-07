package main;

import main.model.Deal;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Storage {
    private static AtomicInteger currentId = new AtomicInteger(1);
    private static final ConcurrentHashMap<Integer, Deal> deals = new ConcurrentHashMap<>();

    public static List<Deal> getAllDeals() {
        ArrayList<Deal> dealsList = new ArrayList<>(deals.values());
        return dealsList;
    }

    public static int addDeal(Deal deal) {
        int id = currentId.incrementAndGet();
        deal.setId(id);
        deals.put(id, deal);
        return id;
    }

    public static Deal getDeal(int dealId) {
        if (deals.containsKey(dealId)) {
            return deals.get(dealId);
        }
        return null;
    }

    public static void deleteDeal(int dealId){
        deals.remove(dealId);
    }

    public static void clearList(){
        deals.clear();
    }

    public static Deal updateDeal(int dealId, Deal newDeal){
        deals.get(dealId).setText(newDeal.getText());
        deals.get(dealId).setDate(newDeal.getDate());
        deals.get(dealId).setReadiness(newDeal.isReadiness());
        return deals.get(dealId);
    }

    public static ArrayList<Deal> updateAll(Deal newDeal){
        ArrayList<Deal> allDeals = new ArrayList<>(deals.values());
        for (Deal deal : allDeals){
            deal.setText(newDeal.getText());
            deal.setDate(newDeal.getDate());
            deal.setReadiness(newDeal.isReadiness());
        }
        return allDeals;
    }
}
