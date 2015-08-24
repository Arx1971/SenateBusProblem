/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senatebusproblem;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Rajind
 */
public class SharedData {
    private final AtomicInteger waiting_riders_count = new AtomicInteger(0);
    private final Semaphore riders_mutex = new Semaphore(1);  
    private final Semaphore bus = new Semaphore(0);  
    private final Semaphore boarded = new Semaphore(0);

    public AtomicInteger getWaiting_riders_count() {
        return waiting_riders_count;
    }

    public Semaphore getRiders_mutex() {
        return riders_mutex;
    }

    public Semaphore getBus() {
        return bus;
    }

    public Semaphore getBoarded() {
        return boarded;
    }
}
