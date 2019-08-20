package main.server.connection.handler.base;

public interface IQueueHandler {
	void StartHandling();
	void StopHandling();
	boolean isRun();
	boolean isFree();
	HandlingData getData();
}
