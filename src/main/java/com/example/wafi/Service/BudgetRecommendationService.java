package com.example.wafi.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BudgetRecommendationService {
    private final AIService aiService;

    public String recommend(Double budget, String category) {
        String prompt = String.format(
                "A user in Saudi Arabia wants a telecom plan under %.2f SAR per month, " +
                        "in the %s category (data, minutes, or compo). Suggest one specific real plan " +
                        "and provider (STC, Mobily, Zain, or Virgin Mobile) that fits this budget. " +
                        "Reply in 2-3 short sentences, no markdown.",
                budget, category
        );

        return aiService.callAi(prompt);
    }
}