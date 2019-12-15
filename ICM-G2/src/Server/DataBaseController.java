package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataBaseController {
	private static Connection c;
	private static String url = "jdbc:mysql://remotemysql.com:3306/jsyC4yp1qF?useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String username = "jsyC4yp1qF";
	private static String password = "50MjwBICSL";

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
