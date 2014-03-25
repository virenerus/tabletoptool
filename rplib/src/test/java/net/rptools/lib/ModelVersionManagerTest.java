package net.rptools.lib;

import junit.framework.TestCase;

import com.t3.ModelVersionManager;
import com.t3.ModelVersionTransformation;

public class ModelVersionManagerTest extends TestCase {

	public void testIsBefore() throws Exception {
		
		assertFalse(ModelVersionManager.isBefore("1", "0"));
		assertFalse(ModelVersionManager.isBefore("1", "1"));
		assertTrue(ModelVersionManager.isBefore("1", "2"));
		
		assertFalse(ModelVersionManager.isBefore("1.1", "1.0"));
		assertFalse(ModelVersionManager.isBefore("1.1", "1.1"));
		assertTrue(ModelVersionManager.isBefore("1.1", "1.2"));
		
		assertFalse(ModelVersionManager.isBefore("2.1", "1.1"));
		assertFalse(ModelVersionManager.isBefore("2.1", "2.1"));
		assertTrue(ModelVersionManager.isBefore("2.1", "3.1"));
	}
	
	public void testFullTransform() throws Exception {
		
		String text = "one two three";
		
		ModelVersionManager mgr = new ModelVersionManager();
		mgr.registerTransformation("1", new ModelVersionTransformation() {
			public String transform(String xml) {
				return xml.replace("two", "2");
			}
		});
	
		mgr.registerTransformation("2", new ModelVersionTransformation() {
			public String transform(String xml) {
				return xml.replace("one", "1");
			}
		}, new ModelVersionTransformation() {
			public String transform(String xml) {
				return xml.replace("three", "3");
			}
		});

		mgr.registerTransformation("4", new ModelVersionTransformation() {
			public String transform(String xml) {
				return xml.replace("2", "HW");
			}
		});
		
		assertEquals("1 HW 3", mgr.transform(text, "0"));
	}
	
	public void testSkipVersion() throws Exception {
		
		String text = "1 two three";
		
		ModelVersionManager mgr = new ModelVersionManager();
		mgr.registerTransformation("1", new ModelVersionTransformation() {
			public String transform(String xml) {
				return xml.replace("two", "2");
			}
		});

		mgr.registerTransformation("4", new ModelVersionTransformation() {
			public String transform(String xml) {
				return xml.replace("2", "HW");
			}
		});

		assertEquals("1 HW three", mgr.transform(text, "0"));
	}
	
	public void testCurrentVersion() throws Exception {
		
		String text = "1 two 3";
		
		ModelVersionManager mgr = new ModelVersionManager();
		mgr.registerTransformation("1", new ModelVersionTransformation() {
			public String transform(String xml) {
				return xml.replace("two", "2");
			}
		});

		mgr.registerTransformation("4", new ModelVersionTransformation() {
			public String transform(String xml) {
				return xml.replace("3", "HW");
			}
		});

		assertEquals("1 two 3", mgr.transform(text, "4"));
		assertEquals("1 two 3", mgr.transform(text, "5")); // future version
	}
	
}
