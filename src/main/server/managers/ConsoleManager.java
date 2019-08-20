package main.server.managers;

import java.io.IOException;
import java.util.Scanner;

import main.server.Server;
import main.server.managers.base.AbstractManager;
import main.server.managers.base.IManager;

public class ConsoleManager extends AbstractManager implements IManager {
	
	public ConsoleManager(Server serverSession) {
		super(serverSession);
	}

	@Override
	public void run() throws IOException {
		Scanner in = new Scanner(System.in);
		for(;;){
			String line = in.nextLine();
			
			if(line.equalsIgnoreCase("start")){
				startSession();
			}
			
			if(line.equalsIgnoreCase("exit")){
				
				closeSession();
				System.exit(0);
				break;
			}
			
		}
		
	}
	
}
