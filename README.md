# Smart Utility Bill Calculator ⚡

A Java console application that helps users accurately verify their electricity bills for both **Residential** and **Commercial** connections using a slab-based pricing model.

## Features

- 🏠 **Residential Bill Calculation** – Slab-based pricing with 8% tax
- 🏢 **Commercial Bill Calculation** – Slab-based pricing with 12% tax
- 📊 Interactive menu-driven interface
- ✅ Fixed charges + unit consumption calculation

## Slab Rates

### Residential
| Units Consumed | Rate (₹/unit) |
|----------------|---------------|
| 0 – 100        | ₹3.50         |
| 101 – 200      | ₹5.00         |
| 201 – 400      | ₹7.00         |
| 401+           | ₹9.50         |

> Fixed Charge: ₹50 | Tax: 8%

### Commercial
| Units Consumed | Rate (₹/unit) |
|----------------|---------------|
| 0 – 200        | ₹6.50         |
| 201 – 500      | ₹8.50         |
| 501+           | ₹11.00        |

> Fixed Charge: ₹150 | Tax: 12%

## How to Run

### Prerequisites
- Java JDK 8 or above

### Compile
```bash
javac SmartUtilityBillCalculator.java
```

### Run
```bash
java SmartUtilityBillCalculator
```

## Project Structure

```
├── SmartUtilityBillCalculator.java   # Main source file
├── .gitignore
└── README.md
```

## OOP Concepts Used

- **Abstraction** – Abstract `Bill` base class
- **Inheritance** – `ResidentialElectricityBill` and `CommercialElectricityBill` extend `Bill`
- **Polymorphism** – Overridden `calculateBill()` and `displayBillDetails()` methods

## Author

Made with ☕ and Java
