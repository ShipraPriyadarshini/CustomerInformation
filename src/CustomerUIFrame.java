import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


@SuppressWarnings("serial")
public class CustomerUIFrame extends JFrame {
    private JTable customerTable;
    private CustomerTable customerTableModel;
    
    public CustomerUIFrame() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }
        setTitle("Customer Manager");
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        add(buildButtonPanel(), BorderLayout.SOUTH);
        customerTable = buildCustomerTable();
        add(new JScrollPane(customerTable), BorderLayout.CENTER);
        pack();
        setVisible(true);        
    }
    
    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel();
        
        JButton addButton = new JButton("Add");
        addButton.setToolTipText("Add customer");
        addButton.addActionListener((ActionEvent) -> {
            doAddButton();
        });
        panel.add(addButton);
        
        JButton editButton = new JButton("Edit");
        editButton.setToolTipText("Edit selected customer");
        editButton.addActionListener((ActionEvent) -> {
            doEditButton();
        });
        panel.add(editButton);
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.setToolTipText("Delete selected customer");
        deleteButton.addActionListener((ActionEvent) -> {
            doDeleteButton();
        });
        panel.add(deleteButton);
        
        return panel;
    }
    
    private void doAddButton() {
        CustomerUI productForm = new CustomerUI(this, "Add Customer", true);
        productForm.setLocationRelativeTo(this);
        productForm.setVisible(true);
    }
    
    private void doEditButton() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "No customer is currently selected.", 
                    "No customer selected", JOptionPane.ERROR_MESSAGE);
        } else {
            CustomerDetails product = customerTableModel.getCustomer(selectedRow);
            CustomerUI productForm = 
                    new CustomerUI(this, "Edit Customer", true, product);
            productForm.setLocationRelativeTo(this);
            productForm.setVisible(true);
        }
    }
    
    private void doDeleteButton() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "No product is currently selected.", 
                    "No product selected", JOptionPane.ERROR_MESSAGE);
        } else {
            CustomerDetails c = customerTableModel.getCustomer(selectedRow);
            int ask = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete " + 
                        c.getFirstName() + " " + c.getLastName() + " from the Customer database?",
                    "Confirm delete", JOptionPane.YES_NO_OPTION);
            if (ask == JOptionPane.YES_OPTION) {
                try {                    
                    CustomerDatabase.delete(c);
                    fireDatabaseUpdatedEvent();
                } catch (DatabaseException e) {
                    System.out.println(e);
                }
            }
        }
    }
    
    void fireDatabaseUpdatedEvent() {
        customerTableModel.databaseUpdated();
    }    
    
    private JTable buildCustomerTable() {
        customerTableModel = new CustomerTable();
        JTable table = new JTable(customerTableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBorder(null);
        return table;
    }    
}