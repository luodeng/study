package com.roden.study.java.util.concurrent.locks;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition中的await()方法相当于Object的wait()方法，
 * Condition中的signal()方法相当于Object的notify()方法
 * Condition中的signalAll()相当于Object的notifyAll()方法
 */

class Buffer {
	private  final Lock lock;
	private  final Condition notFull;
	private  final Condition notEmpty;
	private int	maxSize;
	private List<Date> storage;
	Buffer(int size){
		//使用锁lock，并且创建两个condition，相当于两个阻塞队列
		lock=new ReentrantLock();
		notFull=lock.newCondition();
		notEmpty=lock.newCondition();
		maxSize=size;
		storage=new LinkedList<>();
	}
	public void put()  {
		lock.lock();
		try {
			//如果队列满了
			while (storage.size() ==maxSize ){
				System.out.print(Thread.currentThread().getName()+": wait \n");
				//阻塞生产线程
				notFull.await();
			}
			storage.add(new Date());
			System.out.print(Thread.currentThread().getName()+": put:"+storage.size()+ "\n");
			Thread.sleep(1000);
			//唤醒消费线程
			notEmpty.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{	
			lock.unlock();
		}
	}

	public	void take() {		
		lock.lock();
		try {
			//如果队列空了
			while (storage.size() ==0 ){
				System.out.print(Thread.currentThread().getName()+": wait \n");
				//阻塞消费线程
				notEmpty.await();
			}
			Date d=((LinkedList<Date>)storage).poll();
			System.out.print(Thread.currentThread().getName()+": take:"+storage.size()+ "\n");
			Thread.sleep(1000);
			//唤醒生产线程
			notFull.signalAll();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	} 
}

class Producer implements Runnable{
	private Buffer buffer;
	Producer(Buffer b){
		buffer=b;
	}
	@Override
	public void run() {
		while(true){
			buffer.put();
		}
	}	
}
class Consumer implements Runnable{
	private Buffer buffer;
	Consumer(Buffer b){
		buffer=b;
	}
	@Override
	public void run() {
		while(true){
			buffer.take();
		}
	}	
}
/**
 * @author Roden
 */
public class ConditionProducerConsumer{
	public static void main(String[] arg){
		Buffer buffer=new Buffer(10);
		Producer producer=new Producer(buffer);
		Consumer consumer=new Consumer(buffer);
		for(int i=0;i<3;i++){
			new Thread(producer,"producer-"+i).start();
		}
		for(int i=0;i<3;i++){
			new Thread(consumer,"consumer-"+i).start();
		}
	}
}

