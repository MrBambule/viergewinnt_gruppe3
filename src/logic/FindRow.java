package logic;

	//import controller.Main;

public class FindRow {
	
	private int [][] tmp_field;
	private Field fieldclass = null;
	
	/**
	 * Erkennt die Zeile in welcher ein Stein landen muss wenn die Spalte bekannt ist.
	 * @param column
	 * @param currentPlayer
	 */
	
	public FindRow(Field fieldinstance){
		fieldclass = fieldinstance;
	}
	
	public int getRow(int column){
		
		//MainGui maingui = new MainGui();
		tmp_field = fieldclass.getField();
		int error;
		
		
		for (int rowCounter=0; rowCounter < 6; rowCounter++){
			
			int currentRow = 5 - rowCounter;
			System.out.println(tmp_field[1][1] + "tmp_field");
			
			//Operation abfangen wenn Spalte komplett gefuellt
			if (rowCounter == 5 && tmp_field[currentRow][column] != 0) {
				System.out.println("Die Spalte ist bereits voll!");
				//rowCounter = 7;
				return error = -999;
			}
			
			// Jede Reihe betrachten ob bereits ein Stein enthalten ist.
			// Ist ein Stein enthalten wird durch die Schleife in die naechste Reihe gesprungen
			if (tmp_field[currentRow][column] == 0) {
				
				System.out.println(currentRow + " returned in column " + column);
				return currentRow;
			};
		};
		
		return error = -999;
	
	}
}
