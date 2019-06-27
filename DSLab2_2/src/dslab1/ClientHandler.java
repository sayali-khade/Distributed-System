 
/*
    Name-Sayali Khade
    Id-1001518264

*/
package dslab1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TimeZone;
import org.joda.time.DateTime;

/**
 *
 * @author sayal
 */

/* Multi-Threaded Chat Application  [4]    */
/*Displays and handles server side msgs*/
 class ClientHandler implements Runnable 
{
	Scanner scn = new Scanner(System.in);
	private String clientname;
	DataInputStream dis;
	DataOutputStream dos;
	Socket csocket;
        int clientnum;
	boolean isclientloggedin;
        
	public ClientHandler(Socket csocket, String clientname,
			DataInputStream dis, DataOutputStream dos,int clientnum) {
		this.dis = dis;
		this.dos = dos;
		this.clientname = clientname;
		this.csocket = csocket;
		this.isclientloggedin=true;   
                this.clientnum=clientnum;
	}

	@Override
	public void run() {

		String rmsg;
                String fmsg,message;
		try
		{
		while (true) 
		{
		
                    // reading string msg
                DataInputStream dis = new DataInputStream(csocket.getInputStream());
                rmsg = dis.readUTF();
                                    
                    //variable decalartion for HTTP request [2]
                  
         	    System.out.println(rmsg);   
                    
                    //Displaying the Request msg on server 
                    ChatServer.ServerText.append("\nFrom \t: Client "+clientnum+"\n"+rmsg); 
                    
                  
                    
                    //Client Logout block 
        	     if(rmsg.toLowerCase().equals("logout"))
                     {
			ChatServer.ServerText.append("Client has logged off from chat");
                        DataOutputStream dos=new DataOutputStream(csocket.getOutputStream());
                        //broadcasting to all clients that a particular client has logged off from chat room.
                        for(ClientHandler ch : ChatServer.clientlist)
                            
                            ch.dos.writeUTF("Client "+clientnum+" has logged off from chat");
                        
			this.isclientloggedin=false;
			this.csocket.close();
                        ChatServer.clientlist.remove(clientnum);
			//break;
                     } 
                     
                     //posting msgs on clients via server using http post method
                     //Variable declarations for HTTP Response [2]
                     //function for converting date into http [6]
				
				  
				// Divide the string in 2 parts ie msg and reciepent
				StringTokenizer st = new StringTokenizer(rmsg, "#");
				message = st.nextToken();
                               
                                //seconds difference between time for clients msgs [3]
                              
                                //posting client msgs
                                DataOutputStream dos;
				for (ClientHandler ch : ChatServer.clientlist) {
				dos=new DataOutputStream(csocket.getOutputStream());
					ch.dos.writeUTF("\nFrom \t: Client "+clientnum+"\n"+message+"\n");
                                       
				}
				fmsg=clientname +" : "+ message + "\n\n";
                                
                                //writing responses to file ;mainting backup of server file[1]
				Files.write(Paths.get("./chat.txt"), fmsg.getBytes(), StandardOpenOption.APPEND);         
		}
		}catch (Exception e) {

			e.printStackTrace();
		}
                try
		{
			// closing resources
			this.dis.close();
			this.dos.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
        
        /* INPUT - system date
       This function takes input as system date while sending message and converts it into HTTP format 
       date and time.
        OUTPUT - converts the system date into proper HTTP format
     [6]*/
        String getServerTime() {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return dateFormat.format(calendar.getTime());
}
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