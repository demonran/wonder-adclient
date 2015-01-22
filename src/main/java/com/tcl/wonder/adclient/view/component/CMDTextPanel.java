package com.tcl.wonder.adclient.view.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;



public class CMDTextPanel extends JTextPane implements MouseListener,
		KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isCmdState=false;
	private JPopupMenu pop = null; // 弹出菜单
	private JMenuItem copy = null, paste = null, cut = null,clear=null,cmd=null; //四个功能菜单
	public CMDTextPanel() {
		super();
		init();
	}

	private void init() {
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));   
		this.setBackground(new java.awt.Color(0, 0, 0));
		this.setFont(new java.awt.Font("宋体", 1, 14));
		this.setForeground(new java.awt.Color(0, 204, 0));
		this.setCaretColor(Color.ORANGE);
		pop = new JPopupMenu();
		pop.add(cmd = new JMenuItem("进入命令模式"));
		pop.add(copy = new JMenuItem("复制"));
		pop.add(paste = new JMenuItem("粘贴"));
		pop.add(cut = new JMenuItem("剪切"));
		pop.add(clear= new JMenuItem("清空"));
		cmd.setAccelerator(KeyStroke.getKeyStroke('i', InputEvent.CTRL_MASK));
		copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
		paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
		cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
		clear.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_MASK));
		cmd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action(e);
			}
		});
		copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action(e);
			}
		});
		paste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action(e);
			}
		});
		cut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action(e);
			}
		});
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action(e);
			}
		});
		this.add(pop);
	}

	/**
	 * 菜单动作
	 * 
	 * @param e
	 */
	public void action(ActionEvent e) {
		String str = e.getActionCommand();
		if (str.equals(copy.getText())) { // 复制
			this.copy();
		} else if (str.equals(paste.getText())) { // 粘贴
			this.paste();
		} else if (str.equals(cut.getText())) { // 剪切
			this.cut();
		}else if(str.equals(clear.getText())){// 清空
			this.setText("");
		}else if (str.equals("进入命令模式")) { // 粘贴
			cmd.setText("退出命令模式");
			this.setCaretColor(Color.red);
			this.setText("--->命令模式");
			this.setCaretPosition(this.getDocument().getLength());
			isCmdState=true;
		}else if (str.equals("退出命令模式")) { // 粘贴
			cmd.setText("进入命令模式");
			this.setText("<----非命令模式");
			this.setCaretPosition(this.getDocument().getLength());
			this.setCaretColor(Color.ORANGE);
			isCmdState=false;
		}
	}

	/**
	 * 剪切板中是否有文本数据可供粘贴
	 * 
	 * @return true为有文本数据
	 */
	public boolean isClipboardString() {
		boolean b = false;
		Clipboard clipboard = this.getToolkit().getSystemClipboard();
		Transferable content = clipboard.getContents(this);
		try {
			if (content.getTransferData(DataFlavor.stringFlavor) instanceof String) {
				b = true;
			}
		} catch (Exception e) {
		}
		return b;
	}

	/**
	 * 文本组件中是否具备复制的条件
	 * 
	 * @return true为具备
	 */
	public boolean isCanCopy() {
		boolean b = false;
		int start = this.getSelectionStart();
		int end = this.getSelectionEnd();
		if (start != end)
			b = true;
		return b;
	}

	public JPopupMenu getPop() {
		return pop;
	}

	public void setPop(JPopupMenu pop) {
		this.pop = pop;
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			copy.setEnabled(isCanCopy());
			paste.setEnabled(isClipboardString());
			cut.setEnabled(isCanCopy());
			pop.show(this, e.getX(), e.getY());
		}
	}

	public void mouseReleased(MouseEvent e) {

	}

	public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_DOWN){
        }else if(e.getKeyCode()==KeyEvent.VK_UP){
        }else if(e.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        }else if(e.getKeyCode()==KeyEvent.VK_PAGE_UP){
        }else if(e.getKeyCode()==KeyEvent.VK_ENTER){
        //输入quit或exit则退出
        String input=this.getLastLine();
        if(isCmdState){
       		if(input.equals("quit") || input.equals("exit")){
       			cmd.setText("进入命令模式");
       			isCmdState=false;
       			this.setCaretColor(Color.ORANGE);
       			this.setText("退出了命令模式");
       			return;
       		}
//       		String commnd=invoker.execute(input);
//       		this.setText(this.getText()+"\n"+commnd);
        }else{
        	if(input.equals("enter") || input.equals("vi")){
        		cmd.setText("退出命令模式");
       			isCmdState=true;
       			this.setCaretColor(Color.red);
       			this.setText("进入了命令模式");
       			return;
        	}else{
        		this.setText("非命令模式,要执行命令必须在命令模式下，\n请输入enter或vi进入命令模式\n");
        	}
        }
        }
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {
	
	}

	public String getLastLine(){
		String con=this.getText();
		String[] cons=con.split("\n");
		String commnd=cons[cons.length-1];
		return commnd;
	}

	public boolean isCmdState() {
		return isCmdState;
	}

	public void setCmdState(boolean isCmdState) {
		this.isCmdState = isCmdState;
	}
}
