package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {
    private BufferedReader reader;
    private String fileName;
    private Account account;

    public BudgetPlannerImporter(String filename) {
        fileName = filename;
        Path path = Paths.get(fileName);
        try {
            reader = Files.newBufferedReader(path);
            Mapper();
        } catch (IOException e) {
            System.out.println("bla");
        }
    }

    public void Mapper() {
        try {
            String line = reader.readLine();
            line = reader.readLine();
            List<Payment> payments = new ArrayList<>();

            account = createAccount(line);

            Payment payment;
            while (line != null) {
                payment = createPayment(line);
                payments.add(payment);
                line = reader.readLine();
            }
            account.setPayments(payments);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Account createAccount(String line) {
        String[] splitString = line.split(",");
        Account account = new Account();
        account.setIBAN(splitString[1]);
        account.setName(splitString[0]);
        return account;
    }

    private Payment createPayment(String line) {
        Payment payment;

        String[] splitString = line.split(",");
        String datum;

        //Sun Feb 16 22:41:04 CET 2020
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        LocalDateTime date = LocalDateTime.parse(splitString[3], formatter);
        payment = new Payment(date, Float.parseFloat(splitString[4]), splitString[5], splitString[6]);

        return payment;
    }

    public Account getAccount() {
        return account;
    }
}
