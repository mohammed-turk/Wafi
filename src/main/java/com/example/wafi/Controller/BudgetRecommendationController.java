package com.example.wafi.Controller;

import com.example.wafi.API.APIResponse;
import com.example.wafi.Service.BudgetRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/budget-recommendation")
@RequiredArgsConstructor
public class BudgetRecommendationController {
    private final BudgetRecommendationService budgetRecommendationService;

    @GetMapping("/{budget}/{category}")
    public ResponseEntity<?> recommend(@PathVariable Double budget, @PathVariable String category){
        String normalized = category.toLowerCase();
        if (!normalized.equals("data") && !normalized.equals("minutes") && !normalized.equals("compo"))
            return ResponseEntity.status(400).body(new APIResponse("category must be data, minutes, or compo"));

        String recommendation = budgetRecommendationService.recommend(budget, normalized);
        return ResponseEntity.status(200).body(new APIResponse(recommendation));
    }
}