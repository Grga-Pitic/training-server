package main.server.connection.request.handlers;

import main.server.connection.request.base.IRequest;

public interface IRequestHandler {
	void Handle(IRequest request);
}
