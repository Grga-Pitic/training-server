package main.server.connection.handler.base;

import main.server.connection.request.base.IRequest;
import main.server.connection.request.handlers.IRequestHandler;
import main.server.connection.request.handlers.factory.RequestHandlerFactory;

public class InputHandlerRunnuble extends AbstractRunnuble implements Runnable {
	
	private RequestHandlerFactory factory;
	
	public InputHandlerRunnuble(HandlingData data, RequestHandlerFactory factory){
		super(data);
		this.factory = factory;
	}
	
	@Override
	public void run() {
		while(getData().isRun()){
			if(!getData().getQueue().isEmpty()){
				IRequest request = getData().getQueue().Dequeue();
				IRequestHandler handler = 
						factory.createByType(request.getType());
				
				handler.Handle(request);
				
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
