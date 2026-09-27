# Wafi

AI-powered telecom subscription management backend, built with Spring Boot.

Wafi centralizes a user's telecom subscriptions across providers, proactively
warns before a plan expires (with an AI-suggested competitor alternative),
gives an AI-driven first response when a subscriber reports an issue, and
classifies users (VIP, regular, at-risk, inactive) based on their
subscription and issue history.

## Why

Telecom subscribers often juggle multiple plans across providers with no
unified visibility, no warning before a bad renewal, and no fast path to
resolution when something goes wrong. Wafi was built to close that gap.

## Core Features

1. **Subscription Tracking** — CRUD for subscriptions across providers, categories, and billing periods.
2. **Expiry Alerts + AI Competitor Suggestion** — a scheduled daily check (and an on-demand endpoint) finds subscriptions nearing expiry, asks Gemini for a real competitor plan suggestion, and delivers it via WhatsApp (Green API) with an email fallback.
3. **AI Issue Resolver** — when a user reports an issue, Gemini analyzes it in the context of the related subscription and returns a practical suggestion or flags it for human support.
4. **Budget-Based Recommender** — given a budget and category, Gemini suggests a real matching plan.
5. **Smart User Classification** — JPQL-driven queries classify users as VIP (3+ active subs, 200+ SAR spend), at-risk (2+ open issues), inactive (0 active subs), or regular.

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot, Spring Web, Spring Data JPA |
| Database | MySQL |
| AI | Google Gemini API |
| Messaging | WhatsApp (Green API), Gmail SMTP |
| Testing | Postman (seeded demo collection) |

## Getting Started

### Prerequisites

- Java 17
- Maven
- MySQL running locally
- A Gemini API key ([Google AI Studio](https://aistudio.google.com/apikey))
- A Green API instance for WhatsApp ([green-api.com](https://green-api.com))
- A Gmail account with an [app password](https://myaccount.google.com/apppasswords) for SMTP

### Setup

1. Clone the repo:
   ```bash
   git clone https://github.com/<your-username>/wafi.git
   cd wafi
   ```
2. Create the database:
   ```sql
   CREATE DATABASE WafiDB;
   ```
3. Copy the example config and fill in your own credentials:
   ```bash
   cp application.properties.example src/main/resources/application.properties
   ```
4. Run the app:
   ```bash
   ./mvnw spring-boot:run
   ```
   Tables are created automatically (`ddl-auto=update`).

### API Overview

Base path: `/api/v1`

| Resource | Endpoints |
|---|---|
| `/user` | add, get, update/{id}, delete/{id}, subscriptions-count/{id}, issue-reports/{id}, vip, regular, at-risk, inactive |
| `/subscription` | add, get, update/{id}, delete/{id}, user/{id}, total-spend/{id}, activate/{id}, expire/{id}, category/{category} |
| `/issue-report` | add, get, get/{id}, update/{id}, delete/{id}, status/{status}, subscription/{id}, resolve/{id}, resolve-ai/{id}, open-count |
| `/subscription-expiry` | check, notify/{subscriptionId} |
| `/budget-recommendation` | {budget}/{category} |

A full Postman collection with seed data and a guided demo flow is included for testing all endpoints end to end.

## Project Structure

```
src/main/java/com/example/wafi/
├── API/            # Shared response wrapper
├── Controller/      # REST controllers
├── Model/           # JPA entities
├── Repository/      # Spring Data repositories (JPQL + derived queries)
└── Service/         # Business logic, AI integration, notifications
```

## Author

Mohammed Turki — Software Engineering, KFUPM
