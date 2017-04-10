package com.david.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by haojk on 4/4/17.
 */
public class AtomicReferenceTest {

    public static AtomicReference<User> atomicUserRef = new AtomicReference();

    static class User {
        private String name;
        private int old;

        public User(String name, int old) {
            this.name = name;
            this.old = old;
        }

        public String getName() {
            return this.name;
        }

        public int getOld() {
            return this.old;
        }

        public static void main(String[] args) {
            User user = new User("conan", 5);
            atomicUserRef.set(user);
            User updateUser = new User("Shinichi", 17);
            atomicUserRef.compareAndSet(user, updateUser);
            System.out.println(atomicUserRef.get().getName());
            System.out.println(atomicUserRef.get().getOld());
        }
    }
}
