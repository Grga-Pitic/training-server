package main.server.connection.request.handlers.impls;

import main.server.connection.request.base.IRequest;
import main.server.connection.request.handlers.IRequestHandler;

public class DefaultRequestHandler implements IRequestHandler {

	@Override
	public void Handle(IRequest request) {
		System.out.println(request.getRequestString() + " - wasn't handled");
	}
	
}
