package org.oladushek;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        Foo foo = new Foo();

        Thread t1 = new Thread(foo::second);
        Thread t2 = new Thread(foo::first);
        Thread t3 = new Thread(foo::third);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


class Foo {

    private final ReentrantLock lock = new ReentrantLock();

    private final Condition firstMethodLockCondition = lock.newCondition();

    private final Condition secondMethodLockCondition = lock.newCondition();

    public void first() {
        lock.lock();
        try{
            System.out.println("first");
            firstMethodLockCondition.signal();
        }
        finally {
            lock.unlock();
        }
    }

    public void second() {
        lock.lock();
        try{
            firstMethodLockCondition.await();
            System.out.println("second");
            secondMethodLockCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void third() {
        lock.lock();
        try{
            secondMethodLockCondition.await();
            System.out.println("third");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}


