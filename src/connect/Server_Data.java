package connect;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import logic.KI;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

/**
 * <pre>
 * Klasse Server_Data:
 * 
 * - Liest Server-File * 
 * 
 * - Gibt Gegnerzug an Logik weiter
 * 
 * - Schreibt Agent-File
 * </pre>
 * 
 * @author Christian Samide
 * @param args
 */
public class Server_Data {

	/**
	 * Initialisierungen der Attribute
	 */	
	int enemyMove;
	int playerID;
	String fileNameFromServer;
	String fileName2Server;
	String transferDirectory;
	
	/**
	 *	Konstruktor Server_Data() mit Parameterliste
	 *
	 *	- Ermöglicht Server_Connector die Übergabe der Parameter, die für Lesen und Schreiben der Server- bzw. Agentfiles notwendig sind.
	 *
	 *  - readFile() löst Lese-Vorgang bei Objekterzeugung aus.
	 */
	public Server_Data(int playerID, String fileNameFromServer, String fileName2Server, String transferDirectory) throws InterruptedException, IOException
	{
		this.playerID = playerID;
		this.fileNameFromServer = fileNameFromServer;
		this.fileName2Server = fileName2Server;
		this.transferDirectory = transferDirectory;
		
		readFile();
		
	}
	
	/**
	 * Alternativer Konstruktor Server_Data() ohne Parameterliste
	 */
	public Server_Data(){}
	
	/**
	 * Methode readFile() steuert alle Abläufe zum Lesen des Server-Files
	 */
	public void readFile() throws InterruptedException, IOException
	{
		
		/**
		 * Initialisierung der Variable read_success.
		 */
		boolean read_success = false;	
		
		/**
		 * Schleife steuert Lese-Vorgang
		 * solange read_success = false
		 * Abbruchbedingung: read_success = true
		 */
		do{
		
			Document doc = null;
			File f = new File(transferDirectory+"/"+fileNameFromServer+"");
			System.out.println("Prüfe ob File da ist...");

				if (f.exists())
			{
				System.out.println("File ist da. Beginne lesen...");

					try { 
			        	
			            // Das Dokument erstellen 
			            SAXBuilder builder = new SAXBuilder(); 
			            doc = builder.build(f);
			            
			            XMLOutputter fmt = new XMLOutputter();            
			            Element element = doc.getRootElement();            
			                  
			            // Freigabe lesen
			            Element freigabe = element.getChild("freigabe"); 
			            System.out.println("Freigabe: " + freigabe.getValue());
			            
			            // Satzstatus lesen
			            Element satzstatus = element.getChild("satzstatus"); 
			            System.out.println("Satzstatus: " + satzstatus.getValue());
			            
			            // Gegnerzug lesen
			            Element gegnerzug = element.getChild("gegnerzug"); 
			            System.out.println("Gegnerzug: " + gegnerzug.getValue());
			               
			            enemyMove = Integer.parseInt(gegnerzug.getValue());
			            System.out.println("Ergebnis ist : "+enemyMove);
			            read_success=true;
			            f.delete();
			                
			            // Sieger lesen
			            Element sieger = element.getChild("gegnerzug"); 
			            System.out.println("Sieger: " + sieger.getValue());       
			            	
			   
			          
			        } catch (JDOMException e) { 
			            e.printStackTrace(); 
			        } catch (IOException e) { 
			            e.printStackTrace(); 
			        }
					
			}//if
			else {
			
					read_success=false;
					Thread.sleep(6000);
					System.out.println("300 ms warten...");
			}//else
		
		}while (read_success==false);
		
		KI give = new KI();
		give.createField();
		
		give.setEnemyMove(enemyMove, playerID);
		
		
	}// do
	
	/**
	 * Übernimmt die von logic berechnete Spalte (den Spielzug) und schreibt das Agentfile.
	 */
	public void setStoneWriteFile(int column) throws IOException, InterruptedException
	{
		System.out.println("WriteFile aufgerufen!");
		System.out.println(fileName2Server);
		int move = column;
		FileWriter fw = new FileWriter(transferDirectory+fileName2Server);
	    BufferedWriter bw = new BufferedWriter(fw);	    
	    bw.write(String.valueOf(move));
	    bw.close();
	    readFile();
	}
	
	
}
