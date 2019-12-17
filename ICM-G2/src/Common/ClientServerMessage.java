package Common;

import java.io.Serializable;

import Common.Enums.*;

public class ClientServerMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private MessageEnum type;
	private SQLCmds SQLtype;
	private String msg;

	public ClientServerMessage(MessageEnum type) {
		this.type = type;
	}
	
	public ClientServerMessage(MessageEnum type, SQLCmds SQLtype, String msg) {
		this.type = type;
		this.SQLtype = SQLtype;
		this.msg = msg;
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
}