package com.tcl.wonder.adclient.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Demo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4665344921910340705L;

	protected InfiniteProgressPanel glassPane;

	public Demo() {
		super("Infinite Progress Demo");

		build();

		pack();
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	protected void build() {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(BorderLayout.CENTER, buildInfinitePanel());
	}

	protected Container buildInfinitePanel() {

		JPanel pane = new JPanel(new BorderLayout());

		glassPane = new InfiniteProgressPanel("数据校验中......");
		setGlassPane(glassPane);

		JTable table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		pane.add(BorderLayout.CENTER, scrollPane);

		JPanel buttons = new JPanel();
		JButton button = new JButton("Start");
		button.setToolTipText("111");
		buttons.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				glassPane.start();
				Thread performer = new Thread(new Runnable() {
					public void run() {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException ie) {
						}
						glassPane.stop();
					}
				}, "Performer");
				performer.start();
			}
		});
		pane.add(BorderLayout.SOUTH, buttons);

		return pane;
	}

	public static void main(String[] args) {
		Demo d = new Demo();
		d.setVisible(true);
	}
}