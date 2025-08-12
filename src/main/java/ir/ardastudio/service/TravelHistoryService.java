package ir.ardastudio.service;

import ir.ardastudio.model.Travel;
import ir.ardastudio.repository.TravelRepository;
import ir.ardastudio.shared.Screen;

import java.sql.SQLException;
import java.util.List;

public class TravelHistoryService {
    private final TravelRepository repo = new TravelRepository();

    public void showHistory() {
        try {
            Screen.clear();
            List<Travel> travels = repo.getAllTravels();
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
