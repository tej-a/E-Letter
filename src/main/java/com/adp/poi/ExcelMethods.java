package com.adp.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;


@Service
public class ExcelMethods {
	
	public void createWorkbook() {
		
		
		
		try(FileOutputStream file=new FileOutputStream(new File("workBook.xlsx"));
				Workbook book1=new HSSFWorkbook();
				) {
			
			book1.write(file);			
			
		} catch (IOException  e) {
			e.printStackTrace();
		} 
		}
		
	}
	

