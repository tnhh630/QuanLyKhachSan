/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author Admin
 */
public class Server {

        public static void main(String[] args) throws Exception {
                BufferedReader in = null;
                while (true) {
                        try ( ServerSocket serv = new ServerSocket(6789)) {
                                System.out.println("Waiting for incoming connection...");
                                compress("Server\\AnhNhanVien");

                                try ( Socket socket = serv.accept()) {
                                        System.out.println("Connected....");
                                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                        String request = in.readLine();
                                        if (request.equals("load")) {
                                                try {
                                                        File myFile = new File("Server\\AnhNhanVien.zip");
                                                        byte[] mybytearray = new byte[(int) myFile.length()];

                                                        FileInputStream fis = new FileInputStream(myFile);
                                                        BufferedInputStream bis = new BufferedInputStream(fis);

                                                        DataInputStream dis = new DataInputStream(bis);
                                                        while (dis.available() > 0) {
                                                                dis.readFully(mybytearray, 0, mybytearray.length);

                                                                OutputStream os = socket.getOutputStream();

                                                                //Sending file name and file size to the server  
                                                                DataOutputStream dos = new DataOutputStream(os);
                                                                dos.writeLong(mybytearray.length);
                                                                dos.write(mybytearray, 0, mybytearray.length);
                                                                dos.flush();

                                                                //Sending file data to the server  
                                                                os.write(mybytearray, 0, mybytearray.length);
                                                                os.flush();

                                                                //Closing socket
                                                                os.close();
                                                                dos.close();
                                                                socket.close();
                                                        }

                                                } catch (EOFException e) {
                                                        e.printStackTrace();
                                                }

                                        }
                                        if (request.equals("picture")) {
                                                String name = in.readLine();
                                                BufferedImage image = ImageIO.read(socket.getInputStream());
                                                ImageIO.write(image, "jpg", new File("AnhNhanVien/" + name));
                                                ImageIO.write(image, "jpg", new File("Server/AnhNhanVien/" + name));
                                        }
                                }

                        }

                }

        }

        public static void compress(String dirPath) {
                final Path sourceDir = Paths.get(dirPath);
                String zipFileName = dirPath.concat(".zip");
                try {
                        final ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
                        Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
                                @Override
                                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                                        try {
                                                Path targetFile = sourceDir.relativize(file);
                                                outputStream.putNextEntry(new ZipEntry(targetFile.toString()));
                                                byte[] bytes = Files.readAllBytes(file);
                                                outputStream.write(bytes, 0, bytes.length);
                                                outputStream.closeEntry();
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
                                        return FileVisitResult.CONTINUE;
                                }
                        });
                        outputStream.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
}
