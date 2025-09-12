package ir.ardastudio.service;

import ir.ardastudio.model.Travel;
import ir.ardastudio.model.User;
import ir.ardastudio.repository.TravelRepository;
import ir.ardastudio.shared.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class TravelHistoryService {
    private static final Logger logger = LoggerFactory.getLogger(TravelHistoryService.class);

    private final TravelRepository travelRepo = new TravelRepository();

    public void showHistory(User user) {
        try {
            Screen.clear();
            List<Travel> travels = travelRepo.getAllTravels().stream()
                    .filter(t -> t.getUser().getId().equals(user.getId()))
                    .toList();
            if (travels.isEmpty()) {
                System.out.println("No travel history");
                return;
            }
            System.out.println("..:: Travel History ::..");
            travels.forEach(t -> {
                System.out.println(t);
                System.out.println();
            });
        } catch (SQLException e) {
            logger.error("Error fetching travel history: ", e);
        }
    }
}
