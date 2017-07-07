import java.util.Scanner;

public class modified {
	
	public static void main(String[] args) {
		final String[][] destination = {
				{"Kurla Station","2.5"},
				{"Basant Park","1.5"},
				{"Chembur Naka","1.6"},
				{"Chembur Camp","1.8"},
				{"Swastik","0.8"},
				{"Bhakti bhavan","1.1"},
				{"Navjevan Society","1.7"},
				{"Chembur Colony","0.0"}
		};
		final int CURRENT_STATION_ID=7;
		final String CURRENT_STATION_NAME=destination[CURRENT_STATION_ID][0];
		while(true){
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
				System.out.println("Select Destination..\n -99 = > Cancel");
				
				for(int i=0;i<destination.length;i++){
					System.out.println("destination #"+(i+1)+" "+destination[i][0]);
				}
				
				String dests = scan.next();
				int dest= Integer.parseInt(dests);
				if( dest== -99) {
					continue;
				}
				
				float fare =Float.parseFloat(destination[dest][1])*1.25f*5f;
				
				System.out.println("Your total fare is : Rs."+fare);
				System.out.println("Select Option");
				System.out.println("1. Pay \n2. Cancel");
				
				int choice =scan.nextInt();
				
				if(choice==1){
					int response = 200;
					if(response == 200){
						System.out.println("Take your token\n\n\n\n\n");
					}else{
						System.out.println("Unable to process Error code :"+response);
					}
				}else{
					continue;
				}
			}else if(option == 2){
				System.out.println("Current Station : "+CURRENT_STATION_NAME);
				
				for(int i=0;i<destination.length;i++){
					if((Float.parseFloat(destination[i][1])
							-Float.parseFloat(destination[CURRENT_STATION_ID][1])) <= 1.5f){
						System.out.println("Station : "+destination[i][0]);
					}
				}
			}else if(option == 3){
				System.out.println("payment gateway\n\n\n\n");
			}else{
				System.out.println("Unable to process Error code :"+5);
			}
			scan.close();
		}
	}
}
