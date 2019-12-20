package Common;

import java.io.File;
import java.io.Serializable;

import Common.Enums.*;

public class ClientServerMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private MessageEnum type;
	private String msg;
	private User user;
	private File file;
	private Request request;

	public ClientServerMessage(MessageEnum type) {
		this.type = type;
	}

	public ClientServerMessage(User user) {
		this.user = user;
	}

	public ClientServerMessage(MessageEnum type, String msg) {
		this.type = type;
		this.msg = msg;
	}

	public ClientServerMessage(MessageEnum type, File file,Request request) {
		this.type = type;
		this.file=file;
		this.request=request;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public MessageEnum getType() {
		return type;
	}

	public void setType(MessageEnum type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}