package main.server.connection.request.base;

public enum RequestType {
	Add, 
	Auth,
	Contact,
	Message, 
	Register, 
	ContactList,
	Empty,
	AuthSuccess,
	AuthFailed,
	RegistationSuccess,
	RegistationFailed,
	InvalidMessage,
	InvalidRequest,
	AddSuccess,
	AddFailed
}
