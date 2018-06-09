package game;

public class ModelFactory {
	private Life life;
	
	ModelFactory(Life life){
		this.life = life;
	}
	
	public int[][] setModel_first(){
		
		//life.initGrid();
		int[][] grid = life.getGrid();
		int midRow = grid.length / 2;
		int midCol = grid[0].length / 2;
		for(int i = -7; i < 8; i++) {
			grid[midRow][midCol - i] = 1;
		}
		
		grid[midRow][midCol - 2] = 0;
		grid[midRow][midCol + 2] = 0;
		grid[midRow][midCol + 3] = 0;
		
		return grid;
	}
}
