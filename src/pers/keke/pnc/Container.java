/**
 *
 * @author Keke
 */

package pers.keke.pnc;

import java.util.concurrent.Semaphore;

public class Container {

	static Container container = new Container();
	// Define the max length of the queue
	private final int MAX_SIZE = 9 ;
	
	private Dessert [] desserts = new Dessert[MAX_SIZE];

	public enum Dessert {
		doughnut,
		cake,
		none
	}

	public Dessert [] getDesserts() {
		return desserts;
	}

	public int getContainerSize() {
		return MAX_SIZE;
	}

	private Semaphore mutex = new Semaphore(1);
	private Semaphore full = new Semaphore(0);
	private Semaphore empty = new Semaphore(MAX_SIZE);
	
	private int consumeIndex = 0;
	private int produceIndex = 0;
//	private int count = 0;
	
	public void consume() {
		
		try {
			full.acquire();
			mutex.acquire();
			//取走
			desserts[consumeIndex] = Dessert.none;
			//count--;
		//	System.out.println(Thread.currentThread().getName() + " consume " + consumeIndex);
			System.out.println(getCurrentLog());
			setLog(getCurrentLog());

			consumeIndex++;
			consumeIndex %= MAX_SIZE;
			
			mutex.release();
			empty.release();
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}
	
	public void produce() {
		try {
			empty.acquire();
			mutex.acquire();

			if(getID() == 1) {
				desserts[produceIndex] = Dessert.doughnut;
			} else if (getID() == 2) {
				desserts[produceIndex] = Dessert.cake;
			}
		//	System.out.println(Thread.currentThread().getName() + " produce " + produceIndex);
			System.out.println(getCurrentLog());
            setLog(getCurrentLog());

			produceIndex ++;
			produceIndex %= MAX_SIZE;
			
			mutex.release();
			full.release();
			
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public int getID() {
		String name = Thread.currentThread().getName();
		switch (name) {
			case "Chef     1" : return 1;
			case "Chef     2" : return 2;
			case "Customer 1" : return 3;
			case "Customer 2" : return 4;
			default: return 0;
		}
	}

	private String getCurrentLog() {
		String name = Thread.currentThread().getName();
		int ID = getID();
		String condition;
		int index = 0;
		switch (ID) {
			case 1:
			case 2: {condition = " puts dessert in   container ";
					 index = produceIndex;}break;
			case 3:
			case 4: {condition = " buys dessert from container ";
					 index = consumeIndex;}break;
			default: condition = "null";break;
		}
		return name + condition + Integer.toString(index) ;
	}
	
	private String log = "";

	public String getLog() {
		return log;
	}

	private void setLog(String temp) {
		log += temp + "\n";
	}
	

	
	public static void main(String[] args) {

//	    if (Platform.isSupported(ConditionalFeature.INPUT_METHOD)) {
//	        System.out.println("yes!");
//        };
		Thread produceThread1 = new Thread(new Producer(container), "Chef     1");
		Thread produceThread2 = new Thread(new Producer(container), "Chef     2");
		Thread consumeThread1 = new Thread(new Consumer(container), "Customer 1");
		Thread consumeThread2 = new Thread(new Consumer(container), "Customer 2");

		produceThread1.start();
		produceThread2.start();
		consumeThread1.start();
		consumeThread2.start();

	}

}
