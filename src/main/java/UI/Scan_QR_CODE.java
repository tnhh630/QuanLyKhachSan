package UI;

import DAO.TaiKhoanDAO;
import Entity.TaiKhoan;
import Helper.Auth;
import Helper.MsgBox;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.Timer;

public class Scan_QR_CODE extends javax.swing.JDialog implements Runnable, ThreadFactory {

        public String str = "";
        private WebcamPanel panel = null;
        private Webcam webcam = null;

        private static final long serialVersionUID = 6441489157408381878L;
        private Executor executor = Executors.newSingleThreadExecutor(this);
        public Timer t;
        boolean check = true;

        public Scan_QR_CODE(java.awt.Frame parent, boolean modal) {
                super(parent, modal);
                Auth.user = null;
                initComponents();
                initWebcam();
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                result_field.setVisible(false);
                setLocationRelativeTo(null);
                setResizable(false);
                ActionListener a = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                int value = jProgressBar1.getValue() + 1;
                                if (value == 100) {
                                        checkLogin();
                                }
                                jProgressBar1.setValue(value <= 100 ? value : 0);
                        }
                };
                t = new javax.swing.Timer(10, a);

        }
        //------------------------------------------------------------------------------------------------------------------------------------------------

        //------------------------------------------------------------------------------------------------------------------------------------------------
        public void checkLogin() {
                TaiKhoanDAO tkDAO = new TaiKhoanDAO();
                String[] account = str.split("-");
                String taiKhoan = account[0];
                String matKhau = account[1];
                TaiKhoan tk = tkDAO.SelectByID(taiKhoan);
                if (tk != null) {
                        Auth.user = tk;
                        this.dispose();
                        DangNhapJDialog dialog = new DangNhapJDialog(new javax.swing.JFrame(), true);
                        dialog.login();
//                        dialog.setVisible(true);
                } else {
                        MsgBox.alert(this, "Không hợp lệ !");
                        DangNhapJDialog dialog = new DangNhapJDialog(new javax.swing.JFrame(), true);
                        dialog.login();
                }
                t.stop();
                webcam.close();
                this.dispose();
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jProgressBar1 = new javax.swing.JProgressBar();
                jPanel1 = new javax.swing.JPanel();
                jPanel2 = new javax.swing.JPanel();
                result_field = new javax.swing.JTextField();

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jProgressBar1.setForeground(new java.awt.Color(102, 255, 102));
                getContentPane().add(jProgressBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 480, 30));

                jPanel1.setBackground(new java.awt.Color(255, 255, 255));
                jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jPanel2.setBackground(new java.awt.Color(250, 250, 250));
                jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230)));
                jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
                jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 480, 350));

                getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 360));

                result_field.setBorder(null);
                getContentPane().add(result_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 470, 20));

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
                                if ("Windows".equals(info.getName())) {
                                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                        break;
                                }
                        }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(Scan_QR_CODE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>

                //</editor-fold>

                /* Create and display the form */
 /*
                java.awt.EventQueue.invokeLater(() -> {
                        new Scan_QR_CODE().setVisible(true);
                });
                 */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                Scan_QR_CODE dialog = new Scan_QR_CODE(new javax.swing.JFrame(), true);
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
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JProgressBar jProgressBar1;
        private javax.swing.JTextField result_field;
        // End of variables declaration//GEN-END:variables

        private void initWebcam() {
                Dimension size = WebcamResolution.QVGA.getSize();
                webcam = Webcam.getWebcams().get(0); //0 is default webcam
                webcam.setViewSize(size);

                panel = new WebcamPanel(webcam);
                panel.setPreferredSize(size);
//                panel.setFPSDisplayed(true);

                jPanel2.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 300));

                executor.execute(this);
        }

        @Override
        public void run() {
                do {
                        webcam.open();
                        try {
                                Thread.sleep(100);
                        } catch (Exception e) {
                        }

                        Result result = null;
                        BufferedImage image = null;

                        if (webcam.isOpen()) {
                                if ((image = webcam.getImage()) == null) {
                                        continue;
                                }
                        }

                        LuminanceSource source = new BufferedImageLuminanceSource(image);
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                        try {
                                result = new MultiFormatReader().decode(bitmap);
                        } catch (Exception e) {
                                //No result...
                        }

                        if (result != null) {
                                check = false;
                                result_field.setText(result.getText());
                                str = result.getText();
                                t.start();
                                break;
                        }
                } while (check);
                Thread.interrupted();
        }

        @Override
        public Thread newThread(Runnable r) {
                Thread t1 = new Thread(r, "My Thread");
                t1.setDaemon(true);
                t1.interrupt();
                return t1;
        }
}
