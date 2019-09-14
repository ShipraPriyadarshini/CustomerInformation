import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class LoginFrame extends JDialog {

	private JTextField userName;
	private JPasswordField password;
	private JLabel userName1;
	private JLabel password1;
	private JButton login;
	private JButton cancel;
	private boolean success;

	public LoginFrame(Frame frame) {
		super(frame, "Login", true);
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		userName1 = new JLabel("Username: ");
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		panel.add(userName1, gridBagConstraints);

		userName = new JTextField(20);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		panel.add(userName, gridBagConstraints);

		password1 = new JLabel("Password: ");
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 1;
		panel.add(password1, gridBagConstraints);

		password = new JPasswordField(20);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		panel.add(password, gridBagConstraints);
		panel.setBorder(new LineBorder(Color.BLUE));

		login = new JButton("Login");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (LoginCredentials.authenticate(getUsername(), getPassword())) {
					JOptionPane.showMessageDialog(LoginFrame.this,
							"Hi! You have successfully logged into the database.\n"
							+ "Click OK to display the database.",
							"Login",
							JOptionPane.INFORMATION_MESSAGE);
					success = true;
					dispose();
				} else {
					JOptionPane.showMessageDialog(LoginFrame.this,
							"Invalid username or password",
							"Login",
							JOptionPane.ERROR_MESSAGE);
					userName.setText("");
					password.setText("");
					success = false;
				}
			}
		});
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		JPanel jPanel = new JPanel();
		jPanel.add(login);
		jPanel.add(cancel);
		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(jPanel, BorderLayout.PAGE_END);
		pack();
		setResizable(false);
		setLocationRelativeTo(frame);
	}

	public String getUsername() {
		return userName.getText().trim();
	}

	public String getPassword() {
		return new String(password.getPassword());
	}

	public boolean isSucceeded() {
		return success;
	}
}