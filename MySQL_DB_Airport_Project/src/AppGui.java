import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JFrame;

public class AppGui extends JFrame{
	private static final long serialVersionUID = 1L;
	public static Button ButtonSearch,ButtonConnect,ButtonInfo;
	public static TextArea text = new TextArea();
	public static TextField tfsend = new TextField();
	public static TextField tfusername = new TextField();
	public static TextField tfPassword = new TextField();
	public static ConnectToSQLServer con;
	public static InfoMenu im;
	public static InsertDeparture id;
	public static InsertArrival ia;
	public static ArrayList<String> idList = new ArrayList<String>();
	public AppGui(){
		super("Airport DataBase App");
		setLayout(null);
		this.setFont(new Font("TimesRoman",Font.PLAIN,12));
		this.setResizable(false);
		this.setVisible(true);
		this.toFront();
		this.setBounds(20, 20, 450, 450);
		this.getContentPane().setBackground( Color.gray );
		this.addWindowListener(new CloseWindowAndExit());

		initializeTexts();
		initializeButtons();
		initializeLabels();
	}
	
	public void initializeLabels(){
		Label lbl1 = new Label("Username:");
		lbl1.setBounds(300,20,100,20);
		lbl1.setFont(new Font("TimesRoman",Font.PLAIN,12));
		add(lbl1);
		Label lbl2 = new Label("Password:");
		lbl2.setBounds(300,80,100,20);
		lbl2.setFont(new Font("TimesRoman",Font.PLAIN,12));
		add(lbl2);
	}
	
	public void initializeTexts(){
		text.setEditable(false);
		text.setText("");
		text.setFont(new Font("TimesRoman",Font.PLAIN,12));
		text.setBounds(5, 180, 435, 240);
		this.add(text);
		tfsend.setEditable(true);
		tfsend.setText("");
		tfsend.setBounds(30, 110, 100, 30);
		tfsend.setVisible(true);
		this.add(tfsend);
		tfusername.setEditable(true);
		tfusername.setText("");
		tfusername.setBounds(300, 40, 100, 25);
		tfusername.setVisible(true);
		this.add(tfusername);
		tfPassword.setEditable(true);
		tfPassword.setText("");
		tfPassword.setBounds(300, 100, 100, 25);
		tfPassword.setVisible(true);
		this.add(tfPassword);
	}
	
	public void initializeButtons(){
		ButtonSearch = new Button("Enter");
		ButtonSearch.setEnabled(false);
		ButtonSearch.setBounds(30, 40, 100, 30);
		this.add(ButtonSearch);
		ButtonSearch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				((Button)e.getSource()).setEnabled(false);
				try {
					if (tfsend.getText().equals("1")){
						ConnectToSQLServer.sql = "SELECT * FROM AIRPLANE;";
						text.setText("Airplanes:\n");
						executeSQLCommand(ConnectToSQLServer.sql);
					}
					else if (tfsend.getText().equals("2")){
						ConnectToSQLServer.sql = "SELECT AIRPLANE.ID_airplane,AIRPLANE.no_of_seats,"
								+ "AIRPLANE.airline,IS_PLACED_IN.ID_of_airplane_area FROM AIRPLANE JOIN "
								+ " IS_PLACED_IN ON AIRPLANE.ID_airplane=IS_PLACED_IN.ID_of_placed_airplane;";
						text.setText("Available airplanes :\n");
						executeSQLCommand(ConnectToSQLServer.sql);
					}
					else if (tfsend.getText().equals("3")){
						ConnectToSQLServer.sql = "SELECT * FROM DEPARTURE ;";
						text.setText("Departures :\n");
						executeSQLCommand(ConnectToSQLServer.sql);
					}
					else if (tfsend.getText().equals("4")){
						ConnectToSQLServer.sql = "SELECT * FROM ARRIVAL ;";
						text.setText("Arrivals :\n");
						executeSQLCommand(ConnectToSQLServer.sql);
					}
					else if (tfsend.getText().equals("5")){
						ConnectToSQLServer.sql = "SELECT DEPARTURE.ID_of_departure_flight,ROUTE.ID_airplane,"
						       +"DEPARTURE.scheduled_departure_time,DEPARTURE.actual_departure_time,"
						       +"DEPARTURE.ID_gate,DEPARTURE.ID_corridor,DEPARTURE.ICAO_code,DEPARTURE.IATA_code "
						       +"FROM DEPARTURE JOIN ROUTE ON "
						       +"DEPARTURE.ID_of_departure_flight = ROUTE.ID_flight "
						       +"WHERE DEPARTURE.actual_departure_time IS NULL "
						       +"AND DEPARTURE.scheduled_departure_time<CURRENT_TIMESTAMP;";
						text.setText("DELAYS on departures :\n");
						executeSQLCommand(ConnectToSQLServer.sql);
					}
					else if (tfsend.getText().equals("6")){
						ConnectToSQLServer.sql = "SELECT ARRIVAL.ID_of_arriving_flight, ROUTE.ID_airplane, "
						       + "ARRIVAL.scheduled_arrival_time,ARRIVAL.actual_arrival_time,"
						       + "ARRIVAL.ID_gate,ARRIVAL.ID_corridor,ARRIVAL.ICAO_code,ARRIVAL.IATA_code "
						       + "FROM ARRIVAL JOIN ROUTE ON "
						       + "ARRIVAL.ID_of_arriving_flight = ROUTE.ID_flight "
						       + "WHERE ARRIVAL.actual_arrival_time IS NULL "
						       + "AND ARRIVAL.scheduled_arrival_time<CURRENT_TIMESTAMP;";
						text.setText("DELAYS on arrivals :\n");
						executeSQLCommand(ConnectToSQLServer.sql);
					}
					else if (tfsend.getText().equals("departure")){
						id = new InsertDeparture();
					}
					else if (tfsend.getText().equals("arrival")){
						ia = new InsertArrival();
					}
					else if (tfsend.getText().equals("exit")){
						System.exit(0);
					}
					else{
						text.setText("Error on typing.See Info Menu.\n");
					}
				} catch (SQLException e1) {e1.printStackTrace();
				}
				((Button)e.getSource()).setEnabled(true);
			}
		});
		ButtonConnect = new Button("Connect");
		ButtonConnect.setEnabled(true);
		ButtonConnect.setBounds(300, 140, 100, 30);
		this.add(ButtonConnect);
		ButtonConnect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				((Button)e.getSource()).setEnabled(false);
				ButtonSearch.setEnabled(true);
				ButtonInfo.setEnabled(true);
			}			
		});
		ButtonInfo = new Button("Info Menu");
		ButtonInfo.setEnabled(false);
		ButtonInfo.setBounds(150,40, 100, 30);
		this.add(ButtonInfo);
		ButtonInfo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				((Button)e.getSource()).setEnabled(false);
				im=new InfoMenu();
				AppGui.ButtonInfo.setEnabled(true);
			}
		});
	}
	
	public static void executeSQLCommand(String c) throws SQLException{
		ConnectToSQLServer.rs=ConnectToSQLServer.stmt.executeQuery(ConnectToSQLServer.sql);  
		ResultSetMetaData rsmd = ConnectToSQLServer.rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		int i = 1;
		String d ;
		while(ConnectToSQLServer.rs.next())  
		{	
			d="";
			while (i<=columnsNumber)
			{	
				if(i==1)
					idList.add(ConnectToSQLServer.rs.getString(i));
				d =d+ConnectToSQLServer.rs.getString(i)+"\t" ;
				i+=1;
			}
			text.setText(AppGui.text.getText()+d+"\n");
			i=1;
		}		
	}
	
	class CloseWindowAndExit extends WindowAdapter{
		public void windowClosing (WindowEvent closeWindowAndExit){
		System.exit(0);
		}
	}
}
