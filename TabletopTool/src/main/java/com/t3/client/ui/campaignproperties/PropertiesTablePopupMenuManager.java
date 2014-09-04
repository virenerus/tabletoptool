package com.t3.client.ui.campaignproperties;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;

import com.jidesoft.grid.TreeTable;
import com.jidesoft.swing.JidePopupMenu;
import com.t3.client.ui.campaignproperties.PropertyTypesTableModel.PropertyTypeRow;
import com.t3.model.tokenproperties.old.TokenProperty;
import com.t3.model.tokenproperties.valuetypes.ValueType;

public class PropertiesTablePopupMenuManager extends MouseAdapter {

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger()) {
			TreeTable source = (TreeTable) e.getSource();
			int row = source.rowAtPoint(e.getPoint());
			int column = source.columnAtPoint(e.getPoint());
			if(row!=-1) {
				if (!source.isRowSelected(row))
					source.changeSelection(row, column, false, false);
	
				PropertyTypeRow ptr=(PropertyTypeRow)source.getRowAt(row);
				
				JidePopupMenu popup=new JidePopupMenu();
				
				if(ptr.getProperty().getType()==ValueType.STRUCT)
					popup.add(new AddChildAction("Create Struct Child",ptr,-1));
				
				if(ptr.getParent() instanceof PropertyTypeRow && 
						((PropertyTypeRow)ptr.getParent()).getProperty().getType()==ValueType.STRUCT) {
					
					
					PropertyTypeRow parent=((PropertyTypeRow)ptr.getParent());
					int ptrIndex=parent.getChildIndex(ptr);
					popup.add(new AddChildAction("Create Sibling Above",parent, ptrIndex));
					popup.add(new AddChildAction("Create Sibling Below",parent, ptrIndex+1));
					
					popup.addSeparator();
					
					popup.add(new MoveChildDownAction("Move Up",parent, ptrIndex-1));
					popup.add(new MoveChildDownAction("Move Down",parent, ptrIndex));
					
					popup.addSeparator();
					
					popup.add(new RemoveChildAction("Remove Row",parent, ptrIndex));
				}
				
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	private static class AddChildAction extends AbstractAction {

		private PropertyTypeRow	parentRow;
		private int	childIndex;

		public AddChildAction(String name, PropertyTypeRow parentRow, int childIndex) {
			super(name);
			this.parentRow=parentRow;
			this.childIndex=childIndex;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			PropertyTypeRow child=parentRow.getTreeTableModel().new PropertyTypeRow(new TokenProperty(""));
			if(childIndex==-1)
				parentRow.addChild(child);
			else
				parentRow.addChild(childIndex,child);
		}
	}
	
	private static class MoveChildDownAction extends AbstractAction {

		private int	childIndex;
		private PropertyTypeRow	parentRow;

		public MoveChildDownAction(String name, PropertyTypeRow parentRow, int childIndex) {
			super(name);
			this.parentRow=parentRow;
			this.childIndex=childIndex;
			
			if(childIndex<0 || childIndex>=parentRow.getChildrenCount()-1)
				this.setEnabled(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			parentRow.moveDownChild(parentRow.getChildAt(childIndex));
		}
	}
	
	private static class RemoveChildAction extends AbstractAction {

		private PropertyTypeRow	parentRow;
		private int	childIndex;

		public RemoveChildAction(String name, PropertyTypeRow parentRow, int childIndex) {
			super(name);
			this.parentRow=parentRow;
			this.childIndex=childIndex;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			parentRow.removeChild(parentRow.getChildAt(childIndex));
		}
		
	}
}
