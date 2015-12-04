package com.avaya.plds.service;

public class Balancer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] a={1,2,3,4,5,11,7,8,9,10};
		int serverCount=a.length;
		int totalRequests=0;
		int maxRequest=0;
		int maxRequestIndex=0;
		int loadPerServer=0;
		int rightLoad=0;
		int leftLoad=0;
		int leftCount=0;
		int rightCount=0;
		for (int i = 0; i < a.length; i++) {
			totalRequests=totalRequests+a[i];
			if(a[i]>=maxRequest){
				maxRequest=a[i];
				maxRequestIndex=i;
			    }
		}
		
		loadPerServer=totalRequests/serverCount;
		System.out.println("loadPer server"+loadPerServer);
		System.out.println("maxRequest"+maxRequest);
		System.out.println("maxRequestIndex"+maxRequestIndex);
		rightCount=serverCount-(maxRequestIndex+1);
		leftCount=maxRequestIndex;
		System.out.println("rightCountrightCount"+rightCount);
		System.out.println("leftCount"+leftCount);
		
		for(int i=0;i<rightCount;i++){
			if (a[maxRequestIndex+i]>loadPerServer){
				a[maxRequestIndex+i+1]=	a[maxRequestIndex+i+1]+(a[maxRequestIndex+i]-loadPerServer);
				a[maxRequestIndex+i]=loadPerServer;
				System.out.println("1"+a);
			}
			else if (a[maxRequestIndex+i]<loadPerServer){
				a[maxRequestIndex+i+1]=	a[maxRequestIndex+i+1]+(a[maxRequestIndex+i]-loadPerServer);
				a[maxRequestIndex+i]=loadPerServer;
				System.out.println("1"+a);
			}
			
		}
		
		
		
	  

	}

}
