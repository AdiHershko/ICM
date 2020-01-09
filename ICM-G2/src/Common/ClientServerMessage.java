package Common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.Serializable;
import java.util.List;

import Common.Enums.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientServerMessage.
 */
public class ClientServerMessage implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The type. */
	private MessageEnum type;
	
	/** The msg. */
	private String msg;
	
	/** The user. */
	private User user;
	
	/** The file. */
	private File file;
	
	/** The request. */
	private Request request;
	
	/** The file name. */
	private String fileName;
	
	/** The bis. */
	private BufferedInputStream bis;
	
	/** The buffer. */
	private byte[] buffer;
	
	/** The uploadstatus. */
	private boolean uploadstatus;
	
	/** The array. */
	private Object[] array;
	
	/** The id. */
	private int id;
	
	/** The stage. */
	private int stage;
	
	/** The report. */
	private Report report;
	
	/** The l. */
	private List l;
	
	/** The search. */
	private boolean search;
	
	/** The un active. */
	private boolean unActive;
	
	/** The enm. */
	private SystemENUM enm;
	
	/** The boolarr. */
	private boolean[] boolarr;

	/**
	 * Checks if is search.
	 *
	 * @return true, if is search
	 */
	public boolean isSearch() {
		return search;
	}

	/**
	 * Sets the search.
	 *
	 * @param search the new search
	 */
	public void setSearch(boolean search) {
		this.search = search;
	}

	/**
	 * Gets the report.
	 *
	 * @return the report
	 */
	public Report getReport() {
		return report;
	}

	/**
	 * Sets the report.
	 *
	 * @param report the new report
	 */
	public void setReport(Report report) {
		this.report = report;
	}

	/**
	 * Checks if is uploadstatus.
	 *
	 * @return true, if is uploadstatus
	 */
	public boolean isUploadstatus() {
		return uploadstatus;
	}

	/**
	 * Sets the uploadstatus.
	 *
	 * @param uploadstatus the new uploadstatus
	 */
	public void setUploadstatus(boolean uploadstatus) {
		this.uploadstatus = uploadstatus;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 */
	public ClientServerMessage(MessageEnum type) {
		this.type = type;
	}

	/**
	 * Gets the enm.
	 *
	 * @return the enm
	 */
	public SystemENUM getEnm() {
		return enm;
	}

	/**
	 * Sets the enm.
	 *
	 * @param enm the new enm
	 */
	public void setEnm(SystemENUM enm) {
		this.enm = enm;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param enm the enm
	 */
	public ClientServerMessage(MessageEnum type, SystemENUM enm) {
		this.type = type;
		this.enm = enm;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param user the user
	 */
	public ClientServerMessage(User user) {
		this.user = user;
	}

	/**
	 * Gets the l.
	 *
	 * @return the l
	 */
	public List getL() {
		return l;
	}

	/**
	 * Sets the l.
	 *
	 * @param l the new l
	 */
	public void setL(List l) {
		this.l = l;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param id the id
	 * @param msg the msg
	 */
	public ClientServerMessage(MessageEnum type, int id, String msg) {
		this.type = type;
		this.id = id;
		this.msg = msg;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param id the id
	 * @param stage the stage
	 */
	public ClientServerMessage(MessageEnum type, int id, int stage) {
		this.type = type;
		this.id = id;
		this.stage = stage;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param l the l
	 */
	public ClientServerMessage(MessageEnum type, List l) {
		this.type = type;
		this.l = l;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param id the id
	 */
	public ClientServerMessage(MessageEnum type, int id) {
		this.type = type;
		this.id = id;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param request the request
	 */
	public ClientServerMessage(MessageEnum type, Request request) {
		this.type = type;
		this.request = request;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param request the request
	 * @param stage the stage
	 */
	public ClientServerMessage(MessageEnum type, Request request, int stage) {
		this.type = type;
		this.request = request;
		this.stage = stage;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param request the request
	 * @param msg the msg
	 */
	public ClientServerMessage(MessageEnum type, Request request, String msg) {
		this.type = type;
		this.request = request;
		this.msg = msg;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param report the report
	 */
	public ClientServerMessage(MessageEnum type, Report report) {
		this.type = type;
		this.report = report;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param fileName the file name
	 * @param buffer the buffer
	 * @param reqid the reqid
	 */
	public ClientServerMessage(MessageEnum type, String fileName, byte[] buffer, int reqid) {
		this.type = type;
		this.fileName = fileName;
		this.buffer = buffer;
		this.id = reqid;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param sucess the sucess
	 */
	public ClientServerMessage(MessageEnum type, boolean sucess) {
		this.type = type;
		this.uploadstatus = sucess;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param array the array
	 */
	public ClientServerMessage(MessageEnum type, Object[] array) {
		this.type = type;
		this.array = array;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param msg the msg
	 */
	public ClientServerMessage(MessageEnum type, String msg) {
		this.type = type;
		this.msg = msg;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param id the id
	 * @param stage the stage
	 * @param msg the msg
	 */
	public ClientServerMessage(MessageEnum type, int id, int stage, String msg) {
		this.type = type;
		this.id = id;
		this.msg = msg;
		this.stage = stage;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param msg the msg
	 * @param id the id
	 * @param search the search
	 */
	public ClientServerMessage(MessageEnum type, String msg, int id, boolean search) {
		this.type = type;
		this.msg = msg;
		this.id = id;
		this.search = search;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param msg the msg
	 * @param search the search
	 */
	public ClientServerMessage(MessageEnum type, String msg, boolean search) {
		this.type = type;
		this.msg = msg;
		this.search = search;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param user the user
	 */
	public ClientServerMessage(MessageEnum type, User user) {
		this.type = type;
		this.user = user;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param user the user
	 * @param boolarr the boolarr
	 */
	public ClientServerMessage(MessageEnum type, User user, boolean[] boolarr) {
		this.type = type;
		this.user = user;
		this.boolarr = boolarr;
	}

	/**
	 * Instantiates a new client server message.
	 *
	 * @param type the type
	 * @param msg the msg
	 * @param id the id
	 * @param search the search
	 * @param unActive the un active
	 */
	public ClientServerMessage(MessageEnum type, String msg, int id, boolean search, boolean unActive) {
		this.type = type;
		this.msg = msg;
		this.id = id;
		this.search = search;
		this.unActive = unActive;
	}

	/**
	 * Gets the buffer.
	 *
	 * @return the buffer
	 */
	public byte[] getBuffer() {
		return buffer;
	}

	/**
	 * Sets the buffer.
	 *
	 * @param buffer the new buffer
	 */
	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 *
	 * @param fileName the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Gets the bis.
	 *
	 * @return the bis
	 */
	public BufferedInputStream getBis() {
		return bis;
	}

	/**
	 * Gets the boolarr.
	 *
	 * @return the boolarr
	 */
	public boolean[] getBoolarr() {
		return boolarr;
	}

	/**
	 * Sets the boolarr.
	 *
	 * @param boolarr the new boolarr
	 */
	public void setBoolarr(boolean[] boolarr) {
		this.boolarr = boolarr;
	}

	/**
	 * Sets the bis.
	 *
	 * @param bis the new bis
	 */
	public void setBis(BufferedInputStream bis) {
		this.bis = bis;
	}

	/**
	 * Gets the request.
	 *
	 * @return the request
	 */
	public Request getRequest() {
		return request;
	}

	/**
	 * Sets the request.
	 *
	 * @param request the new request
	 */
	public void setRequest(Request request) {
		this.request = request;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public MessageEnum getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(MessageEnum type) {
		this.type = type;
	}

	/**
	 * Gets the msg.
	 *
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * Sets the msg.
	 *
	 * @param msg the new msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Sets the file.
	 *
	 * @param file the new file
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * Gets the array.
	 *
	 * @return the array
	 */
	public Object[] getArray() {
		return array;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the stage.
	 *
	 * @return the stage
	 */
	public int getStage() {
		return stage;
	}

	/**
	 * Sets the stage.
	 *
	 * @param stage the new stage
	 */
	public void setStage(int stage) {
		this.stage = stage;
	}

	/**
	 * Checks if is un active.
	 *
	 * @return true, if is un active
	 */
	public boolean isUnActive() {
		return unActive;
	}

	/**
	 * Sets the un active.
	 *
	 * @param unActive the new un active
	 */
	public void setUnActive(boolean unActive) {
		this.unActive = unActive;
	}
}