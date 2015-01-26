package com.tcl.wonder.adclient.view.tabpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcl.wonder.adclient.entity.Ad;
import com.tcl.wonder.adclient.entity.Video;
import com.tcl.wonder.adclient.entity.Video.Status;
import com.tcl.wonder.adclient.service.AdWonderService;
import com.tcl.wonder.adclient.utlis.UIUtils;
import com.tcl.wonder.adclient.utlis.Utilities;
import com.tcl.wonder.adclient.utlis.XmlUtils;
import com.tcl.wonder.adclient.view.Contents;
import com.tcl.wonder.adclient.view.component.NTable;
import com.tcl.wonder.adclient.view.table.MyTableModel;
import com.tcl.wonder.adclient.view.table.StatusCellRenderer;
import com.tcl.wonder.adclient.view.worker.Pair;

/**
 * �����ϴ���
 * 
 * @author liuran 2015��1��23��
 *
 */
public class BatchInsertPane extends TabPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(InsertAdPane.class);

	final String[] columnNames = { "���", "���ID", "�������", "��Ƶ·��",// ���������final����
			"״̬" };

	private JTextField videopathFlied;

	private JTextField xmlpathFlied;

	private JButton videopathButton;

	private JButton xmlpathButton;

	private JButton addButton;

	private JButton resetButton;

	private JLabel errorLabel;

	private JTable table;

	private Map<String, Video> videos = new HashMap<String, Video>();

	private Map<String, Ad> adsmap;

	private AdWonderService client = new AdWonderService();

	public BatchInsertPane()
	{
		layoutUI();
	}

	private void layoutUI()
	{

		JPanel adPanel = new JPanel();

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints cLable = UIUtils.getGridBagConstraints(1, 0, 0);
		GridBagConstraints cText = UIUtils.getGridBagConstraints(4, 1, 0);
		GridBagConstraints cButton = UIUtils.getGridBagConstraints(0, 0, 0);
		adPanel.setLayout(gridbag);

		JLabel videpathLabel = new JLabel("�����Ƶ�ļ���:");
		JLabel xmlpathLabel = new JLabel("�����Ϣxml·��:");
		errorLabel = new JLabel();
		errorLabel.setFont(new Font("Monospaced", Font.ITALIC, 20));
		errorLabel.setForeground(Color.RED);

		videopathFlied = new JTextField();
		xmlpathFlied = new JTextField();

		videopathButton = new JButton("ѡ��...");
		xmlpathButton = new JButton("ѡ��...");
		addButton = new JButton("�ϴ�");
		resetButton = new JButton("����");

		gridbag.setConstraints(videpathLabel, cLable);
		adPanel.add(videpathLabel);
		gridbag.setConstraints(videopathFlied, cText);
		adPanel.add(videopathFlied);
		gridbag.setConstraints(videopathButton, cButton);
		adPanel.add(videopathButton);

		gridbag.setConstraints(xmlpathLabel, cLable);
		adPanel.add(xmlpathLabel);
		gridbag.setConstraints(xmlpathFlied, cText);
		adPanel.add(xmlpathFlied);
		gridbag.setConstraints(xmlpathButton, cButton);
		adPanel.add(xmlpathButton);

		table = createTable();
		JScrollPane progressPabel = new JScrollPane(table);
		gridbag.setConstraints(progressPabel, UIUtils.getGridBagConstraints(0, 0, 0, 1));
		adPanel.add(progressPabel);

		JPanel operationPanel = new JPanel();

		this.setLayout(new BorderLayout());
		this.setOpaque(true);
		this.add("North", errorLabel);
		this.add("Center", adPanel);
		this.add(operationPanel, BorderLayout.SOUTH);
		operationPanel.add(addButton);
		operationPanel.add(resetButton);

		logger.debug("add completely");

		addEvent();

		List<Video> list = new ArrayList<Video>();
		list.add(new Video("11", "11", "111", Status.NO_START));
		// list.add(new Video("11", "11", "111", Status.STARTED));
		list.add(new Video("11", "11", "111", Status.STARTED));
		list.add(new Video("11", "11", "111", Status.STARTED));
		list.add(new Video("11", "11", "111", Status.STARTED));
		list.add(new Video("11", "11", "111", Status.STARTED));
		list.add(new Video("11", "", "111", Status.STARTED));
		list.add(new Video("11", "11", "111", Status.STARTED));
		list.add(new Video("11", "11", "111", Status.SUCCESS));
		list.add(new Video("11", "11", "111", Status.FAILED));
		updateTable(list);

	}

	private JTable createTable()
	{
		MyTableModel model = new MyTableModel(columnNames);
		JTable table = new NTable(model);

		table.setPreferredScrollableViewportSize(new Dimension(600, 100));// ���ñ��Ĵ�С
		table.setRowHeight(35);// ����ÿ�еĸ߶�Ϊ20
		table.setRowMargin(5);// �����������е�Ԫ��ľ���
		table.setRowSelectionAllowed(true);// ���ÿɷ�ѡ��.Ĭ��Ϊfalse
		table.setGridColor(Color.black);// ���������ߵ���ɫ
		// table.setBackground(Color.lightGray);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSelectionBackground(Color.GREEN);// ������ѡ���еı���ɫ
		table.setSelectionForeground(Color.BLUE);// ������ѡ���е�ǰ��ɫ

		TableColumnModel columnModel = table.getColumnModel();

		TableColumn firsetColumn = columnModel.getColumn(0);
		firsetColumn.setPreferredWidth(Contents.INT_WIGHT);
		firsetColumn.setMaxWidth(100);
		firsetColumn.setMinWidth(10);

		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		firsetColumn.setCellRenderer(render);

		StatusCellRenderer cellRenderer = new StatusCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumn statusColumn = columnModel.getColumn(4);
		statusColumn.setPreferredWidth(Contents.INT_WIGHT);
		statusColumn.setMaxWidth(100);
		statusColumn.setMinWidth(10);
		statusColumn.setCellRenderer(cellRenderer);
		// statusColumn.setCellEditor(new ButtonCellEidtor());

		return table;
	}

	private void addEvent()
	{
		// ������ʾ��Ԫ���ֵ
		table.addMouseMotionListener(new MouseAdapter()
		{
			public void mouseMoved(MouseEvent e)
			{
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				if (row > -1 && col > -1)
				{
					table.setRowSelectionInterval(row, row);
					Object value = table.getValueAt(row, col);
					if (null != value && !"".equals(value))
						table.setToolTipText(value.toString());// ������ʾ��Ԫ������
					else
						table.setToolTipText(null);// �ر���ʾ
				}
			}
		});

		table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				Point point = e.getPoint();
				final int col = table.columnAtPoint(point);
				final int row = table.rowAtPoint(point);

				if (col == 4
						&& !Utilities.isStringEmpty((String) table.getValueAt(row,
								Contents.TABLE_NAME))
						&& ((Status) table.getValueAt(row, col) == Status.NO_START || (Status) table
								.getValueAt(row, col) == Status.FAILED))
				{
					// ����
					final String id = (String) table.getValueAt(row, 1);
					final Video video = videos.get(id);
					
					video.setStatus(Status.STARTED);
					videos.put(id, video);
					table.setValueAt(video.getStatus(), row, 4);
					
					new Thread()
					{
						public void run()
						{
							boolean success = client.upload(video, adsmap.get(id));
							if (success)
							{
								video.setStatus(Status.SUCCESS);
							} else
							{
								video.setStatus(Status.FAILED);
							}
							
							table.setValueAt(video.getStatus(), row, 4);
						}
					}.start();
					
				}
			}
		});

		videopathButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int option = fc.showOpenDialog(null);
				if (option == JFileChooser.APPROVE_OPTION)
				{
					File dir = fc.getSelectedFile();
					if (dir != null)
					{
						String videopath = dir.getAbsolutePath();
						logger.info("select a video,video path:{} ", videopath);
						videopathFlied.setText(videopath);
						loadInfo();
					}
				}

			}

		});

		xmlpathButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fd = new JFileChooser();
				fd.setFileFilter(new FileNameExtensionFilter("XML file", "xml"));
				int option = fd.showOpenDialog(null);
				if (option == JFileChooser.APPROVE_OPTION)
				{
					File dir = fd.getSelectedFile();
					if (dir != null)
					{
						String videopath = dir.getAbsolutePath();
						logger.info("select a video,video path:{} ", videopath);
						xmlpathFlied.setText(videopath);
						loadInfo();
					}
				}

			}

		});

		resetButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				videopathFlied.setText("");
				xmlpathFlied.setText("");
				MyTableModel tableModel = (MyTableModel) table.getModel();
				tableModel.setRowCount(0);// ���ԭ����
			}
		});

		addButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				addButton.setEnabled(false);
				if (validateFiled())
				{
					bashUpload();

				}
			}
		});

	}

	private void loadInfo()
	{
		String videoPathStr = videopathFlied.getText().trim();
		String xmlfileStr = xmlpathFlied.getText().trim();
		if (Utilities.isStringEmpty(videoPathStr) || Utilities.isStringEmpty(xmlfileStr))
		{
			return;
		}
		if (!Utilities.isDirectoryExist(videoPathStr) || !Utilities.isFileExist(xmlfileStr))
		{
			return;
		}

		File xmlFile = new File(xmlfileStr);
		adsmap = XmlUtils.getAdMapFromXml(xmlFile);

		File videoPath = new File(videoPathStr);

		File[] videoFiles = videoPath.listFiles(new FilenameFilter()
		{

			@Override
			public boolean accept(File dir, String name)
			{
				if (name.endsWith(".mp4") || name.endsWith(".avi"))
				{
					return true;
				}
				return false;
			}
		});
		for (File file : videoFiles)
		{
			String filename = file.getName();
			String fileId = filename.substring(0, filename.indexOf('.'));
			String meta = fileId + ".txt";
			String metaPath = file.getParent() + "/" + meta;
			String id = Utilities.getAdIdFromMetafile(metaPath);
			if (id == null)
			{
				id = fileId;
			}
			Video video = new Video();
			video.setId(id);
			video.setMetaPath(metaPath);
			if (adsmap.keySet().contains(id))
			{
				video.setName(adsmap.get(id).getName());
			}
			video.setPath(file.getAbsolutePath());
			videos.put(id, video);
		}

		updateTable();
	}

	public void updateTable()
	{
		MyTableModel tableModel = (MyTableModel) table.getModel();
		tableModel.setRowCount(0);// ���ԭ����
		// �������
		int i = 1;// ����
		for (Entry<String, Video> entry : videos.entrySet())
		{
			List<Object> rowList = new ArrayList<Object>();
			Video video = entry.getValue();
			rowList.add(i++);
			rowList.add(video.getId());
			rowList.add(video.getName());
			rowList.add(video.getPath());
			rowList.add(video.getStatus());
			// ������ݵ����
			tableModel.addRow(rowList);
		}
		table.updateUI();
	}

	public void updateTable(List<Video> list)
	{
		MyTableModel tableModel = (MyTableModel) table.getModel();
		tableModel.setRowCount(0);// ���ԭ����
		// �������
		int i = 1;// ����
		for (Video video : list)
		{
			List<Object> rowList = new ArrayList<Object>();
			rowList.add(i++);
			rowList.add(video.getId());
			rowList.add(video.getName());
			rowList.add(video.getPath());
			rowList.add(video.getStatus());
			// ������ݵ����
			tableModel.addRow(rowList);
		}
		table.updateUI();
	}

	private boolean validateFiled()
	{
		if (videopathFlied.getText().trim().isEmpty())
		{
			errorLabel.setText("��ѡ������Ƶ�ļ���");
			return false;
		}
		if (xmlpathFlied.getText().trim().isEmpty())
		{
			errorLabel.setText("��ѡ����meta�ļ���");
			return false;
		}
		return true;
	}

	@Override
	public void refresh()
	{
		// TODO Auto-generated method stub
		// updateTableUI();
	}

	private void bashUpload()
	{
		final int rows = table.getRowCount();

		new SwingWorker<Boolean, Pair<Integer, String>>()
		{

			@Override
			protected void process(List<Pair<Integer, String>> chunks)
			{
				System.out.println(chunks);
				Pair<Integer, String> pair = chunks.get(0);

				Integer i = pair.getKey();
				String id = pair.getValue();
				Video video = videos.get(id);
				System.out.println(pair + "" + video.getStatus());
				table.setValueAt(video.getStatus(), i, 4);
			}

			@Override
			protected void done()
			{
				addButton.setEnabled(true);
			}

			@Override
			protected Boolean doInBackground() throws Exception
			{
				for (int i = 0; i < rows; i++)
				{
					final String id = (String) table.getValueAt(i, 1);
					Video video = videos.get(id);

					// û��infoƥ��Ͳ��ǿ�ʼ״̬�Ĳ�������
					if (Utilities.isStringEmpty(video.getName())
							|| video.getStatus() != Status.NO_START
							|| video.getStatus() != Status.FAILED)
					{
						continue;
					}

					video.setStatus(Status.STARTED);
					videos.put(id, video);
					table.setValueAt(video.getStatus(), i, 4);
					boolean success = client.upload(video, adsmap.get(id));
					if (success)
					{
						video.setStatus(Status.SUCCESS);
					} else
					{
						video.setStatus(Status.FAILED);
					}
					videos.put(id, video);
					publish(new Pair<Integer, String>(i, id));
				}

				return null;
			}

		}.execute();

	}

}
