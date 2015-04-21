package com.akash.threading;

public class MyInterrupt {

	public static void main(String args[]) {

		MyThread2 t1 = new MyThread2();
		t1.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("1st check status :"+t1.isInterrupted());
		t1.interrupt();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("2nd check status :"+t1.isInterrupted());
	}
}

class MyThread2 extends Thread {

	public void run() {

		Thread currentThread = Thread.currentThread();
		System.out.println("status before interrupt:"+currentThread.isInterrupted());
		System.out.println("waiting in blocked pool");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			System.out.println("status after interrupt:"+currentThread.isInterrupted());
		}
	}
}
