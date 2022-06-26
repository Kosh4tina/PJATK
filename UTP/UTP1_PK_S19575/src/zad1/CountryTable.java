package zad1;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CountryTable extends AbstractTableModel {
    private List data;
    private String[] columns;
    final Class[] columnClass = new Class[]{
        ImageIcon.class, String.class, String.class, Integer.class
    };

    public CountryTable(String file) {
        data = new ArrayList<Country>();
        try{
            BufferedReader reader =
                    new BufferedReader(new FileReader(file));
            String tmp = reader.readLine();
            String[] tmpStrArr;
            columns = tmp.split("\\t");
            while((tmp = reader.readLine()) != null){
                tmpStrArr = tmp.split("\\t");

                data.add(new Country(new ImageIcon(new ImageIcon("data/" + tmpStrArr[0] + ".gif").getImage().getScaledInstance(60,40,2)), tmpStrArr[1], tmpStrArr[2], Integer.parseInt(tmpStrArr[3].replaceAll(" ", ""))));

                System.out.println(tmp);
            }
            reader.close();
        }catch (ArrayStoreException ex) {
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public JTable create(){
        JTable table = new JTable(this){
            public TableCellRenderer getCellRenderer(int row, int column){
                return super.getCellRenderer(row, column);
            }
            public Component prepareRendererr(TableCellRenderer renderer, int row, int col){
                Component c = renderer.getTableCellRendererComponent(this,getValueAt(row,col),false,false,row,col);
                if (col == 3){
                    int population = (int)getValueAt(row,col);
                    if (population > 20000000){
                        c.setForeground(Color.RED);
                    }else{
                        c.setForeground(super.getForeground());
                    }
                }
                return c;
            }};
        table.setRowHeight(37);
        table.setPreferredScrollableViewportSize(new Dimension(530,500));
        return table;
    }
    public boolean isCellEditable(int row,int column){
        return true;
    }
    public Class<?>getColumnClass(int columnIndex){
        return  columnClass[columnIndex];
    }
    public String getColumnName(int c){
        return columns[c];
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount(){
        return columns.length;
    }
    public Object getValueAt(int rowIndex,int columnIndex){
        Country c = (Country) data.get(rowIndex);
        switch (columnIndex){
            case 0:
                return c.getFlag();
            case 1:
                return c.getCountryName();
            case 2:
                return c.getCapital();
            case 3:
                return c.getPopulation();
                default:
                    return "";
        }
    }
}
