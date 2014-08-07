package com.t3.networking;

import org.testng.annotations.Test;
import org.testng.Assert;
import com.t3.networking.registry.RegisteredServer;
import com.t3.networking.registry.T3Registry;

public class T3RegistryTest {
	@Test
	public void testT3Registry() throws InterruptedException {
		long delay = 100;

		Thread.sleep(delay);
		System.out.println("Register");
		T3Registry.registerInstance("my test", 4444);

		Thread.sleep(delay);
		System.out.println("Heartbeat");

		T3Registry.heartBeat(4444);

		boolean foundServer=false;
		for(RegisteredServer rs:T3Registry.findAllInstances()) {
			foundServer|="my test".equals(rs.getName()) && rs.getPort()==4444;
		}
		Assert.assertTrue(foundServer, "Could not find the registered test game server.");
		
		Thread.sleep(delay);

		Thread.sleep(delay);
		System.out.println("RERegister");
		T3Registry.registerInstance("my test", 4444);

		Thread.sleep(delay);

		Thread.sleep(delay);

		Thread.sleep(delay);
		System.out.println("UnRegister");
		T3Registry.unregisterInstance(4444);

		System.out.println("All instances: " + T3Registry.findAllInstances());
	}
}
