package com.fiserv.b2bclient.client;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.fiserv.b2bclient.util.FileUtils;
import com.fiserv.b2bclient.util.XMLUtility;

/**
 * 
 * @author atul.singh
 *
 */
public class SignedDocumentClient {

	private static String inputDir = "C:\\temp\\NationWide\\outPut\\";
	private static String tempDir = "C:\\temp\\NationWide\\temp";
	private static String finalDir = "C:\\temp\\NationWide\\final\\";

	private static String pattern = "resp:DocumentContent";

	public static void main(String args[]) {
		File[] responseXmls = FileUtils.getAllFiles(inputDir);

		for (File response : responseXmls) {
			if (response.isFile()) {
				String zipPath = tempDir + File.separator + response.getName() + ".zip";

				String xmlString;
				try {
					xmlString = XMLUtility.readTagFromXML(FileUtils.readFile(response), pattern);

					//FileUtils.writeZip(Base64.getDecoder().decode(xmlString), zipPath);
					
					byte[] zipFileConentByteArray = Base64.getDecoder().decode(xmlString);

					File destDir = new File(finalDir + response.getName());
					destDir.mkdirs();
					
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					ZipOutputStream zos = new ZipOutputStream(bos);

					byte[] buffer = new byte[1024];
					ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zipFileConentByteArray));
					ZipEntry zipEntry = zis.getNextEntry();
					while (zipEntry != null) {

						File newFile = FileUtils.copyFileFromZip(destDir, zipEntry);
						if (!newFile.getName().toLowerCase().contains("xml")) {
							
							/*FileOutputStream fos = new FileOutputStream(newFile);
							int len;
							while ((len = zis.read(buffer)) > 0) {
								fos.write(buffer, 0, len);
							}
							fos.close();*/
							ZipEntry newEntrtlc = new ZipEntry(zipEntry.getName());
							
							zos.putNextEntry(newEntrtlc);
							
							int length;
				            while ((length = zis.read(buffer)) > 0) {
				                zos.write(buffer, 0, length);
				            }
				            zos.closeEntry();
							
							
							//Files.deleteIfExists(newFile.toPath());

						}
						zis.closeEntry();
						zipEntry = zis.getNextEntry();
					}
					
					
					
					zis.close();
					
					zos.close();
					
					FileUtils.writeZip(bos.toByteArray(), zipPath);
					
					


				} catch (IOException e) {
					// TODO Auto-generated catch block
					
					System.out.println("Error-" + "-" + e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Error2-" + "-" + e.getMessage());
					e.printStackTrace();
				}

			}
		}
	}

}
