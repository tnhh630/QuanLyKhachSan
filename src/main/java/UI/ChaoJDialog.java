package UI;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author HP
 */
public class ChaoJDialog extends javax.swing.JDialog {

        /**
         * Creates new form ChaoJDialog
         */
        public ChaoJDialog(java.awt.Frame parent, boolean modal) {
                super(parent, modal);
                initComponents();
                GetFolder();
                try {
                        UnZip("AnhNhanVien.zip", "AnhNhanVien");
                        File file = new File("AnhNhanVien.zip");
                        file.delete();
                } catch (Exception e) {

                }

                init();

        }

        /**
         * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                progressBar = new javax.swing.JProgressBar();
                lblBackround = new javax.swing.JLabel();
                txtChao = new javax.swing.JLabel();

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                setUndecorated(true);

                progressBar.setBackground(new java.awt.Color(255, 102, 102));
                progressBar.setStringPainted(true);

                lblBackround.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/hotellogo1.jpg"))); // NOI18N
                lblBackround.setPreferredSize(new java.awt.Dimension(1150, 600));

                txtChao.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
                txtChao.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblBackround, javax.swing.GroupLayout.PREFERRED_SIZE, 697, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtChao, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(104, 104, 104))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(lblBackround, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtChao, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) {
                /* Set the Nimbus look and feel */
                //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
                /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
                 */
                try {
                        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                                if ("Nimbus".equals(info.getName())) {
                                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                        break;
                                }
                        }
                } catch (ClassNotFoundException ex) {
                        java.util.logging.Logger.getLogger(ChaoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(ChaoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(ChaoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(ChaoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>
                //</editor-fold>

                /* Create and display the dialog */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                ChaoJDialog dialog = new ChaoJDialog(new javax.swing.JFrame(), true);
                                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                                        @Override
                                        public void windowClosing(java.awt.event.WindowEvent e) {
                                                System.exit(0);
                                        }
                                });
                                dialog.setVisible(true);
                        }
                });
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel lblBackround;
        private javax.swing.JProgressBar progressBar;
        private javax.swing.JLabel txtChao;
        // End of variables declaration//GEN-END:variables
  void init() {
                setLocationRelativeTo(null);
                Loading();

        }

        int bytesRead;

        void GetFolder() {
                PrintWriter command;
                Socket socket = null;

                try {
                        socket = new Socket("localhost", 55555);
                        System.out.println("Connected...");
                        command = new PrintWriter(socket.getOutputStream(), true);
                        command.println("load");

                        InputStream in = socket.getInputStream();
                        DataInputStream clientData = new DataInputStream(in);

                        OutputStream output = new FileOutputStream("AnhNhanVien.zip");
                        long size = clientData.readLong();
                        byte[] buffer = new byte[1024];
                        while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                                output.write(buffer, 0, bytesRead);
                                size -= bytesRead;
                        }

                        // Closing the FileOutputStream handle
                        in.close();
                        clientData.close();
                        output.close();
                } catch (Exception e) {

                }
        }

        private static final int BUFFER_SIZE = 4096;

        public void UnZip(String zipFilePath, String destDirectory) throws IOException {
                File destDir = new File(destDirectory);
                if (!destDir.exists()) {
                        destDir.mkdir();
                }
                ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
                ZipEntry entry = zipIn.getNextEntry();
                // iterates over entries in the zip file
                while (entry != null) {
                        String filePath = destDirectory + File.separator + entry.getName();
                        if (!entry.isDirectory()) {
                                // if the entry is a file, extracts it
                                extractFile(zipIn, filePath);
                        } else {
                                // if the entry is a directory, make the directory
                                File dir = new File(filePath);
                                dir.mkdirs();
                        }
                        zipIn.closeEntry();
                        entry = zipIn.getNextEntry();
                }
                zipIn.close();
        }

        private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
                byte[] bytesIn = new byte[BUFFER_SIZE];
                int read = 0;
                while ((read = zipIn.read(bytesIn)) != -1) {
                        bos.write(bytesIn, 0, read);
                }
                bos.close();
        }

        public void Loading() {
                setLocationRelativeTo(null);

                Thread t = new Thread() {
                        int i = 0;

                        @Override
                        public void run() {
                                while (true) {
                                        try {
                                                i++;
                                                progressBar.setValue(i);
                                                if (i == 20) {
                                                        txtChao.setText("Welcome to the League Of GodEdoc");
                                                }
                                                if (i == 50) {
                                                        txtChao.setText("       Database is comin'");
                                                }
                                                if (i == 90) {
                                                        txtChao.setText("         3..2..1..GO!!!");
                                                }
                                                if (i == 100) {
                                                        ChaoJDialog.this.dispose();
                                                        break;
                                                }
                                                Thread.sleep(20);
                                        } catch (InterruptedException ex) {
                                                break;
                                        }
                                }
                        }
                };
                t.start();
        }
}