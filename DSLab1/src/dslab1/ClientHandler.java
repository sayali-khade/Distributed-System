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

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

/**
 *
 * @author sayal
 */

/* Multi-Threaded Chat Application  [4]    */
 class ClientHandler implements Runnable 
{
	Scanner scn = new Scanner(System.in);
	private String clientname;
	final DataInputStream dis;
	final DataOutputStream dos;
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
                DateTime initialTime=new DateTime();
                 
		try
		{
		
		while (true) 
		{
		
                    // reading string msg 
                    rmsg = dis.readUTF();
                    
                    //Polling the server with get
                    //variable decalartion for HTTP request [2]
                    String initialRequestLine="GET/path/ChatServer HTTP/1.1";
                    String Host="localhost:8080";
                    DateTime presentTime=new DateTime();
                    String userAgent="Java Application";
         	    System.out.println(rmsg);   
                    
                    //Displaying the Request msg on server 
                    ChatServer.ServerText.append("\n"+initialRequestLine+"\n"+"Host:\t"+Host+"\n"+"From:\tClient"+clientnum+"\n"+"User-Agent:\t"+userAgent+"\n"+"Message:\t" + rmsg+"\n"); 
                    
                    //Client Logout block 
        	     if(rmsg.toLowerCase().equals("logout"))
                     {
			ChatServer.ServerText.append("Client has logged off from chat");
                        //broadcasting to all clients that a particular client has logged off from chat room.
                        for(ClientHandler ch : ChatServer.clientlist)
                            ch.dos.writeUTF("Client "+clientnum+" has logged off from chat");
                        
			this.isclientloggedin=false;
			this.csocket.close();
			break;
                     } 
                     
                     //posting msgs on clients via server using http post method
                     //Variable declarations for HTTP Response [2]
                     //function for converting date into http [6]
                    String hdate=getServerTime();                    
                    String initialResponseLine="POST /path/ChatClient/ HTTP/1.1 200 OK";
                    String contentType="text/plain";
				
				  
				// Divide the string in 2 parts ie msg and reciepent
				StringTokenizer st = new StringTokenizer(rmsg, "#");
				message = st.nextToken();
                                
                                //seconds difference between time for clients msgs [3]
                                Seconds diff=Seconds.secondsBetween(presentTime,initialTime);
                               
                                //posting client msgs
				for (ClientHandler ch : ChatServer.clientlist) {
				
					ch.dos.writeUTF("\n"+ initialResponseLine+"\n" +
							"Date:\t"+hdate+"\n"+ 
							"Content-Type:\t"+contentType+ "\n"+
							"Content-Length\t"+message.length()+this.clientname+
							" : \t"+message+"\t"+ diff+"\n");
                                        initialTime=presentTime;
				}
				fmsg=clientname +" : "+ message + "\n\n";
                                
                                //writing responses to file [1]
				Files.write(Paths.get("./chat.txt"), fmsg.getBytes(), StandardOpenOption.APPEND);
                               
		}
		
		}catch (IOException e) {

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
        
        //Converting date into http format [6]
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

1. “How to Append Text to an Existing File in Java?” Recalll, recalll.co/ask/v/topic/How-to-append-text-to-an-existing-file-in-Java/59f8df3a1126f45d1f8b5ebc.

2. Marshall, James. “HTTP Made Really Easy.” HTTP Made Really Easy, 10 Dec. 2012, www.jmarshall.com/easy/http/#http1.1s3.

3. Mkyong. “How to Calculate Date and Time Difference in Java.” How to Calculate Date and Time Difference in Java, 25 Jan. 2013, www.mkyong.com/java/how-to-calculate-date-time-difference-in-java/.

4. Mahrsee, Rishabh. “Multi-Threaded Chat Application in Java | Set 1 (Server Side Programming).” GeeksforGeeks, 17 June 2017, www.geeksforgeeks.org/multi-threaded-chat-application-set-1/.

5. Mahrsee, Rishabh. “Multi-Threaded Chat Application in Java | Set 2 (Client Side Programming).” GeeksforGeeks, 17 June 2017, www.geeksforgeeks.org/multi-threaded-chat-application-set-2/.

6. R, Hannes. “Getting Date in HTTP Format in Java.” Stack Overflow, 27 Jan. 2015, 8:13, stackoverflow.com/questions/7707555/getting-date-in-http-format-in-java.
*/