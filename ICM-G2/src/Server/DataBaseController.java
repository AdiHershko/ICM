package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import Common.Request;
import Common.Stage;
import Common.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Common.ClientServerMessage;
import Common.Enums;
import Common.Report;

public class DataBaseController {
	private static Connection c;
	private static String url = "jdbc:mysql://remotemysql.com:3306/rPTfgnHCnB?useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String username = "rPTfgnHCnB";
	private static String password = "atcFy4mIAf";
	
	private static EmailService emailService;

	public static void setUrl(String url) {
		DataBaseController.url = url;
	}

	public static void setUsername(String username) {
		DataBaseController.username = username;
	}

	public static void setPassword(String password) {
		DataBaseController.password = password;
	}

	public static boolean Connect() {
		System.out.println("Connecting to database...");

		try {
			c = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected!");
			ServerChooseController.loading = false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		emailService = EmailService.getInstannce();
		return true;
	}

	public static String addISUser(User current) {
		String query = "insert into Users(Users.username,Users.Password,Users.FirstName,Users.LastName,Users.Mail,Users.Role) values (?,?,?,?,?,?)";
		PreparedStatement st;
		try {
			st = c.prepareStatement(query);
			st.setString(1, current.getUsername());
			st.setString(2, current.getPassword());
			st.setString(3, current.getFirstName());
			st.setString(4, current.getLastName());
			st.setString(5, current.getMail());
			st.setInt(6, Enums.Role.getRoleByEnum(current.getRole()));
			st.execute();
		} catch (SQLIntegrityConstraintViolationException e) {
			return "IDEXISTS";
		} catch (SQLException e) {
			return "CANTEXECUTE";
		}
		return "GOOD";
	}

	public static String[] getISUser(String userID) {
		String query = "select Users.Password,Users.FirstName,Users.LastName,Users.Mail,Users.Role from Users where username='"
				+ userID + "' and Users.Role>0";
		PreparedStatement st;
		ResultSet rs = null;
		String[] res = null;
		try {
			st = c.prepareStatement(query);
			rs = st.executeQuery();
			res = new String[5];
			if (!rs.next()) {
				return null;
			}
			for (int i = 0; i < 4; i++)
				res[i] = rs.getString(i + 1);
			res[4] = "" + rs.getInt(5);
		} catch (SQLException e) {
			return null;
		}
		return res;
	}

	public static boolean updateISUser(User current) {
		String query = "update Users set Password=? ,FirstName=? ,LastName=? ,Mail=? ,Role=? where username=?";
		PreparedStatement st;
		try {
			st = c.prepareStatement(query);
			st.setString(1, current.getPassword());
			st.setString(2, current.getFirstName());
			st.setString(3, current.getLastName());
			st.setString(4, current.getMail());
			st.setInt(5, Enums.Role.getRoleByEnum(current.getRole()));
			st.setString(6, current.getUsername());
			st.execute();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public static void updateStageMember(String id, Request req, int stage) {
		PreparedStatement st;
		try {
			st = c.prepareStatement(
					"update Stages set Member='," + id + ",' where StageName=" + stage + " and RequestID=" + req.getId());
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (Enums.RequestStageENUM.getRequestStageENUMByEnum(req.getCurrentStage())==0)
			ChangeRequestStage(req.getId(),true);
		updateLogChangeStageHandler(req.getId(),stage,id);
	}

	public static ObservableList<Request> getRequestsForIS(String UserName, int id, boolean search) {
		String query;
		if (search == false) {
			query = "select * from Requests where (status=0 or status=2) and currenthandlers LIKE '%," + UserName
					+ ",%'";
		} else {
			query = "select * from Requests where currenthandlers LIKE '%," + UserName + ",%'" + "and id=" + id;
		}
		return getRequests(query);
	}

	public static ObservableList<Request> getRequestsForManager(int id, boolean search) {
		String query;
		if (search == false) {
			query = "select * from Requests where (status=0 or status=2 or status=3)";
			return getRequests(query);
		} else {
			query = "select * from Requests where id=" + id;
			return getRequests(query);
		}

	}

	public static void updateRequestDetails(Request r) {
		String query= "UPDATE Requests SET Description = ?, `Change` = ?, ChangeReason = ?, Connect = ? WHERE ID = ?";
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.setString(1, r.getDescription());
			statement.setString(2, r.getChanges());
			statement.setString(3, r.getChangeReason());
			statement.setString(4, r.getComments());
			statement.setInt(5, r.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void ChangeReportFailure(String[] Msg) {
		String query = "UPDATE Stages SET ReportFailure = ? WHERE StageName = '4' and RequestID = ?";
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.setString(1, Msg[0]);
			statement.setInt(1, Integer.parseUnsignedInt(Msg[1]));
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void changeReqToClosed(int Id) {
		String query = "UPDATE Requests SET Stage = 5 WHERE ID =" + Id;
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		query = "update Stages set ActualDate='" + (new DateTime()).toString() + "' where RequestID=" + Id + " and StageName=2";
		try {
			statement = c.prepareStatement(query);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	public static void changeToRejected(int Id) {
		String query = "UPDATE Stages SET `ReportFailure` = 'Rejected' WHERE (`StageName` = '5') and (`RequestID` = '"+Id+"')";
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void changeRequestStatus(int id, int status) {
		String query = "UPDATE Requests SET Status = "+status+ " WHERE ID = " + id;
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		updateLogChangeStatus(id,status);
	}

	public static ObservableList<Request> getRequests(String query) {
		ObservableList<Request> o = FXCollections.observableArrayList();
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				try {
					Request r = new Request(rs.getInt(1), rs.getString(2),
							Enums.SystemENUM.getSystemByInt(rs.getInt(3)), rs.getString(4), rs.getString(5),
							rs.getString(6), new DateTime(rs.getString(9)));
					r.setStages(getRequestStages(rs.getInt(1)));
					r.setCurrentStage(Enums.RequestStageENUM.getRequestStageENUM(rs.getInt(7)));
					r.setComments(rs.getString(10));
					r.setStatus(Enums.RequestStatus.getStatusByInt(rs.getInt(8)));
					r.setIsDenied(rs.getInt(13));
					o.add(r);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return o;
	}

	public static ObservableList<Request> getRequestsForCollege(String userName, int id, boolean search,
			boolean unActive) {
		String query = "select * from Requests where Requestor='" + userName + "'";
		if (unActive == false) {
			query += " and Status=0";
		}
		if (search == true) {
			query += " and ID=" + id;

		}
		return getRequests(query);

	}

	public static ObservableList<Request> getRequestsForIS(String UserName, int id, boolean search, boolean unActive) {
		String query = "select * from Requests where currenthandlers LIKE ',%" + UserName + "%,'";
		if (unActive == false) {
			query+=" and (status=0 or status=2)";
		}
		if (search == true) {
			query+=" and id=" + id;
		}
		return getRequests(query);
	}

	public static ObservableList<Request> getRequestsForManager(int id, boolean search, boolean unActive) {
		String query = "select * from Requests";
		if (unActive == false) {
			query += " where (status=0 or status=2 or status=3)";
			if (search == true) {
				query += " and id=" + id;
			}
		}
		if (search == true && unActive == true) {
			query += " where id=" + id;
		}
		return getRequests(query);

	}

	public static Stage[] getRequestStages(int RequestID) {
		Stage RequestStages[] = new Stage[Enums.numberOfStages];
		String query = "select * from Stages where RequestID=" + RequestID;
		PreparedStatement statement;
		ResultSet rs = null;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < Enums.numberOfStages; i++) {
			RequestStages[i] = new Stage();
		}
		try {
			while (rs.next()) {
				Stage s = RequestStages[rs.getInt(1)];
				Enums.RequestStageENUM tmp = Enums.RequestStageENUM.getRequestStageENUM(rs.getInt(1));
				s.setStageName(tmp);
				s.setPlannedDueDate(rs.getString(2));
				s.setIsApproved(rs.getInt(3) == 1);
				s.setIsExtended(rs.getInt(4) == 1);
				ArrayList<String> stageMembers = new ArrayList<String>();
				String handlers_array[] = (rs.getString(5)).split(",");
				for (int i = 0; i < handlers_array.length; i++)
					stageMembers.add(handlers_array[i]);
				s.setStageMembers(stageMembers);
				s.setActualDate(rs.getString(7));
				s.setExtendedDueDate(rs.getString(8));
				s.setReportFailure(rs.getString(9));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RequestStages;
	}

	public static User SearchUser(String user, String pass) {
		String query = "select * from Users where binary username =? and binary Password =?";
		ResultSet rs = null;
		User us = null;
		PreparedStatement statement;
		try {
			statement = c.prepareStatement(query);
			statement.setString(1, user);
			statement.setString(2, pass);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (rs.next()) {
				try {
					if (rs.getInt(10) == 0 | rs.getInt(10) == 1) {// TODO
						us = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
								rs.getString(5), Enums.Role.getRoleENUM(rs.getInt(6)));
						query = "update Users set isLoggedIn=1 where username ='" + user + "'";
						statement = c.prepareStatement(query);
						statement.execute();
					} else {
						us = new User("", "", "", "", "", Enums.Role.getRoleENUM(rs.getInt(6)));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return us;
	}
	public static User SearchUser(String user) {
		String query = "select * from Users where binary username =?";
		ResultSet rs = null;
		User us = null;
		PreparedStatement statement;
		try {
			statement = c.prepareStatement(query);
			statement.setString(1, user);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (rs.next()) {
				try {
					if (rs.getInt(10) == 0 | rs.getInt(10) == 1) {// TODO
						us = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
								rs.getString(5), Enums.Role.getRoleENUM(rs.getInt(6)));
						query = "update Users set isLoggedIn=1 where username ='" + user + "'";
						statement = c.prepareStatement(query);
						statement.execute();
					} else {
						us = new User("", "", "", "", "", Enums.Role.getRoleENUM(rs.getInt(6)));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return us;
	}

	public static ArrayList<String> GetCommitte() {
		String query = "select username from Users where role=3 or role=2";
		ResultSet rs = null;
		PreparedStatement statement;
		ArrayList<String> Committe = new ArrayList<String>();
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				try {
					Committe.add(rs.getString(1));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Committe;
	}

	public static Report SearchReport(int id) {
		String query = "select * from Reports where requestID=" + String.valueOf(id);
		ResultSet rs = null;
		Report report = null;
		PreparedStatement statement;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (rs.next()) {
				try {
					report = new Report(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(6), rs.getInt(7));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return report;
	}

	public static String getSupervisor() {
		String query = "select Users.username from Users where Role=4";
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getChairman() {
		String query = "select Users.username from Users where Role=2";
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static int countCommitteMembers() {
		String query = "select COUNT(*) from Users where Role=3";
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static int CreateReportForRequest(Report r) {
		Report check = SearchReport(r.getRequestId());
		String query;
		if (check != null) {
			query = "UPDATE Reports SET requestID= ?, description = ?, result = ?, location = ?, ";
			query += "constraints= ?, risks = ?, duration = ?  WHERE requestID = " + String.valueOf(r.getRequestId());
		} else {
			query = "INSERT INTO Reports (Reports.requestID, Reports.description, Reports.result, Reports.location, Reports.constraints,"
					+ "Reports.risks, Reports.duration) Values (?,?,?,?,?,?,?)";
		}
		PreparedStatement st = null;

		try {
			st = c.prepareStatement(query);
			st.setInt(1, r.getRequestId());
			st.setString(2, r.getDescription());
			st.setString(3, r.getResult());
			st.setString(4, r.getLocation());
			st.setString(5, r.getConstrains());
			st.setString(6, r.getRisks());
			st.setInt(7, r.getDurationAssesment());
			st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;

	}

	public static int CreateNewRequest(Request r) {
		String temp = getSupervisor();
		PreparedStatement st = null;
		ResultSet rs;
		int id = 0;
		try {
			String query = "INSERT INTO Requests (Requests.Requestor, Requests.System, Requests.Description, Requests.Change, Requests.ChangeReason,"
					+ " Requests.Stage, Requests.Status, Requests.Date, Requests.Connect, Requests.CurrentHandlers) Values (?,?,?,?,?,?,?,?,?,?)";
			st = c.prepareStatement(query, st.RETURN_GENERATED_KEYS);
			st.setString(1, r.getRequestorID());
			st.setInt(2, Enums.SystemENUM.getSystemByEnum(r.getSystem()));
			st.setString(3, r.getDescription());
			st.setString(4, r.getChanges());
			st.setString(5, r.getChangeReason());
			st.setInt(6, 0);
			st.setInt(7, 0);
			st.setString(8, r.getDate().toString());
			st.setString(9, r.getComments());
			st.setString(10, "," + temp + ",");
			st.executeUpdate();
			rs = st.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		CreateStagesDB(id);
		return id;
	}

	public static void CreateStagesDB(int id) {
		String temp = "";
		try {
			PreparedStatement st = null;
			String query = "INSERT INTO Stages (Stages.StageName, Stages.isApproved, Stages.isExtended, Stages.Member, Stages.RequestID) Values (?,?,?,?,?)";
			for (int i = 0; i < Enums.numberOfStages; i++) {
				Thread.sleep(100);
				st = c.prepareStatement(query);
				st.setInt(1, i);
				st.setInt(2, 0);
				st.setInt(3, 0);
				if (i == 0 || i == 5)
					st.setString(4, "," + getSupervisor() + ",");
				else if (i == 1)
					st.setString(4, GetRandomISUser());
				else if (i == 2)
					st.setString(4, GetComitteString());
				else
					st.setString(4, temp);
				st.setInt(5, id);
				st.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean ChangeRequestStage(int id, boolean Up) {
		String query, newMembers = null;
		ResultSet rs;
		PreparedStatement statement = null;
		int currentStage = 0, newStage;
		
		//getting the current stage
		query = "Select Stage from Requests where id=" + id;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
			rs.next();
			currentStage = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//saving the next stage
		if (Up)
			newStage = currentStage+1;
		else
			newStage = currentStage-1;
		
		//getting new stage members
		query = "Select Member from Stages where RequestID=" + id + " and StageName=" + newStage;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
			rs.next();
			newMembers = rs.getString(1);
			} catch (SQLException e) {
				e.printStackTrace();
				}
		if (newMembers.equals("")) {
			//TODO send message to supervisor
			return false;
		}
		
		//setting current stage ActudalDate
		query = "update Stages set ActualDate='" + (new DateTime()).toString() + "' where RequestID=" + id
				+ " and StageName=" + (currentStage);
		try {
			statement = c.prepareStatement(query);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		//if stages 2 or 4, needs to have auto date
		if (newStage == 2 || newStage == 4) {
			DateTime dueDate = new DateTime();
			dueDate = dueDate.plusDays(7);
			query = "update Stages set PlannedDueDate='" + dueDate.toString() + "' where StageName=" + newStage + " and RequestID=" + id;
			try {
				statement = c.prepareStatement(query);
				statement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//updating stage in requests
		query = "update Requests set Stage="+ newStage +" where id=" + id;
		try {
			statement = c.prepareStatement(query);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		//saving it in requests
		query = "update Requests set CurrentHandlers='" + newMembers + "' where id=" + id;
		try {
			statement = c.prepareStatement(query);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void AppointStageHandlers(int id, int stage, String handlers) {
		String query = "update Stages set Member='," + handlers + ",' where RequestID=" + id + " and StageName="
				+ stage;
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	public static String GetComitteString() {
		String results = "";
		String query = "select * from Users where Role = 2";
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (rs.next()) {
				try {
					results += "," + rs.getString(1) + ",";
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		query = "select * from Users where Role = 3";
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				try {
					results += "," + rs.getString(1) + ",";
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	public static String GetRandomISUser() {
		ArrayList<String> users = new ArrayList<String>();
		String query = "select * from Users where Role = 1";
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (rs.next()) {
				try {
					String tmp = "," + rs.getString(1) + ",";
					users.add(tmp);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		Random rand = new Random();
		return users.get(rand.nextInt(users.size()));
	}

	public static boolean isRequestDenied(int requestID) {
		String query = "SELECT isDenied from Requests where ID=" + requestID;
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			rs.next();
			if (rs.getInt(1) == 1)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void setDate(int requestID, int stage, String date) {
		String query = "update Stages set PlannedDueDate='" + date + "' where StageName=" + stage + " and RequestID="
				+ requestID;
		PreparedStatement statement;
		try {
			statement = c.prepareStatement(query);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		updateLogChangeStageDate(requestID,stage,date);
	}

	public static boolean setRequestDeny(Request r, int stage) {
		String query = "update Requests set isDenied=" + r.getIsDenied() + " where ID=" + r.getId();
		PreparedStatement statement;
		try {
			statement = c.prepareStatement(query);
			statement.execute(query);
			if (r.getIsDenied() == 0)
			{
				query = "update Stages set isExtended=1,isApproved=1, PlannedDueDate=ExtendedDueDate where RequestID="+r.getId()+" and StageName="+stage;
				statement.execute(query);
				statement.execute("update Stages set ExtendedDueDate=null where RequestID="+r.getId()+" and StageName="+stage);
				return false;
			}
			else {
				query = "update Stages set isApproved=0,isExtended=0, ExtendedDueDate=null where RequestID="+r.getId()+" and StageName="+stage;
				statement.execute(query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void DisconnectUser(String UserID) {
		String query = "update Users set isLoggedIn=0 where username ='" + UserID + "'";
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	public static void updateLogRequestDetails(Request request) {
		String query = "INSERT INTO SupLog (`RequestId`, `Date`, `Field`, `WhatChanged`) VALUES (?,?,?,?)";
		String WhatChanged = request.getDescription()+"||"+request.getChanges()+"||"+request.getChangeReason()+"||"+request.getComments();
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.setInt(1, request.getId());
			statement.setString(2, new DateTime().toString("dd/MM/yyyy HH:mm:ss"));
			statement.setString(3, "Details");
			statement.setString(4, WhatChanged);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateLogChangeStatus(int id, int status) {
		String query = "INSERT INTO SupLog (`RequestId`, `Date`, `Field`, `WhatChanged`) VALUES (?,?,?,?)";
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.setInt(1, id);
			statement.setString(2, new DateTime().toString("dd/MM/yyyy HH:mm:ss"));
			statement.setString(3, "Status");
			statement.setString(4, ((Integer)status).toString());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateLogChangeStageHandler(int id, int stage, String member) {
		String query = "INSERT INTO SupLog (`RequestId`, `Date`, `Field`, `WhatChanged`) VALUES (?,?,?,?)";
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.setInt(1, id);
			statement.setString(2, new DateTime().toString("dd/MM/yyyy HH:mm:ss"));
			statement.setString(3, "Stage "+stage+" handler");
			statement.setString(4, member);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateLogChangeStageDate(int id, int stage, String date) {
		String query = "INSERT INTO SupLog (`RequestId`, `Date`, `Field`, `WhatChanged`) VALUES (?,?,?,?)";
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.setInt(1, id);
			statement.setString(2, new DateTime().toString());
			statement.setString(3, "Stage "+stage+" due date");
			statement.setString(4, date);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void changeExtendedDate(Request r,String date)
	{
		DateTime dt;
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		dt = dtf.parseDateTime(date);
		String query = "update Stages set ExtendedDueDate='"+dt.toString()+"' where RequestID ='" + r.getId() + "' and StageName="+Enums.RequestStageENUM.getRequestStageENUMByEnum(r.getCurrentStage());
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}


	public static void setClosingDate(int id) {
		String query = "update Stages set ActualDate='" + (new DateTime()).toString() + "' where RequestID=" + id + " and StageName=5";
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public static void genAutoMessages() {
		String query = "select * from Requests";
		ObservableList<Request> list = getRequests(query);
		autoMessage24hDueDate(list);
		autoMessageExceptions(list);
	}
	
	public static void autoMessage24hDueDate(ObservableList<Request> list) {
		for(Request r : list) {
			int currstageNum = Enums.RequestStageENUM.getRequestStageENUMByEnum(r.getCurrentStage());
			Stage currentStage = r.getStages()[currstageNum];
			if (currentStage.getPlannedDueDate() != null) {
				DateTime plannedDate = new DateTime(currentStage.getPlannedDueDate());
				DateTime check24 = new DateTime();
				check24 = check24.plusDays(1);
				if (plannedDate.isBefore(check24)) {
					User receiver = SearchUser(currentStage.getStageMembers().get(1));
					EmailMessage toSend = new EmailMessage(r, receiver);
					toSend.build24hStageMsg();
					addAndSendMessage(toSend);
				}
			}
		}
	}
	
	public static void autoMessageExceptions(ObservableList<Request> list) {
		
	}
	
	public static void addAndSendMessage(EmailMessage m) {
		String query = "insert into Messages (Messages.RequestID, Messages.Title, Messages.Details, Messages.Receiver) values (?,?,?,?)";
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.setInt(1, m.getRequest().getId());
			statement.setString(2, m.getSubject());
			statement.setString(3, m.getBody());
			statement.setString(4, m.getReceiverUser().getUsername());
			statement.execute();
		} catch (SQLException e) {
			return;
		}
		emailService.sendEmail(m);
	}
}
