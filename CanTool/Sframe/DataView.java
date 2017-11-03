package Sframe;


import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.TooManyListenersException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import CanTool.CanTool;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import serialException.*;
import serialPort.SerialTool;

/**
 * 监测数据显示类
 * @author Zhong
 *
 */
public class DataView extends Frame {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Client client = null;

	private List<String> commList = null;	//保存可用端口号
	private SerialPort serialPort = null;	//保存串口对象
	
	private Font font = new Font("微软雅黑", Font.BOLD, 25);
	
	private Label tx1 = new Label("暂无数据", Label.CENTER);	//温度
	private Label tx2 = new Label("暂无数据", Label.CENTER);	//湿度
	private Label tx3 = new Label("暂无数据", Label.CENTER);
	private Label tx4 = new Label("暂无数据", Label.CENTER);
	private TextField tx5 = new TextField(10);
	
	private Button bu1 = new Button("测试");
	/*private Label pa = new Label("暂无数据", Label.CENTER);	//压强
	private Label rain = new Label("暂无数据", Label.CENTER);	//雨量
	private Label win_sp = new Label("暂无数据", Label.CENTER);	//风速
	private Label win_dir = new Label("暂无数据", Label.CENTER);	//风向
*/	
	private Choice commChoice = new Choice();	//串口选择（下拉框）
	private Choice bpsChoice = new Choice();	//波特率选择
	private Choice fileChoice = new Choice();
	
	private Button bu2 = new Button("发送文档");
	private Button openSerialButton = new Button("打开串口");
	
	Image offScreen = null;	//重画时的画布
	
	//设置window的icon
	Toolkit toolKit = getToolkit();
	//Image icon = toolKit.getImage(DataView.class.getResource("computer.png"));

	/**
	 * 类的构造方法
	 * @param client
	 */
	
	public DataView(Client client) {
		this.client = client;
		commList = SerialTool.findPort();	//程序初始化时就扫描一次有效串口
	}
	
	/**
	 * 主菜单窗口显示；
	 * 添加Label、按钮、下拉条及相关事件监听；
	 */
	public void dataFrame() {
		this.setBounds(client.LOC_X, client.LOC_Y, client.WIDTH, client.HEIGHT);
		this.setTitle("CANTOOL测试");
		//this.setIconImage(icon);
		this.setBackground(Color.white);
		this.setLayout(null);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				if (serialPort != null) {
					//程序退出时关闭串口释放资源
					SerialTool.closePort(serialPort);
				}
				System.exit(0);
			}
			
		});
		
		tx1.setBounds(140, 103, 225, 50);
		tx1.setBackground(Color.black);
		tx1.setFont(font);
		tx1.setForeground(Color.white);
		add(tx1);
		
		tx2.setBounds(520, 103, 225, 50);
		tx2.setBackground(Color.black);
		tx2.setFont(font);
		tx2.setForeground(Color.white);
		add(tx2);
		
		tx3.setBounds(140, 193, 225, 50);
		tx3.setBackground(Color.black);
		tx3.setFont(font);
		tx3.setForeground(Color.white);
		add(tx3);
		
		tx4.setBounds(520, 193, 225, 50);
		tx4.setBackground(Color.black);
		tx4.setFont(font);
		tx4.setForeground(Color.white);
		add(tx4);
		
		tx5.setBounds(140, 283, 225, 50);
		tx5.setBackground(Color.black);
		//tx5.setFont(font);
		tx5.setForeground(Color.white);
		tx5.setFont(new Font("标楷体", Font.BOLD, 14));
		add(tx5);
		
		bu1.setBounds(520, 283, 225, 50);
		//bu1.setBackground(Color.black);
		bu1.setFont(font);
		//bu1.setForeground(Color.white);
		add(bu1);
		
		fileChoice.setBounds(160, 378, 200, 200);
		fileChoice.setFont(new Font("标楷体", Font.BOLD, 16));
		fileChoice.add("intel");
		fileChoice.add("mtla");
		fileChoice.add("fast");
		fileChoice.add("quxian");
		add(fileChoice);
		
		bu2.setBounds(520, 365, 225, 50);
		//bu1.setBackground(Color.black);
		bu2.setFont(font);
		//bu1.setForeground(Color.white);
		add(bu2);
		
		
				
		bu1.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				String temp = tx5.getText() + '\r';
				System.out.println("串口写入数据为"+temp);
				System.out.println(serialPort);
				try {
					SerialTool.sendToPort(serialPort, temp.getBytes());
					System.out.println("串口写入数据为"+temp);
				} catch (SendDataToSerialPortFailure
						| SerialPortOutputStreamCloseFailure e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
		

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
        });
		
		bu2.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				String test = fileChoice.getSelectedItem();
				String file = test+".txt";
				System.out.println(file);
				String fileString;
				Thread thread = new Thread();
				InputStream in1 = null;
				try {
					in1 = new FileInputStream(new File(file));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Scanner scan1 = new Scanner(in1);
				try {
					
					while(scan1.hasNext())
					{
						fileString = scan1.next()+"\r";
						System.out.println("串口写入数据为"+fileString);
						SerialTool.sendToPort(serialPort, fileString.getBytes());
						thread.sleep(200);
					}
					
					/*
					for(int i = 0 ; i<2; i++)
					{
						fileString = scan1.next()+"\r";
						System.out.println("串口写入数据为"+fileString);
						SerialTool.sendToPort(serialPort, fileString.getBytes());
						thread.sleep(200);
					}
					*/
				} catch (InterruptedException | SendDataToSerialPortFailure | SerialPortOutputStreamCloseFailure e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
		

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
        });
		
		
		/*pa.setBounds(140, 193, 225, 50);
		pa.setBackground(Color.black);
		pa.setFont(font);
		pa.setForeground(Color.white);
		add(pa);

		rain.setBounds(520, 193, 225, 50);
		rain.setBackground(Color.black);
		rain.setFont(font);
		rain.setForeground(Color.white);
		add(rain);
		
		win_sp.setBounds(140, 283, 225, 50);
		win_sp.setBackground(Color.black);
		win_sp.setFont(font);
		win_sp.setForeground(Color.white);
		add(win_sp);
		
		win_dir.setBounds(520, 283, 225, 50);
		win_dir.setBackground(Color.black);
		win_dir.setFont(font);
		win_dir.setForeground(Color.white);
		add(win_dir);*/
		
		//添加串口选择选项
		commChoice.setBounds(160, 460, 200, 200);
		//检查是否有可用串口，有则加入选项中
		if (commList == null || commList.size()<1) {
			JOptionPane.showMessageDialog(null, "没有搜索到有效串口！", "错误", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			for (String s : commList) {
				commChoice.add(s);
			}
		}
		add(commChoice);
		
		//添加波特率选项
		bpsChoice.setBounds(526, 460, 200, 200);
		bpsChoice.add("1200");
		bpsChoice.add("2400");
		bpsChoice.add("4800");
		bpsChoice.add("9600");
		bpsChoice.add("14400");
		bpsChoice.add("19200");
		bpsChoice.add("115200");
		add(bpsChoice);
		
		//添加打开串口按钮
		openSerialButton.setBounds(250, 530, 300, 50);
		openSerialButton.setBackground(Color.lightGray);
		openSerialButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
		openSerialButton.setForeground(Color.darkGray);
		add(openSerialButton);
		//添加打开串口按钮的事件监听
		openSerialButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				//获取串口名称
				String commName = commChoice.getSelectedItem();			
				//获取波特率
				String bpsStr = bpsChoice.getSelectedItem();
				
				
				//检查串口名称是否获取正确
				if (commName == null || commName.equals("")) {
					JOptionPane.showMessageDialog(null, "没有搜索到有效串口！", "错误", JOptionPane.INFORMATION_MESSAGE);			
				}
				else {
					//检查波特率是否获取正确
					if (bpsStr == null || bpsStr.equals("")) {
						JOptionPane.showMessageDialog(null, "波特率获取错误！", "错误", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						//串口名、波特率均获取正确时
						int bps = Integer.parseInt(bpsStr);
						try {
							
							//获取指定端口名及波特率的串口对象
							serialPort = SerialTool.openPort(commName, bps);
							CanTool tool = new CanTool(serialPort,tx1,tx2,tx3,tx4);
							//在该串口对象上添加监听器
							SerialTool.addListener(serialPort, new SerialListener(serialPort,tool));
							//监听成功进行提示
							JOptionPane.showMessageDialog(null, "监听成功，稍后将显示监测数据！", "提示", JOptionPane.INFORMATION_MESSAGE);
							
						} catch (SerialPortParameterFailure | NotASerialPort | NoSuchPort | PortInUse | TooManyListeners e1) {
							//发生错误时使用一个Dialog提示具体的错误信息
							JOptionPane.showMessageDialog(null, e1, "错误", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
				
			}
		});
		
		
		this.setResizable(false);
		
		//new Thread(new RepaintThread()).start();	//启动重画线程
		
	}
	
	/**
	 * 画出主界面组件元素
	 */
	public void paint(Graphics g) {
		Color c = g.getColor();
		
		g.setColor(Color.black);
		g.setFont(new Font("微软雅黑", Font.BOLD, 22));
		g.drawString(" ID： ", 45, 130);

		g.setColor(Color.black);
		g.setFont(new Font("微软雅黑", Font.BOLD, 22));
		g.drawString(" LEN： ", 425, 130);
		
		g.setColor(Color.black);
		g.setFont(new Font("微软雅黑", Font.BOLD, 22));
		g.drawString(" DATA： ", 45, 220);
		
		g.setColor(Color.black);
		g.setFont(new Font("微软雅黑", Font.BOLD, 22));
		g.drawString(" T： ", 425, 220);
		
		g.setColor(Color.black);
		g.setFont(new Font("微软雅黑", Font.BOLD, 22));
		g.drawString(" 输入： ", 45, 310);
		
		g.setColor(Color.black);
		g.setFont(new Font("微软雅黑", Font.BOLD, 20));
		g.drawString(" 文件选择： ", 45, 393);
		
		g.setColor(Color.gray);
		g.setFont(new Font("微软雅黑", Font.BOLD, 20));
		g.drawString(" 串口选择： ", 45, 473);
		
		g.setColor(Color.gray);
		g.setFont(new Font("微软雅黑", Font.BOLD, 20));
		g.drawString(" 波特率： ", 425, 473);
		
	}
	
	/**
	 * 双缓冲方式重画界面各元素组件
	 */
	public void update(Graphics g) {
		if (offScreen == null)	offScreen = this.createImage(Client.WIDTH, Client.HEIGHT);
		Graphics gOffScreen = offScreen.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.white);
		gOffScreen.fillRect(0, 0, Client.WIDTH, Client.HEIGHT);	//重画背景画布
		this.paint(gOffScreen);	//重画界面元素
		gOffScreen.setColor(c);
		g.drawImage(offScreen, 0, 0, null);	//将新画好的画布“贴”在原画布上
	}
	
	/*
	 * 重画线程（每隔30毫秒重画一次）
	 */
	private class RepaintThread implements Runnable {
		public void run() {
			while(true) {
				//调用重画方法
				repaint();
				
				
				
				//扫描可用串口
				commList = SerialTool.findPort();
				if (commList != null && commList.size()>0) {
					
					//添加新扫描到的可用串口
					for (String s : commList) {
						
						//该串口名是否已存在，初始默认为不存在（在commList里存在但在commChoice里不存在，则新添加）
						boolean commExist = false;	
						
						for (int i=0; i<commChoice.getItemCount(); i++) {
							if (s.equals(commChoice.getItem(i))) {
								//当前扫描到的串口名已经在初始扫描时存在
								commExist = true;
								break;
							}					
						}
						
						if (commExist) {
							//当前扫描到的串口名已经在初始扫描时存在，直接进入下一次循环
							continue;
						}
						else {
							//若不存在则添加新串口名至可用串口下拉列表
							commChoice.add(s);
						}
					}
					
					//移除已经不可用的串口
					for (int i=0; i<commChoice.getItemCount(); i++) {
						
						//该串口是否已失效，初始默认为已经失效（在commChoice里存在但在commList里不存在，则已经失效）
						boolean commNotExist = true;	
						
						for (String s : commList) {
							if (s.equals(commChoice.getItem(i))) {
								commNotExist = false;	
								break;
							}
						}
						
						if (commNotExist) {
							//System.out.println("remove" + commChoice.getItem(i));
							commChoice.remove(i);
						}
						else {
							continue;
						}
					}
					
				}
				else {
					//如果扫描到的commList为空，则移除所有已有串口
					commChoice.removeAll();
				}

				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					String err = ExceptionWriter.getErrorInfoFromException(e);
					JOptionPane.showMessageDialog(null, err, "错误", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
			}
		}
		
	}
	
	/**
	 * 以内部类形式创建一个串口监听类
	 * @author zhong
	 *
	 */
	private class SerialListener implements SerialPortEventListener {
		
		/**
		 * 处理监控到的串口事件
		 */
		    private SerialPort serialPort;
		    private String buff;
		    private CanTool tool;
		    
		    public SerialListener(SerialPort serialPort,CanTool tool)
		    {
		    	this.serialPort = serialPort;
		    	buff = "";
		    	this.tool = tool;
		    	
		    }
	    public void serialEvent(SerialPortEvent serialPortEvent) {
	    	
	        switch (serialPortEvent.getEventType()) {

	            case SerialPortEvent.BI: // 10 通讯中断
	            	JOptionPane.showMessageDialog(null, "与串口设备通讯中断", "错误", JOptionPane.INFORMATION_MESSAGE);
	            	break;

	            case SerialPortEvent.OE: // 7 溢位（溢出）错误

	            case SerialPortEvent.FE: // 9 帧错误

	            case SerialPortEvent.PE: // 8 奇偶校验错误

	            case SerialPortEvent.CD: // 6 载波检测

	            case SerialPortEvent.CTS: // 3 清除待发送数据

	            case SerialPortEvent.DSR: // 4 待发送数据准备好了

	            case SerialPortEvent.RI: // 5 振铃指示

	            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
	            	break;
	            
	            case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
	            	
	            	System.out.println("found data");
	            	//System.out.println(serialPort);
	            	
					/*byte[] data = null;
					
					try {
						if (serialPort == null) {
							JOptionPane.showMessageDialog(null, "串口对象为空！监听失败！", "错误", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							data = SerialTool.readFromPort(serialPort);	//读取数据，存入字节数组
							System.out.println(new String(data));
							
							//自定义解析过程
							if (data == null || data.length < 1) {	//检查数据是否读取正确	
								JOptionPane.showMessageDialog(null, "读取数据过程中未获取到有效数据！请检查设备或程序！", "错误", JOptionPane.INFORMATION_MESSAGE);
								System.exit(0);
							}
							else {
								System.out.println("come in");
								
								String dataOriginal = new String(data);	//将字节数组数据转换位为保存了原始数据的字符串
								String dataValid = "";	//有效数据（用来保存原始数据字符串去除最开头*号以后的字符串）
								String[] elements = null;	//用来保存按空格拆分原始字符串后得到的字符串数组	
								//解析数据
								if (dataOriginal.charAt(0) == '*') {	//当数据的第一个字符是*号时表示数据接收完成，开始解析							
									dataValid = dataOriginal.substring(1);
									elements = dataValid.split(" ");
									if (elements == null || elements.length < 1) {	//检查数据是否解析正确
										JOptionPane.showMessageDialog(null, "数据解析过程出错，请检查设备或程序！", "错误", JOptionPane.INFORMATION_MESSAGE);
										System.exit(0);
									}
									else {
										try {
											//更新界面Label值
											for (int i=0; i<elements.length; i++) {
												System.out.println(elements[i]);
											}
											//System.out.println("win_dir: " + elements[5]);
											tem.setText(" ℃");
											//hum.setText(elements[1] + " %");
											pa.setText(elements[2] + " hPa");
											rain.setText(elements[3] + " mm");
											win_sp.setText(elements[4] + " m/s");
											win_dir.setText(elements[5] + " °");
										} catch (ArrayIndexOutOfBoundsException e) {
											JOptionPane.showMessageDialog(null, "数据解析过程出错，更新界面数据失败！请检查设备或程序！", "错误", JOptionPane.INFORMATION_MESSAGE);
											System.exit(0);
										}
									}	
								}
							}
							
						}						
						
					} catch (ReadDataFromSerialPortFailure | SerialPortInputStreamCloseFailure e) {
						JOptionPane.showMessageDialog(null, e, "错误", JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);	//发生读取错误时显示错误信息后退出系统
					}	
		            
					break;*/
	            	byte[] data = null;
	                
	                try {
	                	
	                   /* if (serialPort == null) {
	                    	System.out.println("串口对象为空！监听失败");
	                    }
	                    else {
	                    	data = SerialTool.readFromPort(serialPort);    //读取数据，存入字节数组
	                        String dataString = new String(data);	//与缓冲区剩余数据合并
	                        //System.out.println(dataString);
	                        System.out.println("牛批");
	                        String[] dd = dataString.split("/r");
	                        
	                        
	                        tool.readCommand(dd[0]);
	                        //String dataValid = "";	//有效数据（用来保存原始数据字符串去除最开头*号以后的字符串）
	                        if(dataString.length()>=24)
	                        {
	                        	String[] elements = null;	//用来保存按空格拆分原始字符串后得到的字符串数组	
	                        	elements = dataString.split(" ");
							for (int i=0; i<elements.length; i++) {
								System.out.println(i+"、"+elements[i]);
							}
							//tx1.setText(elements[0]);
							//tx2.setText(elements[1]);
							//tx3.setText(elements[2]);
							//tx4.setText(elements[3]);
							
	                        }
	                        
	                        tx2.setText(dataString);
	                        String aaa = c.tx2.getText();
	                        System.out.println("aaa为："+dataString);
	                        else
	                        {
	                        	int len = dataString.length();
		                        for(int i=0;i<len;i++)
		                        {
		                        	char c1= dataString.charAt(i);
		                        	if(c1=='\r')
		                        	{
		                        		buff+="OK";
		                        		JOptionPane.showMessageDialog(null, buff, "正确", JOptionPane.INFORMATION_MESSAGE);
		                        		buff="";
		                        	}
		                        	else if((int)c1==7)
		                        	{
		                        		buff+="ERROR";
		                        		JOptionPane.showMessageDialog(null, buff, "错误", JOptionPane.ERROR_MESSAGE);
		                        		buff="";
		                        	}
		                        	else {
										buff+=c1;
									}
		                        }
		                        
		                        
		                        if(buff.length()>512)
		                        {
		                        	JOptionPane.showMessageDialog(null, buff, "错误", JOptionPane.ERROR_MESSAGE);
		                        	buff="";
		                        }
	                        }
	                        
	                        
	                    }
	                    //System.out.println(buff);
*/	                    
	                	if (serialPort == null) {
	                    	System.out.println("串口对象为空！监听失败");
	                    }
	                    else {
	                        data = SerialTool.readFromPort(serialPort);    //读取数据，存入字节数组
	                        System.out.println("CanTool读取数据："+data);
	                        String dataString = buff + new String(data);	//与缓冲区剩余数据合并
	                        System.out.println(dataString);
	                        
	                        String[] elements = null;
	                        elements = dataString.split("\r");
	                        int len = elements.length;
	                        System.out.println(len);
	                        if(dataString.charAt(dataString.length()-1)!='\r')
	                        {
	                        	buff = elements[len-1];
	                        	len--;
	                        }
	                        else
	                        {
	                        	buff = "";
	                        }
	                        for(int i=0;i<len;i++)
	                        {
	                        	elements[i] = elements[i] + "\r"; 
	                        	tool.readCommand(elements[i]);
	                        }
	                        if(buff.length()>512)
	                        {
	                        	tool.returnTheInfo(0, "");
	                        	buff = "";
	                        }
	                        //System.out.println(new String(data));
	                        //JOptionPane.showInputDialog(new String(data));
	                        //String dataOriginal = new String(data);    //将字节数组数据转换位为保存了原始数据的字符串
	                    }                 
	                } catch (Exception e) {
	                    System.exit(0);
	                }    
	                
	                break;
	        }

	    }

	}
	
	
}
