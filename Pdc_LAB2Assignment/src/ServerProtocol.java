import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ServerProtocol {
	private ConcurrentHashMap<String,ArrayList<String>> DetailsTable;
	
	public ServerProtocol(ConcurrentHashMap<String,ArrayList<String>> Table) {
		DetailsTable = Table;
	}
	
	public String processRequest(String theInput) {
		String theOutput = null;
		String[] Parts = theInput.split(" "); // Splitting with spaces so it's necessary to have spaces between code,state and time in order 
		// to make the protocol work otherwise it will crush.
		if (Parts[0].startsWith("READ")) { // if the request starts with read and code exists return ROK the code and the flight's infos.
			if (DetailsTable.containsKey(Parts[1])){
				theOutput = "ROK " + Parts[1] + " " + DetailsTable.get(Parts[1]);
			}
			else {
				theOutput = "RERR"; //otherwise it prints RERR
			}
		}
		else if (Parts[0].startsWith("WRITE")){//if the request starts with WRITE and the needed infos it writes it into the map or if it exists already overwrites it.
			String State = Parts[2];
			String Time = Parts[3];
			ArrayList<String> Temp = new ArrayList<String>();
			Temp.add(State);
			Temp.add(Time);
			DetailsTable.put(Parts[1],Temp);
			theOutput = "WOK" ;
		}
		else if (Parts[0].startsWith("REMOVE")) { //if the request starts with REMOVE and an already existed code it removes it.
			if (DetailsTable.containsKey(Parts[1])) {
				DetailsTable.remove(Parts[1]);
				theOutput = "SUCCESSFULLY REMOVED";
			}
			else { //if the request tries to remove something which is not in the HashMap print no flight with this code to remove.
				theOutput = "NO FLIGHT WITH THIS CODE TO REMOVE";
			}
		}
		else {//if the request starts with something that server doesn't know print UNKNOWN COMMAND.
			theOutput = "UNKNOWN COMMAND";
		}
		return theOutput;
	}
}
