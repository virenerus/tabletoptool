package com.t3;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An object of this class is created in the {@link PersistenceUtil} code for the purpose of
 * applying XML transforms to data being read or written by a tool (MapTool).  The
 * application registers one or more {@link ModelVersionTransformation} objects under
 * a particular version.  When the {@link #transform(String, String)} method is called
 * with an XML string and a version number string, all transforms currently registered
 * will be searched and those with a version number earlier than the one passed in will
 * have their <code>transform</code> method called.
 *
 * @author tcroft
 */
public class ModelVersionManager {

	private final Map<String, List<ModelVersionTransformation>> transformMap = new LinkedHashMap<String, List<ModelVersionTransformation>>();

	/**
	 * Registers one or more transformations to execute when a campaign file later than
	 * the supplied version number is being processed.  For example, if the version parameter
	 * were <b>1.3.64</b> then the list of transforms would run for any version higher
	 * than that, such as <b>1.3.64.1</b> or <b>1.3.65</b>.
	 *
	 * @param version time at which the change was made (only digits and dots are preserved)
	 * @param transforms one or more transformations to apply to the input string
	 */
	public synchronized void registerTransformation(String version, ModelVersionTransformation... transforms) {
		version = cleanVersionNumber(version);

		List<ModelVersionTransformation> transformList = transformMap.get(version);
		if (transformList == null) {
			transformList = new LinkedList<ModelVersionTransformation>();
			transformMap.put(version, transformList);
		}
		for (ModelVersionTransformation transform : transforms) {
			transformList.add(transform);
		}
	}

	/**
	 * Converts the input string <code>xml</b> by applying all transformations
	 * that are registered for versions after <code>fileVersion</code>.  Transformations
	 * are guaranteed to be applied in the order provided for any given version, and
	 * versions will be applied in order based on a simple String-based sort (this is
	 * wrong for single-digit versions compared against double-digit versions).
	 *
	 * @param xml normally XML input, but could be any string
	 * @param fileVersion typically of the form <b>a.b.c</b> but can have any number
	 * of components
	 * @return the resulting string after all transforms are complete
	 */
	public synchronized String transform(String xml, String fileVersion) {
		fileVersion = cleanVersionNumber(fileVersion);

		Set<String> set = transformMap.keySet();
		String[] entries = new String[set.size()];
		set.toArray(entries);
		Arrays.sort(entries);

		for (String entry : set) {
			if (entry.equals(fileVersion) || isBefore(entry, fileVersion)) {
				continue;
			}
			for (ModelVersionTransformation transform : transformMap.get(entry)) {
				xml = transform.transform(xml);
			}
		}

		return xml;
	}

	/**
	 * Tests whether the first parameter is a version number that comes before the version
	 * number stored in the second parameter.  Version numbers are of the form
	 * <code>1.3.51</code> or <code>1.3.64.1</code> and may have any number
	 * of components separated by periods.  If either parameter contains a component
	 * which cannot be parsed as an integer, this test returns <code>true</code> on
	 * the idea that this must be a development version of the code (so any file version
	 * will always be "before" the development version).
	 *
	 * @param version1 version registered as containing a change
	 * @param version2 version read in from the <b>properties.xml</b> entry
	 * @return
	 */
	public static boolean isBefore(String version1, String version2) {

		if (version1 == null) {
			// if we don't know the version, then we should start at the beginning (before everything)
			return true;
		}

		if (version2 == null) {
			// if there is no comparison version, then we should be up to date (nothing to change)
			return false;
		}

		String[] v1 = version1.indexOf(".") > 0 ? version1.split("\\.") : new String[]{version1};
		String[] v2 = version2.indexOf(".") > 0 ? version2.split("\\.") : new String[]{version2};

		try {
			int index = 0;
			while (index < v1.length && index < v2.length) {

				int val1 = Integer.parseInt(v1[index]);
				int val2 = Integer.parseInt(v2[index]);

				if (val1 < val2) {
					return true;
				}
				if (val1 > val2) {
					return false;
				}

				index ++;
			}
		} catch (NumberFormatException nfe) {
			// Presumably a development version, which would be newest
			return true;
		}

		return false;
	}

	public static String cleanVersionNumber(String v) {
		return v.indexOf('.') > 0 ? v.replaceAll("[^\\d.]", "") : v;
	}
}
