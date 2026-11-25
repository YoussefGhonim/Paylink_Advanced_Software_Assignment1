# PayLink | Phase 1 Requirements Pack

**Prepared by:** Yahia Ahmed  
**Original date:** 22 November 2026  
**Updated:** 25 November 2026

Based on PayLink Year Work Project - Student Brief (16 pages). Team implementation assumptions are listed at the end; they are not represented as instructor-approved decisions.

## 1. Problem summary

PayLink is an Egyptian digital-wallet and bill-payment backend serving customers and agents. Customers need to pay electricity, water, mobile and internet bills without visiting service offices. PayLink must calculate fees, check account restrictions, reserve wallet funds, contact billers, finalize confirmed payments, notify customers and reconcile its records with biller settlement data. Different billers use REST/JSON, SOAP-style XML, asynchronous callbacks or batch files, so integration details must not leak into core payment logic. Financial correctness is critical: duplicate requests must not charge twice, concurrent payments must not produce negative balances, and uncertain biller outcomes must keep money held until resolved. The team will deliver a modular, testable backend with auditable ledger movements, automated tests and a local CI/CD deployment.

## 2. Stakeholders and interested parties

| Stakeholder | Interest / interaction |
| --- | --- |
| Customers | Register, top up, view wallet, inquire and pay bills, receive results |
| Agents | Accept customer payments and receive commission; may provide cash top-ups |
| Administrators | Maintain billers, services, fees and limits |
| Support employees | Find transactions, block/unblock accounts, investigate failures and refund requests |
| Billers (ElectroCo, AquaCo, TopUpNet; optional NetCo) | Provide bills, accept payments, callbacks and settlement results |
| PayLink management | Reliability, revenue, operational reporting and trust |
| Finance / reconciliation staff | Match settlements, investigate discrepancies, track fees and commissions |
| Developers / QA | Implement and verify modules, hard cases and API contracts |
| DevOps / operations | Run containers, CI/CD, deployment, monitoring and rollback |
| Notification providers | Deliver simulated SMS, push and email messages |
| Auditors / regulators | Financial traceability and compliance oversight |

## 3. Functional requirements and acceptance criteria

All 28 original functional requirements are covered. Priorities: 19 Must, 7 Should, 2 Could. Each story retains its original FR identifier.

### US-01 | Customer registration [FR-1 • Must]

As a customer, I want to register to access PayLink.

Given valid registration data is submitted, when registration succeeds, then a basic customer account and zero-balance wallet are created.

Given a duplicate customer identifier is submitted, when registration is attempted, then the request is rejected without creating another account.

### US-02 | Verification upgrade [FR-2 • Should]

As a customer, I want to upgrade verification to access higher limits.

Given a basic customer completes an authorized verification upgrade, when the change is approved, then the account becomes verified.

Given a verified customer initiates a payment, when the daily limit is checked, then the 30,000 EGP verified-account limit applies.

### US-03 | Wallet balance and history [FR-3 • Must]

As a customer, I want to view available and held balances and transactions.

Given a customer requests wallet details, when the wallet is loaded, then available and held balances are returned separately.

Given the wallet has ledger activity, when history is requested, then recorded movements and references are displayed.

### US-04 | Wallet top-up [FR-4 • Must]

As a customer, I want to fund a wallet through simulated card or agent cash.

Given a valid simulated card top-up succeeds, when it is committed, then available funds increase and a ledger entry is created.

Given an authorized agent records a successful cash top-up, when it is committed, then available funds increase and the cash funding source is recorded.

### US-05 | Ledger accounting [FR-5 • Must]

As a finance employee, I want to trace all wallet movements.

Given a hold, capture, release, refund, top-up or commission occurs, when the operation commits, then corresponding immutable ledger entries are recorded.

Given a wallet balance is queried, when the balance is calculated, then it is derived from ledger entries, not overwritten directly.

### US-06 | Manage billers [FR-6 • Must]

As a administrator, I want to create, update and disable billers and services.

Given an authorized administrator submits valid biller/service data, when the request is processed, then the catalog is created or updated.

Given a service is disabled, when a new payment is attempted, then the payment is not accepted for that service.

### US-07 | Browse billers [FR-7 • Must]

As a customer, I want to see billers and required input fields.

Given the catalog is requested, when active services are listed, then the available billers and required fields are returned.

Given a service is selected, when its details are requested, then the required meter, phone or account identifier fields are displayed.

### US-08 | Bill inquiry and quote [FR-8 • Must]

As a customer, I want to review amount, fee and total before paying.

Given valid inquiry fields are provided for ElectroCo or AquaCo, when inquiry succeeds, then bill amount, fee and total are displayed.

Given a TopUpNet top-up is requested, when the customer enters an allowed amount, then a fee and total are quoted without assuming a bill-amount inquiry [proposed].

### US-09 | Pay a bill [FR-9 • Must]

As a customer, I want to pay a bill from wallet funds.

Given funds and fraud checks pass, when payment is confirmed, then funds are held before a biller call.

Given a biller confirms success, when the result is processed, then payment becomes Completed, held funds are captured, fee income is recorded and applicable commission is recorded.

Given AquaCo refuses payment, when the refusal is processed, then payment becomes Failed, its hold is released via Refunded and an SMS with a clear reason is requested.

### US-10 | Idempotent payment [FR-10 • Must]

As a customer, I want to avoid duplicate charges.

Given the same payment request and key are submitted twice, when the duplicate arrives, then the original result is returned and no new biller payment or wallet charge is made.

Given the same key is reused with different details, when the request is checked, then the conflict is rejected [proposed].

### US-11 | Payment state machine [FR-11 • Must]

As a payment operator, I want to enforce only valid state changes.

Given a transition is requested, when the state machine validates it, then only the transitions listed in section 4 of this pack are accepted.

Given a payment is Completed, Rejected or Refunded, when another transition is requested, then it is rejected because the state is final.

Given fraud checks fail or funds are insufficient in Initiated, when validation finishes, then the payment becomes Rejected without a biller call.

### US-12 | Funds hold and release [FR-12 • Must]

As a customer, I want to avoid losing money on failed payments.

Given a valid payment has sufficient funds, when processing starts, then total amount moves from available to held before the biller call.

Given two concurrent payments compete for funds covering only one, when both attempt to reserve funds, then one reservation succeeds, the other payment is Rejected, and available balance never becomes negative.

Given a payment is confirmed failed, when the hold is released, then the payment reaches Refunded and the available balance is restored.

Given a payment remains Unknown, when its outcome is not confirmed, then funds remain held.

### US-13 | Timeout and retry [FR-13 • Must]

As a customer, I want to track uncertain payments safely.

Given ElectroCo takes 12 seconds to answer, when the 10-second timeout is reached, then payment becomes Unknown, funds remain held and the customer is told processing is pending.

Given an Unknown payment is due for re-query, when the configured 1, 5 or 15 minute retry occurs, then the biller status is checked up to three times.

Given a late callback, retry or settlement confirms payment, when the result is validated, then Unknown becomes Completed exactly once.

Given a later refusal or conclusive reconciliation establishes non-receipt, when the result is validated, then Unknown becomes Failed and the hold can then be released.

Given all retries are exhausted with no conclusive outcome, when processing continues, then the payment waits for reconciliation and is not automatically refunded.

### US-14 | TopUpNet callbacks [FR-14 • Must]

As a payment operator, I want to process asynchronous outcomes once.

Given a valid TopUpNet success or failure callback arrives, when the payment is awaiting a result, then the corresponding allowed state transition is applied.

Given the same success callback arrives twice, when the second is processed, then no duplicate capture occurs and the duplicate is logged.

### US-15 | Multiple biller adapters [FR-15 • Must]

As a integration engineer, I want to support different biller protocols.

Given ElectroCo, AquaCo or TopUpNet is selected, when the request is sent, then the correct REST/JSON, XML/HTTP or asynchronous adapter is used.

Given AquaCo is asked to pay more than the bill amount, when the mock biller processes the request, then it refuses and the normalized error is returned.

Given a new biller is added, when its integration and configuration are introduced, then no existing business module requires modification.

Given a biller response arrives, when the adapter handles it, then a normalized internal result is produced without changing core payment logic.

### US-16 | Configurable fees [FR-16 • Must]

As a customer, I want to see accurate charges.

Given electricity or water is selected, when fees are calculated, then the fee is 2.50 EGP.

Given mobile top-up is selected, when fees are calculated, then the fee is 1.5% of amount with a 1 EGP minimum.

Given internet payment is selected, when fees are calculated, then the fee is 2 EGP up to 200 EGP and 4 EGP above 200 EGP.

Given a configured promotion applies to a chosen biller and date, when fees are calculated, then the fee is zero.

### US-17 | Agent commission [FR-17 • Should]

As a agent, I want to pay on behalf of customers and earn commission.

Given an authorized agent initiates a payment for a customer, when the payment is created, then the agent identifier is linked to that payment.

Given an agent-assisted payment completes, when commission is posted, then 40% of the service fee is credited to the agent wallet.

Given an agent-assisted payment fails, when the result is finalized, then no completion commission is paid.

### US-18 | Payment limits [FR-18 • Must]

As a payment operator, I want to reject payments outside limits.

Given the bill amount is below 5 or above 10,000 EGP, when validation runs, then payment is Rejected with a clear reason.

Given a basic account would exceed 5,000 EGP daily, when validation runs, then payment is Rejected.

Given a verified account would exceed 30,000 EGP daily, when validation runs, then payment is Rejected.

### US-19 | Velocity rule [FR-19 • Should]

As a fraud operator, I want to block excessive activity.

Given more than five payments occur within 60 seconds, when the rule is evaluated, then the account is blocked for 10 minutes.

Given the temporary block is active, when a payment is attempted, then the request is Rejected.

Given the block expires, when a new request is checked, then the velocity restriction no longer blocks it.

### US-20 | Support payment search [FR-20 • Must]

As a support employee, I want to inspect a payment by reference.

Given an existing reference is supplied, when support searches, then the payment and full status history are returned.

Given an unknown reference is supplied, when support searches, then a clear not-found result is returned.

### US-21 | Support account restrictions [FR-21 • Should]

As a support employee, I want to block or unblock an account.

Given an authorized employee blocks an account, when a new payment is attempted, then it is rejected.

Given an authorized employee unblocks the account, when a new payment is attempted, then other rules still apply.

Given a block status changes, when the change is saved, then the action is audited.

### US-22 | Payment notifications [FR-22 • Must]

As a customer, I want to receive SMS and push updates.

Given a payment completes, when the outcome is committed, then SMS and push notifications are requested.

Given a payment fails, when the outcome is committed, then SMS and push notifications with a clear failure reason are requested.

### US-23 | Notification retries [FR-23 • Should]

As a operations employee, I want to recover delivery failures.

Given SMS or email delivery fails, when retry processing runs, then up to three retries are attempted.

Given all retries fail, when the final retry ends, then notification is marked undelivered.

Given a notification provider is unavailable, when a payment completes, then payment remains Completed and notification failure is tracked separately.

### US-24 | Editable configuration [FR-24 • Should]

As a administrator, I want to change fees and limits without redeployment.

Given an authorized administrator saves valid fee or limit settings, when a later payment is evaluated, then the new configuration is applied without redeployment.

Given invalid values are submitted, when validation runs, then the old valid configuration remains in force.

### US-25 | Audit trail [FR-25 • Must]

As a auditor, I want to trace money and administrative actions.

Given a financial movement commits, when audit logging runs, then who, when and why are recorded.

Given an administrator changes billers or settings, when the action commits, then an audit record is created.

### US-26 | Reconciliation [FR-26 • Should]

As a finance employee, I want to detect settlement mismatches.

Given a settlement file is supplied, when daily reconciliation runs, then biller records are compared to PayLink payments.

Given a biller lists a payment PayLink marked Failed, when comparison runs, then a discrepancy is flagged for review rather than silently rewritten.

Given reconciliation conclusively confirms an Unknown payment or its non-receipt, when the finding is processed, then the allowed Unknown-to-Completed or Unknown-to-Failed transition is applied.

### US-27 | Completed payment refund request [FR-27 • Could]

As a customer, I want to request review of a completed payment.

Given a customer requests a refund for a Completed payment, when support receives the request, then a separate refund request record is created; original payment remains Completed.

Given support approves under an agreed refund policy, when refund processing succeeds, then a separate compensating ledger transaction is posted without changing the original payment state.

Given support rejects the request, when the decision is saved, then no wallet movement occurs.

### US-28 | Daily financial report [FR-28 • Could]

As a administrator, I want to see payments, fees, commissions and failures.

Given daily report data exists, when the report is requested, then payment totals, fee income, agent commissions and failures per biller are displayed.

Given no activity exists, when the report is requested, then zero totals are displayed.

## 4. Allowed payment state transitions

| From | Event | To |
| --- | --- | --- |
| Initiated | Fraud checks pass and funds sufficient | FundsHeld |
| Initiated | Fraud fails or insufficient funds | Rejected |
| FundsHeld | Request sent to biller | SentToBiller |
| SentToBiller | Biller confirms | Completed |
| SentToBiller | Biller refuses | Failed |
| SentToBiller | No answer by timeout | Unknown |
| Unknown | Late callback/retry/settlement confirms | Completed |
| Unknown | Later refusal or reconciliation establishes non-payment | Failed |
| Failed | Hold released | Refunded |

Completed, Rejected and Refunded are final. Unknown is never refunded automatically. A refund requested for a Completed payment uses a separate refund record and compensating ledger movement, leaving the original payment Completed.

## 5. Priority summary

| Must | Should | Could | Total |
| --- | --- | --- | --- |
| 19 | 7 | 2 | 28 |

## 6. Measurable non-functional requirements

| ID | Target | Measurement |
| --- | --- | --- |
| NFR-01 Correctness | Zero double charges and zero negative balances | Duplicate and concurrent-payment automated tests |
| NFR-02 Performance | 100 payments/second sustained for 60 seconds against mocks | Load-test report for 60 seconds |
| NFR-03 Resilience | One biller outage does not stop payments to others | Failure injection against one biller |
| NFR-04 Modifiability | Adding a biller changes only integration module and configuration | Code diff and new-adapter test |
| NFR-05 Auditability | 100% of money movements audited with who/when/why | Ledger-to-audit reconciliation test |
| NFR-06 Testability | At least 70% domain-logic line coverage in CI | Coverage report generated in pipeline |

## 7. Technical and delivery constraints

TR-01: At least six modules with clear public interfaces and own tests: Accounts/Wallet, Biller Catalog, Payment Processing, Biller Integration, Fees/Commissions, Notifications. Fraud and Reconciliation are additional areas.

TR-02: Relational database with at least 10 tables and version-controlled schema migrations.

TR-03: At least 20 documented REST endpoints (OpenAPI) and at least one asynchronous flow.

TR-04: At least five different design patterns used for genuine needs, with AI-refactoring evidence.

TR-05: The team has agreed on Java with Spring Boot, PostgreSQL, Maven, JUnit 5, Docker, Docker Compose, GitHub Actions and RabbitMQ (Phase 2). The language and framework are declared in Phase 1 and will not change afterward.

TR-06: Put fixed business rules (limits, fees, timeouts, retries, promotions, commissions) in configuration, not hard-coded constants.

TR-07: Use exact decimal money types, never binary floating point.

TR-08: Design a layered or hexagonal modular monolith in Phase 1, implement first in Phase 2, then evolve Notifications and Reconciliation to event-driven communication using RabbitMQ; document ADRs. The choice of layered versus hexagonal architecture is to be confirmed with the architecture lead.

TR-09: Dockerfiles and Compose for app, database and mock billers; GitHub Actions for build, lint, tests, coverage, images and local deployment.

TR-10: Unit/integration tests, TDD commit evidence for three features, and automated tests for all eight hard cases.

TR-11: ElectroCo v2 changes field names, amounts to piasters and requires request signing; change only its integration and configuration.

TR-12: Source diagrams, requirements, prompt log and Phase 1 Git tag; final report, demo, final tag and individual discussion per brief.

## 8. Supporting requirements with acceptance criteria

SR-01 Authentication

Given a registered user provides valid credentials, when authentication succeeds, then the system identifies the user and authorizes operations according to their role. Given invalid credentials, when login is attempted, then access is denied.

SR-02 Agent accounts Given an authorized administrator provides valid agent information, when the agent is provisioned, then a uniquely identifiable agent account and wallet are created.

SR-03 TopUpNet amount and callback recovery Given a valid customer-entered top-up amount, when the request is accepted by TopUpNet, then the system keeps the payment pending confirmation and follows the documented timeout policy. Given a later callback, when validated, then the payment resolves only through permitted transitions.

SR-04 ElectroCo API v2 Given the ElectroCo v2 contract, when its adapter is updated, then renamed fields, piaster amounts and request signatures are handled without changing other business modules.

Email channel note SMS and push are mandatory for completed and failed payments. Email is a supported channel with retry behavior, but the brief does not mandate an email trigger; it may be demonstrated through a configured/test-triggered notification.

## 9. Eight hard-case traceability

| Hard case | Linked requirements |
| --- | --- |
| 1. Duplicate payment | US-10 |
| 2. ElectroCo 12-second delay | US-13 |
| 3. AquaCo refusal | US-09, US-12, US-22 |
| 4. Concurrent wallet payments | US-12 |
| 5. Notification provider outage | US-22, US-23 |
| 6. Duplicate TopUpNet callback | US-14 |
| 7. Settlement mismatch | US-26 |
| 8. New biller without core changes | US-15, NFR-04, TR-08 |

## 10. Assumptions and decisions (final implementation defaults)

The following are implementation defaults, not statements of TA approval. If the TA specifies a different policy, update the requirements, diagrams, tests and this log accordingly. Members 2 and 3 confirmed their work on 25 November 2026. Shared decisions assigned to them are recorded as confirmed within their scope. Remaining defaults are selected for implementation, not represented as Member 4 or TA approval.

| ID | Question | Decision / fallback | Status |
| --- | --- | --- | --- |
| A-01 | Registration fields | Use unique ID, name, phone and credentials | Confirmed — assigned member(s) 2/3 |
| A-02 | Verification procedure | Use authorized simulated verification upgrade | Confirmed — assigned member(s) 2/3 |
| A-03 | Daily limit includes fee? | Count bill amount, not service fee | Adopted assumption — not TA-confirmed |
| A-04 | Pending payments count toward daily limit? | Count Completed and active held payments | Adopted assumption — not TA-confirmed |
| A-05 | Idempotency key reused with changed payload? | Reject as conflict | Confirmed — assigned member(s) 2/3 |
| A-06 | Timeout with possible biller success? | Unknown; keep hold until conclusive result | Brief-based |
| A-07 | Concurrent wallet reservations? | Atomic transaction and wallet lock | Confirmed — assigned member(s) 2/3 |
| A-08 | Money rounding? | Decimal with two places; HALF_UP rounding | Adopted assumption — not TA-confirmed |
| A-09 | Promotion commission? | 40% of zero fee = zero commission | Brief-based |
| A-10 | Notification failure simulation? | Configurable mock provider | Member 3 approved; adopted default for DevOps |
| A-11 | Settlement timing and format? | Daily scheduled run with manual test trigger | Member 3 approved; adopted default for DevOps |
| A-12 | Completed payment refund state? | Original stays Completed; separate refund request and compensating ledger entries | Confirmed — assigned member(s) 2/3 |
| A-13 | NetCo batch handling? | Optional integration deferred; no live inquiry | Adopted implementation default |
| A-14 | Authentication and staff roles? | Role-based authorization and authenticated endpoints | Confirmed — assigned member(s) 2/3 |
| A-15 | Duplicate callback? | Ignore repeat and log it | Brief-based |
| A-16 | Promotion changes mid-payment? | Lock fee at initiation | Confirmed — assigned member(s) 2/3 |
| A-17 | Completed refund: service fee returned? | Refund original bill amount only; fee remains earned unless TA directs otherwise. | Adopted assumption — not TA-confirmed |
| A-18 | Completed refund: agent commission clawback? | Do not claw back agent commission on approved completed-payment refunds unless TA directs otherwise. | Adopted assumption — not TA-confirmed |
| A-19 | TopUpNet inquiry semantics? | Customer enters TopUpNet amount; no bill inquiry assumed. | Adopted assumption — not TA-confirmed |
| A-20 | TopUpNet accepted but no callback? | TopUpNet accepted = acknowledgment, not success; remain SentToBiller until callback or 10-second timeout, then Unknown; later callback resolves. | Adopted assumption — not TA-confirmed |
| A-21 | Retry offsets measured from timeout or previous retry? | Schedule retries at 1, 5, and 15 minutes after initial timeout (not cumulative). | Adopted assumption — not TA-confirmed |
| A-22 | AquaCo bill-amount overpayment? | Mock refuses amount greater than due; normalize error and release hold | Brief-based |
| A-23 | When is fee income recognized? | On confirmed completion, never at initial hold | Brief-based |
| A-24 | What does reconciliation “finds no payment” mean? | When complete reconciliation establishes no biller payment, Unknown -> Failed, then release hold through Refunded; incomplete files do not establish non-receipt. | Adopted assumption — not TA-confirmed |
| A-25 | Technology stack | Java Spring Boot, PostgreSQL, Maven, JUnit 5, Docker, Docker Compose, GitHub Actions, RabbitMQ (Phase 2) | Decided — Team Agreement |


### Final implementation decisions and rationale

| Scope | Selected default and rationale |
| --- | --- |
| Architecture (TR-08) | Layered modular monolith is the selected fallback because it is straightforward to implement and test in Spring Boot. Member 3 approved the work, but did not explicitly name the architecture style in the confirmation received; align this default with the existing design before implementation. |
| A-03, A-04 | Count bill principal only, including active holds and completed payments, to prevent concurrent limit bypass. |
| A-08 | Use exact decimals and HALF_UP to two places for consistent monetary calculations. |
| A-10, A-11 | Configurable notification mocks and daily reconciliation with a manual test trigger make failure and settlement tests reproducible. |
| A-13 | Defer optional NetCo to prioritize mandatory billers. |
| A-17, A-18 | Refund principal only for approved completed-payment refunds; retain the earned fee and commission. This does not apply to failed payments, whose entire hold is released. |
| A-19, A-20 | Customer-entered top-up amount; acceptance is not completion. Keep funds held until conclusive confirmation, using the brief’s 10-second timeout. |
| A-21 | Retry at 1, 5 and 15 minutes from the initial timeout, then await reconciliation. |
| A-24 | Only complete, authoritative settlement evidence covering the payment can establish non-payment; incomplete evidence leaves funds held. |

Member 4 and TA approvals have not been received. These defaults allow implementation to proceed and are subject to correction if the instructor clarifies the rules. No user stories, acceptance criteria or NFRs have been changed.
