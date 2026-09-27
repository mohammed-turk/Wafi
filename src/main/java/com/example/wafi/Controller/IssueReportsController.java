package com.example.wafi.Controller;

import com.example.wafi.API.APIResponse;
import com.example.wafi.Model.IssueReports;
import com.example.wafi.Service.IssueReportsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/issue-report")
@RequiredArgsConstructor
public class IssueReportsController {
    private final IssueReportsService issueReportsService;

    @GetMapping("/get")
    public ResponseEntity<?> get(){
        List<IssueReports> issueReports = issueReportsService.get();
        if (issueReports.isEmpty())
            return ResponseEntity.status(200).body(new APIResponse("no issue reports exist"));
        return ResponseEntity.status(200).body(issueReports);
    }

    @GetMapping("/get/{issueReportId}")
    public ResponseEntity<?> getOne(@PathVariable Integer issueReportId){
        IssueReports issueReport = issueReportsService.getOne(issueReportId);
        if (issueReport == null)
            return ResponseEntity.status(400).body(new APIResponse("no such issue report exist"));
        return ResponseEntity.status(200).body(issueReport);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid IssueReports issueReport, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        APIResponse error = issueReportsService.add(issueReport);
        if (error != null)
            return ResponseEntity.status(400).body(error);

        return ResponseEntity.status(200).body(new APIResponse("issue report added successfully"));
    }

    @PutMapping("/update/{issueReportId}")
    public ResponseEntity<?> update(@PathVariable Integer issueReportId, @RequestBody @Valid IssueReports issueReport, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        APIResponse error = issueReportsService.update(issueReportId, issueReport);
        if (error != null)
            return ResponseEntity.status(400).body(error);

        return ResponseEntity.status(200).body(new APIResponse("issue report updated successfully"));
    }

    @DeleteMapping("/delete/{issueReportId}")
    public ResponseEntity<?> delete(@PathVariable Integer issueReportId){
        APIResponse error = issueReportsService.delete(issueReportId);
        if (error != null)
            return ResponseEntity.status(400).body(error);

        return ResponseEntity.status(200).body(new APIResponse("issue report deleted successfully"));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getByStatus(@PathVariable String status){
        List<IssueReports> reports = issueReportsService.getByStatus(status.toUpperCase());
        if (reports.isEmpty())
            return ResponseEntity.status(200).body(new APIResponse("no issue reports with this status"));
        return ResponseEntity.status(200).body(reports);
    }

    @GetMapping("/subscription/{subscriptionId}")
    public ResponseEntity<?> getBySubscription(@PathVariable Integer subscriptionId){
        List<IssueReports> reports = issueReportsService.getBySubscription(subscriptionId);
        if (reports.isEmpty())
            return ResponseEntity.status(200).body(new APIResponse("no issue reports for this subscription"));
        return ResponseEntity.status(200).body(reports);
    }

    @PutMapping("/resolve/{issueReportId}")
    public ResponseEntity<?> resolve(@PathVariable Integer issueReportId){
        boolean resolved = issueReportsService.resolveIssue(issueReportId);
        if (!resolved)
            return ResponseEntity.status(400).body(new APIResponse("no such issue report exist"));
        return ResponseEntity.status(200).body(new APIResponse("issue report resolved successfully"));
    }

    @GetMapping("/open-count")
    public ResponseEntity<?> getOpenCountPerUser(){
        List<Map<String, Object>> counts = issueReportsService.getOpenCountPerUser();
        if (counts.isEmpty())
            return ResponseEntity.status(200).body(new APIResponse("no open issue reports"));
        return ResponseEntity.status(200).body(counts);
    }

    @PostMapping("/resolve-ai/{issueReportId}")
    public ResponseEntity<?> resolveWithAi(@PathVariable Integer issueReportId){
        String aiResponse = issueReportsService.resolveWithAi(issueReportId);
        if (aiResponse == null)
            return ResponseEntity.status(400).body(new APIResponse("no such issue report exist"));
        return ResponseEntity.status(200).body(new APIResponse(aiResponse));
    }
}