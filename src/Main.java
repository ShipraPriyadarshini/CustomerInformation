import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
    	final JFrame frame = new JFrame("Customer Database");
        final JButton button = new JButton("Click to login");
 
        button.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        LoginFrame loginDialog = new LoginFrame(frame);
                        loginDialog.setVisible(true);
                        if(loginDialog.isSucceeded()){
                        	new CustomerUIFrame();
                        }
                    }
                });
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 200);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(button);
        frame.setVisible(true);
    }
} 