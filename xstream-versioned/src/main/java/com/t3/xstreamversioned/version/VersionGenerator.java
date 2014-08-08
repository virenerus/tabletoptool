package com.t3.xstreamversioned.version;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class VersionGenerator {
	
	private VersionGenerator() {}

	/**
	 * This method generates a version list for a class and its super classes.
	 *  
	 * @param type the type of which you want the version
	 * @param strict if an error should be thrown if a class without a version is encountered
	 * @param packageFilter if this is not null only packages and sub packes given here generate a version other than 0
	 * @return a list of classes and their versions
	 */
	public static Version generateVersion(Class<?> type, boolean strict, Set<String> packageFilter) {
		if(type==null || Object.class.equals(type) || type.isArray())
			return null;
		
		//subclasses of enums can't be annotated and their serialization is done by the enum parent class
		if(type.getSuperclass()!=null && type.getSuperclass().isEnum()) 
			return generateVersion(type.getSuperclass(), strict, packageFilter);
		
		
		Version version=generateVersion(type.getSuperclass(), strict, packageFilter);
		if(version==null || !isInPackages(packageFilter, generateName(type))) //if not controlled class there is no reason to go into depth)
			version=new Version();
		SerializationVersion versionAnnotation=type.getAnnotation(SerializationVersion.class);
		if(versionAnnotation==null) {
			//if strict and class is not default jre class and not in ignroed package
			if(strict && type.getClassLoader()!=null && isInPackages(packageFilter, generateName(type)))
				throw new IllegalArgumentException("Cant serialize Element of type "+generateName(type)+" because it is missing a version.");
			else
				version.add(new VersionElement(generateName(type),0));
		}
		else {
			version.add(new VersionElement(generateName(type), versionAnnotation.value()));
		}
		return version;
	}
	
	//if this every needs to change it is only in one place
	private static String generateName(Class<?> type) {
		return type.getName();
	}
	
	private static boolean isInPackages(Set<String> packageSet, String fullyQualifiedName) {
		for(String p:packageSet)
			if(fullyQualifiedName.startsWith(p))
				return true;
		return false;
	}

}
