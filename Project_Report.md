# BYOP Project Report
## Smart Utility Bill Calculator

---

| Field                  | Details                          |
|------------------------|----------------------------------|
| **Student Name**       | Rahul Malik                      |
| **Registration Number**| 24BAI10816                       |
| **Course**             | Object-Oriented Programming with Java |
| **Platform**           | VITyarthi                        |
| **GitHub Repository**  | https://github.com/coder-rahulm/smart-utility-bill-calculator |
| **Submission Date**    | March 31, 2026                  |

---

## 1. Problem Statement

Electricity bills in India follow a **tiered/slab-based pricing model**, where the per-unit rate increases as consumption increases. Most residential and commercial consumers receive their monthly bills without any clear understanding of how the final amount is computed. This creates a significant **information asymmetry** — consumers cannot independently verify whether the billed amount is correct, and overcharging or miscalculation often goes unnoticed.

The problem, stated simply:

> **How can a consumer — residential or commercial — quickly and accurately verify their electricity bill using standard slab rates, without relying entirely on the utility provider's calculation?**

---

## 2. Why This Problem Matters

- In India, millions of households and businesses receive electricity bills monthly. Even a small per-unit miscalculation at scale results in significant financial impact.
- Consumers, especially in rural or semi-urban areas, often lack the digital tools to cross-check their bills.
- Utility billing disputes are common and frequently unresolved due to complexity in the slab structure.
- Building awareness around pricing structures empowers citizens to challenge incorrect bills.

This project directly addresses a **financial transparency and consumer empowerment** problem that affects everyday life — making it a meaningful, purpose-driven application.

---

## 3. Objectives

1. Implement a console-based Java application that accurately computes electricity bills.
2. Support both **Residential** and **Commercial** connection types with their respective slab rates.
3. Apply slab-based progressive pricing (similar to income tax brackets).
4. Include fixed charges and applicable tax rates.
5. Present a clean, readable bill summary to the user.

---

## 4. Course Concepts Applied

This project was built using core Object-Oriented Programming concepts covered in the course:

| OOP Concept       | How It Was Applied |
|-------------------|--------------------|
| **Abstraction**   | `Bill` is an abstract class that defines the contract (`calculateBill()` and `displayBillDetails()`) without implementing the specifics |
| **Inheritance**   | `ResidentialElectricityBill` and `CommercialElectricityBill` both extend the `Bill` abstract class, inheriting shared attributes like `customerName`, `currentReading`, and `previousReading` |
| **Polymorphism**  | Both subclasses override `calculateBill()` and `displayBillDetails()` — the correct version is invoked at runtime based on object type |
| **Encapsulation** | Rate constants and fixed charges are declared as `private static final` members, protecting them from external modification |
| **Classes & Objects** | The main class creates instances of the appropriate bill type based on user input |

---

## 5. System Design and Approach

### 5.1 Architecture

The application follows a simple **three-tier class hierarchy**:

```
           +----------+
           |   Bill   |  (Abstract Base Class)
           +----------+
           /          \
          /            \
+-----------------------+   +---------------------------+
| ResidentialElectricity|   | CommercialElectricity     |
| Bill                  |   | Bill                      |
+-----------------------+   +---------------------------+

          +-----------------------------+
          | SmartUtilityBillCalculator  |  (Main Driver Class)
          +-----------------------------+
```

### 5.2 Billing Logic

**Residential Slab Rates:**

| Slab           | Units        | Rate (₹/unit) |
|----------------|--------------|---------------|
| Slab 1         | 0 – 100      | ₹3.50         |
| Slab 2         | 101 – 200    | ₹5.00         |
| Slab 3         | 201 – 400    | ₹7.00         |
| Slab 4         | 401+         | ₹9.50         |

- Fixed Charge: ₹50.00
- Tax: 8% on total (fixed + unit charges)

**Commercial Slab Rates:**

| Slab           | Units        | Rate (₹/unit) |
|----------------|--------------|---------------|
| Slab 1         | 0 – 200      | ₹6.50         |
| Slab 2         | 201 – 500    | ₹8.50         |
| Slab 3         | 501+         | ₹11.00        |

- Fixed Charge: ₹150.00
- Tax: 12% on total (fixed + unit charges)

### 5.3 Calculation Method

The billing is calculated **progressively** — each slab only applies to the units consumed within that band, similar to progressive income tax. For example, if a residential user consumes 250 units:

```
Bill = Fixed Charge + (100 × 3.50) + (100 × 5.00) + (50 × 7.00)
     = 50 + 350 + 500 + 350
     = 1250
After 8% tax = 1250 × 1.08 = ₹1,350
```

### 5.4 User Interaction Flow

```
Start
  ↓
Display Menu (Residential / Commercial / Exit)
  ↓
User selects option
  ↓
Input: Customer Name, Current Reading, Previous Reading
  ↓
Create appropriate Bill object
  ↓
Call displayBillDetails() → internally calls calculateBill()
  ↓
Show formatted bill summary
  ↓
Return to menu (loop continues until user exits)
```

---

## 6. Implementation Highlights

### Abstract Base Class — `Bill`

```java
abstract class Bill {
    protected String customerName;
    protected double currentReading;
    protected double previousReading;
    protected double unitsConsumed;

    public abstract double calculateBill();
    public abstract void displayBillDetails();
}
```

This enforces a contract — every bill type **must** implement its own calculation and display logic.

### Polymorphic Object Creation

```java
Bill bill;
if (choice == 1) {
    bill = new ResidentialElectricityBill(customerName, currentReading, previousReading);
} else {
    bill = new CommercialElectricityBill(customerName, currentReading, previousReading);
}
bill.displayBillDetails(); // Polymorphism in action
```

---

## 7. Key Design Decisions

1. **Abstract class over Interface**: I chose an abstract class over an interface because the `Bill` class shares concrete state (fields like `customerName`, `unitsConsumed`) across subclasses — something interfaces cannot hold.

2. **`private static final` for rate constants**: All slab rates and fixed charges are declared as `private static final double` to make them immutable and class-level — preventing accidental modification and ensuring constants are shared efficiently across all instances.

3. **Progressive slab calculation**: Instead of simply multiplying total units by a single rate, the calculation properly applies each slab incrementally. This mirrors the actual utility billing process and ensures accuracy.

4. **Rounding to 2 decimal places**: `Math.round(billAmount * 100.0) / 100.0` ensures the bill is always presented as a proper monetary value, avoiding floating-point imprecision.

5. **do-while loop for the menu**: A `do-while` loop was chosen for the main menu so that it always executes at least once, which is the correct behavior for an interactive menu system.

---

## 8. Challenges Faced

### Challenge 1: Progressive Slab Calculation Logic
**Problem:** Initially, I applied a single flat rate to all units consumed, which gave incorrect results for consumers spanning multiple slabs.  
**Solution:** Restructured the `calculateBill()` method to use cumulative slab logic — calculating each band separately and adding them up.

### Challenge 2: Floating-Point Precision
**Problem:** Java floating-point arithmetic sometimes results in values like `1349.9999999` instead of `1350.00`, which looks unprofessional in a bill.  
**Solution:** Applied `Math.round()` with a scale of 100 to consistently round to 2 decimal places.

### Challenge 3: Scanner Input Buffer Issue
**Problem:** When mixing `nextInt()` and `nextLine()`, there was a newline character left in the buffer that caused the customer name input to be skipped.  
**Solution:** Added `scanner.nextLine()` after reading the integer choice to consume the leftover newline before reading the string.

### Challenge 4: Designing a Scalable Class Hierarchy
**Problem:** Deciding the right level of abstraction — what belongs in the base class vs. the subclasses.  
**Solution:** Moved all shared state (customer name, readings, units consumed) to the abstract `Bill` class and kept all pricing-specific logic within each subclass.

---

## 9. Sample Output

```
=== SMART ELECTRICITY BILL CALCULATOR ===
This tool helps verify your utility bills accurately!

1. Residential Bill
2. Commercial Bill
0. Exit
Choose option: 1

Enter Customer Name: Rahul Malik
Enter Current Meter Reading: 850
Enter Previous Meter Reading: 600

=== RESIDENTIAL ELECTRICITY BILL ===
Customer: Rahul Malik
Units Consumed: 250.00
Total Bill Amount: ₹1350.00
=====================================
```

---

## 10. What I Learned

1. **Abstraction is not just a design choice — it's a communication tool.** The abstract `Bill` class clearly communicates intent: "All bills must be able to calculate themselves and display their details." This makes the codebase self-documenting.

2. **Real problems have edge cases.** The slab billing logic, though seemingly simple, required careful handling of progressive tier boundaries. This taught me to always test boundary values (e.g., exactly 100 units, exactly 200 units).

3. **OOP makes code extensible.** Adding a new bill type (e.g., `IndustrialElectricityBill`) would require only creating a new subclass — the main driver and base class remain untouched. This is the power of the Open/Closed Principle.

4. **User experience matters even in console apps.** Small things like formatting output with `printf`, adding separator lines, and providing a clear menu loop greatly improve usability.

5. **Version control is a development discipline, not just a submission requirement.** Using `git` throughout development helped me track changes and maintain a clean project history on GitHub.

---

## 11. Future Enhancements

If this project were to be expanded, the following features could be added:

- **Water and gas bill support** — extending `Bill` with `WaterBill` and `GasBill` subclasses
- **GUI frontend** — a JavaFX or Swing interface for non-technical users
- **PDF bill generation** — saving the computed bill as a printable document
- **Database integration** — storing customer history using JDBC/SQLite
- **Dynamic slab configuration** — reading slab rates from a config file so they can be updated without changing code

---

## 12. Conclusion

The Smart Utility Bill Calculator successfully solves a real, everyday problem — helping consumers verify their electricity bills independently. The project meaningfully applies the core OOP principles of abstraction, inheritance, polymorphism, and encapsulation in a practical context.

More importantly, it demonstrates that software need not be complex to be useful. A well-structured, clearly documented solution to a simple but genuine problem is more valuable than a technically impressive but purposeless one.

The project met all its stated objectives, was version-controlled on GitHub, and reflects genuine problem-solving effort rooted in the course curriculum.

---

*Report prepared by **Rahul Malik** (24BAI10816)*  
*Submitted as part of the Bring Your Own Project (BYOP) capstone assignment*
