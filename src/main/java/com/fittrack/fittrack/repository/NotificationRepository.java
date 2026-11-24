package com.fittrack.fittrack.repository;

import com.fittrack.fittrack.model.Notification;
import java.util.Comparator;
import java.util.List;

public class NotificationRepository extends FileRepository<Notification> {

    public NotificationRepository() {
        super("data/notifications.dat", Notification::getId);
    }

    public List<Notification> findByMemberId(String memberId) {
        return findAll().stream()
                .filter(n -> memberId.equals(n.getMemberId()))
                .sorted(Comparator.comparing(Notification::getCreatedAt).reversed())
                .toList();
    }
}