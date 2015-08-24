package senatebusproblem;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rajind
 */
public class Rider extends Thread{
    private final SharedData sharedData;
    
    public Rider(SharedData sharedData) {
        this.sharedData = sharedData;
    }
        
    @Override
    public void run(){
       this.runMethod();
    }
    
    private void runMethod(){
        try {
            /*
            New rider that comes to the bus stop waits on the riders mutex 
            to enter the queue (to update waiting_riders_count value)
            */
            sharedData.getRiders_mutex().acquire();
            /*
            New rider acuires the riders mutex
            */
            System.out.println("Riders mutex aquired by a Rider");
            
        
        } catch (InterruptedException ex) {
            Logger.getLogger(Rider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sharedData.getWaiting_riders_count().incrementAndGet();
        /*
        New rider enters the queue by incrementing waiting_riders_count value    
        */
        
        sharedData.getRiders_mutex().release();
        /*
        New rider releases the riders mutex after entering the queue
        */
        
        System.out.println("Riders mutex released by Rider");
        
        try {
            /*
            Now the rider wait to aqcuire the bus semaphore to enter the bus.
            It has to wait till Bus signals to acquire the lock.
            */
            sharedData.getBus().acquire();
            /*
            Rider is able to acquire the bus semaphore
            */
            
            System.out.println("Bus semaphore acquired by a Rider");
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Rider.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Rider boards bus");
        sharedData.getBoarded().release();
        /*
        Rider signals that he has boarded
        */
        System.out.println("Boarded semaphore released by Rider");
    }
}
