package com.t3;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeTimer {

	private Map<String, Timer> timeMap = new HashMap<String, Timer>();
	private String name;
	private long created = System.currentTimeMillis();
	private boolean enabled;
	private int threshold = 1;
	
	public CodeTimer() {
		this("");
	}
	
	public CodeTimer(String name) {
		this.name = name;
		enabled = true;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	
	public void clear() {
		timeMap.clear();
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void start(String id) {
		if (!enabled) {
			return;
		}
		
		Timer timer = timeMap.get(id);
		if (timer == null) {
			timer = new Timer();
			timeMap.put(id, timer);
		}
		
		timer.start();
	}
	
	public void stop(String id) {
		if (!enabled) {
			return;
		}
		
		if (!timeMap.containsKey(id)) {
			throw new IllegalArgumentException("Could not find timer id: " + id);
		}
		
		timeMap.get(id).stop();
	}
	
	public long getElapsed(String id) {
		if (!enabled) {
			return 0;
		}

		if (!timeMap.containsKey(id)) {
			throw new IllegalArgumentException("Could not find timer id: " + id);
		}
		
		return timeMap.get(id).getElapsed();
	}
	
	public void reset(String id) {
		timeMap.remove(id);
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("Timer ").append(name).append(": ");
		builder.append(System.currentTimeMillis() - created).append("\n");
		
		List<String> keySet = new ArrayList<String>(timeMap.keySet());
		Collections.sort(keySet);
		for (String key : keySet) {
			Timer timer = timeMap.get(key);
			if (timer.getElapsed() < threshold) {
				continue;
			}
			builder.append("\t").append(key).append(": ").append(timer.getElapsed()).append("\n");
		}
		
		return builder.toString();
	}
	
	private static class Timer {
		
		long elapsed;
		long start = -1;
		
		public void start() {
			start = System.currentTimeMillis();
		}
		
		public void stop() {
			elapsed += (System.currentTimeMillis() - start);
			start = -1;
		}
		
		public long getElapsed() {
			long time = elapsed;
			if (start > 0) {
				time += (System.currentTimeMillis() - start);
			}
			return time;
		}
	}
}
