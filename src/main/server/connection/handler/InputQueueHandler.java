package main.server.connection.handler;

import main.server.connection.handler.base.HandlingData;
import main.server.connection.handler.base.IQueueHandler;
import main.server.connection.handler.base.InputHandlerRunnuble;
import main.server.connection.request.handlers.factory.RequestHandlerFactory;

public class InputQueueHandler implements IQueueHandler{
	
	// TODO ¬ынести в абстрактный класс AbstractQueueHandler эти пол€. 
	
	private Thread thread;
	private InputHandlerRunnuble runnuble;
	
	public InputQueueHandler(HandlingData data, RequestHandlerFactory factory) {
		this.runnuble = new InputHandlerRunnuble(data, factory);
		this.thread = new Thread(this.runnuble);
	}
	
	// TODO ¬ынести в абстрактный класс AbstractQueueHandler эти методы.
	
	@Override
	public void StartHandling() {
		this.runnuble.getData().setRun(true);
		this.thread.start();
	}

	@Override
	public void StopHandling() {
		this.runnuble.getData().setRun(false);
	}

	@Override
	public boolean isRun() {
		return this.runnuble.getData().isRun();
	}

	@Override
	public HandlingData getData() {
		return this.runnuble.getData();
	}

	@Override
	public boolean isFree() {
		return this.runnuble.getData().getQueue().isEmpty();
	}

}
