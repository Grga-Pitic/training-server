package main.server.managers;

import javax.management.InstanceAlreadyExistsException;

import main.server.ServerSession;
import main.server.managers.base.AbstractManager;
import main.server.managers.base.IManager;

public class ConsoleManager extends AbstractManager implements IManager {
	
	public ConsoleManager(ServerSession serverSession) {
		super(serverSession);
	}

	@Override
	public void run() {
		
		for(;;){
			String line = "start";
			
			if(line.equalsIgnoreCase("start")){
				startSession();
			}
			
			if(line.equalsIgnoreCase("reset")) {
				
			}
			
			if(line.equalsIgnoreCase("exit")){
				return;
			}
			
		}
		
	}
	
}
