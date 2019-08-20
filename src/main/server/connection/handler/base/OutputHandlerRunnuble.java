package main.server.connection.handler.base;

import java.io.DataOutputStream;
import java.io.IOException;

import main.server.connection.request.base.IRequest;

public class OutputHandlerRunnuble extends AbstractRunnuble implements Runnable{
	
	private DataOutputStream out;
	
	public OutputHandlerRunnuble(HandlingData data, DataOutputStream out){
		super(data);
		this.out = out;
	}

	@Override
	public void run() {
		
		while(getData().isRun()){
			if(!getData().getQueue().isEmpty()){
				IRequest request = getData().getQueue().Dequeue();
				String requestString = request.getRequestString();
				
				int length = requestString.length();
				byte [] toSend = requestString.getBytes();
				byte [] lengthByte = new byte[4];
				
				lengthByte[0] = (byte) (length & 0xff);
				lengthByte[1] = (byte)((length >> 8) & 0xff);
				lengthByte[2] = (byte)((length >> 16) & 0xff);
				lengthByte[3] = (byte)((length >> 24) & 0xff);
				
				try {
					out.write(lengthByte);
					out.write(toSend);
				} catch (IOException e) {
					break;
				}
			}
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}
