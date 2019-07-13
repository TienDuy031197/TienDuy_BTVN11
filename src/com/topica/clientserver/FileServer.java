package com.topica.clientserver;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {

	private ServerSocket serverSocket = null;
	private DataInputStream dataInputStream = null;
	private FileOutputStream fileOutputStream = null;

	public FileServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("waiting for the client to connect...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void connect() {
		try {
			Socket socket = serverSocket.accept();
			System.out.println("Client connected!");
			saveFile(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveFile(Socket clientSock) throws IOException {
		dataInputStream = new DataInputStream(clientSock.getInputStream());
		fileOutputStream = new FileOutputStream(Constant.FILE_OUTPUT);
		byte[] buffer = new byte[Constant.SIZE_BYTE];
		int read = 0;
		while ((read = dataInputStream.read(buffer, 0, Constant.SIZE_BYTE)) > 0) {
			fileOutputStream.write(buffer, 0, read);
		}
		System.out.println("Finish!");
	}

	public void close() throws IOException {
		if (fileOutputStream != null) {
			fileOutputStream.close();
		}
		if (dataInputStream != null) {
			dataInputStream.close();
		}
		if (serverSocket != null) {
			serverSocket.close();
		}
	}

	public static void main(String[] args) throws IOException {
		FileServer fileServer = new FileServer(Constant.PORT);
		fileServer.connect();
		fileServer.close();
	}

}
