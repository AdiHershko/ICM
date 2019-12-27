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
import java.util.Random;

import org.joda.time.DateTime;

import Common.Request;
import Common.Stage;
import Common.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import Common.Enums;
import Common.Report;

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
			ServerChooseController.loading=false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static ObservableList<Request> getRequestsForIS(String UserName) {
		String query = "select * from Requests where (status=0 or status=2) and currenthandlers LIKE '%," + UserName + ",%'";
		return getRequests(query);
	}

	public static ObservableList<Request> getRequestsForManager() {
		String query = "select * from Requests where (status=0 or status=2)";
		return getRequests(query);
	}
	public static void updateRequestDetails(String msg) {
		PreparedStatement st = null;
		String[] tem = msg.split("-");
			String query= "UPDATE Requests SET Description = '"+tem[1]+"', `Change` = '"+tem[2]+"', ChangeReason = '"+tem[3]+"', Connect = '"+tem[4]+"' WHERE ID = "+tem[0];
			PreparedStatement statement = null;
			try {
				statement = c.prepareStatement(query);
				statement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	public static void ChangeRequestStatus(int id) {
		String query = "UPDATE Requests SET Status = 1 WHERE ID = " + id + "";
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void Freeze(int Id) {
		String query = "UPDATE Requests SET Status = 2 WHERE ID = " + Id;
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void Unfreeze(int Id) {
		String query = "UPDATE Requests SET Status = 0 WHERE ID = " + Id;
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
							rs.getString(6), new DateTime(rs.getString(9)));
					r.setStages(getRequestStages(rs.getInt(1)));
					r.setCurrentStage(Enums.RequestStageENUM.getRequestStageENUM(rs.getInt(7)));
					r.setComments(rs.getString(10));
					r.setStatus(Enums.RequestStatus.getStatusByInt(rs.getInt(8)));
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
	
	public static ArrayList<String> GetCommitte(){
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
		String query = "select * from Reports where requestID="+String.valueOf(id);
		ResultSet rs = null;
		Report report =  null;
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
					report = new Report(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getString(6),rs.getInt(7));
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
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR!");
		alert.setContentText("No supervisor for system!");
		alert.show();
		return null;
	}

	public static int CreateReportForRequest(Report r) {
		Report check = SearchReport(r.getRequestId());
		String query;
		if (check != null) {
			query = "UPDATE Reports SET requestID= ?, description = ?, result = ?, location = ?, ";
			query+="constraints= ?, risks = ?, duration = ?  WHERE requestID = "+String.valueOf(r.getRequestId());
		}
		else {
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
					st.setString(4, ","+getSupervisor()+",");
				else if (i == 1)//TODO: maybe add department?
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

	public static void ChangeRequestStage(int id, boolean Up) {
		String query, newMembers = null;
		ResultSet rs;
		int currentStage = 0;
		if (Up)
			query = "update Requests set Stage=Stage+1 where id="+id;
		else
			query = "update Requests set Stage=Stage-1 where id="+id;
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(query);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
		query = "Select Stage from Requests where id="+id;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
			rs.next();
			currentStage = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		query = "update Stages set ActualDate='"+(new DateTime()).toString()+"' where RequestID="+id+" and StageName="+(currentStage-1);
		try {
			statement = c.prepareStatement(query);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
		query = "Select Member from Stages where RequestID="+id+" and StageName="+currentStage;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
			rs.next();
			newMembers = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		query = "update Requests set CurrentHandlers='"+newMembers+"' where id="+id;
		try {
			statement = c.prepareStatement(query);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	public static void AppointStageHandlers(int id, int stage, String handlers) {
		String query = "update Stages set Member=',"+handlers+",' where RequestID="+id+" and StageName="+stage;
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
					String tmp = ","+rs.getString(1)+",";
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
}
