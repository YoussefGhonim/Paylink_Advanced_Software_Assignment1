PayLink — Phase 1 AI Prompt Log

**Authors: Yahia Ahmed; Youssef Ghonim; Sandra Fahmy; Menna Ehab**

**Updated: 25 November 2026**

This log merges the entries of four team members. Section 1 (Yahia Ahmed) quotes actual user messages from the PayLink ChatGPT conversation verbatim (including spelling), with the explicitly labeled excerpt in review A; answer summaries are paraphrases. The model identifier (GPT-6.2) for Yahia’s entries was confirmed by the author. Verdicts describe the documented review outcomes, not TA approval.

Section 2 (Youssef Ghonim, Member 4 – DevOps, Backend Setup and Integration) is a reconstructed log: its prompts are illustrative student-style prompts representing the work performed, not verbatim messages. The model version for these entries was not recorded and has not been verified against the original chat.

Section 3 (Sandra Fahmy, Member 2 – System Behavior and UML) quotes actual user messages from the PayLink Claude conversation verbatim (including spelling; one message typed in Arabizi is given with an English gloss); answer summaries are paraphrases. The tool is Claude (claude.ai); the model shown is the configured model, Claude Sonnet 5.5 (model ID claude-sonnet-5-5), and the model that served an individual reply may have differed. Verdicts describe documented review outcomes, not TA approval.

Section 4 (Menna Ehab, Member 3 – Architecture and Database) presents the author's messages from the PayLink Claude conversation translated into English; answer summaries are paraphrases. The tool is Claude (claude.ai); the model shown is the configured model, Claude Sonnet 5.5 (model ID claude-sonnet-5-5), and the model that served an individual reply may have differed. Verdicts describe documented review outcomes, not TA approval.

# 1. Member entries — Yahia Ahmed (Requirements and documentation)

| Date | Author | Stage | Tool and model | Prompt | Summary of answer | Verdict and why |

| --- | --- | --- | --- | --- | --- | --- |

| 22 Nov 2026 | Yahia Ahmed | Requirements | ChatGPT (Plus); GPT-6.2 | Write the problem summary for the paylink and identify stakeholders and anyone intersted / Create at least 25 user stories from FR-1 through FR-28. / Write acceptance criteria using Given/When/Then. / Assign Must, Should, Could priorities. / Define at least 6 measurable NFRs. / Document ambiguities and decisions. | Generated 28 stories, six NFRs and assumptions; priority summary contradicted itself and some hard cases were not explicit. | Fixed in later review; incorrect priority count and incomplete acceptance criteria. |

| 22 Nov 2026 | Yahia Ahmed | Requirements review | ChatGPT (Plus); GPT-6.2 | See exact review prompt A below. | Validated the review and produced a revised Word document with supporting requirements and traceability. | Fixed: completed-payment refund must preserve Completed; explicit hard-case criteria added. |

| 22 Nov 2026 | Yahia Ahmed | Priority verification | ChatGPT (Plus); GPT-6.2 | there is only 7 should FR and 19 must FR | Confirmed 19 Must, 7 Should and 2 Could and listed the corresponding IDs. | Accepted: matches the brief. |

| 22 Nov 2026 | Yahia Ahmed | Quality review | ChatGPT (Plus); GPT-6.2 | See exact review prompt B below. | Validated eight concerns and suggested explicit criteria, callback-state policy and reconciliation handling. | Fixed: identified gaps in earlier draft; subsequent requirements PDF contains the corrections. |

| 22 Nov 2026 | Yahia Ahmed | Requirements clarification | ChatGPT (Plus); GPT-6.2 | what are the assumptions that i should ask the team or the TA about state each one in a table corresponding to it who i should ask | Separated instructor-facing business questions from team implementation choices. | Accepted as a clarification plan, not as confirmed answers. |

| 22 Nov 2026 | Yahia Ahmed | Documentation | ChatGPT (Plus); GPT-6.2 | state your assumptions down the pdf and give me the fixed and edited phase 1 requirments in a pdf has a tables format when needed and the assumptions are down and also do the pdf for the AI prompts as the project file says , put the date and my name(yahia ahmed) | Produced requirements and prompt-log PDFs. | Fixed: original log contained reconstructed prompts rather than exact messages; replaced by this log. |

| 25 Nov 2026 | Yahia Ahmed | Technical decision | ChatGPT (Plus); GPT-6.2 | we have agreed on this frameworks | With the attached stack screenshot, identified the agreed Java Spring Boot, PostgreSQL, Maven, JUnit 5, Docker, GitHub Actions and RabbitMQ stack. | Accepted: reflects the user's explicit team agreement. |

| 25 Nov 2026 | Yahia Ahmed | Final documentation | ChatGPT (Plus); GPT-6.2 | can you do the required edits ( change the Tech assumption , edit prompt log , ... ) without changing the user storis or requriments since they need no changes and make me ready to submmit the .md file | Converted the requirements PDF into Markdown, updated technology decisions and created this verbatim prompt log. | Prepared for review; team/TA decisions not confirmed remain labeled as such. |

## Evidence of improved prompting

### Sequence 1 — Incorrect priority count

Initial prompt: first table entry. Initial answer included the incorrect 18 Must / 8 Should / 2 Could summary. Improved prompt: there is only 7 should FR and 19 must FR. The revised answer correctly identified the seven Should requirements and two Could requirements, leaving 19 Must.

### Sequence 2 — Incomplete requirements and state-machine conflict

Initial prompt: first table entry. The initial answer did not explicitly cover several hard cases and left completed-payment refund policy unclear. Improved prompt: exact review prompt A below, which specifies the missing criteria and the final-state constraint. The revised pack retains Completed for the original payment, records a separate refund, and includes concurrency, refusal and timeout criteria. A further improved prompt (B) identified agent attribution, supporting acceptance criteria and new-biller traceability gaps.

## Exact review prompt A

PayLink Requirements Pack: Review Against the Brief

This review compares the requirements pack (requirements.md) with the PayLink Year Work Project Student Brief. The pack is mostly accurate. All 28 functional requirements are present with the correct IDs and wording, and the six NFRs match Section 3.3 of the brief. A few things are wrong, and some brief requirements aren't linked to any story.

1. Errors to fix

1. The priority counts contradict each other. Section 4 of the pack says 18 Must / 8 Should / 2 Could, but the "Priority count correction" note at the bottom says 19 / 7 / 2. The brief's table gives:

- Should (7): FR-2, FR-17, FR-19, FR-21, FR-23, FR-24, FR-26

- Could (2): FR-27, FR-28

- Must (19): everything else

Fix the summary table and paragraph, and delete the correction note.

2. US-27 conflicts with the state machine. The brief says Completed is a final state, and Refunded is reachable only from Failed. A refund of a completed payment therefore can't move the payment to Refunded. A-12 handles the money side, but it should also say the payment stays Completed and the refund is a separate record. The "defined refund policy" in US-27 is also never defined. Does the fee get refunded, and is the agent commission clawed back? Add an ambiguity entry for this.

3. US-11 points outside the document. "Not defined in Section 2.5" refers to the brief's section. A standalone pack should list the allowed transitions itself, including Initiated→Rejected and Failed→Refunded.

The above is a verbatim excerpt of the longer review prompt (not the entire message). The remainder identified missing hard-case criteria, fee income, agent cash top-up, agent accounts, login, AquaCo overpayment, TopUpNet inquiry and callback handling, retry timing, ElectroCo v2 and technical constraints.

## Exact review prompt B

1. Open decisions. The Phase 1 rubric awards points for "ambiguities resolved." Roughly a dozen items are still "Proposed," "Confirm with TA" or "Open" (A-03, A-04, A-08, A-17 to A-21 and A-24). Before submitting, mark each as Decided, with the fallback you'll use if the TA says otherwise. Then remove the review checklist, "Team review draft" and the "requires team approval" note.

2. A-24 softens a rule in the brief. The brief says an Unknown payment becomes Failed when "reconciliation finds no payment." Your A-24 says absence alone may not be enough. This may be a sensible safety choice, but it deviates from the brief's state table, so record it as a deliberate deviation with a reason, or follow the brief's wording.

3. The SR items have no acceptance criteria. SR-01 to SR-04 are one-line requirements with no Given/When/Then, so they aren't testable like the rest. SR-01 (login) and SR-02 (agent accounts) especially need at least one criterion each.

4. US-17 lost a criterion. The first version said the system records the agent associated with a payment. Commission can't be paid or reported without that, so add it back.

5. Hard case 8 isn't directly covered. The only criterion near it is US-15's "without changing core payment logic." The brief's test is that adding a new biller changes only the integration and configuration, so add that as a criterion. The traceability table also points case 8 at TR-11, which is the ElectroCo v2 change. SR-04 or TR-08 would be a better reference.

6. The state for TopUpNet "accepted" is undefined. After TopUpNet replies "accepted," the payment waits minutes for a callback. Neither US-14 nor the state table says whether it stays SentToBiller or moves to Unknown. Add this to A-20, since it affects the timeout and retry behaviour.

7. Grammar. "As a administrator" (US-06 and US-24), "As a agent" (US-17), "As a auditor" (US-25), "As a integration engineer" (US-15) and "As a operations employee" (US-23) should all be "an."

8. Email has no trigger. US-22 sends SMS and push, and US-23 retries SMS or email, but nothing ever sends an email. This follows the brief, but a one-line note saying email is a supported channel with no mandatory trigger would avoid confusion. can you check those concerns carefully

# 2. Member entries — Youssef Ghonim (Member 4: DevOps, Backend Setup and Integration)

**Phase: Phase 1     Dates: 23–25 November 2026     AI tool: ChatGPT**

**Note: This is a reconstructed prompt log. The prompts are illustrative student-style prompts representing the work performed.**

| Date | Author | Stage | Tool and model | Prompt | Summary of answer | Verdict and why |

| --- | --- | --- | --- | --- | --- | --- |

| 23 Nov 2026 | Youssef Ghonim | Understanding responsibilities | ChatGPT (model not recorded) | I am Member 4 in the PayLink project and I am responsible for DevOps and integration. Can you explain exactly what I need to do for Phase 1 and give me a step-by-step plan to finish my tasks? | Explained the responsibilities, including GitHub setup, backend skeleton, Docker, CI, testing and documentation. | Accepted. Helped me understand the tasks and organize my work. |

| 23 Nov 2026 | Youssef Ghonim | Backend setup | ChatGPT (model not recorded) | We agreed to use Java 21, Spring Boot and PostgreSQL. Can you guide me through creating the Spring Boot project, choosing the right dependencies and organizing the packages according to our architecture? | Explained Spring Initializr configuration, required dependencies and a suitable project structure. | Accepted. Helped establish the backend foundation. |

| 23 Nov 2026 | Youssef Ghonim | Database and health endpoint | ChatGPT (model not recorded) | How can I connect Spring Boot to PostgreSQL using application.properties? I also need a simple /health endpoint to check if the application is running. Can you show me the code and explain how to test both? | Provided database configuration and a basic health controller with testing guidance. | Accepted. Helped verify the backend and database connection. |

| 23 Nov 2026 | Youssef Ghonim | Docker setup | ChatGPT (model not recorded) | I need the project to run with Docker so other teammates can use it easily. Can you help me create a multi-stage Dockerfile and a docker-compose.yml for Spring Boot and PostgreSQL, making sure the application waits for the database to be healthy? | Suggested Docker and Compose configurations with a database health check and service dependency. | Accepted. Provided the initial containerization setup. |

| 23–24 Nov 2026 | Youssef Ghonim | Docker troubleshooting | ChatGPT (model not recorded) | I tried running docker compose up --build but Docker Desktop gave me errors. Can you help me find out whether the issue is in my configuration or Docker itself? Also, does another teammate need Docker installed to run the project? | Explained Docker engine troubleshooting and the software prerequisites for teammates. | Partially accepted. Helped investigate local Docker issues and clarify the handover requirements. |

| 24 Nov 2026 | Youssef Ghonim | GitHub Actions CI | ChatGPT (model not recorded) | I want to create a GitHub Actions workflow that checks the project whenever someone pushes code or creates a pull request. It should use Java 21, Maven caching and run clean verify. Can you write the ci.yml and include the Phase 1 tag trigger too? | Created a CI workflow with Java setup, Maven caching, build and test steps, and branch/tag triggers. | Accepted. Provided the foundation for automated validation. |

| 24 Nov 2026 | Youssef Ghonim | Maven and Checkstyle troubleshooting | ChatGPT (model not recorded) | When I run mvnw clean verify, the application compiles and the tests pass, but the build fails because of Checkstyle. Here is my pom.xml and the errors. Can you explain what is wrong and suggest a reasonable configuration for our project? | Reviewed the Maven configuration and identified package naming, formatting and other Checkstyle violations. Suggested corrections and a smaller configuration. | Accepted with changes. Helped identify and address the build issues. |

| 25 Nov 2026 | Youssef Ghonim | Testing and project review | ChatGPT (model not recorded) | Can you review my current backend structure and tests? I have a health controller test and another Spring Boot test file that is commented out. Tell me what should stay and what I should fix before submission. | Reviewed the basic test setup and explained the purpose of the available tests and remaining improvements. | Accepted. Helped prepare the backend for final verification. |

| 25 Nov 2026 | Youssef Ghonim | README and team integration | ChatGPT (model not recorded) | I need to finish the README and add the other members' work to the repository. Can you tell me what the README should contain and what I should check before integrating the requirements, UML diagrams and database design? | Suggested README sections and an integration checklist for the team's deliverables. | Accepted. Helped prepare the repository for the whole team. |

| 25 Nov 2026 | Youssef Ghonim | Final verification and submission | ChatGPT (model not recorded) | Before submitting Phase 1, can you give me a final checklist for my responsibilities? Include GitHub, Docker, PostgreSQL, the health endpoint, tests, Checkstyle, GitHub Actions, documentation and the Phase 1 Git tag. | Prepared a final checklist and explained how to verify the repository, CI workflow and release tag. | Accepted. Helped review the remaining work before submission. |

## Evidence of prompt improvement (Member 4)

The prompts progressed from general planning questions to specific implementation and troubleshooting requests. I first used AI to understand my responsibilities, then to configure the backend and DevOps tools, and finally to review errors, integrate the team's work and prepare the submission.

## Member 4 contribution summary

AI assistance was used to support the following tasks:

Spring Boot backend setup and PostgreSQL configuration.

Health endpoint and basic automated testing.

Dockerfile and Docker Compose configuration.

GitHub Actions CI workflow.

Maven and Checkstyle troubleshooting.

README preparation and team integration.

Final verification and Phase 1 release preparation.

**Note: The configurations and suggestions were reviewed against the project requirements. Successful clean-clone Docker execution and remote CI success should only be reported after actual verification.**

# 3. Member entries — Sandra Fahmy (Member 2: System Behavior and UML)

**Phase: Phase 1     Dates: 23–25 November 2026     AI tool: Claude (claude.ai), Claude Sonnet 5.5**

**Note: Prompts are quoted as typed in the conversation. The AI could not render PlantUML in its own environment, so it checked the diagram files with a structural script only; rendering and final review of the diagrams are the author’s responsibility.**

| Date | Author | Stage | Tool and model | Prompt | Summary of answer | Verdict and why |

| --- | --- | --- | --- | --- | --- | --- |

| 23 Nov 2026 | Sandra Fahmy | Design: first draft of the five diagrams | Claude (claude.ai); Claude Sonnet 5.5 | iam member 2 and the uplaoded req is the member1 finished work we should do as asked in above / (Sent with the pasted Phase 1 division of work for Member 2, Yahia’s requirements PDF and the student brief.) | Produced five PlantUML sources (use-case, domain class, payment state, pay-a-bill sequence, failed-payment refund sequence) and a README. State diagram had 8 states and 9 transitions from the requirements. | Fixed: the AI’s own re-read caught apostrophe comments inside the class diagram (they would show as text) and a placeholder step in the refund sequence. Larger mismatches with the team design were found later (rows 4 and 5). |

| 23 Nov 2026 | Sandra Fahmy | Verification | Claude (claude.ai); Claude Sonnet 5.5 | i installed PUML you can render now | The AI found no PlantUML in its own workspace, said it could not render the files, and gave local render commands. | Accepted: the limitation was stated honestly; syntax must be checked by the author on her own computer. |

| 23 Nov 2026 | Sandra Fahmy | Planning | Claude (claude.ai); Claude Sonnet 5.5 | ok what should i do exactly now member four didnt even do a repo yet so should i wait and then what and how to do the prompt log thing i dont know | Gave a same-day plan (render and check the diagrams, message Members 3 and 4, open a pull request once the repository exists) and explained the prompt-log format and honesty rule. | Accepted: advice only; consistent with the brief. |

| 25 Nov 2026 | Sandra Fahmy | Design: class diagram versus team ER diagram | Claude (claude.ai); Claude Sonnet 5.5 | now this is the whole members work confirm everything and if it affects my 5 files update them and give them to me + make me my prompt file between us a proffessional one / (Sent with the team repository zip and the requirements confirmation PDF.) | Compared the class diagram with the ER diagram table by table and rewrote it: 19 classes now map to the 19 ER tables, with attribute and enum names taken from the ER columns. | Fixed: the first answer was wrong against the team design (see Sequence 1 below). |

| 25 Nov 2026 | Sandra Fahmy | Design: use-case and sequence diagrams | Claude (claude.ai); Claude Sonnet 5.5 | Same message as the row above; it covered all five files. | Added the missing use cases and the Auditor actor, renamed sequence participants and messages to the architecture notes’ module interfaces, and added the service-active check and the velocity account block. | Fixed: the first answer was incomplete and used generic names (see Sequence 2 below). |

| 25 Nov 2026 | Sandra Fahmy | Review of teammates’ work | Claude (claude.ai); Claude Sonnet 5.5 | Same message as the row above ("confirm everything"). | Confirmed the requirements pack against the brief (28 stories, 19 Must / 7 Should / 2 Could, 6 NFRs, 9 transitions) and rendered the ER diagram (19 tables). Listed gaps for the owners: missing FINANCE role and settlement-file completeness flag, six foreign keys without relationship lines, CI triggers, empty README, package structure. | Accepted with caveats: Maven and Docker were not run, so build and CI findings come from reading the files and need confirmation on GitHub. |

| 25 Nov 2026 | Sandra Fahmy | Documentation | Claude (claude.ai); Claude Sonnet 5.5 | can you give me the prompt log in my name Sandra Fahmy and in word plz | Produced my prompt log as a Word document. | Accepted: formatting only; no new content. |

| 25 Nov 2026 | Sandra Fahmy | Documentation: merge | Claude (claude.ai); Claude Sonnet 5.5 | di el prompt log btaat member1 and 4 zawed aalehom bta3ty baa / [English: "this is Member 1’s and Member 4’s prompt log, add mine to them"] (Sent with the teammates’ prompt log PDF.) | Built this merged log: Yahia’s and Youssef’s entries copied unchanged, the introduction updated for three members, and Section 3 added. | Accepted: no teammate entry was edited. |

## Evidence of improved prompting

### Sequence 1 — Class diagram did not match the team’s ER diagram

Initial prompt: first table entry. It had no ER diagram or architecture notes to align with, because Member 3 wrote them afterwards. The initial class diagram invented or misnamed entities: a separate IdempotencyRecord (the ER diagram keeps idempotency_key and request_hash in payments), AccountBlock (users.blocked_until), LimitConfig and CommissionRule (system_settings), Customer and Agent subclasses (one users table with a role), SettlementRecord and ReconciliationDiscrepancy (reconciliation_items), PaymentStatusHistory (payment_events) and RequiredField (service_input_fields). It used a wallet version field, UUID ids and the statuses REQUESTED and RETRYING, none of which the ER diagram has, and it missed WalletHold, AgentCommission and the COMPENSATION ledger type. Improved prompt: the fourth table entry, which supplied the whole team’s work as attachments and asked the AI to confirm everything and update the diagrams. The revised class diagram maps 19 classes to the 19 ER tables, and a script confirmed that every ER column has a matching attribute.

### Sequence 2 — Incomplete use cases and generic sequence diagrams

Initial prompt: first table entry. The initial use-case diagram had no use case for US-05 (tracing wallet movements in the ledger) or for viewing the audit trail (US-25), and no Auditor actor. The sequence diagrams used generic participants (“Wallet / Ledger”, “Idempotency Store”, “Fee Service”) and invented method names, and did not check that the service is active (US-06) or block the account when the velocity rule fires (US-19). Improved prompt: the fifth table entry (same message, now with architecture.md and the 28 acceptance-criteria stories attached). The revised diagrams reference every user story US-01 to US-28 and use the module interfaces holdFunds, captureHold, releaseHold, postFeeIncome, postCommission, recordCommission and handleBillerResult, with the results SUCCESS, REFUSED, PENDING and TIMEOUT. One judgement call was kept: the architecture notes put the fee quote after the fraud checks, but the brief (section 2.2) and A-16 put it before, so the diagram follows the brief.

### Prompt template for the next round (not yet used)

The prompts above were short and unstructured. For further diagram fixes, a structured prompt will be used: Role (UML reviewer), Context (attach database-erd.mmd, architecture.md, requirements.md and the PlantUML file), Task (list every mismatch of name, attribute, state, transition, interface method or missing user story), Constraints (do not invent entities; use exact names; keep PlantUML syntax valid; change nothing that already matches), Output format (a mismatch table with file and line, the corrected file, one line per change).

## Member 2 contribution summary

AI assistance was used to support the following tasks:

Use-case diagram covering user stories US-01 to US-28.

Domain class diagram mapped to the 19 tables of the ER diagram.

Payment state diagram with 8 states and 9 transitions.

Sequence diagram for paying a bill.

Sequence diagram for refunding a failed payment.

Cross-check of the other members’ work against the brief and the UML files.

**Note: The AI’s suggestions were reviewed against the requirements pack, the ER diagram and the architecture notes. Rendering of the PlantUML files should only be reported after the author has actually rendered them.**

# 4. Member entries — Menna Ehab (Member 3: Architecture and Database)

**Phase: Phase 1     Dates: 24–25 November 2026     AI tool: Claude (claude.ai), Claude Sonnet 5.5**

**Note: Prompts are quoted as typed in the conversation. The AI did not render the ER diagram in its own environment, so it checked the files by reading them only; rendering and final review of the diagram are the author’s responsibility.**

| Date | Author | Stage | Tool and model | Prompt | Summary of answer | Verdict and why |

| --- | --- | --- | --- | --- | --- | --- |

| 24 Nov 2026 | 24 Nov 2026 | Menna Ehab | Claude (claude.ai); Claude Sonnet 5.5 | This is the software assignment. Please explain what is required from me as Member 3. Youssef, who is Member 4, has uploaded the repository and the architecture. How should I clone the repository, and what steps should I follow? I have also uploaded Member 1's work, along with the database, backend, and the things we agreed on. | Explained that Member 3 owns the ER diagram (at least 10 tables, as a Mermaid source file) and the architecture notes, listed a 12-table starting set, and gave the clone, branch and pull-request steps for the protected main branch. | Accepted: advice only, consistent with the brief. The 12-table list was later replaced by the 19-table draft (third entry). |

| 24 Nov 2026 | 24 Nov 2026 | Menna Ehab | Claude (claude.ai); Claude Sonnet 5.5 | I do not understand the project or what is required from me and the other members. All I know is that only Youssef needs Docker, not me. This is what Youssef uploaded. | Re-explained the project in simple terms (wallet and bill payments, backend only, Phase 1 is design plus skeleton), said Docker is Member 4’s work, described the zip (an empty Spring Boot skeleton with PostgreSQL and CI, no docs folder) and the two files required from Member 3. | Accepted: consistent with the brief; the AI read the zip’s README, Compose file, CI workflow and Git configuration before answering. |

| 24 Nov 2026 | 24 Nov 2026 | Menna Ehab | Claude (claude.ai); Claude Sonnet 5.5 | I want to start working on my tasks. Do I need to wait for anything from the other members? | Said nothing blocks the author except agreeing entity names with Member 2, and produced database-erd.mmd: 19 tables with keys and relationships built from Member 1’s requirements (balances from ledger_entries, wallet_holds, separate refund_requests, idempotency key and locked fee in payments). | Fixed later: Member 2’s review found the role list missing FINANCE, no completeness flag on settlement_files and several foreign keys with no relationship line (see Sequence 1). |

| 24 Nov 2026 | 24 Nov 2026 | Menna Ehab | Claude (claude.ai); Claude Sonnet 5.5 | Is this the correct format for the ERD? Is it better to wait until Member 2 finishes and sends their diagram? | Confirmed the file is a valid Mermaid erDiagram source (the brief asks for source files, not screenshots) but a draft the author must review; said it had not rendered the diagram itself; advised sending the ERD to Member 2 now instead of waiting. | Accepted: the limitation was stated honestly; the author opened the file in the Mermaid Preview extension. |

| 24 Nov 2026 | 24 Nov 2026 | Menna Ehab | Claude (claude.ai); Claude Sonnet 5.5 | Should I create the architecture notes now, or wait until Member 2 finishes? | Advised not to wait and produced architecture.md: a layered modular monolith, six modules plus Fraud and Reconciliation, each with responsibility, public interface and owned tables, a dependency diagram, quality-requirement mapping and Phase 2 assumptions. | Fixed later: the orchestration order contradicted brief section 2.2 (see Sequence 2). |

| 25 Nov 2026 | 25 Nov 2026 | Menna Ehab | Claude (claude.ai); Claude Sonnet 5.5 | Member 1 sent us this document and wants me to confirm some points. Member 2 also corrected the class diagram, and as Member 3 I need to fix the written items. | Checked each finding against the brief and Member 2’s class diagram, then applied them: FINANCE in users.role, an is_complete column on settlement_files, seven relationship lines, and the fee-before-fraud order in architecture.md. Also suggested answers for Member 3’s questions in the confirmation PDF. | Fixed: all findings were confirmed correct against brief section 2.2 and the class diagram (Role.FINANCE, SettlementFile.complete). The corrected files still need to be re-rendered by the author. |

| 25 Nov 2026 | 25 Nov 2026 | Menna Ehab | Claude (claude.ai); Claude Sonnet 5.5 | Is Member 1's work confirmed as correct, or what should I tell him? | Compared Member 1’s assumptions with the ER diagram and found A-01, A-02, A-07, A-10, A-11, A-12, A-14 and A-16 supported. Found that US-04 (record the cash funding source of a top-up) has no explicit column and suggested a funding_source column in ledger_entries. | Accepted as a finding. At the time of writing the column has not been added to the ER diagram or the class diagram; the team must decide whether to add it now or defer it to Phase 2. |

| 25 Nov 2026 | 25 Nov 2026 | Menna Ehab | Claude (claude.ai); Claude Sonnet 5.5 | Add my prompt log. | Added this Section 4 to the team Word file, quoting the author’s messages from the conversation and paraphrasing the answers; the author’s name was left as a placeholder. | Accepted as a draft: the author must check each entry against her conversation and fill in her name before submission. |

## Evidence of improved prompting

### Sequence 1 — ER diagram missed names and relationships that the requirements and class diagram need

Initial prompt: the third table entry (ER diagram draft). The initial diagram had 19 tables, but the users.role comment listed CUSTOMER, AGENT, ADMIN and SUPPORT only, although finance staff appear in US-05, US-26 and US-28; settlement_files had no completeness marker, although A-24 says an incomplete file cannot prove non-receipt; and seven foreign-key columns had no relationship line (payments.wallet_id, ledger_entries.hold_id, ledger_entries.created_by, agent_commissions.ledger_entry_id, refund_requests.requested_by and decided_by, system_settings.updated_by). These were found by Member 2’s cross-check, not by the AI or by the author’s first read. Improved prompt: the sixth table entry, which supplied Member 2’s review and asked for the written items to be fixed. The revised diagram has the FINANCE role, an is_complete column and seven added relationship lines (the link from users to refund_requests is drawn twice, for the requester and the decider), and its names now match Member 2’s class diagram.

### Sequence 2 — Architecture notes put the fee quote after the fraud checks

Initial prompt: the fifth table entry (architecture notes). The initial architecture.md gave the orchestration order as “limits and fraud check, quote fee, hold funds, …”. The brief (section 2.2) puts the fee and total (step 3) before the fraud checks (step 4), and A-16 locks the fee at initiation, so the AI’s order was wrong; it had not been checked against the brief. Member 2’s review found it, and Member 2’s sequence diagrams had already followed the brief instead. Improved prompt: the sixth table entry. The revised order is “quote fee and total, limits and fraud check, hold funds, send to biller, apply result, request notifications”.

### Prompt template for the next round (not yet used)

The prompts above were short and unstructured, and in both sequences the correction came from a teammate’s review rather than from the author re-engineering the prompt. For further fixes, a structured prompt will be used: Role (database and software architect), Context (attach requirements.md, domain-class.puml, database-erd.mmd, architecture.md and the brief), Task (list every foreign-key column without a relationship line, every enum value or user-story field with no column, and every step order that differs from brief section 2.2), Constraints (do not invent tables or columns; use the names in the class diagram; keep Mermaid syntax valid; change nothing that already matches), Output format (a mismatch table with file and line, then the corrected file and one line per change).

## Member 3 contribution summary

AI assistance was used to support the following tasks:

Explanation of the project and of the Member 3 deliverables.

ER diagram (database-erd.mmd) with 19 tables, keys and relationships.

Architecture notes (architecture.md): layered modular monolith, six modules plus Fraud and Reconciliation, table ownership and a dependency diagram.

Corrections to both files after Member 2’s cross-check.

Cross-check of Member 1’s assumptions against the ER diagram.

**Note: The AI’s suggestions were reviewed against the requirements pack, the brief and the class diagram. The AI did not render the ER diagram. The author opened it in the Mermaid Preview extension, and Member 2 reported rendering the first version (19 tables, no syntax errors); the corrected version should be re-rendered by the author before the diagram is reported as verified.**
