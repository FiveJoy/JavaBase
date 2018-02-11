package com.fivejoy.threads;

import java.util.concurrent.atomic.AtomicInteger;
///BlockingQueueֻ�����ڷ�����Ϊһ̨����redis�Ƿֲ�ʽ�Ŀ��Զ�̨��������
public class Solution1 {
	private static int count;
	private static ThreadLocal<Integer> threadLocalCount=new ThreadLocal<>();
	//�൱��һ����װ�� �ɽ�Integer��װ threadLocalCount.get();
	private static int water;
	private static AtomicInteger atomicWater;//ԭ�ӻ�����-����sychronized
	
	public static void testNOThreadLocal(){
		for(int i=0;i<10;i++){
			final int finalI=i;
			new Thread(new Runnable() {
				//local variable  defined in an enclosing scope must be final or effectively final
				//�����ͨ�����ж�����ڲ��࣬�����Ҫ���ʷ����Ĳ����򷽷��ж���ı���ʱ����Щ���������ǰ��һ��Ҫ����һ��final�ؼ��֣������޷����á�
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
				//�����ͨ�����ж�����ڲ��࣬�����Ҫ���ʷ����Ĳ����򷽷��ж���ı���ʱ����Щ���������ǰ��һ��Ҫ����һ��final�ؼ��֣������޷����á�
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
//		1. �ֲ߳̾���������ʹ��һ��static��Ա��ÿ���̷߳��ʵı����ǲ�ͬ�ġ�
//		2. ������web�д洢��ǰ�û���һ����̬�������У����̵߳��κεط������Է��ʵ���ǰ�̵߳��û���
//		3. �ο�HostHolder.java���users
		testNOThreadLocal();
		//��ĳһʱ�̣���Thread.sleep��ʱ�򣩣�I=6��ֵ��count���ˡ���Ϊstatic��֤��countֻ��һ�ݣ���������Ҹĵľ���һ��count
		testNOAtomic();
		//testAtomic()�����⣿����
	}

}
//AtomicInteger ������������sychronized��
//ThreadLocal���߳���Ч�ġ������ ĳ�ֲ߳̾���Ч
//��ʹ��static ��
//
//�൱��һ����װ�� �ɽ�Integer��װ
//ThreadLocal<Integer> threadlocalUserID=new ThreadLocal<Integer>
//
//BlockingQueueֻ�����ڷ�����Ϊһ̨����redis�Ƿֲ�ʽ�Ŀ��Զ�̨��������
