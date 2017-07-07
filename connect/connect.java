//Example Java Program - Oracle Database Connectivity
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class connect {

 public static final String DBURL = "jdbc:oracle:thin:@pravinyo:1521:XE";
 public static final String DBUSER = "system";
 public static final String DBPASS = "root";
 

 public static void main(String[] args) throws SQLException {
     
     // Load Oracle JDBC Driver
     DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
     
     
     System.out.println("Done..");
	 
	 System.out.println("Welcome user");
	 
		final int CURRENT_STATION_ID=7;
		ResultSet rs=null;
		int customer_id=1;
		float fare;
		
		while(true){
			Scanner scan = new Scanner(System.in);
			String[] options = {
						"Get Bike",
						"Search Nearest Station",
						"Recharge Card",
						"Last Tour History"
					};
			
			System.out.println("Select Options");
			
			for(int i=0;i<options.length;i++){
				System.out.println("Option #"+(i+1)+" "+options[i]);
			}
			
			int option=1;
			

				option = scan.nextInt();
			
			
			Connection con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

		     Statement statement = con.createStatement();
		     
			if(option == 1){
				System.out.println("Select Destination..\n -99 = > Cancel");
				
				rs = statement.executeQuery("SELECT * FROM station");
				
				while (rs.next()) {
			         System.out.println("Name: "+rs.getString(2)+"\nAddress: "+rs.getString(3));
			     }
				
				String dests = scan.next();
				int dest= Integer.parseInt(dests);
				if( dest== -99) {
					continue;
				}
				rs = statement.executeQuery("SELECT distance FROM station WHERE station_id ="+dest);
				
				if(rs.next()){
					fare =Float.parseFloat(rs.getString(1))*1.25f*5f;
				}else{
					fare = 30;
				}
				
				System.out.println("Your total fare is : Rs."+fare);
				System.out.println("Select Option");
				System.out.println("1. Pay \n2. Cancel");
				
				int choice =scan.nextInt();
				
				if(choice==1){
					int response = payPortal(fare,1, CURRENT_STATION_ID, dest,CURRENT_STATION_ID);
					if(response == 200){
						System.out.println("Take your token\n\n\n\n\n");
					}else{
						System.out.println("Unable to process Error code :"+response);
					}
				}else{
					continue;
				}
			}else if(option == 2){
				
				rs = statement.executeQuery("SELECT name,location,distance FROM station WHERE station_id ="+CURRENT_STATION_ID);
				if(rs.next()){
					System.out.println("Current Station : "+rs.getString(1)+","+rs.getString(2));
				}
				float DISTANCE=rs.getFloat(3);
				
				
				rs = statement.executeQuery("SELECT * FROM station");
				
				while(rs.next()){
					if(rs.getFloat(5) - DISTANCE <= 1.5f){
						System.out.println("Station : "+rs.getString(2)+","+rs.getString(3));
					}
					System.out.println("\n\n\n");
				}
			}else if(option == 3){
				System.out.println("payment gateway\n\n\n\n");
				startPaymentGateway(customer_id);
				
			}else if(option ==4){
				showLastTour(customer_id);
			}else{
				System.out.println("Unable to process Error code :"+5);
			}
			//scan.close();
			 rs.close();
		     statement.close();
		     con.close();
		}
 }

private static void startPaymentGateway(int customer_id) {
	// TODO Auto-generated method stub
	System.out.println("Enter Amount to recharge....");
	Scanner scan = new Scanner(System.in);
	float payment = scan.nextFloat();
	System.out.println("connecting to server....");
	System.out.println("resource allocating....");
	try{
		Connection conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
	    Statement statement = conn.createStatement();
	    ResultSet rs=statement.executeQuery("select count(*) from transsaction ");
		int last_index=0;
		if(rs.next()){
			last_index=rs.getInt(1);
		}
	    rs= statement.executeQuery("insert into transsaction values("+(last_index+1)+","+customer_id+",0,0,0,0,"+payment
	    		+")");
	    
	    System.out.println("Payent success....");
	    scan.close();
	    conn.close();
	    statement.close();
	}catch(Exception e){
		System.out.println(e.getMessage());
		
	}
}

private static void showLastTour(int C_id){
	HashMap<Integer,String> station = new HashMap<Integer,String>();
	System.out.println("connecting to server....");
	System.out.println("resource allocating....");
	try{
		Connection conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
	    Statement statement = conn.createStatement();
	    ResultSet ss=statement.executeQuery("select station_id,name,location from station");
	    while(ss.next()){
	    	
	    	station.put(ss.getInt(1),ss.getString(2)+","+ss.getString(3));
	  
	    }
	    ResultSet rs=statement.executeQuery("select * from tour where  C_ID = "+C_id);
		
	    System.out.println("data fetched success....");
	    
	    while(rs.next()){
	    	System.out.println("Tour ID: "+rs.getInt(1)+", Source : "+station.get(rs.getInt(2))+
	    			", Destination : "+station.get(rs.getInt(3)));
	    }
	    
	    conn.close();
	    statement.close();
	    
	}catch(Exception e){
		System.out.println(e.getMessage());
		
	}
}
private static int payPortal(float fare,int customer_id,int source,int destination,int station_id) {
	// TODO Auto-generated method stub
	System.out.println("setup process started..");
	Statement statement=null;
	Connection con=null;
	try{
		 con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
	    statement = con.createStatement();
	}catch(Exception e){
		
	}
    System.out.println("connecting to server started..");
    try {
		ResultSet checkAmount = statement.executeQuery("select balance from customer where C_ID="+customer_id);
		if(checkAmount.next()){
			if(!(checkAmount.getFloat(1)>fare)){
				startPaymentGateway(customer_id);
			}
		}
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try {
		ResultSet rs=statement.executeQuery("select count(*) from transsaction ");
		int last_index=0;
		if(rs.next()){
			last_index=rs.getInt(1);
		}
		rs=statement.executeQuery("insert into transsaction values ("+(last_index+1)+","+customer_id+","+
		25+","+station_id+","+source+","+destination+","+fare+")");
		System.out.println("Payment Success ...");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
		return 457;
	}
	try {
		con.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return 200;
}
}