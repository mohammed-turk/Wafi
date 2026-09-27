package com.example.wafi.Service;

import com.example.wafi.API.APIResponse;
import com.example.wafi.Model.IssueReports;
import com.example.wafi.Model.Subscriptions;
import com.example.wafi.Model.Users;
import com.example.wafi.Repository.IssueReportsRepository;
import com.example.wafi.Repository.UsersRepository;
import com.example.wafi.Repository.SubscriptionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IssueReportsService {
    private final IssueReportsRepository issueReportsRepository;
    private final UsersRepository usersRepository;
    private final SubscriptionsRepository subscriptionsRepository;
    private final AIService aiService;

    public List<IssueReports> get(){
        return issueReportsRepository.findAll();
    }

    public IssueReports getOne(Integer issueReportId){
        return issueReportsRepository.findIssueReportsById(issueReportId);
    }

    public APIResponse add(IssueReports issueReport){
        if (usersRepository.findUsersById(issueReport.getUserId()) == null)
            return new APIResponse("no such user exist");

        if (subscriptionsRepository.findSubscriptionsById(issueReport.getSubscriptionId()) == null)
            return new APIResponse("no such subscription exist");

        issueReport.setStatus("OPEN");
        issueReportsRepository.save(issueReport);
        return null;
    }

    public APIResponse update(Integer issueReportId, IssueReports issueReport){
        IssueReports oldIssueReport = issueReportsRepository.findIssueReportsById(issueReportId);

        if (oldIssueReport == null)
            return new APIResponse("no such issue report exist");

        oldIssueReport.setDescription(issueReport.getDescription());
        issueReportsRepository.save(oldIssueReport);
        return null;
    }

    public APIResponse delete(Integer issueReportId){
        IssueReports issueReport = issueReportsRepository.findIssueReportsById(issueReportId);

        if (issueReport == null)
            return new APIResponse("no such issue report exist");

        issueReportsRepository.delete(issueReport);
        return null;
    }

    public Boolean resolveIssue(Integer issueReportId) {
        IssueReports issue = issueReportsRepository.findIssueReportsById(issueReportId);
        if (issue == null)
            return false;

        issue.setStatus("RESOLVED");
        issueReportsRepository.save(issue);
        return true;
    }

    public List<IssueReports> getByStatus(String status){
        return issueReportsRepository.findIssueReportsByStatus(status);
    }

    public List<IssueReports> getBySubscription(Integer subscriptionId){
        return issueReportsRepository.findIssueReportsBySubscriptionId(subscriptionId);
    }

    public List<Map<String, Object>> getOpenCountPerUser() {
        List<IssueReports> openIssues = issueReportsRepository.findIssueReportsByStatus("OPEN");
        Map<Integer, Long> counts = new HashMap<>();
        for (IssueReports i : openIssues) {
            counts.merge(i.getUserId(), 1L, Long::sum);
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<Integer, Long> entry : counts.entrySet()) {
            Users user = usersRepository.findUsersById(entry.getKey());
            Map<String, Object> row = new HashMap<>();
            row.put("userId", entry.getKey());
            row.put("userName", user != null ? user.getName() : "unknown");
            row.put("openIssueCount", entry.getValue());
            result.add(row);
        }
        return result;
    }

    public String resolveWithAi(Integer issueReportId) {
        IssueReports issue = issueReportsRepository.findIssueReportsById(issueReportId);
        if (issue == null) return null;

        Subscriptions sub = subscriptionsRepository.findSubscriptionsById(issue.getSubscriptionId());
        String planContext = sub != null
                ? String.format("%s plan from %s (%s category, status: %s)",
                sub.getPlanName(), sub.getProviderName(), sub.getCategory(), sub.getStatus())
                : "an unspecified plan";

        String prompt = String.format(
                "A telecom subscriber in Saudi Arabia reported this issue: \"%s\" " +
                        "(related to a %s). Give a short, practical troubleshooting suggestion " +
                        "or explanation in 2-3 sentences. If it likely needs human support, say so plainly. No markdown.",
                issue.getDescription(), planContext
        );

        String aiResponse = aiService.callAi(prompt);
        issue.setAiResponse(aiResponse);
        issueReportsRepository.save(issue);
        return aiResponse;
    }
}