import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDatabase {

	public static ArrayList<CustomerDetails> getCustomers() throws DatabaseException {
		ArrayList<CustomerDetails> customers = new ArrayList<>();

		String query = "SELECT * FROM Customer ORDER BY EmailAddress";

		Connection connection = DatabaseConnectivity.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("CustomerID");
				String firstName = rs.getString("FirstName");
				String lastName = rs.getString("LastName");
				String address = rs.getString("Address");
				String phoneNumber = rs.getString("PhoneNumber");
				String email = rs.getString("EmailAddress");
				CustomerDetails c = new CustomerDetails(id, firstName, lastName, address, phoneNumber, email);
				customers.add(c);
			}
			return customers;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	public static CustomerDetails getCustomer(int id) throws DatabaseException {
		String selectCustomer
		= "SELECT * FROM Customer WHERE CustomerID = ?";

		Connection connection = DatabaseConnectivity.getConnection();
		try (   PreparedStatement ps = connection.prepareStatement(selectCustomer)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String firstName = rs.getString("FirstName");
				String lastName = rs.getString("LastName");
				String address = rs.getString("Address");
				String phoneNumber = rs.getString("PhoneNumber");
				String email = rs.getString("EmailAddress");
				CustomerDetails c = new CustomerDetails(id, firstName, lastName, address, phoneNumber, email);
				return c;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	public static void add(CustomerDetails c) throws DatabaseException {
		String insert
		= "INSERT INTO Customer (FirstName, LastName, Address, PhoneNumber, EmailAddress)"
				+ "VALUES (?, ?, ?, ?, ?)";
		Connection connection = DatabaseConnectivity.getConnection();        
		try (   PreparedStatement ps = connection.prepareStatement(insert)) {
			ps.setString(1, c.getFirstName());
			ps.setString(2, c.getLastName());
			ps.setString(3, c.getAddress());
			ps.setString(4, c.getPhoneNumber());
			ps.setString(5, c.getEmail());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	public static void update(CustomerDetails customerDetails) throws DatabaseException {
		String sql = "UPDATE Customer SET "
				+ "FirstName = ?, "
				+ "LastName = ?, "
				+ "Address = ?, "
				+ "PhoneNumber = ?,"
				+ "EmailAddress = ? "
				+ "WHERE CustomerID = ?";
		Connection connection = DatabaseConnectivity.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, customerDetails.getFirstName());
			ps.setString(2, customerDetails.getLastName());
			ps.setString(3, customerDetails.getAddress());
			ps.setString(4, customerDetails.getPhoneNumber());
			ps.setString(5, customerDetails.getEmail());
			ps.setInt(6, customerDetails.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}        
	}

	public static void delete(CustomerDetails c) throws DatabaseException {
		String delete
		= "DELETE FROM Customer "
				+ "WHERE CustomerID = ?";
		Connection connection = DatabaseConnectivity.getConnection();
		try (   PreparedStatement ps = connection.prepareStatement(delete)) {
			ps.setInt(1, c.getId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
}
