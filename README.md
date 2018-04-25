# SenateBusProblem

This is Java Implementation of the Semaphore based solution for Senate Bus Problem

The Senate Bus Problem
(From The Little Book of Semaphores by Allen B. Downey)

This problem was originally based on the Senate bus at Wellesley College. Riders come to a bus stop and wait for a bus. 
When the bus arrives, all the waiting riders invoke boardBus, but anyone who arrives while the bus is boarding has to 
wait for the next bus. The capacity of the bus is 50 people; if there are more than 50 people waiting, some will have 
to wait for the next bus. When all the waiting riders have boarded, the bus can invoke depart. If the bus arrives when 
there are no riders, it should depart immediately.

Class “Simulation” contains the main method.

If you are using the command prompt :
1) compile it using : javac Simulation.java
2) run it using : java Simulation

You will be prompted to enter a integer parameter to rider count.

Using paramater in Class Bus,
Program is set to terminate after 60 seconds.(TERMINATION_TIME)

Delay after one bus turn is set to 2 seconds. (DELAY_AFTER_ONE_TURN)

Intial delay for bus arrival is set to 1 seconds. (INITIAL_DELAY)

https://notebookbft.wordpress.com/2015/08/24/the-senate-bus-problem/#more-55
