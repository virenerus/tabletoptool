/*
 *  This software copyright by various authors including the RPTools.net
 *  development team, and licensed under the LGPL Version 3 or, at your
 *  option, any later version.
 *
 *  Portions of this software were originally covered under the Apache
 *  Software License, Version 1.1 or Version 2.0.
 *
 *  See the file LICENSE elsewhere in this distribution for license details.
 */

package com.t3.client.ui.lookuptable;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.t3.client.TabletopTool;
import com.t3.client.swing.AbeillePanel;
import com.t3.language.I18N;
import com.t3.model.LookupTable;
import com.t3.swing.ImagePanel;
import com.t3.swing.SwingUtil;
import com.t3.util.PersistenceUtil;

public class LookupTablePanel extends AbeillePanel<LookupTableImagePanelModel> {
	private static final long serialVersionUID = -4404834393567699280L;

	private ImagePanel imagePanel;
	private JDialog editorDialog;
	private EditLookupTablePanel editorPanel;

	public LookupTablePanel() {
		super("com/t3/client/ui/forms/lookupTablePanel.xml");
		panelInit();
	}

	public void updateView() {
		getButtonPanel().setVisible(TabletopTool.getPlayer().isGM());
		revalidate();
		repaint();
	}

	public JDialog getEditorDialog() {
		if (editorDialog == null) {
			editorDialog = new JDialog(TabletopTool.getFrame(), true);
			editorDialog.setSize(500, 400);
			editorDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			editorDialog.add(editorPanel);
			SwingUtil.centerOver(editorDialog, TabletopTool.getFrame());
		}
		return editorDialog;
	}

	public void initImagePanel() {
		imagePanel = new ImagePanel();
		imagePanel.setBackground(Color.white);
		imagePanel.setModel(new LookupTableImagePanelModel(this));
		imagePanel.setSelectionMode(ImagePanel.SelectionMode.SINGLE);
		imagePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					List<Object> ids = getImagePanel().getSelectedIds();
					if (ids == null || ids.size() == 0) {
						return;
					}
					LookupTable lookupTable = TabletopTool.getCampaign().getLookupTableMap().get(ids.get(0));
					if (lookupTable == null) {
						return;
					}
					TabletopTool.getFrame().getCommandPanel().getCommandTextArea().setText("/tbl \"" + lookupTable.getName() + "\"");
					TabletopTool.getFrame().getCommandPanel().commitCommand();
				}
			}
		});
		replaceComponent("mainForm", "imagePanel", new JScrollPane(imagePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
	}

	public JPanel getButtonPanel() {
		return (JPanel) getComponent("buttonPanel");
	}

	public void initEditorPanel() {
		editorPanel = new EditLookupTablePanel();
	}

	public JButton getNewButton() {
		return (JButton) getComponent("newButton");
	}

	public JButton getEditButton() {
		return (JButton) getComponent("editButton");
	}

	public JButton getDeleteButton() {
		return (JButton) getComponent("deleteButton");
	}

	public JButton getDuplicateButton() {
		return (JButton) getComponent("duplicateButton");
	}

	public JButton getRunButton() {
		return (JButton) getComponent("runButton");
	}

	public ImagePanel getImagePanel() {
		return imagePanel;
	}

	public JButton getImportButton() {
		return (JButton) getComponent("importButton");
	}

	public JButton getExportButton() {
		return (JButton) getComponent("exportButton");
	}

	public void initDuplicateButton() {
		getDuplicateButton().setMargin(new Insets(0, 0, 0, 0));
		getDuplicateButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Object> ids = getImagePanel().getSelectedIds();
				if (ids == null || ids.size() == 0) {
					return;
				}
				LookupTable lookupTable = new LookupTable(TabletopTool.getCampaign().getLookupTableMap().get(ids.get(0)));
				lookupTable.setName("Copy of " + lookupTable.getName());

				editorPanel.attach(lookupTable);

				getEditorDialog().setTitle(I18N.getString("LookupTablePanel.msg.titleNew"));
				getEditorDialog().setVisible(true);

				imagePanel.clearSelection();
				repaint();
			}
		});
	}

	public void initEditTableButton() {
		getEditButton().setMargin(new Insets(0, 0, 0, 0));
		getEditButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Object> ids = getImagePanel().getSelectedIds();
				if (ids == null || ids.size() == 0) {
					return;
				}
				LookupTable lookupTable = TabletopTool.getCampaign().getLookupTableMap().get(ids.get(0));

				editorPanel.attach(lookupTable);

				getEditorDialog().setTitle(I18N.getString("LookupTablePanel.msg.titleEdit"));
				getEditorDialog().setVisible(true);

			}
		});
	}

	public void initNewTableButton() {
		getNewButton().setMargin(new Insets(0, 0, 0, 0));
		getNewButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editorPanel.attach(null);

				getEditorDialog().setTitle(I18N.getString("LookupTablePanel.msg.titleNew"));
				getEditorDialog().setVisible(true);

				imagePanel.clearSelection();
				repaint();
			}
		});
	}

	public void initDeleteTableButton() {
		getDeleteButton().setMargin(new Insets(0, 0, 0, 0));
		getDeleteButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Object> ids = getImagePanel().getSelectedIds();
				if (ids == null || ids.size() == 0) {
					return;
				}
				LookupTable lookupTable = TabletopTool.getCampaign().getLookupTableMap().get(ids.get(0));

				if (TabletopTool.confirm("LookupTablePanel.confirm.delete", lookupTable.getName())) {
					TabletopTool.getCampaign().getLookupTableMap().remove(lookupTable.getName());
					TabletopTool.serverCommand().updateCampaign(TabletopTool.getCampaign().getCampaignProperties());

					imagePanel.clearSelection();
					repaint();
				}
			}
		});
	}

	public void initImportButton() {
		getImportButton().setMargin(new Insets(0, 0, 0, 0));
		getImportButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = TabletopTool.getFrame().getLoadTableFileChooser();

				if (chooser.showOpenDialog(TabletopTool.getFrame()) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				final File selectedFile = chooser.getSelectedFile();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Map<String, LookupTable> lookupTables = TabletopTool.getCampaign().getLookupTableMap();
							LookupTable newTable = PersistenceUtil.loadTable(selectedFile);
							Boolean alreadyExists = lookupTables.keySet().contains(newTable.getName());
							if (alreadyExists) {
								if (TabletopTool.confirm("LookupTablePanel.confirm.import", newTable.getName())) {
									lookupTables.remove(newTable.getName());
								} else {
									return;
								}
							}
							lookupTables.put(newTable.getName(), newTable);
							imagePanel.clearSelection();
							imagePanel.repaint();
							TabletopTool.serverCommand().updateCampaign(TabletopTool.getCampaign().getCampaignProperties());
						} catch (IOException ioe) {
							TabletopTool.showError("LookupTablePanel.error.loadFailed", ioe);
						}
					}
				});
			}
		});
	}

	public void initExportButton() {
		getExportButton().setMargin(new Insets(0, 0, 0, 0));
		getExportButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = TabletopTool.getFrame().getSaveTableFileChooser();

				if (chooser.showSaveDialog(TabletopTool.getFrame()) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				final File selectedFile = chooser.getSelectedFile();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						if (selectedFile.exists()) {
							if (selectedFile.getName().endsWith(".mttable")) {
								if (!TabletopTool.confirm("LookupTablePanel.confirm.export", selectedFile.getName())) {
									return;
								}
							} else if (!TabletopTool.confirm("LookupTablePanel.confirm.overwrite", selectedFile.getName())) {
								return;
							}
						}
						try {
							List<Object> ids = getImagePanel().getSelectedIds();
							if (ids == null || ids.size() == 0) {
								return;
							}
							LookupTable lookupTable = TabletopTool.getCampaign().getLookupTableMap().get(ids.get(0));
							PersistenceUtil.saveTable(lookupTable, selectedFile);
							TabletopTool.showInformation(I18N.getText("LookupTablePanel.info.saved", selectedFile.getName()));
						} catch (IOException ioe) {
							ioe.printStackTrace();
							TabletopTool.showError("LookupTablePanel.error.saveFailed", ioe);
						}
					}
				});
			}
		});
	}
}
