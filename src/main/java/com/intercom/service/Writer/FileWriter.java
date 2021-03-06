package com.intercom.service.Writer;

import com.intercom.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class FileWriter implements IFileWriter {

    @Override
    public void write(List<Customer> customerList) {
        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream("customers.txt"));
            String newLine = System.getProperty("line.separator");

            // Write objects to file
            for (Customer customer : customerList) {
                dos.writeBytes(customer.getName() + " " + customer.getUser_id());
                dos.writeBytes(newLine);
            }

            dos.close();

        } catch (FileNotFoundException e) {
            log.error("File not found");
        } catch (IOException e) {
            log.error("Error initializing stream");
        }
    }
}
