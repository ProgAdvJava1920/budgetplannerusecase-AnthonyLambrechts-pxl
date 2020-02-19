package be.pxl.student;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.util.BudgetPlannerImporter;

public class BudgetPlanner {
    public static void main(String[] args) {
        BudgetPlannerImporter importer = new BudgetPlannerImporter("src\\main\\resources\\account_payments.csv");

        Account account = importer.getAccount();
        for (Payment payment : account.getPayments()) {
            System.out.println(payment.toString());
        }
    }

}
