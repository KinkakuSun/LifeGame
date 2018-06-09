package game;
 
public class Life {  
    private int maxRow;  //网格行数
    private int maxCol;  //网格列数
  
    private int[][] grid;  //网格
  
    
    //构造函数，初始化网格
    public Life(int maxRow, int maxCol) {  
        this.maxRow = maxRow;  
        this.maxCol = maxCol;  
        grid = new int[maxRow + 2][maxCol + 2];  
        for (int row = 0; row <= maxRow + 1; row++)  
            for (int col = 0; col <= maxCol + 1; col++)  
                grid[row][col] = 0;  
    }  
    
    
    
    //获取网格
    public int[][] getGrid() {  
        return grid;  
    }  
  
    
    //设置网格
    public void setGrid(int[][] grid) {  
        this.grid = grid;  
    }  
  
    
    //更新网格中cell的状态
    public void update() {  
        int[][] newGrid = new int[maxRow + 2][maxCol + 2];  
  
        for (int row = 1; row <= maxRow; row++)  
            for (int col = 1; col <= maxCol; col++)  
                switch (getNeighborCount(row, col)) {  
                    case 2:  
                        newGrid[row][col] = grid[row][col]; // Cell状态不变  
                        break;  
                    case 3:  
                        newGrid[row][col] = 1; // Cell存活  
                        break;  
                    default:  
                        newGrid[row][col] = 0; // Cell死亡.  
                }  
  
        for (int row = 1; row <= maxRow; row++)  
            for (int col = 1; col <= maxCol; col++)  
                grid[row][col] = newGrid[row][col];  
    }  
  
    
    //获取3x3网格存活的cell数量，以该cell为中心。
    private int getNeighborCount(int row, int col) {  
        
    	int count = 0;  
  
        for (int i = row - 1; i <= row + 1; i++)  
            for (int j = col - 1; j <= col + 1; j++)  
                count += grid[i][j]; //如果相邻cell存活，则增加存活数量  
        count -= grid[row][col]; // 如果cell没有相邻的存活cell，则死亡 
  
        return count;  
    }  
}  