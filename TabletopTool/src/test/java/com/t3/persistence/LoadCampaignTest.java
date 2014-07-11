package com.t3.persistence;

import java.io.File;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.t3.macro.MacroEngine;

public class LoadCampaignTest {
  @Test
  public void loadCampagin() throws IOException {
	  File testCampaign=new File("target/test-classes/com/t3/persistence/test.cmpgn");
	  MacroEngine.initialize(); //needed
	  System.out.println(testCampaign.getAbsolutePath());
	  Assert.assertTrue(testCampaign.exists());
	  
	  PackedFile pf=new PackedFile(testCampaign);
	  
	  Assert.assertNotNull(pf.getContent());
	  pf.close();
  }
}
