package motorph.payroll;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Scanner;

public class MotorPhPayroll {

    public static void main(String[] args) {

        // SAMPLE EMPLOYEE DATA (can be replaced later with file/DB)
        int employeeNumber = 10001;
        String employeeName = "Garcia, Manuel III";
        String birthday = "10/11/1983";
        double hourlyRate = 313.51; // sample hourly rate

        Scanner scanner = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("      MOTORPH PAYROLL SYSTEM     ");
        System.out.println("=================================");

        // Display Employee Info
        System.out.println("\nEMPLOYEE INFORMATION");
        System.out.println("Employee Number: " + employeeNumber);
        System.out.println("Employee Name: " + employeeName);
        System.out.println("Birthday: " + birthday);

        // Input attendance for 5 working days
        double totalWeeklyHours = 0;

        for (int day = 1; day <= 5; day++) {
            System.out.println("\nDAY " + day);

            System.out.print("Enter Time In (HH:mm): ");
            String timeInStr = scanner.nextLine();

            System.out.print("Enter Time Out (HH:mm): ");
            String timeOutStr = scanner.nextLine();

            LocalTime timeIn = LocalTime.parse(timeInStr);
            LocalTime timeOut = LocalTime.parse(timeOutStr);

            // Official start time and grace period
            LocalTime officialStart = LocalTime.of(8, 0);
            LocalTime gracePeriodEnd = LocalTime.of(8, 10);

            LocalTime creditedTimeIn;

            // Apply 10-minute grace period
            if (timeIn.isAfter(gracePeriodEnd)) {
                creditedTimeIn = timeIn;   // late
            } else {
                creditedTimeIn = officialStart; // within grace period
            }

            long minutesWorked = Duration.between(creditedTimeIn, timeOut).toMinutes();
            double hoursWorked = minutesWorked / 60.0;

            if (hoursWorked < 0) {
                hoursWorked = 0;
            }

            totalWeeklyHours += hoursWorked;

            System.out.printf("Credited Hours for Day %d: %.2f hours\n", day, hoursWorked);
        }

        // Compute Gross Weekly Salary
        double grossWeeklySalary = totalWeeklyHours * hourlyRate;

        // Mock deductions for Phase 1
        double sss = grossWeeklySalary * 0.05;         // 5%
        double philhealth = grossWeeklySalary * 0.03;  // 3%
        double pagibig = grossWeeklySalary * 0.02;     // 2%
        double withholdingTax = grossWeeklySalary * 0.10; // 10%

        double totalDeductions = sss + philhealth + pagibig + withholdingTax;
        double netWeeklySalary = grossWeeklySalary - totalDeductions;

        // Display Salary Summary
        System.out.println("\n=================================");
        System.out.println("        WEEKLY PAY SUMMARY       ");
        System.out.println("=================================");
        System.out.printf("Total Weekly Hours Worked: %.2f hours\n", totalWeeklyHours);
        System.out.printf("Hourly Rate: PHP %.2f\n", hourlyRate);
        System.out.printf("Gross Weekly Salary: PHP %.2f\n", grossWeeklySalary);

        System.out.println("\nDEDUCTIONS:");
        System.out.printf("SSS (5%%): PHP %.2f\n", sss);
        System.out.printf("PhilHealth (3%%): PHP %.2f\n", philhealth);
        System.out.printf("Pag-IBIG (2%%): PHP %.2f\n", pagibig);
        System.out.printf("Withholding Tax (10%%): PHP %.2f\n", withholdingTax);

        System.out.printf("\nTotal Deductions: PHP %.2f\n", totalDeductions);
        System.out.printf("Net Weekly Salary: PHP %.2f\n", netWeeklySalary);

        System.out.println("\nThank you for using MotorPH Payroll System!");
        scanner.close();
    }
}
