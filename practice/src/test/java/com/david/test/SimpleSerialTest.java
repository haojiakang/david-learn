package com.david.test;

import com.david.learn.serialize.Gender;
import com.david.learn.serialize.Person;
import org.junit.Test;

import java.io.*;

public class SimpleSerialTest {

    @Test
    public void testFileOutPut() {
        File file = new File("person.out");

        try (ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(file));
             ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file))) {
            Person person = new Person("John", 101, Gender.MALE);
            oout.writeObject(person);
            oout.close();

            Person newPerson = (Person) oin.readObject();
            oin.close();
            System.out.println(newPerson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testByteOutPut() {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(os)) {
            Person person = new Person("Lili", 98, Gender.FEMALE);
            out.writeObject(person);

            byte[] personBytes = os.toByteArray();
            ByteArrayInputStream is = new ByteArrayInputStream(personBytes);
            ObjectInputStream in = new ObjectInputStream(is);
            Person newPerson = (Person) in.readObject();
            is.close();
            System.out.println(newPerson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
