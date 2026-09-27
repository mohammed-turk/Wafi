package com.example.wafi.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subscriptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int not null")
    @Positive
    @NotNull
    private Integer userId;

    @Column(columnDefinition = "varchar(20) not null")
    @Size(max = 20)
    @NotNull
    private String providerName;

    @Column(columnDefinition = "varchar(30) not null")
    @NotNull
    @Size(max = 30)
    private String planName;

    @Column(columnDefinition = "varchar(10) not null")
    @Check(constraints = "category='compo' or category='data' or category = 'minutes'")
    @NotNull
    @Pattern(regexp = "data|minutes|compo", message = "category must be one of: data, minutes, compo")
    private String category;

    @Column(columnDefinition = "double not null")
    @NotNull
    private Double charge;

    @Column(columnDefinition = "double")
    private Double dataAllowanceGb;

    @Column(columnDefinition = "double")
    private Double minutesAllowance;

    @Column(columnDefinition = "double")
    private Double smsAllowance;

    @Column(columnDefinition = "varchar(15) not null")
    @NotNull
    @Pattern(regexp = "DAY|WEEK|MONTH|THREE_MONTHS|SIX_MONTHS|YEAR",
            message = "period must be one of: DAY, WEEK, MONTH, THREE_MONTHS, SIX_MONTHS, YEAR")
    private String period;

    @Column(columnDefinition = "date not null")
    @NotNull
    @PastOrPresent(message = "startDate cannot be in the future")
    private LocalDate startDate;

    // endDate calculated in service layer from startDate + period

    @Column(columnDefinition = "varchar(7) not null")
    @NotNull
    @Pattern(regexp = "ACTIVE|EXPIRED", message = "status must be ACTIVE or EXPIRED")
    private String status;

    @Column(columnDefinition = "boolean not null")
    @NotNull
    private Boolean autoRenew;

    @Column(columnDefinition = "varchar(1000)")
    private String message;

    @Column(columnDefinition = "varchar(1000)")
    private String aiSuggestion;

    private Boolean sent;

}