import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ServerProtocol {
	private ConcurrentHashMap<String,ArrayList<String>> DetailsTable;
	
	public ServerProtocol(ConcurrentHashMap<String,ArrayList<String>> Table) {
		DetailsTable = Table;
	}
	
	public String processRequest(String theInput) {
		String theOutput = null;
		System.out.println("Received message from client: " + theInput);
		String[] Parts = theInput.split(" "); // Splitting with spaces so it's necessary to have spaces between code,state and time in order 
		// to make the protocol work otherwise it will crush.
		if (Parts[0].startsWith("READ")) {
			if (DetailsTable.containsKey(Parts[1])){
				theOutput = "ROK " + Parts[1] + " " + DetailsTable.get(Parts[1]);
			}
			else {
				theOutput = "RERR";
			}
		}
		else if (Parts[0].startsWith("WRITE")){
			String State = Parts[2];
			String Time = Parts[3];
			ArrayList<String> Temp = new ArrayList<String>();
			Temp.add(State);
			Temp.add(Time);
			DetailsTable.put(Parts[1],Temp);
			theOutput = "WOK" ;
		}
		else if (Parts[0].startsWith("REMOVE")) {
			if (DetailsTable.containsKey(Parts[1])) {
				// 5 secs for deleting an element.
				DetailsTable.remove(Parts[1]);
				theOutput = "SUCCESSFULLY REMOVED";
			}
			else {
				theOutput = "THERE IS NO ELEMEMNT WITH THIS KEY";
			}
		}
		else {
			theOutput = "THERE IS NO COMMAND AS THIS";
		}
		return theOutput;
	}
}
