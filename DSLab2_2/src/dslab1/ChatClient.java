/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
        Name-Sayali Khade
        Id- 1001518264

*/
package dslab1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author sayal
 */

/* Multithreaded chat application-client side code [5] */
public class ChatClient extends javax.swing.JFrame {
    
    final static int serverPort = 8080;
    static Socket csocket;
    static DataInputStream dis;
    static DataOutputStream dos;
    static int secondsLeft=0;
    static Matcher m;
    static Matcher m1;
    static String msg="";
    static String message="";
    static String hdate="";
    static String initialResponseLine="";
    static String contentType="";
    static String httpmsg="";
    static String clientnum="";
    static String cnum="";
    static Logger log1;
    static FileHandler filehandler;
    
   
    
    public ChatClient() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ClientText = new javax.swing.JTextArea();
        abort_btn = new javax.swing.JButton();
        ClientLabel = new javax.swing.JLabel();
        commit_btn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ClientText.setEditable(false);
        ClientText.setColumns(20);
        ClientText.setRows(5);
        jScrollPane1.setViewportView(ClientText);

        abort_btn.setText("Abort");
        abort_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abort_btnActionPerformed(evt);
            }
        });

        ClientLabel.setText("Participant");

        commit_btn.setText("Commit");
        commit_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commit_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(abort_btn)
                        .addGap(18, 18, 18)
                        .addComponent(commit_btn))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(ClientLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(ClientLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(abort_btn)
                    .addComponent(commit_btn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /* Input - Button Action event
       This function is when user clicks on ABORT button it will send VOTE_ABORT on dos 
       if the participant wants to abort
       Output - Displays VOTE_ABORT on DOS*/
    private void abort_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abort_btnActionPerformed
        // TODO add your handling code here
        try {
            //Initailizing dos 
            DataOutputStream dos = new DataOutputStream(csocket.getOutputStream());    
            String word = "VOTE_ABORT";
            // writing on dos VOTE_ABORT encoded with http
            dos.writeUTF(httpmsg+word+"\n");
                																								// method to exit the server operation
            } catch (Exception e) {
                e.printStackTrace();
            }
        //Messages are send from client and broadcasted to others.
        
    }//GEN-LAST:event_abort_btnActionPerformed

    /* Input - Button Action event
       This function is when user clicks on COMMIT button it will send VOTE_COMMIT on dos 
       if the participant wants to commit
       Output - Displays VOTE_COMMIT on DOS*/
    private void commit_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commit_btnActionPerformed
        // TODO add your handling code here:
         try {
               //Initailizing dos 
             DataOutputStream dos = new DataOutputStream(csocket.getOutputStream());// get the word to search entered by user   
             String word = "VOTE_COMMIT";
             //writing on DOS VOTE_COMMIT encoded with http
             dos.writeUTF(httpmsg+word+"\n");
             //writing into log file 
             log1.info("READY");																				// method to exit the server operation
            } catch (Exception e) {
                e.printStackTrace();
            }
    }//GEN-LAST:event_commit_btnActionPerformed

  
    /* INPUT - system date
       This function takes input as system date while sending message and converts it into HTTP format 
       date and time.
        OUTPUT - converts the system date into proper HTTP format [6]
    */
     static String getServerTime() {
         //[6]
         //gets the current calendar instance
            Calendar calendar = Calendar.getInstance();
            //http format of date and time to which system date is to be converted
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            //returning date of proper format
            return dateFormat.format(calendar.getTime());
}
    
    /**
     * @param args the command line arguments
     */
     /*Pseudo-code implementation of participant
         It is waiting for VOTE_REQUEST from Coordinator,once it receives VOTE_REQUEST it will either 
        send a commit or abort on whose basis if it gets GLOBAL_COMMIT or GLOBAL_ABORT from coordinator it will dos 
        the same respectively.
        The code is also handling timeout of participant by sendin "NEED_DECISION"
        Maintaing log files for its states.
        Also has a timer implemented for 20secs    
        [15]
        */
     //[15] Implemeneted psuedo code from the DS book mentioned in lab manual
    public static void main(String args[]) throws UnknownHostException, IOException  {
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
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        
        	
        /* Create and display the form */
        /* File handler code
            When the particular participant is loaded initial file is loaded of every participant*/
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //displaying participant screen
                new ChatClient().setVisible(true);
                /*file handling code to read participant file and loading on the 
                    screen of each participant*/
               int i=1;
                try {
                for(i=1;i<=3;i++)
                {    
                  //file reader code [12]  
                File fl = new File("./backup " + i + ".txt");
                    if (fl.exists()) {
                    //[12]
                        BufferedReader br = new BufferedReader(new FileReader(fl));
                        String st = "";
                        ClientText.append("Commited msg for " + i + " are: \n");
                        while ((st = br.readLine()) != null) {

                            ClientText.append(st + "\n");
                        }
                    
                    } else {
                        ClientText.append("No Commited msg for " + i + " \n");
                    }
                }
                    } catch (Exception ex) {
                        //Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                        ex.printStackTrace();
                    }
            }
        });
        
        /*Pseudo-code implementation of participant
         It is waiting for VOTE_REQUEST from Coordinator,once it receives VOTE_REQUEST it will either 
        send a commit or abort on whose basis if it gets GLOBAL_COMMIT or GLOBAL_ABORT from coordinator it will dos 
        the same respectively.
        The code is also handling timeout of participant by sendin "NEED_DECISION"
        Maintaing log files for its states.
        Also has a timer implemented for 20secs        
        */
        try{
                //[15]            
                int countDown=20;
                //connecting to server socket [4][5]
    		csocket = new Socket(InetAddress.getByName("localhost"), serverPort);
                    dis = new DataInputStream(csocket.getInputStream());
                    dos= new DataOutputStream(csocket.getOutputStream());
                    //http msg format [2]
                        hdate=getServerTime();                    
                        //http msg format details
                        initialResponseLine="POST /path/ChatClient/ HTTP/1.1 200 OK";
                        contentType="text/plain";
                        String UserAgent="Java Application";
                        httpmsg=("\n"+ initialResponseLine+"\n" +
                                "User Agent:\t"+UserAgent+"\n"+  
			"Date:\t"+hdate+"\n"+
                        "Content-Type:\t"+contentType+ "\n"+
                        "Message:\t");
                        
                        //code handling responses from coordinator
                        //implementation of pseudo code from book [15]
                        while(true) {
			DataInputStream dis = new DataInputStream(csocket.getInputStream());
                        //reading msgs using dis
                        msg = dis.readUTF();
                        ClientText.append("\n"+msg);
                        /*checking if the msg has keyword client
                          if the pattern is matched it is extracting the particular client num
                           and storing it in cnum after replacing the phrase "From \t Client to extract teh number */
                        //pattern matching code to retrive client num [9][10][11]
                        if(msg.contains("Client"))
                        {
                            //compile has the regex that needs to be matched
                          
                        Pattern p1=Pattern.compile(".*From \t: Client [0-9]*");
                            m1 = p1.matcher(msg);
                            while(m1.find()){
                                //group function looks for pattern specified and if present return the string
                                  //ref [9]
                                 clientnum=m1.group();
                            }
                            //replacing the phrase "From \t: Client" with blank to extract client number
                             cnum=clientnum.replace("From \t: Client "," ");
                           //  System.out.println(cnum);
                        
                        }
                        
                          /*checking if the msg has keyword NEED_DECISIOM
                          if it is a need_decision the particulare client will dos local_abort as
                          this situation occurs when coordinator crashes or timesout without giving its decision
                        */
                       
                        if(msg.contains("NEED_DECISION"))
                                            {
                                                System.out.println("Local_abort");
                                                String m="LOCAL_ABORT";
                                                dos.writeUTF(m+"\n");
                                               msg="";
                                            }
                           /*checking if the msg has keyword VOTE_REQUEST
                          if it is a vote_request the particulare client will either abort or commit
                           its timer is started and it awaits for coordinator decision so that it will either GLOBAL_COMMIT
                            or GLOBAL_ABORT or NEED_DECISION
                        */
                         if(msg.contains("VOTE_REQUEST"))
                         {
                             //creating logger 
                             log1=Logger.getLogger(cnum);     //[13][14]
                             
                             //file handing code for log files
                             filehandler=new FileHandler("./"+cnum+".log");
                         
                             log1.addHandler(filehandler);     //[13][14]
                             
                             SimpleFormatter format=new SimpleFormatter();
                             filehandler.setFormatter(format);
                             //initially the log file for all participants is going to be INIT as it is in 
                             //INITIAL state
                            
                             log1.info("INIT");  //[13][14]
                             
                             //secondsLeft is used timer
                              secondsLeft=countDown;
                              //if read msg contains "Message:" then it will extract the particular msg send along 
                              //with VOTE_REQUEST from coordinator and stores in message
                             Pattern p=Pattern.compile(".*Message:[ \t][a-zA-Z0-9]*[ ]*[a-zA-Z0-9]*[ \n]"); //[9][10][11]
                            
                             m = p.matcher(msg);
                             while(m.find()){
                                // [9]
                                 message=m.group();
                                 
                             }
                           //  System.out.println("Message:"+message);
                            
                             String actualMsg=message.replace("Message:"+'\t'," ");
                            // System.out.println("Actualmsg:"+actualMsg);
                             
                              
                            //Participant timer starts where it waits for the decision from coordinator
                            //that either will be GLOBAL_ABORT or GLOBAL_COMMIT 
                             //[7][8] entire timer function is referenced from these references
                            final Timer timer=new Timer();
                            //scheduling the timer task
                            timer.scheduleAtFixedRate(new TimerTask() {
                                @Override
                                public void run() {
                                     secondsLeft--; 
                                     try {
                                        //if msg cotains GLOBAL_ABORT from coordinator then it will dos 
                                        //GLOBAL_ABORT and write ABORT in its dos file
                                        if(msg.contains("GLOBAL_ABORT"))
                                        {
                                          
                                             dos = new DataOutputStream(csocket.getOutputStream());
                                             //encoding the string in httpformat ie GLOBAL_ABORT and sending on dos
                                            dos.writeUTF(httpmsg+"GLOBAL_ABORT"+"\n");
                                               System.out.println("GLOBAL_ABORT");
                                               //writing abort to its log file
                                                   //[13][14]
                                               log1.info("ABORT");
                                               msg="";
                                               //cancelling timer of participant
                                               timer.cancel();
                                        }
                                         //if msg cotains GLOBAL_COMMIT from coordinator then it will dos 
                                        //GLOBAL_ABORT and write COMMIT in its dos file and 
                                        //writes the string for which VOTE_REQUEST has been done to the file for which
                                        //GLOBAL_COMMIT takes place
                                       else if(msg.contains("GLOBAL_COMMIT"))
                                        {
                                            
                                             dos = new DataOutputStream(csocket.getOutputStream());
                                             //writing global_commit to dos if receives global_commit from coordinator
                                            dos.writeUTF(httpmsg+"GLOBAL_COMMIT"+"\n");
                                               System.out.println("GLOBAL_COMMIT");
                                               String fmsg=actualMsg+"\n"+"GLOBAL_COMMIT";
                                               
                                               //     System.out.println("fmsg:-"+fmsg);
                                               //writing to file the string
                                               //[16] file writing code
                                                    FileWriter filewriter = new FileWriter("./backup"+cnum+".txt",true);  //[16]
                                                    BufferedWriter bw=new BufferedWriter(filewriter);
                                                    bw.append(fmsg+"\n");
                                                    bw.flush();
                                                    msg="";
                                                    //writing to log commit
                                                        //[13][14]
                                                    log1.info("COMMIT");
                                               timer.cancel();
                                        }
                                        /*This is in case coordinator fails or doesnt send any response to participant
                                         the participant writes to dos local_abort
                                        if global commit has been recieved to any participant then it will commit else will
                                        dos need decsision and then local abort as the decision and write abort to its log and dos that it
                                        is aborting*/
                                        //[15]
                                        if(secondsLeft<0)
                                        {
                                            //dos need_decsion in httpformat to everyone
                                            String timeout_msg="NEED_DECISION";
                                            dos = new DataOutputStream(csocket.getOutputStream());
                                            dos.writeUTF(httpmsg+timeout_msg+"\n");
                                            
                                       //    System.out.println("inside timeout");
                                        
                                            while(true){
                                              
                                            
                                               // System.out.println("in while");
                                               /*if msg contains global commit it will write the string in file
                                                the word and write commit in its log*/
                                               
                                                if(msg.contains("GLOBAL_COMMIT"))
                                                {
                                                   String fmsg1=actualMsg+"\n"+"GLOBAL_COMMIT";
                                                         //   System.out.println("fmsg:-"+fmsg1);
                                                         //file handling code for writing to file
                                                         //[16] file writing code
                                                            FileWriter filewriter = new FileWriter("./backup"+cnum+".txt",true); 
                                                            BufferedWriter bw=new BufferedWriter(filewriter);
                                                            bw.append(fmsg1+"\n");
                                                            bw.flush();
                                                            msg="";
                                                            //writing commit to log file
                                                                //[13][14]
                                                            log1.info("COMMIT");
                                                           // timer.cancel();
                                                            break;
                                                
                                                }
                                                   /*if msg contains is global_abort or local_abort it will write abort in its log
                                                and dos abort*/
                                                else if(msg.contains("GLOBAL_ABORT") || (msg.contains("LOCAL_ABORT")))
                                                {
                                                    dos=new DataOutputStream(csocket.getOutputStream());
                                                    //dos abort
                                                            dos.writeUTF(httpmsg+"ABORT"+"\n");
                                                            msg="";
                                                            //writing abort to log
                                                                //[13][14]
                                                            log1.info("ABORT");
                                                            break;
                                              
                                               
                                                }
                                                
                                              /*System.out.println("choice is :"+choice);
                                                switch(choice)
                                                {
                                                
                                                    case 1 :
                                                             
                                                    case 2 :
                                                    default: System.out.println("Connection timed out error");
                                                            timer.cancel();
                                                             break;
                                                            
                                                }*/
                                                
                                            
                                           //timer.cancel();                                   
                                            }
                                            timer.cancel();
                                            
                                           }    
                                        }
                                       
                                      catch (Exception ex) {
                                         ex.printStackTrace();
                                     }
                                     //printing timer value
                                      System.out.println(secondsLeft);
                           
                   
                        }
                                
                            },0,1000);//timer task declaration 
                         }
 
                         }
                            
 
        }catch(Exception e){
                System.out.println("Cannot connect to socket");
                e.printStackTrace();
           }
    }

   
 
        
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ClientLabel;
    public static javax.swing.JTextArea ClientText;
    private javax.swing.JButton abort_btn;
    private javax.swing.JButton commit_btn;
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