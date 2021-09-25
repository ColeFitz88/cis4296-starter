package edu.temple.cis.c3238.banksim;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Cay Horstmann
 * @author Modified by Paul Wolfgang
 * @author Modified by Charles Wang
 * @author Modified by Alexa Delacenserie
 * @author Modified by Tarek Elseify
 */
public class Account {

    private volatile int balance;
    private final int id;
    public ReentrantLock lock;

    public Account(int id, int initialBalance) {
        this.id = id;
        this.balance = initialBalance;
        this.lock = new ReentrantLock();
    }

    public int getBalance() {
        return balance;
    }

    public boolean withdraw(int amount) {
        
    try {	
    	lock.lock();
    	if (amount <= balance) {
            int currentBalance = balance;
            Thread.yield(); // Try to force collision
            int newBalance = currentBalance - amount;
            balance = newBalance;
            return true;
        } else {
            return false;
        }
    } finally {
    	lock.unlock();
    }
    }

    public void deposit(int amount) {
       try {
    	   lock.lock();
    	int currentBalance = balance;
        Thread.yield();   // Try to force collision
        int newBalance = currentBalance + amount;
        balance = newBalance;
       } finally {
    	   lock.unlock();
       }
    }
    
    @Override
    public String toString() {
        return String.format("Account[%d] balance %d", id, balance);
    }
}