package CHAT;

import Helper.Auth;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.*;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.awt.*;

/**
 *
 * @author Tran Trung Nghia
 */
public class LoginFrame extends javax.swing.JFrame {

        private JPanel contentPane;
        private JTextField txtUsername;
        private JPasswordField txtPassword;

        private String host = "localhost";
        private int port = 9999;
        private Socket socket;

        private DataInputStream dis;
        private DataOutputStream dos;

        private String username;

        /**
         * Launch the application.
         */
        public static void main(String[] args) {
                EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                try {
                                        LoginFrame frame = new LoginFrame();
//                                        frame.setVisible(false);
                                } catch (Exception e) {
                                        e.printStackTrace();
                                }
                        }
                });
        }

        /**
         * Create the frame.
         */
        public LoginFrame() {
                setTitle("CHAT");
                setLocationRelativeTo(null);
                setResizable(false);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setBounds(100, 100, 450, 300);
                contentPane = new JPanel();
                contentPane.setBackground(new Color(230, 240, 247));
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);

                JPanel headerPanel = new JPanel();
                headerPanel.setBackground(new Color(160, 190, 223));

                JLabel lbUsername = new JLabel("Username");
                lbUsername.setFont(new Font("Arial", Font.BOLD, 14));
                lbUsername.setForeground(Color.red);

                JLabel lbPassword = new JLabel("Password");
                lbPassword.setFont(new Font("Arial", Font.BOLD, 14));
                lbPassword.setForeground(Color.red);

                txtUsername = new JTextField();
                txtUsername.setColumns(10);
                txtUsername.setText(Auth.user.getMaTaiKhoan());
                txtUsername.setEditable(false);

                txtPassword = new JPasswordField();
                txtPassword.setText(Auth.user.getMatKhau());

                JPanel buttons = new JPanel();
                buttons.setBackground(new Color(230, 240, 247));
                JPanel notificationContainer = new JPanel();
                notificationContainer.setBackground(new Color(230, 240, 247));
                GroupLayout gl_contentPane = new GroupLayout(contentPane);
                gl_contentPane.setHorizontalGroup(
                        gl_contentPane.createParallelGroup(Alignment.LEADING)
                                .addComponent(headerPanel, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                                .addGroup(gl_contentPane.createSequentialGroup()
                                        .addGap(69)
                                        .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                .addGroup(gl_contentPane.createSequentialGroup()
                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                        .addComponent(lbPassword)
                                                        .addGap(18)
                                                        .addComponent(txtPassword, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                                                .addGroup(gl_contentPane.createSequentialGroup()
                                                        .addComponent(lbUsername)
                                                        .addGap(18)
                                                        .addComponent(txtUsername, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))
                                        .addGap(81))
                                .addComponent(notificationContainer, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                                .addComponent(buttons, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                );
                gl_contentPane.setVerticalGroup(
                        gl_contentPane.createParallelGroup(Alignment.LEADING)
                                .addGroup(gl_contentPane.createSequentialGroup()
                                        .addComponent(headerPanel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                        .addGap(33)
                                        .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(lbUsername)
                                                .addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(30)
                                        .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lbPassword))
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addComponent(notificationContainer, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addComponent(buttons, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                        .addGap(22))
                );

                JLabel notification = new JLabel("");
                notification.setForeground(Color.RED);
                notification.setFont(new Font("Times New Roman", Font.PLAIN, 13));
                notificationContainer.add(notification);

                JButton login = new JButton("Log in");
                String response = Login(txtUsername.getText(), String.copyValueOf(txtPassword.getPassword()));

                // đăng nhập thành công thì server sẽ trả về  chuỗi "Log in successful"
                if (response.equals("Log in successful")) {
                        username = txtUsername.getText();
                        EventQueue.invokeLater(new Runnable() {
                                public void run() {
                                        try {
                                                dispose();
                                                ChatFrame frame = new ChatFrame(username, dis, dos);
                                                frame.setVisible(true);
                                        } catch (Exception e) {
                                                e.printStackTrace();
                                        }
                                }
                        });
                }
                login.setEnabled(true);
                buttons.add(login);
              
                JLabel headerContent = new JLabel("LOGIN");
                headerContent.setFont(new Font("Poor Richard", Font.BOLD, 24));
                headerPanel.add(headerContent);
                contentPane.setLayout(gl_contentPane);

                txtUsername.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                                if (txtUsername.getText().isBlank() || String.copyValueOf(txtPassword.getPassword()).isBlank()) {
                                        login.setEnabled(false);
                                } else {
                                        login.setEnabled(true);
                                }
                        }
                });

                txtPassword.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                                if (txtUsername.getText().isBlank() || String.copyValueOf(txtPassword.getPassword()).isBlank()) {
                                        login.setEnabled(false);
                                } else {
                                        login.setEnabled(true);
                                }
                        }
                });

                this.getRootPane().setDefaultButton(login);
        }

        /**
         * Gửi yêu cầu đăng nhập đến server Trả về kết quả phản hồi từ server
         */
        public String Login(String username, String password) {
                try {
                        Connect();

                        dos.writeUTF("Log in");
                        dos.writeUTF(username);
                        dos.writeUTF(password);
                        dos.flush();

                        String response = dis.readUTF();
                        return response;

                } catch (IOException e) {
                        e.printStackTrace();
                        return "Network error: Log in fail";
                }
        }

        /**
         * Gửi yêu cầu đăng ký đến server Trả về kết quả phản hồi từ server
         */
        public String Signup(String username, String password) {
                try {
                        Connect();

                        dos.writeUTF("Sign up");
                        dos.writeUTF(username);
                        dos.writeUTF(password);
                        dos.flush();

                        String response = dis.readUTF();
                        return response;

                } catch (IOException e) {
                        e.printStackTrace();
                        return "Network error: Sign up fail";
                }
        }

        /**
         * Kết nối đến server
         */
        public void Connect() {
                try {
                        if (socket != null) {
                                socket.close();
                        }
                        socket = new Socket(host, port);
                        this.dis = new DataInputStream(socket.getInputStream());
                        this.dos = new DataOutputStream(socket.getOutputStream());
                } catch (IOException ex) {
                        ex.printStackTrace();
                }
        }

        public String getUsername() {
                return this.username;
        }
}
