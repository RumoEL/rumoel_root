package com.github.rumoel.rumosploit.client.gui.task;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.github.rumoel.rumosploit.client.header.Header;
import com.github.rumoel.rumosploit.tasks.BotTaskAnswer;

public class TasksTableModel implements TableModel {

	private Set<TableModelListener> listeners = new HashSet<>();

	private ArrayList<Field> fields = new ArrayList<>();

	public TasksTableModel() {
		Field[] arfields = BotTaskAnswer.class.getDeclaredFields();
		for (Field field : arfields) {
			if (field.getName().contains("serialVersionUID")) {
				continue;
			}
			fields.add(field);
		}
	}

	@Override
	public void addTableModelListener(TableModelListener listener) {
		listeners.add(listener);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return fields.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return fields.get(columnIndex).getName();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
			BotTaskAnswer bean = Header.getBotTaskAnswers().get(rowIndex);
			Field field = BotTaskAnswer.class.getField(fields.get(columnIndex).getName());

			Object data = field.get(bean);
			if (field.getName().equals("output")) {
				return new String((byte[]) data, StandardCharsets.UTF_8);
			}

			return data;
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public int getRowCount() {
		return Header.getBotTaskAnswers().size();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		// IGNORE
	}

}
