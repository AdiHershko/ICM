package Common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.Serializable;

import Common.Enums.*;
import javafx.collections.ObservableList;

public class ClientServerMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private MessageEnum type;
	private String msg;
	private User user;
	private File file;
	private Request request;
	private String fileName;
	private BufferedInputStream bis;
	private byte[] buffer;
	private boolean uploadstatus;
	private Object [] list;
	private int id;

	public boolean isUploadstatus() {
		return uploadstatus;
	}

	public void setUploadstatus(boolean uploadstatus) {
		this.uploadstatus = uploadstatus;
	}

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
	
	public ClientServerMessage(MessageEnum type, int id) {
		this.type = type;
		this.id = id;
	}
	
	public ClientServerMessage(MessageEnum type, Request request) {
		this.type = type;
		this.request = request;
	}

	public ClientServerMessage(MessageEnum type, String fileName,byte[] buffer,Request request) {
		this.type = type;
		this.fileName=fileName;
		this.buffer=buffer;
		this.request=request;
	}

	public ClientServerMessage(MessageEnum type, boolean sucess) {
		this.type=type;
		this.uploadstatus=sucess;
	}
	
	public ClientServerMessage(MessageEnum type, Object[] list) {
		this.type=type;
		this.list=list;
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BufferedInputStream getBis() {
		return bis;
	}

	public void setBis(BufferedInputStream bis) {
		this.bis = bis;
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
	
	public Object[] getList() {
		return list;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}