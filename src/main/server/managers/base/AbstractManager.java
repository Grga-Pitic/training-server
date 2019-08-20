package main.server.managers.base;

import java.io.IOException;

import main.server.Server;

public abstract class AbstractManager implements IManager {
	
	private Server serverSession;
	
	public AbstractManager(Server serverSession) {
		
		this.serverSession = serverSession;
		
	}
	
	protected void closeSession() throws IOException {
		
		serverSession.getServerSocket().close();
		
	}
	
	protected void startSession() {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				serverSession.run();
			}
		}).start();
		
	}
}
