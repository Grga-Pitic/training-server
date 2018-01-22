package main.server.managers.base;

import java.io.IOException;

import main.server.ServerSession;
import main.server.services.ServerSessionService;

public abstract class AbstractManager implements IManager {
	
	private ServerSession serverSession;
	
	public AbstractManager(ServerSession serverSession) {
		
		this.serverSession = serverSession;
		
	}
	
	protected int closeUserSession() {
		int sessionCounter = 0;
		
		// TODO
		
		return sessionCounter;
	}
	
	protected void closeSession() throws IOException {
		serverSession.getServerSocket().close();
		
	}
	
	protected void startSession() {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				ServerSessionService serverService = new ServerSessionService(serverSession);
				serverService.run(serverSession);
			}
		}).start();
		
	}
}
