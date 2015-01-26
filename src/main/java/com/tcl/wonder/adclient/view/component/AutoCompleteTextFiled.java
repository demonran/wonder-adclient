package com.tcl.wonder.adclient.view.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 自动提示输入框
 * @author liuran
 * 2015年1月23日
 *
 */
public class AutoCompleteTextFiled extends JTextField
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();

	JComboBox<String> cbInput;

	public AutoCompleteTextFiled()
	{
		layoutUI();
	}

	private void layoutUI()
	{
		cbInput = new JComboBox<String>(model)
		{
			private static final long serialVersionUID = -1561972276111566922L;

			public Dimension getPreferredSize()
			{
				return new Dimension(super.getPreferredSize().width, 0);
			}
		};
	}

	private boolean isAdjusting(JComboBox<String> cbInput)
	{
		if (cbInput.getClientProperty("is_adjusting") instanceof Boolean)
		{
			return (Boolean) cbInput.getClientProperty("is_adjusting");
		}
		return false;
	}

	private void setAdjusting(JComboBox<String> cbInput, boolean adjusting)
	{
		cbInput.putClientProperty("is_adjusting", adjusting);
	}

	public void setupAutoCompleteItem(final Set<String> items)
	{

		setAdjusting(cbInput, false);
		for (String item : items)
		{
			model.addElement(item);
		}
		cbInput.setSelectedItem(null);
		cbInput.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println(e.getSource());
				if (!isAdjusting(cbInput))
				{
					if (cbInput.getSelectedItem() != null)
					{
						setText(cbInput.getSelectedItem().toString());
					}
				}
			}
		});

		addKeyListener(new KeyAdapter()
		{

			@Override
			public void keyPressed(KeyEvent e)
			{
				setAdjusting(cbInput, true);
				if (e.getKeyCode() == KeyEvent.VK_SPACE)
				{
					if (cbInput.isPopupVisible())
					{
						e.setKeyCode(KeyEvent.VK_ENTER);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_UP
						|| e.getKeyCode() == KeyEvent.VK_DOWN)
				{
					e.setSource(cbInput);
					cbInput.dispatchEvent(e);
					if (e.getKeyCode() == KeyEvent.VK_ENTER)
					{
						if(cbInput.getSelectedItem() != null)
						{
							setText(cbInput.getSelectedItem().toString());
							cbInput.setPopupVisible(false);
						}
						
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					cbInput.setPopupVisible(false);
				}
				setAdjusting(cbInput, false);
			}
		});
		getDocument().addDocumentListener(new DocumentListener()
		{
			public void insertUpdate(DocumentEvent e)
			{
				updateList();
			}

			public void removeUpdate(DocumentEvent e)
			{
				updateList();
			}

			public void changedUpdate(DocumentEvent e)
			{
				updateList();
			}

			private void updateList()
			{
				setAdjusting(cbInput, true);
				model.removeAllElements();
				String input = getText();
				if (!input.isEmpty())
				{
					for (String item : items)
					{
						if (item.toLowerCase().startsWith(input.toLowerCase()))
						{
							model.addElement(item);
						}
					}
				}
				cbInput.setPopupVisible(model.getSize() > 0);
				setAdjusting(cbInput, false);
			}
		});
		setLayout(new BorderLayout());
		add(cbInput, BorderLayout.SOUTH);
	}
}
