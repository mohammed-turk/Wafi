package com.example.wafi.Repository;

import com.example.wafi.Model.IssueReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueReportsRepository extends JpaRepository<IssueReports, Integer> {
    IssueReports findIssueReportsById(Integer id);
    List<IssueReports> findIssueReportsByStatus(String status);
    List<IssueReports> findIssueReportsBySubscriptionId(Integer subscriptionId);
    List<IssueReports> findIssueReportsByUserId(Integer userId);
}