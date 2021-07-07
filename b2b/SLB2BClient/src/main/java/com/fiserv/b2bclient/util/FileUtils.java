package com.fiserv.b2bclient.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;

public class FileUtils {

	private static final char DEFAULT_SEPARATOR = ',';
	private static final char DEFAULT_QUOTE = '"';

	public static ArrayList readCSVFile(String path) throws FileNotFoundException {

		ArrayList listValues = new ArrayList();
		Scanner scanner = new Scanner(new File("C:\\temp\\NationWide\\input\\NationWide_LenderCaseNumber.csv"));
		scanner.useDelimiter(",");
		while (scanner.hasNext()) {
			List<String> line = parseLine(scanner.nextLine());
			listValues.add(line);
			// System.out.print(line);
		}
		scanner.close();

		return listValues;

	}

	public static List<String> parseLine(String cvsLine) {
		return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
	}

	public static List<String> parseLine(String cvsLine, char separators) {
		return parseLine(cvsLine, separators, DEFAULT_QUOTE);
	}

	public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

		List<String> result = new ArrayList();

		// if empty, return!
		if (cvsLine == null && cvsLine.isEmpty()) {
			return result;
		}

		if (customQuote == ' ') {
			customQuote = DEFAULT_QUOTE;
		}

		if (separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}

		StringBuffer curVal = new StringBuffer();
		boolean inQuotes = false;
		boolean startCollectChar = false;
		boolean doubleQuotesInColumn = false;

		char[] chars = cvsLine.toCharArray();

		for (char ch : chars) {

			if (inQuotes) {
				startCollectChar = true;
				if (ch == customQuote) {
					inQuotes = false;
					doubleQuotesInColumn = false;
				} else {

					// Fixed : allow "" in custom quote enclosed
					if (ch == '\"') {
						if (!doubleQuotesInColumn) {
							curVal.append(ch);
							doubleQuotesInColumn = true;
						}
					} else {
						curVal.append(ch);
					}

				}
			} else {
				if (ch == customQuote) {

					inQuotes = true;

					// Fixed : allow "" in empty quote enclosed
					if (chars[0] != '"' && customQuote == '\"') {
						curVal.append('"');
					}

					// double quotes in column will hit this!
					if (startCollectChar) {
						curVal.append('"');
					}

				} else if (ch == separators) {

					result.add(curVal.toString().trim());

					curVal = new StringBuffer();
					startCollectChar = false;

				} else if (ch == '\r') {
					// ignore LF characters
					continue;
				} else if (ch == '\n') {
					// the end, break!
					break;
				} else {
					curVal.append(ch);
				}
			}

		}

		result.add(curVal.toString().trim());

		return result;
	}

	public static String readFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

	public static File[] getAllFiles(String dir) {
		File folder = new File(dir);
		return folder.listFiles();
	}

	public static void writeZip(byte[] byteArray, String outputFile) throws IOException {
		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(outputFile), 4096);
			out.write(byteArray);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static File copyFileFromZip(File destinationDir, ZipEntry zipEntry) throws IOException  {
		File destFile = new File(destinationDir, zipEntry.getName());

		String destDirPath;
		try {
			destDirPath = destinationDir.getPath();
		
		String destFilePath = destFile.getPath();

		if (!destFilePath.startsWith(destDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
		}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(destinationDir.getCanonicalPath() + "-:" + zipEntry.getName());
			throw e;
		}

		return destFile;
	}
}
