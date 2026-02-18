package reciepts;

import model.Transaction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReceiptGenerator {
    private static String path = "src/reciepts/generatedReceipts/";
    public static void generateReceipt(Transaction t){

        try{
            File dir = new File(path);
            if(!dir.exists()){
                dir.mkdirs();
            }

            String fileName = "Receipt_" + t.getAccountNumber() + "_" +System.currentTimeMillis() + ".txt";
            File receiptFile = new File(dir, fileName);

            try(BufferedWriter writer = new BufferedWriter(new FileWriter(receiptFile));) {
                writer.write("====================GG Bank======================");
                writer.newLine();

                writer.write("Date of Transaction  : " + t.getTransactionDate());
                writer.newLine();
                writer.write("Transaction Type     :  " + t.getTransactionType());
                writer.newLine();
                if (t.getTransactionType().equals("Transfer")) {
                    writer.write("From                 : " + t.getRelatedAccountNumber());
                    writer.newLine();
                    writer.write("To(your account)     : " + t.getAccountNumber());
                    writer.newLine();
                }
                writer.write("Amount               : ₹" + t.getAmount());
                writer.newLine();
                writer.write("Description          : " + t.getDescription());
                writer.newLine();
                writer.write("=================================================");
            }

        } catch(SecurityException | IOException e) {
            System.out.println("Error: Failed to generate receipt due to " + e.getMessage() + " problem.");
        }

    }
}
