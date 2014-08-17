package com.t3.tools.versionsetter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class BuildSetter {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, JDOMException {
		File root=new File("./").getAbsoluteFile().getParentFile().getParentFile();
		
		File mainPom=new File(root,"pom.xml");
		if(!mainPom.exists())
			throw new RuntimeException("Could not find main pom.xml in "+root.getAbsolutePath());
		
		Document doc=new SAXBuilder().build(mainPom);
		Namespace ns=doc.getRootElement().getNamespace();
		if(!"t3-master".equals(doc.getRootElement().getChild("artifactId",ns).getText()))
			throw new RuntimeException(mainPom.getAbsolutePath()+" is not main pom but "+doc.getRootElement().getChild("artifactId",ns).getText());
		String oldVersion=doc.getRootElement().getChild("version",ns).getText();
		
		
		String newVersion=showVersionDialog(oldVersion);
		System.out.println(newVersion);
		doc.getRootElement().getChild("version",ns).setText(newVersion);
		Files.walk(root.toPath(), 2).parallel().filter(p -> p.endsWith("pom.xml")).forEach(p -> {
			try {
				Document subProj=new SAXBuilder().build(p.toFile());
				Element parentElement=subProj.getRootElement().getChild("parent",ns);
				if(parentElement!=null && checkUpdateVersion(parentElement,newVersion)) {
					int updated=0;
					if(subProj.getRootElement().getChild("dependencies",ns)!=null)
						for(Element dep:subProj.getRootElement().getChild("dependencies",ns).getChildren("dependency",ns))
							if(checkUpdateVersion(dep, newVersion))
								updated++;
					System.out.println("Updated "+p.toFile().getParentFile().getName()+" with "+updated+" entries");
					
					save(p,subProj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		});
		
		save(mainPom.toPath(), doc);
	}

	private static void save(Path p, Document doc) throws IOException {
		try(BufferedWriter w=Files.newBufferedWriter(p)) {
			XMLOutputter out = new XMLOutputter();
			out.output(doc, w );
			w.close();
		}
	}

	private static boolean checkUpdateVersion(Element e, String newVersion) {
		try {
			if("tabletoptool".equals(e.getChild("groupId",e.getNamespace()).getText().trim())) {
				e.getChild("version",e.getNamespace()).setText(newVersion);
				return true;
			}
		} catch(NullPointerException ex) {
			System.err.println("Could not update:\n"+e.toString());
		}
		return false;
	}
	
	private static String showVersionDialog(String oldVersion) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFrame f=new JFrame();
		String s = (String)JOptionPane.showInputDialog(
		                    f,
		                    "What should be the new version?",
		                    "Version",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    null,
		                    oldVersion);

		//If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
		    return s;
		}
		else {
			System.exit(0);
			return null;
		}
	}

}
