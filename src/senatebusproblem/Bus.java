package senatebusproblem;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rajind
 */
public class Bus implements Runnable{
    //number of passengers bus can take in one turn
    private final int SEAT_LIMIT = 50;
    
    //initial time before bus comes to the station
    private final int TERMINATION_TIME = 60;  //60 seconds

    //time delay after completion of one bus turn
    private final int DELAY_AFTER_ONE_TURN = 2; // 2 seconds
    
    private final SharedData sharedData;
    private final ScheduledExecutorService scheduler;
    
    public Bus(SharedData sharedData) {
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.sharedData = sharedData;
    }
    
    public void busActivity(){
     final Runnable senateBus = new Runnable() {
       public void run() {
           runMethod();
       }
     };
     
     /*executes a periodic action that becomes enabled first after the given 
     initial delay, and subsequently with the given delay between the termination 
     of one execution and the commencement of the next.
     */
     final ScheduledFuture<?> busHandle =
       scheduler.scheduleWithFixedDelay(senateBus, 0, DELAY_AFTER_ONE_TURN, TimeUnit.SECONDS);
     
     //executes a schedule future after a given delay
     scheduler.schedule(new Runnable(){ 
         public void run() {
             busHandle.cancel(true);
             System.exit(0);
         }
     }, TERMINATION_TIME, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        this.runMethod();
    }
    
    private void runMethod(){
        try {
            /*
            Bus waits to acquire riders mutex so that it can start boarding
            */
            sharedData.getRiders_mutex().acquire();
            /*
            Bus acquired writers lock. So now no other Rider can enter the queue
            till the Bus releases the lock
            */
            System.out.println("Riders mutex aquired by Bus");
            //System.out.println("Bus came to the stop & locked riders_count");
        } catch (InterruptedException ex) {
            Logger.getLogger(Bus.class.getName()).log(Level.SEVERE, null, ex);
        }

        int boarding_riders_count = Math.min(sharedData.getWaiting_riders_count().get(), SEAT_LIMIT);
        /*if there are riders more than the value of SEAT_LIMIT waiing then 
            we set r_count to SEAT_LIMIT
        */

        System.out.format("\nWaiting riders count: %d\n",sharedData.getWaiting_riders_count().get());
        System.out.format("\nBoarding riders count: %d\n", boarding_riders_count);

        for(int i=0; i < boarding_riders_count; i++){
            /*
            Bus signals riders that one of them can enter
            */
            sharedData.getBus().release();
            System.out.println("Bus semaphore released by Bus");
            try {
                sharedData.getBoarded().acquire();
                /*
                Bus aquires boarded sepahore to say rider has boarded
                */
                System.out.println("Boarded semaphore aquired by Bus");
            } catch (InterruptedException ex) {
                Logger.getLogger(Bus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        int n = sharedData.getWaiting_riders_count().get();
        sharedData.getWaiting_riders_count().set(Math.max(n - 50, 0));
        
        /*
        if(sharedData.getWaiting_riders_count().get() == 0){
            System.exit(0);
        }*/
        
        sharedData.getRiders_mutex().release();
        /*
        Bus updates waiting queue count and releases lock
        */
        System.out.println("Riders mutex released by Bus");
        System.out.println("Bus Departs");
    }
}
