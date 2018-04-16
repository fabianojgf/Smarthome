package org.smart.home.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkUtil {
	public static String getHostAddress() {
		String ipv4 = "";
		Enumeration<?> e;
		try {
			e = NetworkInterface.getNetworkInterfaces();
			
			while(e.hasMoreElements())
			{
			    NetworkInterface n = (NetworkInterface) e.nextElement();
			    
			    Enumeration<?> ee = n.getInetAddresses();
			    while (ee.hasMoreElements())
			    {
			    	InetAddress i = (InetAddress) ee.nextElement();
			        if(i.isSiteLocalAddress() && n.getHardwareAddress() != null)
			        	ipv4 = i.getHostAddress();
			    }
			}
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		return ipv4;
	}
	
	public static void printInetNetworkInformation(NetworkInterface n) {
		System.out.println(n.getName() + " - " + n.getDisplayName());
	    try {
			System.out.println("isLoopback = " + n.isLoopback());
		    System.out.println("isPointToPoint = " + n.isPointToPoint());
		    System.out.println("isUp = " + n.isUp());
		    System.out.println("isVirtual = " + n.isVirtual());
		    
		    Enumeration<?> ee = n.getInetAddresses();
		    while (ee.hasMoreElements())
		    {
		    	InetAddress i = (InetAddress) ee.nextElement();
		    	
		    	printInetInformation(i);
		    	
		    	if(n.getHardwareAddress() != null)
		    		System.out.println("	" + n.getHardwareAddress()[0]);
		    	
		    	System.out.println("\n");
		    }
	    } catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public static void printInetNetworkInformation(NetworkInterface n, InetAddress i) {
		try {
			System.out.println(n.getName() + " - " + n.getDisplayName());
		    System.out.println("isLoopback = " + n.isLoopback());
		    System.out.println("isPointToPoint = " + n.isPointToPoint());
		    System.out.println("isUp = " + n.isUp());
		    System.out.println("isVirtual = " + n.isVirtual());
		    
		    printInetInformation(i);
	    	
	    	if(n.getHardwareAddress() != null)
	    		System.out.println("	" + n.getHardwareAddress()[0]);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public static void printInetInformation(InetAddress i) {
		System.out.println("	" + i.getHostAddress() + " - " + i.getHostName());
    	System.out.println("	isAnyLocalAddress = " + i.isAnyLocalAddress());
    	System.out.println("	isLinkLocalAddress = " + i.isLinkLocalAddress());
    	System.out.println("	isLoopbackAddress = " + i.isLoopbackAddress());
    	System.out.println("	isMCGlobal = " + i.isMCGlobal());
    	System.out.println("	isMCLinkLocal = " + i.isMCLinkLocal());
    	System.out.println("	isMCNodeLocal = " + i.isMCNodeLocal());
    	System.out.println("	isMCOrgLocal = " + i.isMCOrgLocal());
    	System.out.println("	isMCSiteLocal = " + i.isMCSiteLocal());
    	System.out.println("	isMulticastAddress = " + i.isMulticastAddress());
    	System.out.println("	isSiteLocalAddress = " + i.isSiteLocalAddress());
	}
}
