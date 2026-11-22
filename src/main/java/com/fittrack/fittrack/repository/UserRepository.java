package com.fittrack.fittrack.repository;

import com.fittrack.fittrack.model.Member;
import com.fittrack.fittrack.model.Trainer;
import com.fittrack.fittrack.model.User;
import java.util.List;
import java.util.Optional;

public class UserRepository extends FileRepository<User> {

    public UserRepository() {
        super("data/users.dat", User::getId);
    }

    public Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public List<Member> findAllMembers() {
        return findAll().stream()
                .filter(u -> u instanceof Member)
                .map(u -> (Member) u)
                .toList();
    }

    public List<Trainer> findAllTrainers() {
        return findAll().stream()
                .filter(u -> u instanceof Trainer)
                .map(u -> (Trainer) u)
                .toList();
    }

    public List<Member> findMembersByTrainer(String trainerId) {
        return findAllMembers().stream()
                .filter(m -> trainerId.equals(m.getAssignedTrainerId()))
                .toList();
    }
}