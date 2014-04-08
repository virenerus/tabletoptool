/*
 * Copyright (c) 2014 tabletoptool.com team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     rptools.com team - initial implementation
 *     tabletoptool.com team - further development
 */
package com.t3.networking.registry;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.t3.client.TabletopTool;

public class T3Registry {
	private static final String SERVICE_URL = "http://services.tabletoptool.com/running_server_registry.php";
	private static final Logger log=Logger.getLogger(T3Registry.class);

	public static List<RegisteredServer> findAllInstances() {
		ArrayList<RegisteredServer> servers=new ArrayList<RegisteredServer>();
		Document doc=callRegistry("findAll");
		for(Element e:doc.getElementsByTag("server")) {
			servers.add(new RegisteredServer(
					e.text(),
					e.attr("address"), 
					Integer.parseInt(e.attr("port")), 
					e.attr("version"), 
					Integer.parseInt(e.attr("players"))));
		}
		return servers;
	}

	public static String getAddress() {
		return returnValue(callRegistry("returnAddress"));
	}

	private static String returnValue(Document d) {
		return d.getElementsByTag("return").get(0).text();
	}

	public static boolean registerInstance(String name, int port) {
		return Boolean.parseBoolean(
				returnValue(
						callRegistry("register", 
								name, 
								Integer.toString(port), 
								TabletopTool.getVersion(), 
								Integer.toString(TabletopTool.getPlayerList().size()))));
	}

	public static void unregisterInstance(int port) {
		callRegistry("unregister",Integer.toString(port)); 
	}

	public static boolean heartBeat(int port) {
		return Boolean.parseBoolean(callRegistry("heartbeat", 
				Integer.toString(port), 
				Integer.toString(TabletopTool.getPlayerList().size())).text());
	}

	private static Document callRegistry(String action, String... params) {
		try {
			StringBuilder url=new StringBuilder(SERVICE_URL);
			url.append("?action=").append(action);
			for(int i=0;i<params.length;i++)
				url.append("&param").append(i).append("=").append(URLEncoder.encode(params[i],"utf8"));
			return Jsoup.parse(new URL(url.toString()), 5000);
		} catch (Exception e) {
			log.error("Error while trying to call t3 registry with action "+action+" and "+Arrays.toString(params),e);
			return null;
		}
		
	}

	public static void main(String[] args) throws Exception {
		long delay = 100;

		Thread.sleep(delay);
		System.out.println("Register");
		registerInstance("my test", 4444);

		Thread.sleep(delay);
		System.out.println("Heartbeat");

		heartBeat(4444);

		Thread.sleep(delay);

		Thread.sleep(delay);
		System.out.println("RERegister");
		registerInstance("my test", 4444);

		Thread.sleep(delay);

		Thread.sleep(delay);

		Thread.sleep(delay);
		System.out.println("UnRegister");
		unregisterInstance(4444);

		System.out.println("All instances: " + findAllInstances());
	}
}
