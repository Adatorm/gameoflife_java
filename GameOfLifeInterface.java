/**
 * @author Guner Acet,no:121044049
 *
 */
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.MouseInfo;

import java.util.Timer;
import java.util.TimerTask;

public class GameOfLifeInterface extends GameOfLife{
	/**
	 * window frame
	 */
	private JFrame frame;
	/**
	 * game table panel
	 */
	private JPanel table;
	/**
	 * save button
	 */
	private JButton saveButton;
	/**
	 * load button
	 */
	private JButton loadButton;
	/**
	 * play button
	 */
	private JButton playOneStep;
	/**
	 * continuous button
	 */
	private JButton playContinue;
	/**
	 * stop button
	 */
	private JButton stop;
	/**
	 * reset button
	 */
	private JButton reset;
	/**
	 * exit button
	 */
	private JButton exit;
	/**
	 * load file obj
	 */
	private File openFile;
	/**
	 * save file obj
	 */
	private File saveFile;
	/**
	 * GameOfLife obj
	 */
	private GameOfLife game;
	/**
	 * toggle button
	 */
	private JButton bigButton;
	/**
	 * cell array
	 */
	private JLabel[][] map;
	/**
	 * timer obj,for continue and stop button
	 */
	private Timer myTimer;
	/**
	 * if game is continue,value equals true,
	 * if game is not continue,value equals false,
	 */
	private boolean contFlag;
    /**
     * @param label take a label and switch visibility,
     * @param row take argument ,clicked cell x coordinate
     * @param col take argument ,clicked cell y coordinate
     * if given label visible,this method change to invisible
     * if given label is invisible,this method change to visible
     */
    private void switcher(JLabel label,int row,int col){
    	if(label.getBackground()==Color.black)
    	{
    		label.setBackground(Color.blue);
    		if(game.checkCell(row,col)==true)
    		{
    			game.deleteCell(new Cell(row,col));
    			game.shirink();
    		}
    		else
    			System.err.printf("ops,we have an error(deleteCell) :/ !\n");
    	}
    	else
    	{
    		label.setBackground(Color.black);
    		if(game.checkCell(row,col)==false)
    		{
    			game.addCell(new Cell(row,col));
    			game.shirink();
    		}
    		else
    			System.err.printf("ops,we have an error(addCell) :/ !\n");
    	}
    }
    
    /**
     * default constructor
     */
    public GameOfLifeInterface(){
    	
    	game=new GameOfLife();
    	contFlag=false;
		frame=new JFrame("GameOfLife");
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,540);
		frame.setVisible(true);
		frame.setResizable(false);
		table=new JPanel();
		table.setSize(495, 495);
		
		map=new JLabel[100][100];
		for(int row=0;row<100;++row)
		{
			for(int col=0;col<100;++col)
			{
				map[row][col]=new JLabel();
				map[row][col].setBackground(Color.blue);
				map[row][col].setOpaque(true);
				table.add(map[row][col]);
			}
		}
		frame.add(table);
    }
		
	/**
	 * create and add buttons on frame
	 */
	private void drawButton(){
		
		saveButton=new JButton("Save Game");
		saveButton.setToolTipText("Save Game to File");
		saveButton.addActionListener(
				new ActionListener()
				{
                    public void actionPerformed(ActionEvent e)
                    {
                    	if(contFlag==true)
                    	{
                    		myTimer.cancel();
                        	myTimer.purge();
                        	contFlag=false;
                    	}
                    	JFileChooser save = new JFileChooser();
                		FileNameExtensionFilter filter = new FileNameExtensionFilter(
                			    "txt GameOfLife files", "txt");
                		save.setFileFilter(filter);
                		int returnVal = save.showSaveDialog(null);
                		if (returnVal == JFileChooser.APPROVE_OPTION) {
                            saveFile = save.getSelectedFile();
                            game.saveGame(saveFile);
                        } else {
                            System.err.println("error,file not saved");
                        }
                    }
                }
			);
		loadButton=new JButton("Load Game");
		loadButton.setToolTipText("Load Game from File");
		loadButton.addActionListener(
				new ActionListener()
				{
                    public void actionPerformed(ActionEvent e)
                    {
                    	if(contFlag==true)
                    	{
                    		myTimer.cancel();
                        	myTimer.purge();
                        	contFlag=false;
                    	}
                    	JFileChooser open = new JFileChooser();
                		FileNameExtensionFilter filter = new FileNameExtensionFilter(
                			    "txt GameOfLife files", "txt");
                		open.setFileFilter(filter);
                		int returnVal = open.showOpenDialog(null);
                		if (returnVal == JFileChooser.APPROVE_OPTION) {
                            openFile=open.getSelectedFile();
                            game=new GameOfLife(openFile);
                            paintTable();
                        } else {
                            System.err.println("error,file not opened");
                        }
                    }
                }
			);
		
		playOneStep=new JButton("Step");
		playOneStep.setToolTipText("play one step");
		playOneStep.addActionListener(
				new ActionListener()
				{
                    public void actionPerformed(ActionEvent e)
                    {
                    	if(contFlag==true)
                    	{
                    		myTimer.cancel();
                        	myTimer.purge();
                        	contFlag=false;
                    	}
                    	game.play();
                    	paintTable();
                    }
                }
			);
		
		playContinue=new JButton("Continue");
		playContinue.setToolTipText("Continue playing...");
		playContinue.addActionListener(
				new ActionListener()
				{
                    public void actionPerformed(ActionEvent e)
                    {
                    	contFlag=true;
                    	myTimer = new Timer();
                        myTimer.schedule(new TimerTask(){
                        	public void run(){
                        		game.play();
                            	paintTable();
                        	}
                        },500,500);
                    }
                }
			);
		
		stop=new JButton("Stop");
		stop.setToolTipText("Stop playing!");
		stop.addActionListener(
				new ActionListener()
				{
                    public void actionPerformed(ActionEvent e)
                    {
                    	myTimer.cancel();
                    	myTimer.purge();
                    	contFlag=false;
                    }
                }
			);
		
		reset=new JButton("Reset");
		reset.setToolTipText("reset game");
		reset.addActionListener(
				new ActionListener()
				{
                    public void actionPerformed(ActionEvent e)
                    {
                    	if(contFlag==true)
                    	{
                    		myTimer.cancel();
                        	myTimer.purge();
                        	contFlag=true;
                    	}
                    	game=null;
                    	game=new GameOfLife();
                    	paintTable();
                    }
                }
			);
		
		exit=new JButton("Exit");
		exit.setToolTipText("Exit game!");
		exit.addActionListener(
			new ActionListener()
			{
	            public void actionPerformed(ActionEvent e)
	            {
	            	int status=0;
	            	status=JOptionPane.showConfirmDialog(frame, "are you sure?", "Exit Game", JOptionPane.OK_CANCEL_OPTION);
	            	if(status==JOptionPane.OK_OPTION)
	            	{
	            		if(contFlag==true)
                    	{
                    		myTimer.cancel();
                        	myTimer.purge();
                        	contFlag=false;
                    	}
	            		System.exit(0);
	            	}
	            }
	        }
		);
		
		bigButton=new JButton();
		bigButton.setOpaque(false);
		bigButton.setContentAreaFilled(false);
		bigButton.setBorderPainted(false);
		bigButton.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					int mouseY = MouseInfo.getPointerInfo().getLocation().y;
			        int mouseX = MouseInfo.getPointerInfo().getLocation().x;
			        int cellX=(mouseX-frame.getBounds().x-5)/5;
			        int cellY=(mouseY-frame.getBounds().y-5)/5-5;
			        try{
			        switcher(map[cellY][cellX],cellY,cellX);
			        }
			        catch(Exception t){}
				}
			}
		);

		frame.add(saveButton);
		frame.add(loadButton);
		frame.add(playOneStep);
		frame.add(playContinue);
		frame.add(stop);
		frame.add(reset);
		frame.add(exit);
		frame.add(bigButton);
	}
		
	/**
	 * set containers bounds
	 */
	private void drawTable(){
		setTable();
		saveButton.setBounds(550, 5, 150, 40);
		loadButton.setBounds(550, 45, 150, 40);
		playOneStep.setBounds(550, 140, 150, 40);
		playContinue.setBounds(550, 180, 150, 40);
		stop.setBounds(550, 220, 150, 40);
		reset.setBounds(550, 260, 150, 40);
		exit.setBounds(550, 300, 150, 40);
		bigButton.setBounds(frame.getBounds().x+5,frame.getBounds().y+5, 505, 505);
		table.setBounds(frame.getBounds().x+5,frame.getBounds().y+5, 505, 505);
		
		for(int row=0;row<100;++row)
		{
			for(int col=0;col<100;++col)
			{
				map[row][col].setVisible(true);
			}
		}
	}
	/**
	 * draw game
	 */
	public void drawGame(){
		
		drawButton();
		drawTable();
	}
	/**
	 * set new color cell arrays
	 */
	private void paintTable()
	{
		for(int i=0;i<100;++i)
		{
			for(int j=0;j<100;++j)
			{
				if(game.checkCell(i,j)==true)
				{
					map[i][j].setBackground(Color.black);
				}
				else
				{
					map[i][j].setBackground(Color.blue);
				}
			}
		}
	}
	/**
	 * set bounds cell array
	 */
	private void setTable(){
		for(int i=0;i<100;++i)
		{
			for(int j=0;j<100;++j)
			{
				if(game.checkCell(i,j)==true)
				{
					map[i][j].setBackground(Color.black);
					map[i][j].setBounds(j*5,i*5, 5, 5);
				}
				else
				{
					map[i][j].setBackground(Color.blue);
					map[i][j].setBounds(j*5,i*5, 5, 5);
				}
			}
		}
	}
}
