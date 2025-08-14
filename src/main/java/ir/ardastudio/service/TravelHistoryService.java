package ir.ardastudio.service;

import ir.ardastudio.model.Travel;
import ir.ardastudio.repository.TravelRepository;
import ir.ardastudio.shared.Screen;

import java.sql.SQLException;
import java.util.List;

public class TravelHistoryService {
    private final TravelRepository travelRepo = new TravelRepository();

    public void showHistory() {
        try {
            Screen.clear();
            List<Travel> travels = travelRepo.getAllTravels();
            if (travels.isEmpty()) {
                System.out.println("No travel history.");
                return;
            }
            System.out.println("..:: Travel History ::..");
            travels.forEach(t -> {
                System.out.println(t);
                System.out.println();
            });
        } catch (SQLException e) {
            System.err.println("Error fetching travel history: " + e.getMessage());
        }
    }
}
