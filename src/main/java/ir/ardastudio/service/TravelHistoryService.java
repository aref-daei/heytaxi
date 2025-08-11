package ir.ardastudio.service;

import ir.ardastudio.model.Travel;
import ir.ardastudio.shared.Screen;

import java.util.List;

public class TravelHistoryService {
    public void showHistory(List<Travel> travels) {
        Screen.clear();
        if (travels.isEmpty()) {
            System.out.println("No travel history.");
            return;
        }
        System.out.println("..:: Travel History ::..");
        travels.forEach(t -> {
            System.out.println(t);
            System.out.println();
        });
    }
}
