package main.server.connection.handler;

import java.io.DataOutputStream;

import main.server.connection.handler.base.HandlingData;
import main.server.connection.handler.base.IQueueHandler;
import main.server.connection.handler.base.OutputHandlerRunnuble;

public class OutputQueueHandler implements IQueueHandler {

	private Thread thread;
	private OutputHandlerRunnuble runnuble;
	
	public OutputQueueHandler(HandlingData data, DataOutputStream out) {
		this.runnuble = new OutputHandlerRunnuble(data, out);
		this.thread = new Thread(runnuble);
	}

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
