package cellsociety_team05;

public class CellSociety {
	//main class
		//0 is empty, 1 is red, 2 is blue
		private int type;
		private int rowNumber;
		private int columnNumber;
		
		public CellSociety(int type, int rowNumber, int columnNumber) {
			this.setType(type);
		}
		
		public CellSociety(int rowNumber, int columnNumber) {
			this.setType(0);
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}


}
