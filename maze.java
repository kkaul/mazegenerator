import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;

public class maze extends JFrame implements ActionListener
{
static final int NORTH = 0;
static final int EAST = 1;
static final int SOUTH = 2;
static final int WEST = 3;
private int bh = 12;   	// height of a graphical block (for display only)
private int bw = 12;	// width of a graphical block
private int mh = 51;	// default height and width of maze (can change!)
private int mw = 51;
private int ah, aw;	// height and width of graphical maze
private int yoff = 40;    // init y-cord of maze
private Graphics g;
private int dtime = 20;   // 40 ms delay time  - set to 0 for immediate maze
private byte[][] M;	// the array for the maze
private Button startbutton;

public void paint(Graphics g) {} // override automatic repaint

public maze(int bh0, int mh0, int mw0)
 { 
   bh = bw = bh0;  mh = mh0;  mw = mw0;
   ah = bh*mh;
   aw = bw*mw;
   startbutton = new Button("Begin");
   startbutton.setBounds((aw/2)-40,yoff+20,80,30);
   Container pane = this.getContentPane(); // the "content pane" of window
   pane.setLayout(null); // else java will place items automatically
   pane.add(startbutton);
   startbutton.addActionListener( this );
   M = new byte[mh][mw];  // initialize maze (default all 0's - walls).
   this.setBounds(0,0,aw+10,10+ah+yoff);	
   this.setVisible(true);
   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   try{Thread.sleep(500);} catch(Exception e) {} // Synch with system
   g = getGraphics();    //g.setColor(Color.red);
 }

// The args determine block size, maze height, and maze width:
public static void main(String[] args)
    {
       int blocksize = 12, mheight = 41, mwidth = 51;
       if (args.length==3)
	   {
	       mheight=Integer.parseInt(args[0]);
	       mwidth=Integer.parseInt(args[1]);
	       blocksize=Integer.parseInt(args[2]);
	   }
       maze W = new maze(blocksize,mheight,mwidth);
    }

// this function is called when the "begin" button is clicked:
  public void actionPerformed( ActionEvent e )
   { 
     startbutton.setVisible(false);
     g.setColor(Color.green);
     g.fill3DRect(0,yoff,aw,ah,true);  
     digout(mh-1,10);
   }   

public void drawblock(int y, int x)
    {
	g.setColor(Color.blue);
        g.fill3DRect(x*bw,yoff+(y*bh),bw,bh,true);
        try{Thread.sleep(dtime);} catch(Exception e) {} 
    }

 public void digout(int y, int x)  
 {

     M[y][x] = 1;  // digout maze at coordinate y,x
     drawblock(y,x);  // change graphical display to reflect space dug out
     
     int[] P = {0,1,2,3};
     for (int i =0;i <P.length;i++)
	 {
	     int r = (int)(Math.random()*P.length);
	     int temp = P[i];
	     P[i] = P[r];
	     P[r]=temp;
	 }
     for (int r =0;r<4;r++)
	 {
	     if(P[r]==0)
		 {
		     if (x+2<mw && M[y][x+2]==0 && P[r]==0) 
			 {
			     M[y][x+1] = 1;//up - 0
			     drawblock(y,x+1);
			     digout(y,x+2);
			 }  
		 }

	     else if(P[r]==1)
		 { 
		     if (y-2>=0 && M[y-2][x]==0 && P[r]==1)//right - 1
			 {
			     M[y-1][x] = 1;
			     drawblock(y-1,x);
			     digout(y-2,x);
			 }
		     
		 }
	     
	     else if(P[r]==2)
		 {

		     if (y+2<mh && M[y+2][x]==0 && P[r]==2)//down - 2
			 {
			     M[y+1][x] = 1;
			     drawblock(y+1,x);
			     digout(y+2,x);
			 }
		 }

	     else if (P[r]==3)
		 {
		     if (x-2>=0 && M[y][x-2]==0 && P[r]==3)//left - 3
			 {
			     M[y][x-1] = 1;
			     drawblock(y,x-1);
			     digout(y,x-2);
			 }   
		 }
	 }//for
	     

     
 } // digout

 } // class maze
