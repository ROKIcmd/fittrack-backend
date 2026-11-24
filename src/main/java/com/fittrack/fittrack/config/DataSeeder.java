package com.fittrack.fittrack.config;

import com.fittrack.fittrack.model.Member;
import com.fittrack.fittrack.model.Trainer;
import com.fittrack.fittrack.repository.DataStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {
    @Override
    public void run(String... args) {
        var users = DataStore.getInstance().users();

        if (users.findById("M001").isEmpty()) {
            users.save(new Member("M001", "Juan", "Dela Cruz", "juandelacruz@gmail.com", "password123",
                    LocalDate.of(1998, 5, 12), "Male", "Build muscle", 1.75, 75.0));
        }
        if (users.findById("T001").isEmpty()) {
            users.save(new Trainer("T001", "John", "Smith", "johnsmith@fittrack.com", "password123",
                    "BS Exercise Science", "Strength & Conditioning", 5, "Let's get stronger."));
        }
    }
}