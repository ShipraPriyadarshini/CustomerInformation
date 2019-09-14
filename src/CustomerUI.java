import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class CustomerUI extends JDialog {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField addressField;
    private JTextField phoneNumberField;
    private JTextField emailField;
    private JButton confirmButton;
    private JButton cancelButton;

    private CustomerDetails customer = new CustomerDetails();

    public CustomerUI(java.awt.Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        initComponents();
    }
    
    public CustomerUI(java.awt.Frame parent, String title,
            boolean modal, CustomerDetails customerDetails) {
        this(parent, title, modal);        
        this.customer = customerDetails;
        confirmButton.setText("Save");
        firstNameField.setText(customerDetails.getFirstName());
        lastNameField.setText(customerDetails.getLastName());
        addressField.setText(customerDetails.getAddress());
        phoneNumberField.setText(customerDetails.getPhoneNumber());
        emailField.setText(customerDetails.getEmail());
    }

    private void initComponents() {
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        addressField = new JTextField();
        phoneNumberField = new JTextField();
        emailField = new JTextField();
        cancelButton = new JButton();
        confirmButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);     
        
        Dimension dim = new Dimension(150, 30);
        firstNameField.setPreferredSize(dim);
        firstNameField.setMinimumSize(dim);        
        lastNameField.setPreferredSize(dim);
        lastNameField.setMinimumSize(dim);
        addressField.setPreferredSize(dim);
        addressField.setMinimumSize(dim);
        phoneNumberField.setPreferredSize(dim);
        phoneNumberField.setMinimumSize(dim);
        emailField.setPreferredSize(dim);
        emailField.setMinimumSize(dim); 
        
        cancelButton.setText("Cancel");
        cancelButton.addActionListener((ActionEvent) -> {
            cancelButtonActionPerformed();
        });

        confirmButton.setText("Add");
        confirmButton.addActionListener((ActionEvent) -> {
            confirmButtonActionPerformed();
        });

        // JLabel and JTextField panel
        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new GridBagLayout());
        customerPanel.add(new JLabel("First Name:"), 
                getConstraints(0, 0, GridBagConstraints.LINE_END));
        customerPanel.add(firstNameField,
                getConstraints(1, 0, GridBagConstraints.LINE_START));
        customerPanel.add(new JLabel("Last Name:"), 
                getConstraints(0, 1, GridBagConstraints.LINE_END));
        customerPanel.add(lastNameField, 
                getConstraints(1, 1, GridBagConstraints.LINE_START));
        customerPanel.add(new JLabel("Address:"), 
                getConstraints(0, 2, GridBagConstraints.LINE_END));
        customerPanel.add(addressField, 
                getConstraints(1, 2, GridBagConstraints.LINE_START));
        customerPanel.add(new JLabel("Phone Number:"), 
                getConstraints(0, 3, GridBagConstraints.LINE_END));
        customerPanel.add(phoneNumberField, 
                getConstraints(1, 3, GridBagConstraints.LINE_START));
        customerPanel.add(new JLabel("Email:"), 
                getConstraints(0, 4, GridBagConstraints.LINE_END));
        customerPanel.add(emailField, 
                getConstraints(1, 4, GridBagConstraints.LINE_START));

        // JButton panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        // add panels to main panel
        setLayout(new BorderLayout());
        add(customerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();        
    }
    
    private GridBagConstraints getConstraints(int x, int y, int anchor) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 0, 5);
        c.gridx = x;
        c.gridy = y;
        c.anchor = anchor;
        return c;
    }

    private void cancelButtonActionPerformed() {                                             
        dispose();
    }                                            

    private void confirmButtonActionPerformed() {
        if (validateData()) {
            setData();
            if (confirmButton.getText().equals("Add")) {
                doAdd();
            } else {
                doEdit();
            }
        }
    }
    
    private boolean validateData() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String address = addressField.getText();
        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();
        if (firstName == null || lastName == null || address == null || phoneNumber == null || email == null ||
                firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please fill in all fields.",
                    "Missing Fields", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void setData() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String address = addressField.getText();
        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAddress(address);
        customer.setPhoneNumber(phoneNumber);
        customer.setEmail(email);
    }
    
    private void doEdit() {
        try {
            CustomerDatabase.update(customer);
            dispose();
            fireDatabaseUpdatedEvent();
        } catch (DatabaseException e) {
            System.out.println(e);
        }
    }
    
    private void doAdd() {
        try {
            CustomerDatabase.add(customer);
            dispose();
            fireDatabaseUpdatedEvent();
        } catch(DatabaseException e) {
            System.out.println(e);
        }
    }
    
    private void fireDatabaseUpdatedEvent() {
        CustomerUIFrame mainWindow = (CustomerUIFrame) getOwner();
        mainWindow.fireDatabaseUpdatedEvent();
    }
}
