/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.t3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;

public class FileUtil {
	private static final Logger log = Logger.getLogger(FileUtil.class);

	public static byte[] loadFile(File file) throws IOException {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			return IOUtils.toByteArray(is);
		} finally {
			try {
				if (is != null) is.close();
			} catch (Exception e) { }
		}
	}

	public static byte[] loadResource(String resource) throws IOException {
		InputStream is = null;
		try {
			is = FileUtil.class.getClassLoader().getResourceAsStream(resource);
			return getBytes(is);
		} finally {
			try {
				if (is != null) is.close();
			} catch (Exception e) { }
		}
	}

	public static List<String> getLines(File file) throws IOException {
		List<String> list;
		list = IOUtils.readLines(new FileReader(file));
		return list;
	}

	public static void saveResource(String resource, File destDir) throws IOException {
		int index = resource.lastIndexOf('/');
		String filename = index >= 0 ? resource.substring(index + 1) : resource;
		saveResource(resource, destDir, filename);
	}

	public static void saveResource(String resource, File destDir, String filename) throws IOException {
		File outFilename = new File(destDir + File.separator + filename);
		InputStream inStream = null;
		OutputStream outStream = null;
		try {
			inStream = FileUtil.class.getClassLoader()	.getResourceAsStream(resource);
			outStream = new BufferedOutputStream(new FileOutputStream(outFilename));
			copyWithoutClose(inStream, outStream);
		} finally {
			try {
				if (outStream != null) outStream.close();
			} catch (Exception e) { }
			try {
				if (inStream != null) inStream.close();
			} catch (Exception e) { }
		}
	}

	private static final Pattern TRIM_EXTENSION_PATTERN = Pattern.compile("^(.*)\\.([^\\.]*)$");

	public static String getNameWithoutExtension(File file) {
		return getNameWithoutExtension(file.getName());
	}
	public static String getNameWithoutExtension(String filename) {
		if (filename == null) {
			return null;
		}

		Matcher matcher = TRIM_EXTENSION_PATTERN.matcher(filename);
		if (!matcher.find()) {
			return filename;
		}

		return matcher.group(1);
	}

	public static String getNameWithoutExtension(URL url) {
		return getNameWithoutExtension(new File(url.getFile()));
	}

	private static byte[] getBytes(InputStream inStream) throws IOException {
		if (inStream == null)
			throw new IllegalArgumentException("InputStream cannot be null");
		byte[] result = IOUtils.toByteArray(inStream);
		return result;
	}

	public static byte[] getBytes(URL url) throws IOException {
		InputStream is = null;
		try {
			is = url.openStream();
			return getBytes(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	public static byte[] getBytes(File file) throws IOException {
		return getBytes(file.toURI().toURL());
	}

	/**
	 * Returns the data in a file using the UTF-8 character encoding.  The platform
	 * default may not be appropriate since the file could've been produced on a
	 * different platform.  The only safe thing to do is use UTF-8 and hope that
	 * everyone uses it by default when they edit text files. :-/
	 */
	public static String getString(InputStream is) throws IOException {
		if (is == null)
			throw new IllegalArgumentException("InputStream cannot be null");
		String result = IOUtils.toString(is, "UTF-8");
		return result;
	}

	/**
	 * Reads the given file and returns the contents as a UTF-8 encoded string.
	 * See {@link #getString(InputStream)} for additional details.
	 * 
	 * @param file file to retrieve contents from
	 * @return String representing the contents
	 * @throws IOException
	 */
	public static String getString(File file) throws IOException {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			return getString(is);
		} finally {
			try {
				if (is != null) is.close();
			} catch (Exception e) { }
		}
	}

	/**
	 * Given an InputStream this method tries to figure out what the content type might be.
	 * @param in the InputStream to check
	 * @return a <code>String</code> representing the content type name
	 */
	public static String getContentType(InputStream in) {
		String type = "";
		try {
			type = URLConnection.guessContentTypeFromStream(in);
			if (log.isDebugEnabled())
				log.debug("result from guessContentTypeFromStream() is " + type);
		} catch (IOException e) {
		}
		return type;
	}

	/**
	 * Given a URL this method tries to figure out what the content type might be based
	 * only on the filename extension.
	 * 
	 * @param url the URL to check
	 * @return a <code>String</code> representing the content type name
	 */
	public static String getContentType(URL url) {
		String type = "";
		type = URLConnection.guessContentTypeFromName(url.getPath());
		if (log.isDebugEnabled())
			log.debug("result from guessContentTypeFromName(" + url.getPath() + ") is " + type);
		return type;
	}

	/**
	 * Given a URL this method tries to figure out what the content type might be based
	 * only on the filename extension.
	 * 
	 * @param url the URL to check
	 * @return a <code>String</code> representing the content type name
	 */
	public static String getContentType(File file) {
		try {
			return getContentType(file.toURI().toURL());
		} catch (MalformedURLException e) {
			return null;
		}
	}

	/**
	 * Returns a Reader from the given <code>File</code> object.
	 * 
	 * @param file the input data source
	 * @return a String representing the data
	 * @throws IOException
	 */
	public static Reader getFileAsReader(File file) throws IOException {
		return getURLAsReader(file.toURI().toURL());
	}

	/**
	 * Given a URL this method determines the content type of the URL (if possible) and
	 * then returns a Reader with the appropriate character encoding.
	 * 
	 * @param url the source of the data stream
	 * @return String representing the data
	 * @throws IOException
	 */
	public static Reader getURLAsReader(URL url) throws IOException {
		InputStreamReader isr = null;
		URLConnection conn = null;
		String encoding = "UTF-8";
		// We're assuming character here, but it could be bytes.  Perhaps we should
		// check the MIME type returned by the network server?
		conn = url.openConnection();
		if (log.isDebugEnabled()) {
			String type = URLConnection.guessContentTypeFromName(url.getPath());
			log.debug("result from guessContentTypeFromName(" + url.getPath() + ") is " + type);
			type = getContentType(conn.getInputStream());
			// Now make a guess and change 'encoding' to match the content type...
		}
		isr = new InputStreamReader(conn.getInputStream(), encoding);
		return isr;
	}

	public static InputStream getFileAsInputStream(File file) throws IOException {
		return getURLAsInputStream(file.toURI().toURL());
	}

	/**
	 * Given a URL this method determines the content type of the URL (if possible) and
	 * then returns a Reader with the appropriate character encoding.
	 * 
	 * @param url the source of the data stream
	 * @return String representing the data
	 * @throws IOException
	 */
	public static InputStream getURLAsInputStream(URL url) throws IOException {
		InputStream is = null;
		URLConnection conn = null;
		// We're assuming character here, but it could be bytes.  Perhaps we should
		// check the MIME type returned by the network server?
		conn = url.openConnection();
		if (log.isDebugEnabled()) {
			String type = URLConnection.guessContentTypeFromName(url.getPath());
			log.debug("result from guessContentTypeFromName(" + url.getPath() + ") is " + type);
			type = getContentType(conn.getInputStream());
			log.debug("result from getContentType(" + url.getPath() + ") is " + type);
		}
		is = conn.getInputStream();
		return is;
	}

	/**
	 * Because this method takes a <code>Reader</code> it can be locale-correct by
	 * creating a FileReader (for example) that uses UTF-8 or some other charset.
	 * 
	 * @param r the <code>Reader</code> to obtain textual data from
	 * @return an array of <code>char</code>
	 * @throws IOException
	 */
	private static String getChars(Reader r) throws IOException {
		if (r == null) {
			throw new IllegalArgumentException("Reader cannot be null");
		}
		StringWriter sw = new StringWriter(10 * 1024);

		char[] c = new char[4096];
		while (true) {
			int read = r.read(c);

			if (read == -1) {
				break;
			}
			sw.write(c, 0, read);
		}
		sw.close();
		return sw.toString();
	}

	public static void writeBytes(File file, byte[] data) throws IOException {
		// Ensure path exists
		if (file.exists())
			file.delete();
		else
			file.getParentFile().mkdirs();

		OutputStream os = new FileOutputStream(file);
		try {
			IOUtils.write(data, os);
		} finally {
			try {
				if (os != null) os.close();
			} catch (Exception e) { }
		}
	}

	public static void copyFile(File sourceFile, File destFile) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(sourceFile);
			os = new FileOutputStream(destFile);
			IOUtils.copy(is, os);
		} finally {
			try {
				if (os != null) os.close();
			} catch (Exception e) { }
			try {
				if (is != null) is.close();
			} catch (Exception e) { }
		}
	}

	public static void unzip(String classpathFile, File destDir) throws IOException {
		try {
			unzip(FileUtil.class.getClassLoader().getResource(classpathFile), destDir);
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	public static void unzip(URL url, File destDir) throws IOException {
		if (url == null)
			throw new IOException ("URL cannot be null");

		InputStream is = url.openStream();
		ZipInputStream zis = null;
		try {
			zis = new ZipInputStream(new BufferedInputStream(is));
			unzip(zis, destDir);
		} finally {
			try {
				if (zis != null) zis.close();
			} catch (Exception e) { }
		}
	}

	public static void unzip(ZipInputStream in, File destDir) throws IOException {
		if (in == null)
			throw new IOException("input stream cannot be null");

		// Prepare destination
		destDir.mkdirs();
		String absDestDir = destDir.getAbsolutePath() + File.separator;

		// Pull out the files
		OutputStream os = null;
		ZipEntry entry = null;
		try {
			while ((entry = in.getNextEntry()) != null) {
				if (entry.isDirectory())
					continue;

				// Prepare file destination
				String path = absDestDir + entry.getName();
				File file = new File(path);
				file.getParentFile().mkdirs();

				OutputStream out = new FileOutputStream(file);
				copyWithoutClose(in, out);
				out.close();
				in.closeEntry();
			}
		} finally {
			try {
				if (os != null) os.close();
			} catch (Exception e) { }
		}
	}

	public static void unzipFile(File sourceFile, File destDir) throws IOException {
		if (!sourceFile.exists())
			throw new IOException("source file does not exist: " + sourceFile);

		ZipFile zipFile = null;
		InputStream is = null;
		OutputStream os = null;
		try {
			zipFile = new ZipFile(sourceFile);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();

			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				if (entry.isDirectory())
					continue;

				String path = destDir.getAbsolutePath() + File.separator + entry.getName();
				File file = new File(path);
				file.getParentFile().mkdirs();

				//System.out.println("Writing file: " + path);
				is = zipFile.getInputStream(entry);
				os = new BufferedOutputStream(new FileOutputStream(path));
				copyWithClose(is, os);
			}
		} finally {
			try {
				if (os != null) os.close();
			} catch (Exception e) { }
			try {
				if (is != null) is.close();
			} catch (Exception e) { }
			try {
				if (zipFile != null) zipFile.close();
			} catch (Exception e) { }
		}
	}

	public static void copyWithoutClose(InputStream is, OutputStream os) throws IOException {
		IOUtils.copy(is, os);
	}

	public static void copyWithClose(InputStream is, OutputStream os) throws IOException {
		try {
			IOUtils.copy(is, os);
		} catch (IOException ioe) {
			try {
				if (os != null) os.close();
			} catch (Exception e) { }
			try {
				if (is != null) is.close();
			} catch (Exception e) { }
		}
	}

	public static void delete(File file, int daysOld) throws IOException {
		Calendar olderThan = new GregorianCalendar();
		olderThan.add(Calendar.DATE, -daysOld);

		boolean shouldDelete = new Date(file.lastModified()).before(olderThan.getTime());

		if (file.isDirectory()) {
			// Wipe the contents first
			for (File currfile : file.listFiles()) {
				if (".".equals(currfile.getName()) || "..".equals(currfile.getName()))
					continue;
				delete(currfile, daysOld);
			}
		}
		if (shouldDelete)
			file.delete();
	}

	public static void delete(File file) {
		if (file.isDirectory()) {
			// Wipe the contents first
			for (File currfile : file.listFiles()) {
				if (".".equals(currfile.getName()) || "..".equals(currfile.getName()))
					continue;
				delete(currfile);
			}
		}
		file.delete();
	}

	public static Object objFromResource(Class<?> loadingClass, String name) {
		try (InputStream is=loadingClass.getResourceAsStream(name)) {
			return new XStream().fromXML(is);
		} catch (IOException e) {
			throw new Error(e);
		}
	}
}
