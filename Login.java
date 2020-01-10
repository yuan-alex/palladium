import java.io.*;

public class Login {
    final String ACCOUNT_LIST_TEMP = "login_test.txt";

    LoginAccount[] list;
    int numAccount;

    final String FILE_ERROR = "FILE_ERROR";
    final String WEAK_PASSWORD = "WEAK_PASSWORD";
    final String USERNAME_TAKEN = "USERNAME_TAKEN";

    public Login() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(ACCOUNT_LIST_TEMP));
            String input;
            while ((input = in.readLine()) != null) {
                numAccount = Integer.parseInt(input);
                list = new LoginAccount[numAccount];
                for (int i = 0; i < numAccount; i++) {
                    list[i] = new LoginAccount(in.readLine(), in.readLine(), in.readLine());
                }
            }
            in.close();
        } catch (IOException iox) {
            System.out.println(" ____________________________________");
            System.out.println("|                                    |");
            System.out.println("| File Reading Error!                |");
            System.out.println("|                                    |");
            System.out.println("|____________________________________|");
            System.out.println("Error: " + iox + "\n");            
        }
    }

    public String compareLogin(String username, String password) {
        for (int i = 0; i < numAccount; i++) {
            if (username.compareTo(list[i].getUsername()) + password.compareTo(list[i].getPassword()) == 0) {
                return username;
            }
        }
        return "USER_NOT_FOUND";
    }

    public String forgetPassword(String username, String email) {
        for (int i = 0; i < numAccount; i++) {
            if (username.compareTo(list[i].getUsername()) + email.compareTo(list[i].getEmail()) == 0)
                return list[i].getPassword();
        }
        return "Username does not exist or wrong email";
    }

    public String register(String username, String password, String email) {
        boolean usernameTaken = false;
        for (int i = 0; i < numAccount; i++) {
            if (username.compareTo(list[i].getUsername()) == 0) {
                usernameTaken = true;
            }
        }

        if (checkPasswordStrength(password) && usernameTaken == false) {
            addUser(username, password, email);
            return username;
        } else if (usernameTaken)
            return "USERNAME_TAKEN";
        else
            return WEAK_PASSWORD;
    }

    private boolean checkPasswordStrength(String password) {
        boolean uppercase = false;
        boolean lowercase = false;
        boolean number = false;
        boolean symbol = false;
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) <= 'z' && password.charAt(i) >= 'a')
                lowercase = true;
            else if (password.charAt(i) <= 'Z' && password.charAt(i) >= 'A')
                uppercase = true;
            else if (password.charAt(i) <= '9' && password.charAt(i) >= '0')
                number = true;
            else
                symbol = true;

        }
        return password.length() >= 8 && uppercase && lowercase && number && symbol;
    }

    public void addUser(String username, String password, String email) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(ACCOUNT_LIST_TEMP, true));

            RandomAccessFile randomAccess = new RandomAccessFile(ACCOUNT_LIST_TEMP, "rw");
            randomAccess.seek(0);
            randomAccess.write(("" + (numAccount + 1)).getBytes());
            out.newLine();
            out.write(username);
            out.newLine();
            out.write(password);
            out.newLine();
            out.write(email);
            out.newLine();
            out.close();
            randomAccess.close();
        } catch (IOException iox) {
            System.out.println("Error: " + iox);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}