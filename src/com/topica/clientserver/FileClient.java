package com.topica.clientserver;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class FileClient {

	private Socket socket = null;
	private DataOutputStream dataOutputStream = null;
	private FileInputStream fileInputStream = null;

	public FileClient(String host, int port) {
		try {
			socket = new Socket(host, port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendFile(String file) throws IOException {
		dataOutputStream = new DataOutputStream(socket.getOutputStream());
		fileInputStream = new FileInputStream(file);
		byte[] buffer = new byte[Constant.SIZE_BYTE];

		while (fileInputStream.read(buffer) != -1) {
			dataOutputStream.write(buffer);
		}
		System.out.println("send file completed!");
	}

	public void close() throws IOException {
		if (fileInputStream != null) {
			fileInputStream.close();
		}
		if (dataOutputStream != null) {
			dataOutputStream.close();
		}
		if (socket != null) {
			socket.close();
		}
	}

	public static void main(String[] args) throws IOException {
		FileClient fileClient = new FileClient(Constant.HOST, Constant.PORT);
		fileClient.sendFile(Constant.FILE_INPUT);
		fileClient.close();
	}

}
