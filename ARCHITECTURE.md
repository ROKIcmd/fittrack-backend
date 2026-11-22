# FitTrack Backend — Architecture & OOP Documentation

## 1. Layered Package Structure
Base package: `com.fittrack.fittrack`

- **`model`** — Pure Java domain entities, rich enums, and state transitions (`Serializable`, zero Spring).
- **`repository`** — `java.io` file persistence layer (`ObjectInputStream`/`ObjectOutputStream`, zero Spring).
- **`service`** — Business logic and design patterns (zero Spring).
- **`api`** — Thin Spring Boot `@RestController`s, DTOs, and CORS config *(Spring lives only here)*.

---

## 2. OOP Principles Applied

- **Abstraction:** `User` and `FitnessPlan` are `abstract` classes defining shared contracts (`getRole()`, `getPlanType()`). `Repository<T>` abstracts CRUD operations away from file handling.
- **Inheritance:**
  - `Member` and `Trainer` extend `User`.
  - `WorkoutPlan` and `DietPlan` extend `FitnessPlan`.
  - `UserRepository` and `PlanRepository` extend `FileRepository<T>`.
- **Polymorphism:**
  - `UserRepository` stores and queries both `Member` and `Trainer` from `data/users.dat` via `User`.
  - `PlanRepository` stores and queries both `WorkoutPlan` and `DietPlan` from `data/plans.dat` via `FitnessPlan`.
- **Encapsulation:**
  - Models protect invariants and state transitions internally: `User.verifyPassword()`, `Membership.renew()` / `getDaysRemaining()`, `BillingRecord.approve()` / `decline()`, `TrainingSession.start()` / `complete()` / `markNoShow()` / `reschedule()`, `RescheduleRequest.approve()` / `decline()`, and `BodyMetric` auto-calculates BMI.
  - `WorkoutPlan` returns an unmodifiable `List<Exercise>`.
  - `MembershipTier` and `BillingCycle` are rich enums encapsulating pricing, days, and inclusions.
- **Composition:** `WorkoutPlan` owns `List<Exercise>`.

---

## 3. Design Patterns Implemented

- **Repository Pattern (`Repository<T>`, `FileRepository<T>`, `UserRepository`, `PlanRepository`):** Decouples business services from `java.io` serialization and thread-safe file reads/writes (`data/*.dat`).
- **Singleton Pattern (`DataStore`):** Thread-safe, double-checked locking `DataStore.getInstance()` centralizes all 7 repositories so services share one consistent file gateway.
- **Factory Pattern (`UserFactory`):** Centralizes `MEM-` / `TRN-` ID generation and instantiation of `Member` and `Trainer`.

---

## 4. Class Inventory (Implemented So Far)

### `com.fittrack.fittrack.model` (15 Files)
| Class / Enum | Role |
|---|---|
| `User` *(abstract)* | Base account (`id`, `firstName`, `lastName`, `email`, `password`). |
| `Member` | Client profile (`phoneNumber`, `dateOfBirth`, `sex`, `primaryGoal`, `heightMeters`, `targetWeightKg`, `assignedTrainerId`). |
| `Trainer` | Coach profile (`education`, `specialization`, `yearsOfExperience`, `shortMessage`). |
| `MembershipTier` *(enum)* | `BRONZE`, `SILVER`, `GOLD` with `monthlyPrice` and `inclusions`. |
| `BillingCycle` *(enum)* | `WEEKLY`, `MONTHLY`, `ANNUALLY` with `days` and `calculatePrice()`. |
| `PaymentStatus` *(enum)* | `PENDING`, `APPROVED`, `DECLINED`. |
| `Membership` | Tracks active plan, `startDate`, `renewalDate`, `getDaysRemaining()`, `renew()`. |
| `BillingRecord` | Payment log with `referenceNumber`, `amount`, `billingDate`, and `PaymentStatus`. |
| `FitnessPlan` *(abstract)* | Shared plan fields (`planName`, `memberId`, `trainerId`, `goal`, `durationWeeks`, `startDate`, `draft`). |
| `WorkoutPlan` | Holds `daysPerWeek`, `coachingNotes`, and `List<Exercise>`. |
| `Exercise` | Holds `name`, `sets`, `reps`, `restSeconds`, `loadKg`. |
| `DietPlan` | Holds macro targets (`dailyEnergyKcal`, `proteinGrams`, `carbsGrams`, `fatsGrams`, `dailyWaterLiters`) and `dietaryNotes`. |
| `TrainingSession` | Scheduled/logged workout with nested `Status` (`SCHEDULED`, `STARTED`, `COMPLETED`, `NO_SHOW`, `RESCHEDULED`). |
| `RescheduleRequest` | Member request + trainer reply with nested `Status` (`PENDING`, `APPROVED`, `DECLINED`). |
| `BodyMetric` | Historical entry (`weightKg`, `bodyFatPercent`, `muscleMassKg`, auto-computed `bmi`, `trainerNote`). |

### `com.fittrack.fittrack.repository` (5 Files)
| Class / Interface | Role |
|---|---|
| `Repository<T>` | Generic CRUD interface (`save`, `findById`, `findAll`, `deleteById`). |
| `FileRepository<T>` | Generic `java.io` object-stream engine (`synchronized` reads/writes to `.dat` files). |
| `UserRepository` | Adds `findByEmail()`, `findAllMembers()`, `findAllTrainers()`, `findMembersByTrainer()`. |
| `PlanRepository` | Adds `findWorkoutPlansByMember()`, `findDietPlansByMember()`. |
| `DataStore` | Singleton providing `users()`, `plans()`, `memberships()`, `billing()`, `sessions()`, `reschedules()`, `metrics()`. |

### `com.fittrack.fittrack.service` (In Progress)
| Class | Role |
|---|---|
| `UserFactory` | Factory for creating `Member` and `Trainer` instances with generated IDs. |
| `AuthService` | Login (role-checked), member registration (auto-assigns trainer), profile updates, client lookup. |