package com.example.wafi.Repository;

import com.example.wafi.Model.Subscriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionsRepository extends JpaRepository<Subscriptions, Integer> {
    Subscriptions findSubscriptionsById(Integer id);
    List<Subscriptions> findSubscriptionsByUserId(Integer userId);
    List<Subscriptions> findSubscriptionsByCategory(String category);
    List<Subscriptions> findSubscriptionsByStatus(String status);
    List<Subscriptions> findSubscriptionsByUserIdAndStatus(Integer userId, String status);
}