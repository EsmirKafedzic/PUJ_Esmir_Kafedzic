import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DashboardForm extends JFrame {
    private JPanel dashboardPanel; //glavni prozor
    private JLabel lbAdmin; // polje gdje pise ime logovanog korisnika
    private JButton btnRegister; //dugme za registraciju
    private JButton btnViewUsers; //  dugme za pregled svih korisnika
    private JButton btnLogout; // dugme za logout
    private JButton btnAssignTask; // dugme za dodjelu taskova
    private JLabel lblTaskDescription; // polje za prikazivanje zadatka
    private JButton btnCompleteTask; // dugme za završavanje zadatka
    private JButton btnShowAllTasks; // dugme za prikaz svih zadataka
    private JLabel lbReg;
    private JLabel lbShow;
    private JLabel lbAdd;
    private JLabel lbShowT;
    private JTextField tfSearch; // Polje za pretragu taska
    private JButton btnSearch;   // dugme za pretragu taska
    private JLabel srLabelTask;


    private User user;

    public DashboardForm() {
        setTitle("Dashboard");
        setContentPane(dashboardPanel);
        setMinimumSize(new Dimension(500, 429));
        setSize(1000, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        boolean hasRegisteredUsers = connectToDatabase();

        // Ako postoje registrovani korisnici, poziva see logiforma
        if (hasRegisteredUsers) {

            LoginForm loginForm = new LoginForm(this);
            user = loginForm.user;
            System.out.println(user.getIsAdmin());
            if (user != null) {
                lbAdmin.setText("User: " + user.name);

                // provjera koji korisnik se loguje
                if (user.getIsAdmin() == 3) {
                    setManagerPrivileges();
                }else if (user.getIsAdmin() == 2) {
                    setSuperAdminPrivileges();
                } else if (user.getIsAdmin() == 1) {
                    System.out.println("Login uspjesan !!");
                    setAdminPrivileges();
                } else {
                    setUserPrivileges();
                }


                setLocationRelativeTo(null);
                setVisible(true);
            } else {
                dispose();  // Ako nije prijavljen korisnik, zatvori dashboard
            }
        } else {
            // Ako nema registrovanih korisnika, pozivamo regform
            RegistrationForm registrationForm = new RegistrationForm(this);
            user = registrationForm.user;

//            if (user != null) {
//                lbAdmin.setText("User: " + user.name);
//
//                // Prikazivanje dugmadi i privilegija na osnovu korisničkog tipa
//                if (user.getIsAdmin() == 1) {
//                    setAdminPrivileges();
//                } else if (user.getIsAdmin() == 2) {
//                    setSuperAdminPrivileges();
//                } else {
//                    setUserPrivileges();
//                }
//
//                setLocationRelativeTo(null);
//                setVisible(true);
//            } else {
//                dispose();  // Ako korisnik nije registrovan, zatvori DashboardForm
//            }
        }

        btnSearch.addActionListener(e -> performSearch());
        btnCompleteTask.addActionListener(e -> completeUserTask());
        btnShowAllTasks.addActionListener(e -> showAllTasks());
        btnAssignTask.addActionListener(e -> {
            AssignTaskForm form = new AssignTaskForm(this);
            form.setVisible(true);
        });

        btnRegister.addActionListener(e -> {
            RegistrationForm registrationForm = new RegistrationForm(DashboardForm.this);
        });

        btnViewUsers.addActionListener(e -> showAllUsers());


        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(DashboardForm.this,
                    "Da li ste sigurni da želite da se odjavite?",
                    "Logout", JOptionPane.YES_NO_OPTION);
            this.user = null;

            if (confirm == JOptionPane.YES_OPTION) {
                resetDashboard();
                setVisible(false);

                // Ponovna prijava
                LoginForm loginForm1 = new LoginForm(DashboardForm.this);
                User loggedInUser = loginForm1.user;
                if (loggedInUser != null) {
                    user = loggedInUser; // Setuj ponovo korisnika
                    lbAdmin.setText("User: " + user.name);

                    // Prikazivanje odgovarajućih dugmadi i opcija za prijavljenog korisnika
                    if (user.getIsAdmin() == 1) {

                        setAdminPrivileges();
                    } else if (user.getIsAdmin() == 2) {
                        setSuperAdminPrivileges(); // Pozovite za super admina
                    } else {
                        setUserPrivileges();
                    }

                    setVisible(true); // Prikazivanje DashboardForm
                } else {
                    JOptionPane.showMessageDialog(DashboardForm.this,
                            "Login failed.", "Error", JOptionPane.ERROR_MESSAGE);
                    dispose(); // Ako prijava ne uspe, zatvori aplikaciju
                }
            }
        });


    }

    private void setAdminPrivileges() {

        btnSearch.setVisible(false);
        tfSearch.setVisible(false);
        btnViewUsers.setVisible(true);
        btnRegister.setVisible(true);
        btnAssignTask.setVisible(true);
        btnCompleteTask.setVisible(false);
        lblTaskDescription.setVisible(false);
        btnShowAllTasks.setVisible(true);
        lbAdd.setVisible(true);
        lbReg.setVisible(true);
        lbShow.setVisible(true);
        lbShowT.setVisible(true);
        srLabelTask.setVisible(false);

    }

    private void setManagerPrivileges() {

        btnSearch.setVisible(true);
        tfSearch.setVisible(true);
        btnViewUsers.setVisible(true);
        btnRegister.setVisible(false);
        btnAssignTask.setVisible(false);
        btnCompleteTask.setVisible(true);
        lblTaskDescription.setVisible(true);
        btnShowAllTasks.setVisible(true);
        lbAdd.setVisible(false);
        lbReg.setVisible(false);
        lbShow.setVisible(true);
        lbShowT.setVisible(true);
        srLabelTask.setVisible(true);
        showUserTask();
    }



    private void setSuperAdminPrivileges() {
        btnSearch.setVisible(true);
        tfSearch.setVisible(true);
        btnViewUsers.setVisible(true);
        btnRegister.setVisible(true);
        btnAssignTask.setVisible(true);
        btnCompleteTask.setVisible(false);
        lblTaskDescription.setVisible(false);
        btnShowAllTasks.setVisible(true);
        lbAdd.setVisible(true);
        lbReg.setVisible(true);
        lbShow.setVisible(true);
        lbShowT.setVisible(true);
        srLabelTask.setVisible(true);
    }

    private void setUserPrivileges() {

        btnSearch.setVisible(false);
        tfSearch.setVisible(false);
        btnViewUsers.setVisible(false);
        btnRegister.setVisible(false);
        btnAssignTask.setVisible(false);
        btnCompleteTask.setVisible(true);
        lblTaskDescription.setVisible(true);
        btnShowAllTasks.setVisible(false);
        lbAdd.setVisible(false);
        lbReg.setVisible(false);
        lbShow.setVisible(false);
        lbShowT.setVisible(false);
        tfSearch.setVisible(false);
        btnSearch.setVisible(false);
        srLabelTask.setVisible(false);

        showUserTask();
    }


    // Metoda za resetovanje Dashboarda pri odjavi
    private void resetDashboard() {
        btnViewUsers.setVisible(false);
        btnRegister.setVisible(false);
        btnAssignTask.setVisible(false);
        btnCompleteTask.setVisible(false);
        lblTaskDescription.setText("");
    }

    private void performSearch() {
        String searchTerm = tfSearch.getText().toLowerCase().trim();

        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Molimo vas da unesete bar jedan kriterijum za pretragu.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            // SQL upit za pretragu tačnog imena korisnika ili opisa zadatka
            String sql = "SELECT t.id, t.description, t.completed, u.name AS user_name " +
                    "FROM tasks t " +
                    "JOIN users u ON t.user_id = u.id " +
                    "WHERE u.name = ? OR t.description LIKE ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, searchTerm);
                stmt.setString(2, "%" + searchTerm + "%");

                try (ResultSet rs = stmt.executeQuery()) {
                    StringBuilder results = new StringBuilder("Rezultati pretrage:\n\n");

                    while (rs.next()) {
                        int taskId = rs.getInt("id");
                        String taskDescription = rs.getString("description");
                        boolean taskCompleted = rs.getBoolean("completed");
                        String taskStatus = taskCompleted ? "Završen" : "Nije završen";
                        String userName = rs.getString("user_name");

                        results.append("ID: ").append(taskId)
                                .append(", Zadataka: ").append(taskDescription)
                                .append(", Korisnik: ").append(userName)
                                .append(", Status: ").append(taskStatus)
                                .append("\n");
                    }

                    // Prikazivanje korisničkog interfejsa sa rezultatima
                    String resultString = results.toString();
                    if (resultString.trim().isEmpty()) {
                        resultString = "Nema rezultata za vašu pretragu.";
                    }

                    JOptionPane.showMessageDialog(this, resultString, "Rezultati pretrage", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greška prilikom učitavanja podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void showAllTasks() {
        final String DB_URL = "jdbc:mysql://localhost:3306/managment?serverTimeZone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT t.id, t.description, t.completed, u.name AS user_name " +
                     "FROM tasks t JOIN users u ON t.user_id = u.id")) {

            StringBuilder taskList = new StringBuilder("Svi zadaci:\n\n");
            while (rs.next()) {
                int taskId = rs.getInt("id");
                String taskDescription = rs.getString("description");
                boolean taskCompleted = rs.getBoolean("completed");
                String taskStatus = taskCompleted ? "Završen" : "Nije završen";
                String userName = rs.getString("user_name");

                taskList.append("ID: ").append(taskId)
                        .append(", Zadataka: ").append(taskDescription)
                        .append(", Korisnik: ").append(userName)
                        .append(", Status: ").append(taskStatus)
                        .append("\n");
            }

            // Prikazivanje korisničkog interfejsa
            String taskString = taskList.toString();
            JOptionPane.showMessageDialog(this, taskString, "Lista svih zadataka", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greška prilikom učitavanja zadataka.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void showUserTask() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/managment?serverTimeZone=UTC", "root", "");
            String sql = "SELECT description FROM tasks WHERE user_id = ? AND completed IS NULL"; // Pretpostavljamo da postoji kolona 'completed'
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.getId()); // Postavljamo ID korisnika
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String taskDescription = rs.getString("description");
                lblTaskDescription.setText("Vaš zadatak: " + taskDescription); // Prikazivanje opisa zadatka


                btnCompleteTask.setVisible(true);
            } else {
                lblTaskDescription.setText("Nemate dodeljen zadatak."); // Ako nema dodeljen zadatak
                btnCompleteTask.setVisible(false);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greška prilikom učitavanja zadatka!", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void clearSession() {
        this.user = null;

    }

    private void showAllUsers() {
        final String DB_URL = "jdbc:mysql://localhost:3306/managment?serverTimeZone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name, email, phone, address, salary FROM users")) {

            StringBuilder userList = new StringBuilder("Users:\n\n");
            while (rs.next()) {
                int userId = rs.getInt("id");
                String userName = rs.getString("name");
                String userEmail = rs.getString("email");
                String userPhone = rs.getString("phone");
                String userAddress = rs.getString("address");
                String salary = rs.getString("salary");

                userList.append("ID: ").append(userId)
                        .append(", Name: ").append(userName)
                        .append(", Email: ").append(userEmail)
                        .append(", Phone: ").append(userPhone)
                        .append(", Address: ").append(userAddress)
                        .append(", Salary: ").append(salary)
                        .append("\n");
            }

            // Prikazivanje korisničkog interfejsa sa svim korisnicima
            String userString = userList.toString();
            if (userString.trim().isEmpty()) {
                userString = "Nema korisnika u sistemu.";
            }


            if (user.getIsAdmin() == 2) {
                String userInput = JOptionPane.showInputDialog(this, userString + "\nUnesite ID korisnika kojeg želite da obrišete:");

                if (userInput == null || userInput.trim().isEmpty()) {
                    return;
                }

                try {
                    int userIdToDelete = Integer.parseInt(userInput.trim()); // Pretvaramo uneseni tekst u broj

                    deleteUser(userIdToDelete);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Uneti ID nije validan broj!", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            } else {

                JOptionPane.showMessageDialog(this, userString, "Lista korisnika", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greška prilikom učitavanja korisnika.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void deleteUser(int userId) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/managment?serverTimeZone=UTC", "root", "")) {
            String sql = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Korisnik sa ID " + userId + " je uspešno obrisan.", "Uspešno", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Korisnik sa tim ID-om ne postoji.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greška prilikom brisanja korisnika.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void completeUserTask() {
        try {
            // veza sa bazom
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/managment?serverTimeZone=UTC", "root", "");

            // SQL upit
            String sql = "UPDATE tasks SET completed = true WHERE user_id = ? AND completed IS NULL";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.getId()); // Postavljamo ID korisnika da se završi njegov zadatak

            int rowsUpdated = stmt.executeUpdate();


            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Zadatak je uspješno završen.", "Uspješno", JOptionPane.INFORMATION_MESSAGE);
                lblTaskDescription.setText("Nemate dodijeljen zadatak.");

                btnCompleteTask.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Nemate dodeljen zadatak ili je zadatak već završen.", "Greška", JOptionPane.ERROR_MESSAGE);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greška prilikom završavanja zadatka.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean connectToDatabase() {
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardForm());
    }
}
