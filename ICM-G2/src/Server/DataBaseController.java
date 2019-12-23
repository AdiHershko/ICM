package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import Common.Request;
import Common.Stage;
import Common.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import Common.Enums;

public class DataBaseController {
	private static Connection c;
	private static String url = "jdbc:mysql://remotemysql.com:3306/rPTfgnHCnB?useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String username = "rPTfgnHCnB";
	private static String password = "atcFy4mIAf";

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
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static ObservableList<Request> getRequestsForIS(String UserName) {
		String query = "select * from Requests where status=0 and currenthandlers LIKE '%," + UserName + ",%'";
		return getRequests(query);
	}

	public static ObservableList<Request> getRequestsForCollege(String userName) {
		String query = "select * from Requests where Requestor='" + userName + "'";
		return getRequests(query);
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
							rs.getString(6), rs.getDate(9).toString());
					r.setStages(getRequestStages(rs.getInt(1)));
					r.setCurrentStage(Enums.RequestStageENUM.getRequestStageENUM(rs.getInt(7)));
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

	public static Stage[] getRequestStages(int RequestID) {
		Stage RequestStages[] = new Stage[5];
		String query = "select * from Stages where RequestID=" + RequestID;
		PreparedStatement statement;
		ResultSet rs = null;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 5; i++) {
			RequestStages[i] = new Stage();
		}
		try {
			while (rs.next()) {
				Stage s = RequestStages[1];
				s.setStageName(Enums.RequestStageENUM.getRequestStageENUM(rs.getInt(1)));
				s.setPlannedDueDate(rs.getDate(2));
				s.setIsApproved(rs.getInt(3) == 1);
				s.setIsExtended(rs.getInt(4) == 1);
				ArrayList<String> stageMembers = new ArrayList<String>();
				String handlers_array[] = (rs.getString(5)).split(",");
				for (int i = 0; i < handlers_array.length; i++)
					stageMembers.add(handlers_array[i]);
				s.setStageMembers(stageMembers);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RequestStages;
	}

	public static User SearchUser(String user, String pass) {
		String query = "select * from Users where binary username ='" + user + "' and binary Password ='" + pass + "'";
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
	
	public static String getSupervisor() {
		String query = "select Users.username from Users where Role=4";
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
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static int CreateNewRequest(Request r) {
		String temp=getSupervisor();
		if(temp==null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setContentText("no supervisor for system!!!");
			alert.show();
			return 0;
		}
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
			st.setDate(8, java.sql.Date.valueOf(r.getDate()));
			st.setString(9, r.getComments());
			st.setString(10, ","+temp+",");
			st.executeUpdate();
			rs = st.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO create stages for request
		return id;
	}

}
