import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class InsertArrival  extends JFrame{
	private static final long serialVersionUID = 1L;
	public static Button ButtonInsert;
	public static TextField tf1= new TextField();
	public static TextField tf2= new TextField();
	public static TextField tf3= new TextField();
	public static TextField tf4= new TextField();
	public static TextField tf5= new TextField();
	public static TextField tf6= new TextField();
	public static TextField tf7= new TextField();
	public static TextField tf8= new TextField();
	public static TextField tf9= new TextField();
	public static TextField tf10= new TextField();
	public InsertArrival(){
		super("New Arrival");
		setLayout(null);
		this.setFont(new Font("TimesRoman",Font.PLAIN,12));
		this.setResizable(false);
		this.setVisible(true);
		this.toFront();
		this.setBounds(400, 250, 580, 420);
		this.addWindowListener(new CloseWindowAndExit());
		this.getContentPane().setBackground( Color.gray );
		
		initializeLabels();
		initializeTexts();
		initializeButtons();
	}
	
	public void initializeLabels(){
		Label lbl1 = new Label("Type the new flight ID:");
		lbl1.setBounds(10,10,250,20);
		lbl1.setFont(new Font("TimesRoman",Font.PLAIN,12));
		add(lbl1);
		Label lbl2 = new Label("Type the airplane ID that will make the arrival:");
		lbl2.setBounds(10,60,250,20);
		lbl2.setFont(new Font("TimesRoman",Font.PLAIN,12));
		add(lbl2);
		Label lbl3 = new Label("Type number of passengers:");
		lbl3.setBounds(10,110,250,20);
		lbl3.setFont(new Font("TimesRoman",Font.PLAIN,12));
		add(lbl3);
		Label lbl4 = new Label("Type total time of last flight:");
		lbl4.setBounds(10,160,250,20);
		lbl4.setFont(new Font("TimesRoman",Font.PLAIN,12));
		add(lbl4);
		Label lbl5 = new Label("Type scheduled arrival time:");
		lbl5.setBounds(10,210,250,20);
		lbl5.setFont(new Font("TimesRoman",Font.PLAIN,12));
		add(lbl5);
		Label lbl6 = new Label("Type the corridor ID of arrival:");
		lbl6.setBounds(310,10,250,20);
		lbl6.setFont(new Font("TimesRoman",Font.PLAIN,12));
		add(lbl6);
		Label lbl7 = new Label("Type the gate ID of arrival:");
		lbl7.setBounds(310,60,250,20);
		lbl7.setFont(new Font("TimesRoman",Font.PLAIN,12));
		add(lbl7);
		Label lbl8 = new Label("Type the allowed time for traversal from airplane:");
		lbl8.setBounds(310,110,260,20);
		lbl8.setFont(new Font("TimesRoman",Font.PLAIN,12));
		add(lbl8);
		Label lbl9 = new Label("Type the ICAO code of previous airport:");
		lbl9.setBounds(310,160,250,20);
		lbl9.setFont(new Font("TimesRoman",Font.PLAIN,12));
		add(lbl9);
		Label lbl10 = new Label("Type the IATA code of previous airport:");
		lbl10.setBounds(310,210,250,20);
		lbl10.setFont(new Font("TimesRoman",Font.PLAIN,12));
		add(lbl10);
	}
	
	public void initializeTexts(){
		tf1.setEditable(true);
		tf1.setBounds(10, 30, 120, 25);
		tf1.setVisible(true);
		this.add(tf1);
		tf2.setEditable(true);
		tf2.setBounds(10, 80, 120, 25);
		tf2.setVisible(true);
		this.add(tf2);
		tf3.setEditable(true);
		tf3.setBounds(10, 130, 120, 25);
		tf3.setVisible(true);
		this.add(tf3);
		tf4.setEditable(true);
		tf4.setBounds(10, 180, 120, 25);
		tf4.setVisible(true);
		this.add(tf4);
		tf5.setEditable(true);
		tf5.setBounds(10, 230, 120, 25);
		tf5.setVisible(true);
		this.add(tf5);
		tf6.setEditable(true);
		tf6.setBounds(310, 30, 120, 25);
		tf6.setVisible(true);
		this.add(tf6);
		tf7.setEditable(true);
		tf7.setBounds(310, 80, 120, 25);
		tf7.setVisible(true);
		this.add(tf7);
		tf8.setEditable(true);
		tf8.setBounds(310, 130, 120, 25);
		tf8.setVisible(true);
		this.add(tf8);
		tf9.setEditable(true);
		tf9.setBounds(310, 180, 120, 25);
		tf9.setVisible(true);
		this.add(tf9);	
		tf10.setEditable(true);
		tf10.setBounds(310, 230, 120, 25);
		tf10.setVisible(true);
		this.add(tf10);
	}
	
	public void initializeButtons(){
		ButtonInsert = new Button("Insert Arrival");
		ButtonInsert.setEnabled(true);
		ButtonInsert.setBounds(200, 320, 120, 30);
		this.add(ButtonInsert);
		ButtonInsert.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				((Button)e.getSource()).setEnabled(false);
				try { 
					//
					String d1 = "";
					String d2 = "";
					String d3 = "";
					String d4 = "";
					String d5 = "";
					String d6 = "";
					ConnectToSQLServer.sql="SELECT * FROM ROUTE WHERE ID_flight='"+tf1.getText()+"';";
					ConnectToSQLServer.rs=ConnectToSQLServer.stmt.executeQuery(ConnectToSQLServer.sql); 
					while(ConnectToSQLServer.rs.next())  
						{d1 =ConnectToSQLServer.rs.getString(1) ;}
					ConnectToSQLServer.sql="SELECT * FROM AIRPLANE WHERE ID_airplane='"+tf2.getText()+"';";
					ConnectToSQLServer.rs=ConnectToSQLServer.stmt.executeQuery(ConnectToSQLServer.sql); 
					while(ConnectToSQLServer.rs.next())  
						{d2 =ConnectToSQLServer.rs.getString(1) ;}
					ConnectToSQLServer.sql="SELECT no_of_seats FROM AIRPLANE WHERE AIRPLANE.ID_airplane ='"+tf2.getText()+"';";
					ConnectToSQLServer.rs=ConnectToSQLServer.stmt.executeQuery(ConnectToSQLServer.sql); 
					while(ConnectToSQLServer.rs.next())  
						{d3 =ConnectToSQLServer.rs.getString(1) ;}
					ConnectToSQLServer.sql="SELECT * FROM CORRIDOR WHERE ID_corridor='"+tf6.getText()+"';";
					ConnectToSQLServer.rs=ConnectToSQLServer.stmt.executeQuery(ConnectToSQLServer.sql); 
					while(ConnectToSQLServer.rs.next())  
						{d4 =ConnectToSQLServer.rs.getString(1) ;}
					ConnectToSQLServer.sql="SELECT * FROM GATE WHERE ID_gate='"+tf7.getText()+"';";
					ConnectToSQLServer.rs=ConnectToSQLServer.stmt.executeQuery(ConnectToSQLServer.sql); 
					while(ConnectToSQLServer.rs.next())  
						{d5 =ConnectToSQLServer.rs.getString(1) ;}
					ConnectToSQLServer.sql="SELECT * FROM AIRPORT WHERE ICAO_code='"+tf9.getText()+"' AND IATA_code='"+tf10.getText()+"';";
					ConnectToSQLServer.rs=ConnectToSQLServer.stmt.executeQuery(ConnectToSQLServer.sql); 
					while(ConnectToSQLServer.rs.next())  
						{d6 =ConnectToSQLServer.rs.getString(1) ;}
					if (!d1.equals("")){
						AppGui.text.setText("The ID of flight submitted is already used.");
					    }
					else if (d2.equals("")){
						AppGui.text.setText("The ID of airplane submitted does not exist.");
						}
					else if (Integer.parseInt(d3)<Integer.parseInt(tf3.getText())){
						AppGui.text.setText("The no of passengers submitted exceed the no of max available seats.");
						}
					else if (d4.equals("")){
						AppGui.text.setText("The ID of corridor submitted does not exist.");
						}
					else if (d5.equals("")){
						AppGui.text.setText("The ID of gate submitted does not exist.");
						}
					else if (d6.equals("")){
						AppGui.text.setText("The ICAO/IATA codes submitted do not exist.");
						}
					//
					else{
					ConnectToSQLServer.stmt.executeUpdate("INSERT INTO ROUTE " + 
		                "(`ID_flight`,`ID_airplane`,`no_of_passengers`) "+
		                 "VALUES ('"+tf1.getText()+"','"+tf2.getText()+"',"+tf3.getText()+");"); 
					
					
					ConnectToSQLServer.stmt.executeUpdate("INSERT INTO ARRIVAL " + 
 								"(`ID_of_arriving_flight`,`ID_gate`,`scheduled_arrival_time`,`actual_arrival_time`,`allowed_traversal_time`,"+
 							    "`scheduled_total_time_of_last_flight`,`ICAO_code`, `IATA_code`, `ID_corridor`)  "+
				                "VALUES ('"+tf1.getText()+"','"+tf7.getText()+"','"+tf5.getText()+"',NULL,'"+tf8.getText()+"','"+tf4.getText()+"','"+tf9.getText()+"','"+tf10.getText()+"','"+tf6.getText() +"');"); 				
					AppGui.text.setText("Arrival inserted successfully.");
					}
		        } catch (Exception  er) {
					AppGui.text.setText("An error occurred!");
					er.printStackTrace(); 
		        } 
				ButtonInsert.setEnabled(true);
			}
		});
	}
	
	class CloseWindowAndExit extends WindowAdapter{
		public void windowClosing (WindowEvent closeWindowAndExit){
			closeJframe();
		}
	}
	public void closeJframe(){
        super.dispose();
	}
}
