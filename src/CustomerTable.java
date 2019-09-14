import java.util.List;

import javax.swing.table.AbstractTableModel;
@SuppressWarnings("serial")
public class CustomerTable extends AbstractTableModel {
    private List<CustomerDetails> customers;
    private static final String[] COLUMN_NAMES = {
        "First Name", "Last Name", "Address", "Phone Number", "Email"
    };

    
    public CustomerTable() {
        try {
            customers = CustomerDatabase.getCustomers();
        } catch (DatabaseException e) {
            System.out.println(e);
        }
    }
    
    CustomerDetails getCustomer(int rowIndex) {
        return customers.get(rowIndex);
    }
    
    void databaseUpdated() {
        try {
            customers = CustomerDatabase.getCustomers();
            fireTableDataChanged();
        } catch (DatabaseException e) {
            System.out.println(e);
        }        
    }

    @Override
    public int getRowCount() {
        return customers.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return customers.get(rowIndex).getFirstName();
            case 1:
                return customers.get(rowIndex).getLastName();
            case 2:
                return customers.get(rowIndex).getAddress();
            case 3:
                return customers.get(rowIndex).getPhoneNumber();
            case 4:
                return customers.get(rowIndex).getEmail();
            default:
                return null;
        }
    }   
}