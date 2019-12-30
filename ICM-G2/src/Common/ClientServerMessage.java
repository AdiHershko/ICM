package Common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	private Object [] array;
	private int id;
	private int stage;
	private Report report;
	private List l;

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

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

	public List getL() {
		return l;
	}

	public void setL(List l) {
		this.l = l;
	}

	public ClientServerMessage(MessageEnum type,int id,String msg)
	{
		this.type=type;
		this.id=id;
		this.msg=msg;
	}

	public ClientServerMessage(MessageEnum type,int id,int stage)
	{
		this.type=type;
		this.id=id;
		this.stage=stage;
	}


	public ClientServerMessage(MessageEnum type, List l) {
		this.type = type;
		this.l=l;
	}

	public ClientServerMessage(MessageEnum type, int id) {
		this.type = type;
		this.id = id;
	}

	public ClientServerMessage(MessageEnum type, Request request) {
		this.type = type;
		this.request = request;
	}
	public ClientServerMessage(MessageEnum type, Report report) {
		this.type = type;
		this.report = report;
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

	public ClientServerMessage(MessageEnum type, Object[] array) {
		this.type=type;
		this.array=array;
	}

	public ClientServerMessage(MessageEnum type, String msg) {
		this.type = type;
		this.msg = msg;
	}

	public ClientServerMessage(MessageEnum type, int id, int stage, String msg) {
		this.type = type;
		this.id = id;
		this.msg = msg;
		this.stage = stage;
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

	public Object[] getArray() {
		return array;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}
}