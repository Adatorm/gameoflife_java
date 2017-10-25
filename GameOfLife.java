/**
 * @author Guner Acet,no:121044049
 *
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;
import javax.swing.JOptionPane;

/**
 * @author Guner Acet,no:121044049
 *
 */
public class GameOfLife {

	/**
	 * it holds array of living cells
	 */

	private Cell[] livingCells;

	/**
	 * it holds min hight coordinate
	 */
	private int minHeight;

	/**
	 * it holds min width coordinate
	 */
	private int minWidth;

	/**
	 * it holds max height coordinate
	 */
	private int maxHeight;

	/**
	 * it holds max width coordinate
	 */
	private int maxWidth;

	/**
	 * @param x
	 *            take cell's height coor
	 * @param y
	 *            take cell's width coor
	 * @return true if cell(x,y) is exist in livingCells array,otherwise false
	 */
	protected boolean checkCell(int x, int y) {
		for (int i = 0; i < size; ++i) {
			if (x == livingCells[i].getX() && y == livingCells[i].getY())
				return true;
		}
		return false;
	}
	
	/**
	 * it holds size of livingCells array
	 */
	private int size;
	/**
	 * it holds capacity of livingCells
	 */
	private int capacity;
	/**
	 * it holds number of all living cells
	 */
	private static int allLivingCells=0;

	/**
	 * @param x
	 *            cell's x coor
	 * @param y
	 *            cell's y coor
	 * @return if given cells number of living neighbors is 3,return 3,if it is
	 *         2 return 2,otherwise return 1
	 */

	private int checkNeighbors(int x, int y) {
		int liveCell = 0;
		if (checkCell(x - 1, y - 1))/*upper left*/
			liveCell++;
		if (checkCell(x - 1, y))/*up*/
			liveCell++;
		if (checkCell(x - 1, y + 1))/*upper right*/
			liveCell++;
		if (checkCell(x, y - 1))/*left*/
			liveCell++;
		if (checkCell(x, y + 1))/*right*/
			liveCell++;
		if (checkCell(x + 1, y - 1))/*bottom left*/
			liveCell++;
		if (checkCell(x + 1, y))/*down*/
			liveCell++;
		if (checkCell(x + 1, y + 1))/*bottom right*/
			liveCell++;
		if (liveCell == 2)
			return 2;
		else if (liveCell == 3)
			return 3;
		else
			return 1;
	}

	/**
	 * @param c
	 *            takes cell and add livingCells array,if array is full,resize
	 *            array
	 */
	protected void addCell(Cell c) {
		if(c.getX()>=100 || c.getY()>=100)
			return;
		if (capacity == size) {
			Cell[] tempList = new Cell[size + 10];
			copyLivingCells(tempList);
			livingCells=null;
			livingCells = tempList;
			tempList = null;
			capacity = capacity + 10;
		}
		livingCells[size] = c;
		++size;
	}

	protected boolean deleteCell(Cell c){
		for(int i=0;i<size;++i)
		{
			if(livingCells[i].getX()==c.getX()&&livingCells[i].getY()==c.getY())
			{
				for(int j=i;j<size-1;++j)
				{
					livingCells[j].setX(livingCells[j+1].getX());
					livingCells[j].setY(livingCells[j+1].getY());
				}
				size=size-1;
				return true;
			}
		}
		return false;
	}
	/**
	 * @param list
	 *            copy livingCells array to given list array
	 */
	private void copyLivingCells(Cell[] list) {
		for (int i = 0; i < size; ++i) {
			list[i]=new Cell(livingCells[i].getX(),livingCells[i].getY());
		}
	}

	/**
	 * set min and max size
	 */
	protected void shirink() {
		int minH = 8000, minW = 8000, maxH = -8000, maxW = -8000;
		for (int i = 0; i < size; ++i) {
			if (livingCells[i].getX() > maxH)
				maxH = livingCells[i].getX();
			if (livingCells[i].getX() < minH)
				minH = livingCells[i].getX();
			if (livingCells[i].getY() > maxW)
				maxW = livingCells[i].getY();
			if (livingCells[i].getY() < minW)
				minW = livingCells[i].getY();
		}
		maxHeight = maxH;
		maxWidth = maxW;
		minHeight = minH;
		minWidth = minW;
		/*
		for (int i = 0; i < size; ++i) {
			livingCells[i].setX(livingCells[i].getX() - getMinHeight());
			livingCells[i].setY(livingCells[i].getY() - getMinWidth());
		}
		maxHeight = (maxHeight - minHeight);
		maxWidth = (maxWidth - minWidth);
		minHeight = 0;
		minWidth = 0;*/
	}
	/**
	 * this method only using in play method for playing game
	 * @param out input game.
	 */
	private void copyGame(GameOfLife out){
		out.capacity =capacity;
		out.livingCells = new Cell[size];
		copyLivingCells(out.livingCells);
		out.maxHeight = maxHeight;
		out.maxWidth = maxWidth;
		out.minHeight = minHeight;
		out.minWidth = minWidth;
		out.size = size;
	}
	/**
	 * Default Constructor,all data fields set to 0
	 */
	public GameOfLife() {
		capacity = 0;
		livingCells = null;
		maxHeight = 0;
		maxWidth = 0;
		minHeight = 0;
		minWidth = 0;
		size = 0;
	}

	/**
	 * take input file and initialize game with this file
	 * @param fileName take file name with parameter and initialize board
	 */
	public GameOfLife(String fileName) {
		Scanner read=null;
		try {
			read = new Scanner(new FileInputStream(fileName));
			int row,column;
			row=read.nextInt();
			column=read.nextInt();
			for(int i=0;i<row;++i)
			{
				for(int j=0;j<column;++j)
				{
					if(read.nextInt()==1)
					{
						addCell(new Cell(i,j));
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			System.exit(0);
		}
		allLivingCells=allLivingCells+size;
		//shirink();
	}
	/**
	 * take file and initialize game with this file
	 * @param file take File obj with parameter and initialize board
	 */
	public GameOfLife(File file){
		capacity = 0;
		livingCells = null;
		maxHeight = 0;
		maxWidth = 0;
		minHeight = 0;
		minWidth = 0;
		size = 0;
		Scanner read=null;
		try {
			read = new Scanner(new FileInputStream(file.getPath()));
			int row=0,column=0;
			try{
			row=read.nextInt();
			column=read.nextInt();
			
			
			
			for(int i=0;i<row;++i)
			{
				for(int j=0;j<column;++j)
				{
					if(read.nextInt()==1)
					{
						addCell(new Cell(i,j));
					}
				}
			}
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "ops,the file is not gameOflife save files or it damaged");
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			System.exit(0);
		}
		finally
		{
			read.close();
		}
		allLivingCells=allLivingCells+size;
		shirink();
	}

	/**
     * get min width
	 * @return minWidth
	 */
	public int getMinWidth() {
		return minWidth;
	}

	/**
     * get min height
	 * @return minHeight
	 */
	public int getMinHeight() {
		return minHeight;
	}

	/**
     * get max width
	 * @return maxWidth
	 */
	public int getMaxWidth() {
		return maxWidth;
	}

	/**
     * get max Height
	 * @return maxHeight
	 */
	public int getMaxHeight() {
		return maxHeight;
	}

	/**
	 * print current board size
	 */
	public void printBoardSize() {
		System.out.printf("BoardSize;X=%d,Y=%d\n", getMaxHeight()+1,
				getMaxWidth()+1);
	}

	/**
	 * print current board
	 */
	public void printBoard() {
		System.out.printf(" ");
		for (int i = getMinWidth(); i <= getMaxWidth(); ++i)
			System.out.printf("- ");
		System.out.printf("\n");
		for (int i = getMinHeight(); i <= getMaxHeight(); ++i) {
			System.out.printf("|");
			for (int j = getMinWidth(); j <= getMaxWidth(); ++j) {
				if (checkCell(i, j))
					System.out.printf("x");
				else
					System.out.printf(" ");
				if(j!=getMaxWidth())
					System.out.printf(" ");
			}
			System.out.printf("|\n");
		}
		System.out.printf(" ");
		for (int i = getMinWidth(); i <= getMaxWidth(); ++i)
			System.out.printf("- ");
		System.out.printf("\n");
	}

	/**
     * get number of living Cells for this object
	 * @return size of livingCells array
	 */
	public int getSize() {
		return size;
	}

	/**
	 * play game one step
	 */
	public void play() {
		GameOfLife temp = new GameOfLife();
		copyGame(temp);
		allLivingCells=allLivingCells-size;
		for(int i=0;i<size;++i)
			livingCells[i]=null;
		size = 0;
		capacity=0;
		for (int i = temp.getMinHeight() - 1; i <= temp.getMaxHeight() + 1; ++i) {
			for (int j = temp.getMinWidth() - 1; j <= temp.getMaxWidth() + 1; ++j) {
				if ((temp.checkNeighbors(i, j) == 2 && temp.checkCell(i,j))
						|| temp.checkNeighbors(i, j) == 3) {
					addCell(new Cell(i, j));
				}
			}
		}
		temp = null;
		allLivingCells=allLivingCells+size;
		shirink();
	}

	/**
     * add game to other game
	 * @param newGame
	 *            take GameOfLife references and copy newGame's livingCells if
	 *            this game is not have newGame's livingCells
	 */
	public void addGame(GameOfLife newGame) {
		for (int i = newGame.getMinHeight(); i <= newGame.getMaxHeight(); ++i) {
			for (int j = newGame.getMinWidth(); j <= newGame.getMaxWidth(); ++j) {
				if (!checkCell(i, j) && newGame.checkCell(i,j))
				{
					addCell(new Cell(i, j));
					allLivingCells++;
				}
			}
		}
		shirink();
	}

	/**
	 * return number of all living cells
	 * @return allLivingCells
	 */
	public static int getAllLivingCells() {
		return allLivingCells;
	}
	/**
	 * @param file get file object and create or overwrite given file
	 */
	public void saveGame(File file){
		shirink();
		BufferedWriter writer =null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
		    writer.write(Integer.toString(getMaxHeight()+1-getMinHeight()));
		    writer.write(" ");
		    writer.write(Integer.toString(getMaxWidth()+1-getMinWidth()));
		    writer.newLine();
		    for(int row=getMinHeight();row<=getMaxHeight();++row)
		    {
		    	for(int col=getMinWidth();col<=getMaxWidth();++col)
		    	{
		    		if(checkCell(row,col))
		    			writer.write("1 ");
		    		else
		    			writer.write("0 ");
		    	}
		    	writer.newLine();
		    }
		} catch (IOException ex) {
		  // report
		} finally {
		   try {writer.close();} catch (Exception ex) {}
		}
	}
}


