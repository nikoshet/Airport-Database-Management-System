import java.sql.*;

public class ConnectToSQLServer {
	private static String url;
	private static String username;
	private static String passwd;
	private static String jdbcDriver;
	public static String sql;
	public static Statement stmt;
	public static ResultSet rs;
	private static String DBname;
	public ConnectToSQLServer() throws InterruptedException{
		while(true)
		{
			while(true)
			{
			Thread.sleep(50);
			if (AppGui.ButtonConnect.isEnabled()==false)
				{
				AppGui.ButtonConnect.setEnabled(true);
				break;}
			else{}
			}
			try {
				AppGui.text.setText(AppGui.text.getText()+"Connecting to Database ..."+"\n");	 
	        	DBname="airport";
				url="jdbc:mysql://localhost/";
				username= AppGui.tfusername.getText();
				passwd = AppGui.tfPassword.getText();
				jdbcDriver = "com.mysql.jdbc.Driver";
				//Register JDBC driver
				Class.forName(jdbcDriver);
				//Connect to Database
				Connection con = DriverManager.getConnection(url + DBname +"?useUnicode=yes&characterEncoding=utf-8",username,passwd);
				AppGui.text.setText(AppGui.text.getText()+"Connected!"+"\n");
				stmt=con.createStatement();  
				break;
			} catch (Exception  e) {
				AppGui.text.setText("An error occurred,try again!");
				//e.printStackTrace();	
				AppGui.tfusername.setText("");
				AppGui.tfPassword.setText("");
			} 
		}
	}
}
