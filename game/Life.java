package game;
 
public class Life {  
    private int maxRow;  //��������
    private int maxCol;  //��������
  
    private int[][] grid;  //����
  
    
    //���캯������ʼ������
    public Life(int maxRow, int maxCol) {  
        this.maxRow = maxRow;  
        this.maxCol = maxCol;  
        grid = new int[maxRow + 2][maxCol + 2];  
        for (int row = 0; row <= maxRow + 1; row++)  
            for (int col = 0; col <= maxCol + 1; col++)  
                grid[row][col] = 0;  
    }  
    
    
    
    //��ȡ����
    public int[][] getGrid() {  
        return grid;  
    }  
  
    
    //��������
    public void setGrid(int[][] grid) {  
        this.grid = grid;  
    }  
  
    
    //����������cell��״̬
    public void update() {  
        int[][] newGrid = new int[maxRow + 2][maxCol + 2];  
  
        for (int row = 1; row <= maxRow; row++)  
            for (int col = 1; col <= maxCol; col++)  
                switch (getNeighborCount(row, col)) {  
                    case 2:  
                        newGrid[row][col] = grid[row][col]; // Cell״̬����  
                        break;  
                    case 3:  
                        newGrid[row][col] = 1; // Cell���  
                        break;  
                    default:  
                        newGrid[row][col] = 0; // Cell����.  
                }  
  
        for (int row = 1; row <= maxRow; row++)  
            for (int col = 1; col <= maxCol; col++)  
                grid[row][col] = newGrid[row][col];  
    }  
  
    
    //��ȡ3x3�������cell�������Ը�cellΪ���ġ�
    private int getNeighborCount(int row, int col) {  
        
    	int count = 0;  
  
        for (int i = row - 1; i <= row + 1; i++)  
            for (int j = col - 1; j <= col + 1; j++)  
                count += grid[i][j]; //�������cell�������Ӵ������  
        count -= grid[row][col]; // ���cellû�����ڵĴ��cell�������� 
  
        return count;  
    }  
}  