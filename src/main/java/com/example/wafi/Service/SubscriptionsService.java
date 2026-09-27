package com.example.wafi.Service;

import com.example.wafi.Model.Subscriptions;
import com.example.wafi.Repository.SubscriptionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionsService {
    private final SubscriptionsRepository subscriptionsRepository;

    public List<Subscriptions> get(){
        return subscriptionsRepository.findAll();
    }

    public void add(Subscriptions subscription){
        subscription.setStatus("ACTIVE");
        subscription.setSent(false);
        subscriptionsRepository.save(subscription);
    }

    public Boolean update(Integer subscriptionId, Subscriptions subscription){
        Subscriptions oldSubscription = subscriptionsRepository.findSubscriptionsById(subscriptionId);

        if (oldSubscription == null)
            return false;

        oldSubscription.setAutoRenew(subscription.getAutoRenew());
        oldSubscription.setCategory(subscription.getCategory());
        oldSubscription.setCharge(subscription.getCharge());
        oldSubscription.setDataAllowanceGb(subscription.getDataAllowanceGb());
        oldSubscription.setMinutesAllowance(subscription.getMinutesAllowance());
        oldSubscription.setPeriod(subscription.getPeriod());
        oldSubscription.setPlanName(subscription.getPlanName());
        oldSubscription.setProviderName(subscription.getProviderName());
        oldSubscription.setSmsAllowance(subscription.getSmsAllowance());
        oldSubscription.setStartDate(subscription.getStartDate());

        subscriptionsRepository.save(oldSubscription);
        return true;
    }

    public Boolean delete(Integer subscriptionId){
        Subscriptions desiredSubscription = subscriptionsRepository.findSubscriptionsById(subscriptionId);

        if (desiredSubscription == null)
            return false;

        subscriptionsRepository.delete(desiredSubscription);
        return true;
    }

    public List<Subscriptions> getUserSubscriptions(Integer userId) {
        return subscriptionsRepository.findSubscriptionsByUserId(userId);
    }

    public Double getTotalSpend(Integer userId) {
        List<Subscriptions> activeSubs = subscriptionsRepository.findSubscriptionsByUserIdAndStatus(userId, "ACTIVE");
        double total = 0;
        for (Subscriptions s : activeSubs) {
            if (s.getCharge() != null) total += s.getCharge();
        }
        return total;
    }

    public Boolean activate(Integer subscriptionId) {
        Subscriptions subscription = subscriptionsRepository.findSubscriptionsById(subscriptionId);
        if (subscription == null) return false;

        subscription.setStatus("ACTIVE");
        subscriptionsRepository.save(subscription);
        return true;
    }

    public Boolean expire(Integer subscriptionId) {
        Subscriptions subscription = subscriptionsRepository.findSubscriptionsById(subscriptionId);
        if (subscription == null) return false;

        subscription.setStatus("EXPIRED");
        subscriptionsRepository.save(subscription);
        return true;
    }

    public List<Subscriptions> getByCategory(String category){
        String normalized = category.toLowerCase();
        if (!normalized.equals("data") && !normalized.equals("minutes") && !normalized.equals("compo"))
            return null;

        return subscriptionsRepository.findSubscriptionsByCategory(normalized);
    }

    public LocalDate calculateEndDate(Subscriptions sub) {
        LocalDate start = sub.getStartDate();
        return switch (sub.getPeriod()) {
            case "DAY" -> start.plusDays(1);
            case "WEEK" -> start.plusWeeks(1);
            case "MONTH" -> start.plusMonths(1);
            case "THREE_MONTHS" -> start.plusMonths(3);
            case "SIX_MONTHS" -> start.plusMonths(6);
            case "YEAR" -> start.plusYears(1);
            default -> start;
        };
    }
}