package com.t3.persistence;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.File;
import java.io.IOException;

import com.t3.macro.MacroEngine;

public class LoadCampaignTest {
  @Test
  public void loadCampagin() throws IOException {
	  File testCampaign=new File("target/test-classes/com/t3/persistence/test.cmpgn");
	  MacroEngine.initialize(); //needed
	  System.out.println(testCampaign.getAbsolutePath());
	  AssertJUnit.assertTrue(testCampaign.exists());
	  
	  PackedFile pf=new PackedFile(testCampaign);
	  
	  AssertJUnit.assertNotNull(pf.getContent());
	  pf.close();
  }
}
