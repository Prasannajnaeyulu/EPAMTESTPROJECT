package com.epam.mantisbt.common;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Prasannanjaneyulu Padavala
 * <p>
 *  This is the Helper class to read data from excel (xls) file(s). It has different general purpose overloaded methods to make reading simpler.
 *  Like, Getting headers, getting rows and columns count, reading the data of specific column(s) etc..
 *  Usage: First create the object for this class by supplying path of the xls filename to read to its constructor. After that, we can call respective
 *  methods defined here as per specific needs.
 * </p>
 *
 */
public class Xls_Reader {

	public  String path;
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;
	private HSSFRow row   =null;
	private HSSFCell cell = null;

	public Xls_Reader(String path) {

		this.path=path;
		try {
			File f = new File(path);
			if(f.exists()){
				fis = new FileInputStream(f);
				workbook = new HSSFWorkbook(fis);
				fis.close();
			}
			else
			{
				throw new FileNotFoundException("Specified File not found in the classpath: "+ f.getAbsolutePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	// returns the row count in a sheet
	public int getRowCount(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		return getRowCount(index);
	}

	public int getRowCount(int index){
		if(index==-1)
			return 0;
		else{
			sheet = workbook.getSheetAt(index);
			int number=sheet.getLastRowNum()+1;
			return number;
		}
	}

	// returns the data from a cell
	public String getCellData(String sheetName,String colName,int rowNum){
		if(rowNum <=0)
			return "";

		int index = workbook.getSheetIndex(sheetName);

		return getCellData(index, colName, rowNum);
	}


	public String getCellData(int index,String colName,int rowNum){
		try{
			int col_Num=-1;
			if(index==-1)
				return "";

			sheet = workbook.getSheetAt(index);
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num=i;
			}
			if(col_Num==-1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);
			if(row==null)
				return "";
			cell = row.getCell(col_Num);

			if(cell==null)
				return "";
			//System.out.println(cell.getCellType());
			if(cell.getCellType()==Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ){
				String cellText  = String.valueOf(cell.getNumericCellValue());
				return cellText;
			}
			else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
				return ""; 
			else 
				return String.valueOf(cell.getBooleanCellValue());

		}
		catch(Exception e){

			e.printStackTrace();
			return "row "+rowNum+" or column "+colName +" does not exist in xls";
		}

	}

	// returns the data from a cell
	public String getCellData(String sheetName,int colNum,int rowNum){
		try{
			if(rowNum <=0)
				return "";

			int index = workbook.getSheetIndex(sheetName);

			if(index==-1)
				return "";


			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);
			if(row==null)
				return "";
			cell = row.getCell(colNum);
			if(cell==null)
				return "";

			if(cell.getCellType()==Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ){

				String cellText  = String.valueOf(cell.getNumericCellValue());
				return cellText;
			}else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
				return "";
			else 
				return String.valueOf(cell.getBooleanCellValue());
		}
		catch(Exception e){
			e.printStackTrace();
			return "row "+rowNum+" or column "+colNum +" does not exist in xls";
		}
	}

	// find whether sheets exists	
	public boolean isSheetExist(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1){
			index=workbook.getSheetIndex(sheetName.toUpperCase());
			if(index==-1)
				return false;
			else
				return true;
		}
		else
			return true;
	}

	// returns number of columns in a sheet	
	public int getColumnCount(String sheetName){
		// check if sheet exists
		if(!isSheetExist(sheetName))
			return -1;

		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);

		if(row==null)
			return -1;

		return row.getLastCellNum();
	}

	//get rownumber of a particular cell from a sheet named <sheetName>
	public int getCellRowNum(String sheetName,String colName,String cellValue){

		for(int i=2;i<=getRowCount(sheetName);i++){
			if(getCellData(sheetName,colName , i).equalsIgnoreCase(cellValue)){
				return i;
			}
		}
		return -1;
	}
	//get rownumber of a particular cell from a sheet at index
	public int getCellRowNum(int index,String colName,String cellValue){
		Sheet sheet = workbook.getSheetAt(index);
		return getCellRowNum(sheet.getSheetName(), colName, cellValue);
	}

	public Map<String,String> readData(int sheetindex){
		Map<String, String> testdatamap = new HashMap<String, String>();
		int rowcount = getRowCount(sheetindex);

		for(int i=2;i<=rowcount;i++){ //first row is header. So, Ignore it
			String property = getCellData(sheetindex, "Property", i);
			String value = getCellData(sheetindex, "Value", i);
			testdatamap.put(property, value);
		}
		return testdatamap;
	}
}