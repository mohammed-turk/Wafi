package com.example.wafi.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class IssueReports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int not null")
    @Positive(message = "user id must be positive")
    @NotNull(message = "user id can not be null")
    private Integer userId;

    @Column(columnDefinition = "int not null")
    @Positive(message = "subscription id must be positive")
    @NotNull(message = "subscription id can not be null")
    private Integer subscriptionId;

    @Column(columnDefinition = "varchar(1000) not null")
    @NotEmpty(message = "description can not be empty")
    @Size(max = 1000, message = "description max length is 1000")
    private String description;

    @Column(columnDefinition = "varchar(2000)")
    private String aiResponse;

    @Column(columnDefinition = "varchar(10) not null")
    @Pattern(regexp = "OPEN|RESOLVED", message = "status must be OPEN or RESOLVED")
    private String status;
}