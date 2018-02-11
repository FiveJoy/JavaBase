package com.fivejoy.threads;

import java.util.concurrent.atomic.AtomicInteger;
///BlockingQueue只能用于服务器为一台。而redis是分布式的可以多台机器来用
public class Solution1 {
	private static int count;
	private static ThreadLocal<Integer> threadLocalCount=new ThreadLocal<>();
	//相当于一个包装类 可将Integer包装 threadLocalCount.get();
	private static int water;
	private static AtomicInteger atomicWater;//原子化整型-类似sychronized
	
	public static void testNOThreadLocal(){
		for(int i=0;i<10;i++){
			final int finalI=i;
			new Thread(new Runnable() {
				//local variable  defined in an enclosing scope must be final or effectively final
				//类的普通方法中定义的内部类，如果想要访问方法的参数或方法中定义的变量时，这些参数或变量前面一定要增加一个final关键字，否则无法调用。
				@Override
				public void run() {
					 try {
					count=finalI;
					Thread.sleep(1000);
					System.out.println("count="+ count+" "+System.currentTimeMillis());
				
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
					
				}
			}).start();
		}
	}
	public static void testThreadLocal(){
		for(int i=0;i<10;i++){
			final int finalI=i;
			new Thread(new Runnable() {
				//local variable  defined in an enclosing scope must be final or effectively final
				//类的普通方法中定义的内部类，如果想要访问方法的参数或方法中定义的变量时，这些参数或变量前面一定要增加一个final关键字，否则无法调用。
				@Override
				public void run() {
					 try {
					threadLocalCount.set(finalI);
					Thread.sleep(1000);
					System.out.println("threadLocalCount="+ threadLocalCount.get()+" "+System.currentTimeMillis());
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
					
				}
			}).start();
		}
	}
	
	public static void testAtomic(){
		for(int i=0;i<10;i++){
			new Thread(new Runnable() {			
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(int j=0;j<10;j++)
						System.out.println("atomicWater"+atomicWater.incrementAndGet());				
				}
			}).start();
		}
	}
	public static void testNOAtomic(){
		for(int i=0;i<10;i++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(int j=0;j<10;j++)
						System.out.println("water"+water++);
					
				}
			}).start();
		}
	}
	
	public static void main(String[] args){
		testThreadLocal();
//		1. 线程局部变量。即使是一个static成员，每个线程访问的变量是不同的。
//		2. 常见于web中存储当前用户到一个静态工具类中，在线程的任何地方都可以访问到当前线程的用户。
//		3. 参考HostHolder.java里的users
		testNOThreadLocal();
		//在某一时刻（在Thread.sleep的时候），I=6的值将count改了。因为static保证了count只有一份，所以最后大家改的就是一个count
		testNOAtomic();
		//testAtomic()有问题？不懂
	}

}
//AtomicInteger 反正就是类似sychronized呗
//ThreadLocal是线程有效的。即针对 某线程局部有效
//即使是static 的
//
//相当于一个包装类 可将Integer包装
//ThreadLocal<Integer> threadlocalUserID=new ThreadLocal<Integer>
//
//BlockingQueue只能用于服务器为一台。而redis是分布式的可以多台机器来用
