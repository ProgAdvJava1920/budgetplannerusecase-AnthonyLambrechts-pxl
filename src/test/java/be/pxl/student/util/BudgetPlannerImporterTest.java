package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BudgetPlannerImporterTest {
    String line;
    BudgetPlannerImporter importer;


    @BeforeEach
    public void BeforeAll() {
        line = "Jos,BE69771770897312,BE75332522943076,Sun Feb 16 22:41:04 CET 2020,-2494.72,EUR,Asperiores perspiciatis veniam esse vero qui itaque sunt.";
        importer = new BudgetPlannerImporter();
        importer.Mapper("src\\main\\resources\\account_payments.csv");

    }

    @Test
    public void CreatePaymentTest() {
        //Arrange
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        LocalDateTime date = LocalDateTime.parse("Sun Feb 16 22:41:04 CET 2020", formatter);
        float amount = (float)-2494.72;
        String currency = "EUR";
        String detail =  "Asperiores perspiciatis veniam esse vero qui itaque sunt.";
        Payment payment = new Payment(date, amount, currency, detail);

        //Act
        Payment sut = importer.createPayment(line);

        //Assert
        Assertions.assertEquals(sut.toString(), payment.toString());
    }

    @Test
    public void CreateAccountTest(){
        //Arrange
        String iban = "BE69771770897312";
        String name = "Jos";


        Account account = new Account();
        account.setIBAN(iban);
        account.setName(name);

        List<Payment> payments = new ArrayList<>();
        payments.add(importer.createPayment(line));
        account.setPayments(payments);

        //Act
        Account sut = importer.createAccount(line);
        sut.setPayments(payments);


        //Assert
        Assertions.assertEquals(sut.toString(), account.toString());
    }
}
