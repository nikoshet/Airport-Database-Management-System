import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class InfoMenu extends JFrame{
	private static final long serialVersionUID = 1L;
	public static Button ButtonFindMeasures;
	public static TextArea info= new TextArea();
	public static InfoMenu im;
	public InfoMenu(){
		super("Info Menu");
		setLayout(null);
		this.setFont(new Font("TimesRoman",Font.PLAIN,14));
		this.setResizable(false);
		this.setVisible(true);
		this.toFront();
		this.setBounds(350, 230, 420, 210);
		this.addWindowListener(new CloseWindowAndExit());
		this.getContentPane().setBackground( Color.gray );
		initializeTextArea();
	}
	
	public void initializeTextArea(){
		info.setEditable(false);
		info.setText("");
		info.setText(info.getText()+"Type '1' to see all the airplanes.\n");
		info.setText(info.getText()+"Type '2' to see all the available airplanes in the Parking Area.\n");
		info.setText(info.getText()+"Type '3' to see all the departures.\n");
		info.setText(info.getText()+"Type '4' to see all the arrivals.\n");
		info.setText(info.getText()+"Type '5' to see all the DELAYS on departures.\n");
		info.setText(info.getText()+"Type '6' to see all the DELAYS on arrivals.\n");
		info.setText(info.getText()+"Type 'departure' to insert a new departure.\n");
		info.setText(info.getText()+"Type 'arrival' to insert a new arrival.\n");
		info.setText(info.getText()+"Type 'exit' to exit from the program.\n");
		info.setBounds(0, 0, 410, 220);
		info.setVisible(true);
		info.setFont(new Font("TimesRoman",Font.PLAIN,14));
		this.add(info);
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
