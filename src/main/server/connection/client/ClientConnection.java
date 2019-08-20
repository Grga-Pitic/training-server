package main.server.connection.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import main.server.connection.handler.InputQueueHandler;
import main.server.connection.handler.OutputQueueHandler;
import main.server.connection.handler.base.IQueueHandler;
import main.server.connection.request.factory.RequestFactory;

// client connection handling in a new thread.
public class ClientConnection implements Runnable {
	private IQueueHandler inputHandler;
	private IQueueHandler outputHandler;
	
	private Socket socket;
	
	private boolean isRunHandling;
	
	public ClientConnection(InputQueueHandler inputHandler, OutputQueueHandler outputHandler, Socket socket){
		this.inputHandler = inputHandler;
		this.outputHandler = outputHandler;
		this.socket = socket;
		this.isRunHandling = false;
	}
	
	
	@Override
	public void run() {
		boolean isContinueHandling = true;

		inputHandler.StartHandling();
		outputHandler.StartHandling();
		
		isRunHandling = true;
		
		try {
			DataInputStream in  = new DataInputStream(socket.getInputStream());
			while(isContinueHandling){
				isContinueHandling = isRunHandling || !outputHandler.isFree();
				
				String query = "";
				byte[] lenBytes = new byte[4];
				in.read(lenBytes, 0, 4);
				int len = (((lenBytes[3] & 0xff) << 24) | ((lenBytes[2] & 0xff) << 16) |
		                   ((lenBytes[1] & 0xff) << 8)  |  (lenBytes[0] & 0xff));

				byte[] receivedBytes = new byte[len];
				in.read(receivedBytes, 0, len);
				query = new String(receivedBytes, 0, len);

				inputHandler.getData().getQueue().Enqueue(RequestFactory.getInstance().createRequestByString(query));
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		} catch (IOException e) {
			BreakHandling();
		}
		
		this.inputHandler.StopHandling();
		this.outputHandler.StopHandling();
		
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void BreakHandling() {
		this.isRunHandling = false;
	}
	
	
	
}
