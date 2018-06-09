package game;

import javax.swing.*;

import test.GridTest;

import java.awt.*;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.awt.event.WindowAdapter;  
import java.awt.event.WindowEvent;  
import java.util.Arrays;  
  
  
public class GameFrame extends JFrame implements ActionListener {  
    private static GameFrame frame;  
    private JPanel backPanel, centerPanel, bottomPanel;  //三个画布，将中心画布和底 画布放在背景画布之上
    private JButton btnOK, btnStart, btnStop, btnExit;  //操作按钮
    private JButton[][] btnBlock;  //网格按钮
    private JLabel lblRow, lblMod;  //文本框
    private JComboBox rowList, colList;  //下拉菜单
    private boolean[][] isSelected;  //是否被选择
    private int maxRow, maxCol;  //最大行和列数
    private Life life;  //生命类
    private boolean isRunning;  //是否运行
    private Thread thread;  
    private boolean isDead;  //cell状态
    static final int init_maxRow = 60; //默认行数
    static final int init_maxCol = 60; //默认列数
    ModelFactory md;
    private int chooseModel;
    private boolean isSelect = true;
    
    
    //
    public static void main(String arg[]) {  
        frame = new GameFrame("LifeGame");  
    }  
  
    public int getMaxRow() {  
        return maxRow;  
    }  
  
    public void setMaxRow(int maxRow) {  
        this.maxRow = maxRow;  
    }  
  
    public int getMaxCol() {  
        return maxCol;  
    }  
  
    public void setMaxCol(int maxCol) {  
        this.maxCol = maxCol;  
    }  
    
    public void select() {
    	chooseModel = colList.getSelectedIndex();
    	md = new ModelFactory(life);
    	 switch(chooseModel) {
     	case 1:
     		int[][] gridTest = md.setModel_first();
         	life.setGrid(gridTest);
     		break;
     	case 2:
     		break;
     	case 3:
     		break;
     	default:
     		break;
     }
     
    }
  
    public void initGUI() {  
        /** 
         * 设计地图生成器界面 * 
         */  
        if (maxRow == 0) {  
            maxRow = init_maxRow;  
        }  
  
        if (maxCol == 0) {  
            maxCol = init_maxCol;  
        }  
        //用获取到的网格数来初始化网格
        life = new Life(maxRow, maxCol);
 
        backPanel = new JPanel(new BorderLayout());  
        centerPanel = new JPanel(new GridLayout(maxRow, maxCol));  
        bottomPanel = new JPanel();  
        
        //下拉选项初始化，最小为3，最大为60
        rowList = new JComboBox();  
        for (int i = 3; i <= 100; i++) {  
            rowList.addItem(String.valueOf(i));  
        }  
        colList = new JComboBox();  
        for (int i = 0; i <= 3; i++) {
        	if(i == 0) {
        		 colList.addItem("默认模式"); 
        		 continue;
        	}
            colList.addItem("模式" + String.valueOf(i));  
        }  
        //设置开始的默认选项
        rowList.setSelectedIndex(maxRow - 3);  
        
        btnOK = new JButton("确定");   
        btnBlock = new JButton[maxRow][maxCol];  
        btnStart = new JButton("开始");  
        btnStop = new JButton("停止");  
        btnExit = new JButton("退出");  
        isSelected = new boolean[maxRow][maxCol];  
        lblRow = new JLabel("设置行数：");  
        lblMod = new JLabel("演示模型：");  
        this.setContentPane(backPanel);  
  
        backPanel.add(centerPanel, "Center");  
        backPanel.add(bottomPanel, "South");  
  
        for (int i = 0; i < maxRow; i++) {  
            for (int j = 0; j < maxCol; j++) {  
                btnBlock[i][j] = new JButton("");  
                btnBlock[i][j].setBackground(Color.BLACK);  
                centerPanel.add(btnBlock[i][j]);  
            }  
        }  
  
        bottomPanel.add(lblRow);  
        bottomPanel.add(rowList);
        bottomPanel.add(btnOK);
        bottomPanel.add(lblMod);
        bottomPanel.add(colList);            
        bottomPanel.add(btnStart);  
        bottomPanel.add(btnStop);  
        bottomPanel.add(btnExit);  
  
        // 设置窗口  
        this.setSize(600, 600);  
        this.setResizable(true);  
        this.setLocationRelativeTo(null); // 让窗口在屏幕居中  
  
        // 将窗口设置为可见的  
        this.setVisible(true);  
  
        // 注册监听器  
        this.addWindowListener(new WindowAdapter() {  
            public void windowClosed(WindowEvent e) {  
                System.exit(0);  
            }  
        });  
        
        //增加监听
        btnOK.addActionListener(this);  
        btnStart.addActionListener(this);  
        btnStop.addActionListener(this);  
        btnExit.addActionListener(this);  
        for (int i = 0; i < maxRow; i++) { //对每个网格中的button增加监听 
            for (int j = 0; j < maxCol; j++) {  
                btnBlock[i][j].addActionListener(this);  
            }  
        }  
    }  
  
    
    //构造函数初始化界面
    public GameFrame(String name) {  
        super(name);  
        initGUI();  
    }  
  
    
    //按钮触发事件
    public void actionPerformed(ActionEvent e) {  
        if (e.getSource() == btnOK) {//确定按钮 设置网格的行和列
            frame.setMaxRow(rowList.getSelectedIndex() + 3);  
            frame.setMaxCol(rowList.getSelectedIndex() + 3);
            initGUI();  
            life = new Life(getMaxRow(), getMaxCol()); 
        } else if (e.getSource() == btnStart) {  //开始按钮 开启线程进行迭代
            isRunning = true;  
            thread = new Thread(new Runnable() {  
                @Override  
                public void run() {  
                    while (isRunning) { 
                    	if(isSelect) {
                    		select();
                    		isSelect = false;
                    	}
                    	
                        makeNextGeneration();  
                        boolean isSame = true;  
                        try {  
                            Thread.sleep(100);  
                        } catch (InterruptedException e1) {  
                            e1.printStackTrace();  
                        }  
                        isDead = true;  
                        for(int row = 1; row <= maxRow; row++) {  
                            for (int col = 1; col <= maxCol; col++) {  
                                if (life.getGrid()[row][col] != 0) {  
                                    isDead = false;  
                                    break;  
                                }  
                            }  
                            if (!isDead) {  
                                break;  
                            }  
                        }  
                        if (isDead) {  
                            JOptionPane.showMessageDialog(null, "凉凉");  
                            isRunning = false;  
                            thread = null;  
                        }  
                    }  
                }  
            });  
            thread.start();  
        } else if (e.getSource() == btnStop) {  
            isRunning = false;  
            thread = null;  
        } else if (e.getSource() == btnExit) {  
            System.exit(0);  
        } else {  
            int[][] grid = life.getGrid();  
            for (int i = 0; i < maxRow; i++) {  
                for (int j = 0; j < maxCol; j++) {  
                    if (e.getSource() == btnBlock[i][j]) {  
                        isSelected[i][j] = !isSelected[i][j];  
                        if (isSelected[i][j]) {  
                            btnBlock[i][j].setBackground(Color.WHITE);  
                            grid[i + 1][j + 1] = 1;  
                        } else {  
                            btnBlock[i][j].setBackground(Color.BLACK);  
                            grid[i + 1][j + 1] = 0;  
                        }  
                        break;  
                    }  
                }  
            }  
            life.setGrid(grid);  
           
        }  
    }  
  
    private void makeNextGeneration() {  
        life.update();  
        int[][] grid = life.getGrid();  
        for (int i = 0; i < maxRow; i++) {  
            for (int j = 0; j < maxCol; j++) {  
                if (grid[i + 1][j + 1] == 1) {  
                    btnBlock[i][j].setBackground(Color.WHITE);  
                } else {  
                    btnBlock[i][j].setBackground(Color.BLACK);  
                }  
            }  
        }  
    }  
}  