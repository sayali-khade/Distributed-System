/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
    Name-Sayali Khade
    Id-1001518264

*/
package dslab1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Date;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

/**
 *
 * @author sayal
 */

/*Mutli-Threaded Chat Application-Server [4]  */
public class ChatServer extends javax.swing.JFrame {

    static ArrayList<ClientHandler> clientlist = new ArrayList<ClientHandler>();  //to keep a track of clients
    static int i = 0;
    static ServerSocket serverSocket;

    public ChatServer() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ServerText = new javax.swing.JTextArea();
        ServerLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ServerText.setEditable(false);
        ServerText.setColumns(20);
        ServerText.setRows(5);
        jScrollPane1.setViewportView(ServerText);

        ServerLabel.setText("Server");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(ServerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ServerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    /*
        reloading server msgs on server screen
        and calling acceptCLients to accept incomming client/participant/coordinator request on socket
    */
    public static void main(String args[]) {

        try {
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
                java.util.logging.Logger.getLogger(ChatServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(ChatServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(ChatServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(ChatServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>

            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new ChatServer().setVisible(true);
                      try {
                          //file reader code; reading file of server and loading on its screen whenever server is started
                          //[12]
                File fl = new File("./chat.txt");
                //if file exitst read the file
                    if (fl.exists()) {
                    //if file exists it will read from file and append to server text whenever reloaded [12]
                        BufferedReader br = new BufferedReader(new FileReader(fl));
                        String st = "";
                        ServerText.append("Server bckup Messsages for are: \n");
                        while ((st = br.readLine()) != null) {

                            ServerText.append(st + "\n");
                        }

                    } else {
                        ServerText.append("Server bckup Messsages for are: \n");
                    }
                    } catch (Exception ex) {
                        //Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                        ex.printStackTrace();
                    }
                }
            });

            //creating serversocket and calling acceptclients to accept multiple clients.
             serverSocket = new ServerSocket(8080);
             acceptClients();

        } catch (IOException ex) {
           ex.printStackTrace();
        }
        //</editor-fold>
    }
    /*
        it is accepting all socket connections
        and assigning dis dos and a number to every coordinator
        accepts all incomming requests

    */
    //[4]
    public static void acceptClients(){
         Socket csocket;
         try{
    //run loop to accept clients
            while (true)
            {
                // Accept the incoming request
                csocket = serverSocket.accept();
                 DataInputStream dis = new DataInputStream(csocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(csocket.getOutputStream());
                System.out.println("New client request received : " + csocket);

                // Assigning streams for i/o and o/p


                ServerText.append("Creating a new handler for "+ csocket.getClass().getName() +" client"+i+"\n");

                // For handling the request creating a new object handler

                ClientHandler clients = new ClientHandler(csocket,"\n client" + i, dis, dos,i);

                // Thread creation for the object
                Thread t1 = new Thread(clients);

                System.out.println("Adding the client to an online client list");

                //adding clients to arraylist to maintain a list of clients in the chat app.
                clientlist.add(clients);

                t1.start();

                i++;

            }
         }catch(Exception ex)
         {
             ex.printStackTrace();
         }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ServerLabel;
    public static javax.swing.JTextArea ServerText;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

/*
References:-

1.	“How to Append Text to an Existing File in Java?” Recalll, recalll.co/ask/v/topic/How-to-append-text-to-an-existing-file-in-Java/59f8df3a1126f45d1f8b5ebc.

2.	Marshall, James. “HTTP Made Really Easy.” HTTP Made Really Easy, 10 Dec. 2012, www.jmarshall.com/easy/http/#http1.1s3.

3.	Mkyong. “How to Calculate Date and Time Difference in Java.” How to Calculate Date and Time Difference in Java, 25 Jan. 2013, www.mkyong.com/java/how-to-calculate-date-time-difference-in-java/.

4.	Mahrsee, Rishabh. “Multi-Threaded Chat Application in Java | Set 1 (Server Side Programming).” GeeksforGeeks, 17 June 2017, www.geeksforgeeks.org/multi-threaded-chat-application-set-1/.

5.	Mahrsee, Rishabh. “Multi-Threaded Chat Application in Java | Set 2 (Client Side Programming).” GeeksforGeeks, 17 June 2017, www.geeksforgeeks.org/multi-threaded-chat-application-set-2/.

6.	R, Hannes. “Getting Date in HTTP Format in Java.” Stack Overflow, 27 Jan. 2015, 8:13, stackoverflow.com/questions/7707555/getting-date-in-http-format-in-java.

7.	“Custom Countdown Second Timer.” Java - Custom Countdown Second Timer - Code Review Stack Exchange, codereview.stackexchange.com/questions/118669/custom-countdown-second-timer.

8.	Oracle. “ Copyright (c) 2002, Oracle and/or Its Affiliates.” Moved, docs.oracle.com/javase/7/docs/technotes/guides/lang/Countdown.java.

9.	Jenkov, Jakob. “Java Regex - Matcher.” Jenkov.com, tutorials.jenkov.com/java-regex/matcher.html. ---group function

10.	Oracle. “Pattern.” Pattern (Java Platform SE 7 ), 19 Dec. 2017, docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html.

11.	Javatpoint. “Java Regex | Regular Expression - Javatpoint.” Www.javatpoint.com, www.javatpoint.com/java-regex.

12.	mkyong. “How to Read File in Java – BufferedReader.” How to Read File in Java – BufferedReader – Mkyong.com, 1 Dec. 2008, www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/.

13.	Vogel, Lars. “Quick Links.” Vogella.com, 26 Sept. 2016, www.vogella.com/tutorials/Logging/article.html.

14.	Jenkov, Jakob. “Java Logging.” Jenkov.com, 23 June 2014, tutorials.jenkov.com/java-logging/index.html.

15.	Steen, Marteen Van, and Andrew S Tannenbaum. “Distributed Systems.” Distributed System Edition 3, 2017 (Distributed System Edition 3 Book mentioned in lab manual-Pg 487 and 488 pseudo commit of 2PC)


16.	mkyong. “How to Write to File in Java – BufferedWriter.” How to Write to File in Java – BufferedWriter – Mkyong.com, 2 June 2010, www.mkyong.com/java/how-to-write-to-file-in-java-bufferedwriter-example/.

*/
