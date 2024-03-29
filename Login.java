import java.io.*;

public class Login {
    String filePath;

    User[] list;
    int numAccount;

    final String FILE_ERROR = "FILE_ERROR";
    final String USERNAME_TAKEN = "USERNAME_TAKEN";
    final String WEAK_PASSWORD = "WEAK_PASSWORD";
    final String INCORRECT_INFO = "Incorrect username or password";

    public Login(String filePath) {
        this.filePath = filePath;
        try {
            BufferedReader in = new BufferedReader(new FileReader(this.filePath));


                numAccount = Integer.parseInt(in.readLine());
                list = new User[numAccount];
                for (int i = 0; i < numAccount; i++) {
                    list[i] = new User(in.readLine(), in.readLine(), in.readLine());
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

    public boolean compareLogin(String username, String password) {
        for (int i = 0; i < numAccount; i++) {
            if (username.compareTo(list[i].getUsername()) + password.compareTo(list[i].getPassword()) == 0) {
                return true;
            }
        }
        return false;
    }

    public String forgetPassword(String username, String email) {
        for (int i = 0; i < numAccount; i++) {
            if (username.compareTo(list[i].getUsername()) + email.compareTo(list[i].getEmail()) == 0)
                return list[i].getPassword();
        }
        return INCORRECT_INFO;
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
            return USERNAME_TAKEN;
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
            BufferedWriter out = new BufferedWriter(new FileWriter(filePath, true));

            RandomAccessFile randomAccess = new RandomAccessFile(filePath, "rw");
            randomAccess.seek(0);
            randomAccess.write(("" + (numAccount + 1)).getBytes());
            out.newLine();
            out.write(username);
            out.newLine();
            out.write(password);
            out.newLine();
            out.write(email);
            out.close();
            randomAccess.close();
        } catch (IOException iox) {
            System.out.println("Error: " + iox);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}