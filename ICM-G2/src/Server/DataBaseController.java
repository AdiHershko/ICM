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
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import Common.Request;
import Common.Stage;
import Common.SupervisorLog;
import Common.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Common.Enums;
import Common.Enums.SystemENUM;
import Server.Controllers.ServerChooseController;
import Common.Message;
import Common.Report;

// TODO: Auto-generated Javadoc
/**
 * The Class DataBaseController.
 * Handles all the SQL Data Base queries and functions.
 */
public class DataBaseController {
	
	/** The c. */
	private static Connection c;
	
	/** The url. */
	private static String url = "jdbc:mysql://remotemysql.com:3306/rPTfgnHCnB?useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	/** The username. */
	private static String username = "rPTfgnHCnB";
	
	/** The password. */
	private static String password = "atcFy4mIAf";

	/** The email service. */
	private static EmailService emailService;

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public static void setUrl(String url) {
		DataBaseController.url = url;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public static void setUsername(String username) {
		DataBaseController.username = username;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public static void setPassword(String password) {
		DataBaseController.password = password;
	}

	/**
	 * Connect.
	 *
	 * @return true, if successful
	 */
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
		setAllDisconnected();
		return true;
	}

	/**
	 * Sets the all disconnected.
	 */
	public static void setAllDisconnected() {
		String query = "update Users set isLoggedIn=0";
		PreparedStatement st;
		try {
			st = c.prepareStatement(query);
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the IS user.
	 *
	 * @param current the current user
	 * @param permissions the permissions he has
	 * @return the answer string
	 */
	public static String addISUser(User current, boolean[] permissions) {
		String query = "insert into Users(Users.username,Users.Password,Users.FirstName,Users.LastName,Users.Mail,Users.Role,Users.Department) values (?,?,?,?,?,?,?)";
		PreparedStatement st;
		try {
			st = c.prepareStatement(query);
			st.setString(1, current.getUsername());
			st.setString(2, current.getPassword());
			st.setString(3, current.getFirstName());
			st.setString(4, current.getLastName());
			st.setString(5, current.getMail());
			st.setInt(6, Enums.Role.getRoleByEnum(current.getRole()));
			String departments = ",";
			for (int i = 0; i < permissions.length; i++)
				if (permissions[i] == true)
					departments += Enums.SystemENUM.getSystemByInt(i).toString() + ",";
			if (departments.equals(","))
				departments = "";
			st.setString(7, departments);
			st.execute();
		} catch (SQLIntegrityConstraintViolationException e) {
			return "IDEXISTS";
		} catch (SQLException e) {
			return "CANTEXECUTE";
		}
		return "GOOD";
	}

	/**
	 * Gets the IS user.
	 *
	 * @param userID the user ID
	 * @return the users
	 */
	public static String[] getISUser(String userID) {
		String query = "select Users.Password,Users.FirstName,Users.LastName,Users.Mail,Users.Role,Users.Department from Users where username='"
				+ userID + "' and Users.Role>0";
		PreparedStatement st;
		ResultSet rs = null;
		String[] res = null;
		try {
			st = c.prepareStatement(query);
			rs = st.executeQuery();
			res = new String[6];
			if (!rs.next()) {
				return null;
			}
			for (int i = 0; i < 4; i++)
				res[i] = rs.getString(i + 1);
			res[4] = "" + rs.getInt(5);
			res[5] = rs.getString(6);
		} catch (SQLException e) {
			return null;
		}
		return res;
	}

	/**
	 * Update IS user.
	 *
	 * @param current the current user
	 * @param permissions the new permissions
	 * @return true, if successful
	 */
	public static boolean updateISUser(User current, boolean[] permissions) {
		String query = "update Users set Password=? ,FirstName=? ,LastName=? ,Mail=? ,Role=? ,Department=? where username=?";
		PreparedStatement st;
		try {
			st = c.prepareStatement(query);
			st.setString(1, current.getPassword());
			st.setString(2, current.getFirstName());
			st.setString(3, current.getLastName());
			st.setString(4, current.getMail());
			st.setInt(5, Enums.Role.getRoleByEnum(current.getRole()));
			st.setString(7, current.getUsername());
			String departments = ",";
			for (int i = 0; i < permissions.length; i++)
				if (permissions[i] == true)
					departments += Enums.SystemENUM.getSystemByInt(i).toString() + ",";
			if (departments.equals(","))
				departments = "";
			st.setString(6, departments);
			st.execute();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	/**
	 * Update stage member.
	 *
	 * @param id the user id
	 * @param req the request
	 * @param stage the stage to update
	 */
	public static void updateStageMember(String id, Request req, int stage) {
		PreparedStatement st;
		try {
			st = c.prepareStatement("update Stages set Member='," + id + ",' where StageName=" + stage
					+ " and RequestID=" + req.getId());
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		updateLogChangeStageHandler(req.getId(), stage, id);
	}

	/**
	 * Update request details.
	 *
	 * @param r the request
	 */
	public static void updateRequestDetails(Request r) {
		String query = "UPDATE Requests SET Description = ?, `Change` = ?, ChangeReason = ?, Connect = ? WHERE ID = ?";
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

	/**
	 * Change report failure.
	 *
	 * @param Msg the message
	 */
	public static void ChangeReportFailure(String[] Msg) {
		String query = "UPDATE Stages SET ReportFailure = ? WHERE StageName = '4' and RequestID = ?";
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.setString(1, Msg[0]);
			statement.setInt(2, Integer.parseUnsignedInt(Msg[1]));
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Change request to closed.
	 *
	 * @param Id the id
	 */
	public static void changeReqToClosed(int Id) {
		String query = "UPDATE Requests SET Stage = 5 WHERE ID =" + Id;
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		query = "update Stages set ActualDate='" + (new DateTime()).toString() + "' where RequestID=" + Id
				+ " and StageName=2";
		try {
			statement = c.prepareStatement(query);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Change request to rejected.
	 *
	 * @param Id the id
	 */
	public static void changeToRejected(int Id) {
		String query = "UPDATE Stages SET `ReportFailure` = 'Rejected' WHERE (`StageName` = '5') and (`RequestID` = '"
				+ Id + "')";
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Change request status.
	 *
	 * @param id the request id
	 * @param status the request status
	 */
	public static void changeRequestStatus(int id, int status) {
		String query = "UPDATE Requests SET Status = " + status + " WHERE ID = " + id;
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		updateLogChangeStatus(id, status);
	}

	/**
	 * Gets the requests.
	 *
	 * @param query the query
	 * @return the requests observable list
	 */
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

	/**
	 * Gets the requests with all data (for manager screen).
	 *
	 * @param query the query
	 * @return the requests observable list
	 */
	public static ObservableList<Request> getRequests1(String query) {
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
							rs.getString(6), Enums.RequestStageENUM.getRequestStageENUM(rs.getInt(7)),
							Enums.RequestStatus.getStatusByInt(rs.getInt(8)), new DateTime(rs.getString(9)),
							rs.getString(10), rs.getString(12), rs.getInt(13));

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

	/**
	 * Gets the requests for college.
	 *
	 * @param userName the user name
	 * @param id the request id
	 * @param search if search is active
	 * @param unActive the un active
	 * @return the requests for college
	 */
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

	/**
	 * Gets the requests for IS.
	 *
	 * @param UserName the user name
	 * @param id the request id
	 * @param search if search is active
	 * @param unActive the un-active boolean
	 * @return the requests for IS
	 */
	public static ObservableList<Request> getRequestsForIS(String UserName, int id, boolean search, boolean unActive) {
		String query = "select * from Requests where currenthandlers LIKE ',%" + UserName + "%,'";
		if (unActive == false) {
			query += " and (status=0 or status=2)";
		}
		if (search == true) {
			query += " and id=" + id;
		}
		return getRequests(query);
	}

	/**
	 * Gets the requests for manager.
	 *
	 * @param id the request id
	 * @param search if search is active
	 * @param unActive the un-active boolean
	 * @return the requests for manager
	 */
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

	/**
	 * Gets the request stages.
	 *
	 * @param RequestID the request ID
	 * @return the request stages
	 */
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

	/**
	 * Search user.
	 *
	 * @param user the user name
	 * @param pass the password
	 * @return the user
	 */
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
					if (rs.getInt(10) == 0) {
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

	/**
	 * Search user.
	 *
	 * @param user the username
	 * @return the user
	 */
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
				us = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						Enums.Role.getRoleENUM(rs.getInt(6)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return us;
	}

	/**
	 * Gets the committee.
	 *
	 * @return the array list of the committee members
	 */
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

	/**
	 * Search report.
	 *
	 * @param id the request id
	 * @return the report
	 */
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

	/**
	 * Gets the supervisor.
	 *
	 * @return the supervisor user name (string)
	 */
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

	/**
	 * Gets the supervisor user.
	 *
	 * @return the supervisor user
	 */
	public static User getSupervisorUser() {
		String query = "select * from Users where Role=4";
		ResultSet rs = null;
		User us = null;
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
					us = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
							Enums.Role.getRoleENUM(rs.getInt(6)));

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return us;
	}

	/**
	 * Gets the chairman.
	 *
	 * @return the chairman (username string)
	 */
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

	/**
	 * Count committee members.
	 *
	 * @return the number of committee members
	 */
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

	/**
	 * Creates the report for request.
	 *
	 * @param r the request
	 * @return 0 if failed, 1 if successful
	 */
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

	/**
	 * Creates the new request.
	 *
	 * @param r the r
	 * @return 0 if failed, 1 if successful
	 */
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

	/**
	 * Creates the stages in DB.
	 *
	 * @param id the request id
	 */
	public static void CreateStagesDB(int id) {
		String temp = "";
		try {
			PreparedStatement st = null;
			ResultSet rs = null;
			st = c.prepareStatement("select Requests.System from Requests where ID=" + id);
			rs = st.executeQuery();
			rs.next(); // saves the system number
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
					st.setString(4, GetRandomISUser(rs.getInt(1)));
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

	/**
	 * Change request stage.
	 *
	 * @param id the request id
	 * @param Up if up, false if down
	 * @return true, if successful
	 */
	public static boolean ChangeRequestStage(int id, boolean Up) {
		String query, newMembers = null;
		ResultSet rs;
		PreparedStatement statement = null;
		int currentStage = 0, newStage;

		// getting the current stage
		query = "Select Stage from Requests where id=" + id;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
			rs.next();
			currentStage = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// saving the next stage
		if (Up)
			newStage = currentStage + 1;
		else
			newStage = currentStage - 1;

		// getting new stage members
		query = "Select Member from Stages where RequestID=" + id + " and StageName=" + newStage;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
			rs.next();
			newMembers = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// setting current stage ActudalDate
		query = "update Stages set ActualDate='" + (new DateTime()).toString() + "' where RequestID=" + id
				+ " and StageName=" + (currentStage);
		try {
			statement = c.prepareStatement(query);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		// if stages 2 or 4, needs to have auto date
		if (newStage == 2 || newStage == 4) {
			DateTime dueDate = new DateTime();
			dueDate = dueDate.plusDays(7);
			query = "update Stages set PlannedDueDate='" + dueDate.toString() + "' where StageName=" + newStage
					+ " and RequestID=" + id;
			try {
				statement = c.prepareStatement(query);
				statement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// updating stage in requests
		query = "update Requests set Stage=" + newStage + " where id=" + id;
		try {
			statement = c.prepareStatement(query);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		// saving it in requests
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

	/**
	 * Appoint stage handlers.
	 *
	 * @param id the request id
	 * @param stage the stage
	 * @param handlers the stage handlers
	 */
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

	/**
	 * Gets the committee string.
	 *
	 * @return the string
	 */
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

	/**
	 * Gets the random IS user.
	 *
	 * @param system the system handled
	 * @return the username string
	 */
	public static String GetRandomISUser(int system) {
		ArrayList<String> users = new ArrayList<String>();
		String query = "select username from Users where (Role = 1 or Role = 2 or Role = 3) and Department LIKE '%"
				+ Enums.SystemENUM.getSystemByInt(system).toString() + "%'";
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
					String tmp = "," + rs.getString(1) + ",";
					users.add(tmp);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (users.size()==0) {//if nobody supports the system, get committee chairman
				users.add(getChairman());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		Random rand = new Random();
		return users.get(rand.nextInt(users.size()));
	}

	/**
	 * Checks if is request denied.
	 *
	 * @param requestID the request ID
	 * @return true, if successful
	 */
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

	/**
	 * Sets the date.
	 *
	 * @param requestID the request ID
	 * @param stage the request stage
	 * @param date the new date
	 */
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
		updateLogChangeStageDate(requestID, stage, date);
	}

	/**
	 * Sets the request deny.
	 *
	 * @param r the request
	 * @param stage the stage
	 * @return true, if successful
	 */
	public static boolean setRequestDeny(Request r, int stage) {
		String query = "update Requests set isDenied=" + r.getIsDenied() + " where ID=" + r.getId();
		PreparedStatement statement;
		try {
			statement = c.prepareStatement(query);
			statement.execute(query);
			if (r.getIsDenied() == 0) {
				calculateDateDiv(r, stage);
				query = "update Stages set isExtended=1,isApproved=1, PlannedDueDate=ExtendedDueDate where RequestID="
						+ r.getId() + " and StageName=" + stage;
				statement.execute(query);
				statement.execute("update Stages set ExtendedDueDate=null where RequestID=" + r.getId()
						+ " and StageName=" + stage);
				return false;
			} else {
				query = "update Stages set isApproved=0,isExtended=0, ExtendedDueDate=null where RequestID=" + r.getId()
						+ " and StageName=" + stage;
				statement.execute(query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		updateLogDenyExtension(r.getId(), stage);
		return true;
	}

	/**
	 * Calculate date difference.
	 *
	 * @param r the request
	 * @param stage the stage
	 */
	public static void calculateDateDiv(Request r, int stage) {
		PreparedStatement statement;
		ResultSet rs = null;
		String query;
		DateTime originalDueDate = null;
		DateTime newDueDate = null;
		try {
			query = "select PlannedDueDate,ExtendedDueDate from Stages where RequestID=" + r.getId() + " and StageName="
					+ stage;
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
			rs.next();
			originalDueDate = new DateTime(rs.getString(1));
			newDueDate = new DateTime(rs.getString(2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Duration dur = new Duration(originalDueDate, newDueDate);
		long diff = dur.getStandardDays();
		try {
			query = "update Stages set DaysOfExtension=" + diff + " where RequestID=" + r.getId() + " and StageName="
					+ stage;
			statement = c.prepareStatement(query);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Disconnect user.
	 *
	 * @param UserID the user ID
	 */
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

	/**
	 * Update log request details.
	 *
	 * @param request the request in log
	 */
	public static void updateLogRequestDetails(Request request) {
		String query = "INSERT INTO SupLog (`RequestId`, `Date`, `Field`, `WhatChanged`) VALUES (?,?,?,?)";
		String WhatChanged = request.getDescription() + "||" + request.getChanges() + "||" + request.getChangeReason()
				+ "||" + request.getComments();
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.setInt(1, request.getId());
			statement.setString(2, new DateTime().toString("dd/MM/yyyy HH:mm:ss"));
			statement.setString(3, "Request details");
			statement.setString(4, WhatChanged);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update log change status.
	 *
	 * @param id the request id
	 * @param status the request status
	 */
	public static void updateLogChangeStatus(int id, int status) {
		String query = "INSERT INTO SupLog (`RequestId`, `Date`, `Field`, `WhatChanged`) VALUES (?,?,?,?)";
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.setInt(1, id);
			statement.setString(2, new DateTime().toString("dd/MM/yyyy HH:mm:ss"));
			statement.setString(3, "Status");
			statement.setString(4, ((Integer) status).toString());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update log change stage handler.
	 *
	 * @param id the request id
	 * @param stage the request stage
	 * @param member the member (user id)
	 */
	public static void updateLogChangeStageHandler(int id, int stage, String member) {
		String query = "INSERT INTO SupLog (`RequestId`, `Date`, `Field`, `WhatChanged`) VALUES (?,?,?,?)";
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.setInt(1, id);
			statement.setString(2, new DateTime().toString("dd/MM/yyyy HH:mm:ss"));
			statement.setString(3, "Stage " + stage + " handler");
			statement.setString(4, member);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update log change stage date.
	 *
	 * @param id the request id
	 * @param stage the request stage
	 * @param date the stage date
	 */
	public static void updateLogChangeStageDate(int id, int stage, String date) {
		String query = "INSERT INTO SupLog (`RequestId`, `Date`, `Field`, `WhatChanged`) VALUES (?,?,?,?)";
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.setInt(1, id);
			statement.setString(2, new DateTime().toString());
			statement.setString(3, "Stage " + stage + " due date");
			statement.setString(4, date);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update log deny extension.
	 *
	 * @param id the request id
	 * @param stage the request stage
	 */
	public static void updateLogDenyExtension(int id, int stage) {
		String query = "INSERT INTO SupLog (`RequestId`, `Date`, `Field`, `WhatChanged`) VALUES (?,?,?,?)";
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.setInt(1, id);
			statement.setString(2, new DateTime().toString());
			statement.setString(3, "Stage extension not apporved");
			statement.setString(4, "");
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Change extended date.
	 *
	 * @param r the request
	 * @param date the extension date
	 */
	public static void changeExtendedDate(Request r, String date) {
		DateTime dt;
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		dt = dtf.parseDateTime(date);
		String query = "update Stages set ExtendedDueDate='" + dt.toString() + "' where RequestID ='" + r.getId()
				+ "' and StageName=" + Enums.RequestStageENUM.getRequestStageENUMByEnum(r.getCurrentStage());
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the closing date.
	 *
	 * @param id the request id
	 */
	public static void setClosingDate(int id) {
		String query = "update Stages set ActualDate='" + (new DateTime()).toString() + "' where RequestID=" + id
				+ " and StageName=5";
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Generate auto messages.
	 */
	public static void genAutoMessages() {
		String query = "select * from Requests";
		ObservableList<Request> list = getRequests(query);
		autoMessage24hDueDate(list);
		autoMessageExceptions(list);
	}

	/**
	 * Auto message for stages 24h from due date.
	 *
	 * @param list the requests list
	 */
	public static void autoMessage24hDueDate(ObservableList<Request> list) {
		for (Request r : list) {
			int currstageNum = Enums.RequestStageENUM.getRequestStageENUMByEnum(r.getCurrentStage());
			Stage currentStage = r.getStages()[currstageNum];
			if (currentStage.getPlannedDueDate() != null) {
				DateTime plannedDate = new DateTime(currentStage.getPlannedDueDate());
				DateTime check24 = new DateTime();
				check24 = check24.plusDays(1);
				if (plannedDate.isBefore(check24) && !plannedDate.isBefore(new DateTime())
						&& r.getStatus() == Enums.RequestStatus.Active) {
					User receiver = SearchUser(currentStage.getStageMembers().get(1));
					EmailMessage toSend = new EmailMessage(r, receiver);
					toSend.build24hStageMsg();
					sendMessage(toSend, true);
				}
			}
		}
	}

	/**
	 * Extension message.
	 *
	 * @param r the request
	 */
	public static void extensionMessage(Request r) {
		String managerMail = getManagerMail();
		User receiver = getSupervisorUser();
		EmailMessage toSend = new EmailMessage(r, receiver);
		toSend.buildExtensionMsg();
		toSend.addToCC(managerMail);
		sendMessage(toSend, false);
	}

	/**
	 * User message of closing.
	 *
	 * @param r the request
	 */
	public static void userMessageOfClosing(Request r) {
		String supervisorMail = getSupervisorMail();
		User receiver = SearchUser(r.getRequestorID());
		EmailMessage toSend = new EmailMessage(r, receiver);
		toSend.buildClosingMsg();
		toSend.addToCC(supervisorMail);
		sendMessage(toSend, false);
	}

	/**
	 * Auto message exceptions.
	 *
	 * @param list the requests list
	 */
	public static void autoMessageExceptions(ObservableList<Request> list) {
		String supervisorMail = getSupervisorMail();
		String managerMail = getManagerMail();
		for (Request r : list) {
			int currstageNum = Enums.RequestStageENUM.getRequestStageENUMByEnum(r.getCurrentStage());
			Stage currentStage = r.getStages()[currstageNum];
			if (currentStage.getPlannedDueDate() != null) {
				DateTime plannedDate = new DateTime(currentStage.getPlannedDueDate());
				if (plannedDate.isBefore(new DateTime()) && r.getStatus() == Enums.RequestStatus.Active) {
					User receiver = SearchUser(currentStage.getStageMembers().get(1));
					EmailMessage toSend = new EmailMessage(r, receiver);
					toSend.buildExceptionMsg();
					toSend.addToCC(supervisorMail);
					toSend.addToCC(managerMail);
					sendMessage(toSend, true);
				}
			}
		}
	}

	/**
	 * Gets the supervisor mail.
	 *
	 * @return the supervisor mail (string)
	 */
	public static String getSupervisorMail() {
		String query = "select Users.Mail from Users where Role=4";
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

	/**
	 * Gets the manager mail.
	 *
	 * @return the manager mail (string)
	 */
	public static String getManagerMail() {
		String query = "select Users.Mail from Users where Role=5";
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

	/**
	 * Send message.
	 *
	 * @param m the message
	 * @param checkSent check if sent
	 */
	public static void sendMessage(EmailMessage m, boolean checkSent) {
		if (checkSent) {
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
		}
		emailService.sendEmail(m);
	}

	/**
	 * Gets the max request ID.
	 *
	 * @return the max request ID
	 */
	public static int getMaxRequestID() {
		String query = "select max(ID) from Requests";
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Removes the user.
	 *
	 * @param id the request id
	 * @return true, if successful
	 */
	public static boolean removeUser(String id) {
		String query = "delete from Users where username='" + id + "'";
		PreparedStatement statement;
		try {
			statement = c.prepareStatement(query);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Gets the extension durations.
	 *
	 * @return the extension durations
	 */
	public static ArrayList<Double> getExtensionDurations() {
		ResultSet rs = null;
		PreparedStatement statement;
		String query;
		ArrayList<Double> list = new ArrayList<Double>();
		try {
			query = "SELECT DaysOfExtension FROM Stages";
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				try {
					Double tmp = (double) rs.getInt(1);
					if (tmp != 0)
						list.add(tmp);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Gets all users.
	 *
	 * @return all users
	 */
	public static ObservableList<User> getAllUsers() {
		ObservableList<User> o = FXCollections.observableArrayList();
		String query = "select * from Users";
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
					User u = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), Enums.Role.getRoleENUM(rs.getInt(6)), rs.getInt(7), rs.getString(8),
							rs.getInt(9));
					o.add(u);
				} catch (SQLException e) {
					e.printStackTrace();

				}

			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return o;

	}

	/**
	 * Gets all reports.
	 *
	 * @return all reports
	 */
	public static ObservableList<Report> getAllReports() {
		ObservableList<Report> o = FXCollections.observableArrayList();
		String query = "select * from Reports";
		ResultSet rs = null;
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				try {
					Report r = new Report(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(6), rs.getInt(7));

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

	/**
	 * Gets the all request stages.
	 *
	 * @return all request stages
	 */
	public static ObservableList<Common.Stage> getALLRequestStages() {
		ObservableList<Common.Stage> o = FXCollections.observableArrayList();
		String query = "select * from Stages";
		PreparedStatement statement;
		ResultSet rs = null;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			while (rs.next()) {
				Stage s = new Stage(Enums.RequestStageENUM.getRequestStageENUM(rs.getInt(1)), rs.getString(2),
						rs.getBoolean(3), rs.getBoolean(4), rs.getString(5), rs.getInt(6), rs.getString(7),
						rs.getString(8), rs.getString(9), rs.getInt(10));
				o.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * Gets all requests.
	 *
	 * @return all requests
	 */
	public static ObservableList<Request> getAllRequests() {
		String query;
		query = "select * from Requests";
		return getRequests(query);
	}

	/**
	 * Gets the delays durations.
	 *
	 * @param enm the system enum
	 * @return the delays durations
	 */
	public static ArrayList<Double> getDelaysDurations(SystemENUM enm) {
		ResultSet rs = null;
		PreparedStatement statement;
		String query;
		DateTime plenDueDate = null;
		DateTime realDueDate = null;
		Duration dur = null;
		ArrayList<Double> list = new ArrayList<Double>();
		if (enm == SystemENUM.All) {
			query = "SELECT Stages.PlannedDueDate,Stages.ActualDate FROM Stages where PlannedDueDate IS NOT NULL ";
		} else {
			int i = SystemENUM.getSystemByEnum(enm);
			query = "SELECT Stages.PlannedDueDate,Stages.ActualDate FROM Stages,Requests where Stages.PlannedDueDate IS NOT NULL "
					+ "and Stages.RequestID=Requests.ID and Requests.System=" + i;
		}
		try {

			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				try {
					plenDueDate = new DateTime(rs.getString(1));
					realDueDate = new DateTime(rs.getString(2));
					if (plenDueDate.isBefore(realDueDate)) {
						dur = new Duration(plenDueDate, realDueDate);
						long diff = dur.getStandardHours();
						list.add((double) diff);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Gets the all addons.
	 *
	 * @return all addons list
	 */
	public static ArrayList<Double> getAllAddons() {
		String query = "select * from Requests";
		ObservableList<Request> list = getRequests(query);
		ArrayList<Double> res = new ArrayList<Double>();
		for (Request r : list) {
			DateTime openingDate = new DateTime(r.getDate());
			DateTime closingDate = new DateTime(r.getStages()[5].getActualDate());
			Duration dur = new Duration(openingDate, closingDate);
			int requestduration = (int) dur.getStandardDays();
			Report rep = SearchReport(r.getId());
			if (rep != null) {
				int excpectedDuration = rep.getDurationAssesment();
				if (requestduration > excpectedDuration) {
					res.add((double) (requestduration - excpectedDuration));
				}
			}
		}
		return res;
	}

	/**
	 * Gets all messages.
	 *
	 * @return all messages
	 */
	public static ObservableList<Message> getAllMessages() {
		ObservableList<Message> o = FXCollections.observableArrayList();
		String query = "select * from Messages";
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
					Message m = new Message(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
					o.add(m);
				} catch (SQLException e) {
					e.printStackTrace();

				}

			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return o;

	}

	/**
	 * Gets the supervisor log.
	 *
	 * @return the supervisor log
	 */
	public static ObservableList<SupervisorLog> getSupervisorLog() {
		ObservableList<SupervisorLog> o = FXCollections.observableArrayList();
		String query = "select * from SupLog";
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
					SupervisorLog s = new SupervisorLog(rs.getInt(1), rs.getString(2), rs.getString(3),
							rs.getString(4));
					o.add(s);
				} catch (SQLException e) {
					e.printStackTrace();

				}

			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return o;

	}

	/**
	 * Gets the activity data.
	 *
	 * @param arr the date strings array
	 * @return the activity data
	 */
	public static ArrayList<Integer> getActivityData(String[] arr) {
		int active = 0, closed = 0, frozen = 0, rejected = 0;
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dt1 = null, dt2 = null;
		int durations = 0;
		try {
			dt1 = dtf.parseDateTime(arr[0]);
			dt2 = dtf.parseDateTime(arr[1]);
		} catch (IllegalArgumentException e) {
		}
		String query = "select * from Requests";
		ObservableList<Request> olist = getRequests(query);
		for (int i = 0; i < olist.size(); i++) {
			Request r = olist.get(i);
			if (r.getDate().isAfter(dt1) && r.getDate().isBefore(dt2)) {
				if (r.getStatus().equals(Enums.RequestStatus.Active))
					active++;
				if (r.getStatus().equals(Enums.RequestStatus.Closed))
					closed++;
				if (r.getStatus().equals(Enums.RequestStatus.Frozen))
					frozen++;
				if (r.getStatus().equals(Enums.RequestStatus.Rejected)
						|| r.getStatus().equals(Enums.RequestStatus.RejectedClosed))
					rejected++;
				DateTime openingDate = new DateTime(r.getDate());
				DateTime closingDate = new DateTime(r.getStages()[5].getActualDate());
				Duration dur = new Duration(openingDate, closingDate);
				durations += (int) dur.getStandardDays();
			}

		}
		ArrayList<Integer> l = new ArrayList<>();
		l.add(active);
		l.add(closed);
		l.add(frozen);
		l.add(rejected);
		l.add(durations);
		return l;
	}

}
