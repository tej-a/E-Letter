package com.adp.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.adp.entity.BasicCandidate;

public class BasicCadidateService {
	
	private BasicCadidateService() {
	}
	
	public static boolean isValidExcelFile(MultipartFile file) {
		return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	}
	
	public static List<BasicCandidate> getBasicCadidateDetailsFromExcel(InputStream inputStream){
		List<BasicCandidate> candidates=new ArrayList<>();
		
		try(
			XSSFWorkbook workbook=new XSSFWorkbook(inputStream);
				) {
			
			XSSFSheet sheet=workbook.getSheet("Sheet1");
			
			int rowIndex=0;
			
			for(Row row:sheet) {
				if(rowIndex==0) {
					rowIndex++;
					continue;
				}
				BasicCandidate basicCandidate=new BasicCandidate();
				Iterator<Cell> cellIterator=row.iterator();
				int cellIndex=0;
				while(cellIterator.hasNext()) {
					Cell cell =cellIterator.next();
					switch(cellIndex) {
					case 0:
						basicCandidate.setEmail(cell.getStringCellValue());
						break;
					case 1:
						basicCandidate.setName(cell.getStringCellValue());
						break;
					case 2:
						basicCandidate.setPhnNumber((long)cell.getNumericCellValue());
						break;
					case 3:
						basicCandidate.setCollegeName(cell.getStringCellValue());
						break;
					default:
						continue;
					}
					cellIndex++;
				}
				candidates.add(basicCandidate);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return candidates;
	}
}
