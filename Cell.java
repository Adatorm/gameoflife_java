/**
 * @author Guner Acet,no:121044049
 *
 */
public class Cell {
	/**
	 * it hold cell of X coor
	 */
	private int x;
	/**
	 * it hold cell of Y coor
	 */
	private int y;
	
	/**
	 * default constructor,set x=0 and y=0
	 */
	public Cell(){
		setX(0);
		setY(0);
	}/*default constructor*/
	/**
	 * @param a take a value to set x
	 * @param b take value to set y
	 */
	public Cell(int a,int b)
	{
		setX(a);
		setY(b);
	}
	/**
     * set x coor
	 * @param a take value to set x
	 */
	public void setX(int a){x=a;}
	/**
     * set y coor
	 * @param b take value to set y
	 */
	public void setY(int b){y=b;}
	/**
     * get x value
	 * @return x 
	 */
	public int getX(){return x;}
	/**
     * get y value
	 * @return y
	 */
	public int getY(){return y;}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return String.format("Cell(%d,%d)",x,y);
	}
}
