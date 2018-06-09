package test;

import game.Life;

public class LifeTest {

	/**
	 * 3x3Õ¯∏Ò≤‚ ‘
	 * @param args
	 */
	public static void main(String[] args) {
		int maxRow = 3;
		int maxCol = 3;
		Life life = new Life(maxRow, maxCol);
		
		/*int[][] grid = {{0,0,0,0,0},
						{0,0,0,0,0},
						{0,0,0,0,0},
						{0,0,0,0,0},
						{0,0,0,0,0}};*/
		
		int[][] grid = life.getGrid();
		System.out.println(grid.length+"---"+grid[0].length);
		grid[2][1] = 1;
		grid[2][2] = 1;
		grid[2][3] = 1;
		
		for(int i=0;i<5;i++) {
			
			for (int row = 0; row < maxRow+2; row++) {
				 for (int col = 0; col < maxCol+2; col++) {
	            	 System.out.print(grid[row][col]);
				 }
				 System.out.println();
			}
	        System.out.println("==================================");
			life.update();	
			grid = life.getGrid();
		}
	}

}
