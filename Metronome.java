import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;


public class Metronome extends JFrame
{
	 JPanel panel1=new JPanel();
	 JPanel panel2=new JPanel();
	 JPanel panel3=new JPanel();
	 JPanel panel4=new JPanel();
	 JPanel panel5=new JPanel();
	 
	 JLabel label=new JLabel("Select your BPM using box or slider then press start");
	 JButton back=new JButton("Go Back");//Use this later
	 JTextField box=new JTextField(10);
	 JSlider slider=new JSlider(JSlider.HORIZONTAL,60,220,100);
	 JButton start=new JButton("Start");
	 JButton stop=new JButton("Stop");
	 JLabel errorMessage=new JLabel("");

	 
	 boolean activated=false;
	 
	 Timer timer;

	
	public Metronome()//constructor 
	{		
		setTitle("Metronome");
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon img=new ImageIcon("icon.jpg");
		setIconImage(img.getImage());
		
		buildPanel1();
		buildPanel2();
		buildPanel3();
		buildPanel4();
		buildPanel5();
		
		//ALL THE LISTENERS
		box.addActionListener(new BoxListener());
		slider.addChangeListener(new SliderListener());
		start.addActionListener(new StartListener());
		stop.addActionListener(new StopListener());
		
		setLayout(new GridLayout(5,1));
		add(panel1,BorderLayout.NORTH);
		add(panel2);
		add(slider);
		add(panel4);
		add(panel5);
		
		setVisible(true);
	}
	
	//BUILD ALL THE PANELS
	public void  buildPanel1()
	{
		/*GET BACK WORKING AT THE END*/
		//panel1.setLayout(new GridLayout(1,2));
		//panel1.add(back);
		panel1.add(label);
		
	}
	public void buildPanel2()
	{
		box.setText("100");
		box.setForeground(Color.BLUE);
		panel2.add(box);
	
	}
	public void buildPanel3()
	{
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(5);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		//slider.setSnapToTicks(true);//MIGHT PUT BACK
		panel3.add(slider);
	}
	
	public void buildPanel4()
	{
		panel4.add(start);
		panel4.add(stop);
		stop.setVisible(false);
		
	}
	
	public void buildPanel5()
	{
		panel5.add(errorMessage);
		errorMessage.setForeground(Color.RED);
		
	}
	

	//USED TO UPDATE BOX WHEN SLIDER IS CHANGED
	class SliderListener implements ChangeListener
	{	
		
		public void stateChanged(ChangeEvent e )
		{
			
			int num=slider.getValue();
			
			String nums=""+num;
			//if(nums.charAt(nums.length()-1)=='0'||nums.charAt(nums.length()-1)=='5')
			{
				int slow=60+53;
				int normal=slow+53;
				
				if(num<slow)
					box.setForeground(Color.BLUE);
				else if(num<normal)
					box.setForeground(Color.ORANGE);
				else
					box.setForeground(Color.RED);
					
				
				box.setText(nums);
				
				if(errorMessage.getText().equals("Bad Input"))
				{
					start.setVisible(true);
					errorMessage.setText("");
				}
				
				
				

				
			}
		}
		
	}
	
	//GETS THE APPROPRIATE DELAY AND STARTS THE TIMER
	class StartListener implements ActionListener
	{
		
		public void actionPerformed(ActionEvent e)
		{
			if(!activated)
			{
				activated=true;
				
				
				int bpm=slider.getValue();
				double bps=bpm/60.0;
				double millid=1000/bps;
				int milli=(int)millid;
				
				timer=new Timer(milli,new TimerDelay());
				timer.start();
				
				start.setVisible(false);
				stop.setVisible(true);
				label.setText("Press Stop to end");
			}
			else
			{
				timer.stop();
				
			}
			
			
		}	
	}
	
	class StopListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(activated)
			{
				timer.stop();
				activated=false;
				start.setVisible(true);
				stop.setVisible(false);
				label.setText("Select BPM and press start");
			}
		
			
		}
		
	}
	
	//USED TO GET THE ACTION THAT WE WANT REPEARED IN OUR TIMER
	class TimerDelay implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			playBeep();
		}
		
		public void playBeep()
		{
			try
			{
	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("beep.wav"));
	         Clip clip = AudioSystem.getClip();
	         clip.open(audioIn);
	         clip.start();
			}
			catch(Exception e)
			{
				System.out.print("Error playing music");	
				e.printStackTrace();
			}	
		}
		
		
	}
	
	//CHANGES THE SLIDER'S VALUE WHEN BOX IS UPDATED
	class BoxListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				String nums=box.getText();
				int num=Integer.parseInt(nums);
				slider.setValue(num);
				errorMessage.setText("");
				
				start.setVisible(true);
				
				
			}
			catch(Exception p)
			{
				errorMessage.setText("Bad Input");
				start.setVisible(false);
				stop.setVisible(false);
			}
		}
		
	}
	
	public static void main(String[]args)
	{
		new Metronome();
	}
	
}