package main.server.factoryes;

import java.io.IOException;
import java.net.ServerSocket;

import main.server.ServerSession;

public class ServerSocketFactory {
	public ServerSocket createServerSocket(){
		try {
			return new ServerSocket(ServerSession.PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
