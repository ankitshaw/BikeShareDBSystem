import java.util.Scanner;

public class Kiosk {

	private static String[][] destination = {
			{"Kurla Station","2.5"},
			{"Basant Park","1.5"},
			{"Chembur Naka","1.6"},
			{"Chembur Camp","1.8"},
			{"Swastik","0.8"},
			{"Bhakti bhavan","1.1"},
			{"Navjevan Society","1.7"},
			{"Chembur Colony","0.0"}
	};
	private static final int CURRENT_STATION_ID=7;
	private static final String CURRENT_STATION_NAME=destination[CURRENT_STATION_ID][0];
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		start();
	}


	private static void start() {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		String[] options = {
					"Get Bike",
					"Search Nearest Station",
					"Recharge Card"
				};
		System.out.println("Select Options");
		for(int i=0;i<options.length;i++){
			System.out.println("Option #"+(i+1)+" "+options[i]);
		}
		
		int option=1;
		option = scan.nextInt();
		
		
		if(option == 1){
			getBike();
		}else if(option == 2){
			searchNearestStation();
		}else if(option == 3){
			rechargeCard();
		}else{
			error(5);
		}
		
		start();
	}


	private static void rechargeCard() {
		// TODO Auto-generated method stub
		System.out.println("payment gateway\n\n\n\n");
	}


	private static void searchNearestStation() {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		System.out.println("Current Station : "+CURRENT_STATION_NAME);
		
		for(int i=0;i<destination.length;i++){
			if((Float.parseFloat(destination[i][1])
					-Float.parseFloat(destination[CURRENT_STATION_ID][1])) <= 1.5f){
				System.out.println("Station : "+destination[i][0]);
			}
		}
		scan.nextLine();
		
	}


	private static void getBike() {
		Scanner scan = new Scanner(System.in);
		// TODO Auto-generated method stub
		System.out.println("Select Destination..\n -99 = > Cancel");
		
		for(int i=0;i<destination.length;i++){
			System.out.println("destination #"+(i+1)+" "+destination[i][0]);
		}
		
		String dests = scan.next();
		int dest= Integer.parseInt(dests);
		if( dest== -99) {
			return;
		}
		
		float fare=	calculateFare(dest-1);
		
		System.out.println("Your total fare is : Rs."+fare);
		System.out.println("Select Option");
		System.out.println("1. Pay \n2. Cancel");
		
		int choice =scan.nextInt();
		
		if(choice==1){
			int response = paymentGateway(fare);
			if(response == 200){
				generateToken();
				start();
			}else{
				error(response);
			}
		}else{
			PreviousScreen();
		}
		scan.close();
	}


	private static void PreviousScreen() {
		// TODO Auto-generated method stub
		getBike();
		
	}


	private static void error(int response) {
		// TODO Auto-generated method stub
		System.out.println("Unable to process Error code :"+response);
	}


	private static void generateToken() {
		// TODO Auto-generated method stub
		System.out.println("Take your token\n\n\n\n\n");
	}


	static int paymentGateway(float fare) {
		return 200;
		// TODO Auto-generated method stub
		
	}

	private static float calculateFare(int dest) {
		float fare =Float.parseFloat(destination[dest][1])*1.25f*5f;
		return fare;
		// TODO Auto-generated method stub
		
	}

}
