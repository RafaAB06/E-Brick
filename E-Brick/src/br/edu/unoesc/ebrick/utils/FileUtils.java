package br.edu.unoesc.ebrick.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public final class FileUtils {
	public static byte[] getBytes(String path) throws IOException {
		File file = new File(path);
		FileInputStream inputStream = new FileInputStream(file);
		byte[] bytes = inputStream.readAllBytes();
		inputStream.close();
		
		return bytes;
	}
}
