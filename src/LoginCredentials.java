public class LoginCredentials {
	public static boolean authenticate(String username, String password) {
		if (username.equals("customer") && password.equals("shipra")) {
			return true;
		}
		return false;
	}
}