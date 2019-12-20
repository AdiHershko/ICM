package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import Common.Request;
import Common.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Common.Enums;

public class DataBaseController {
	private static Connection c;
	private static String url = "jdbc:mysql://remotemysql.com:3306/rPTfgnHCnB?useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String username = "rPTfgnHCnB";
	private static String password = "atcFy4mIAf";

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

	public static ObservableList<Request> getTable(String UserName) {
		ObservableList<Request> o = FXCollections.observableArrayList();
		String query = "select * from Requests where currenthandlers LIKE '%,"+UserName+",%' and status=0";
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
					Request r = new Request(rs.getInt(1), rs.getInt(2), Enums.SystemENUM.getSystemByInt(rs.getInt(3)), rs.getString(4), rs.getString(5),
							rs.getString(6), rs.getDate(9).toString());
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

	public static void setUrl(String url) {
		DataBaseController.url = url;
	}

	public static void setUsername(String username) {
		DataBaseController.username = username;
	}

	public static void setPassword(String password) {
		DataBaseController.password = password;
	}

}
