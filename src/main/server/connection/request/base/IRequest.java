package main.server.connection.request.base;

public interface IRequest {
	String getRequestString();
	RequestType getType();
}
