package com.adp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adp.dto.OfferGenerationDetails;
import com.adp.entity.CandidateInfo;
import com.adp.exception.UserException;
import com.adp.repository.CandidateInfoRepository;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;

@Service
public class GenerateOfferLettersService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CandidateInfoRepository candidateInfoRepository;

	@Autowired
	CandidateInfoService candidateInfoService;

	FileInputStream fis = null;
	XWPFDocument document = null;
	InputStream doc1 = null;
	XWPFDocument document1 = null;
	OutputStream out = null;
	String date = null;

	static final  double BASIC_SALARY_PERCENTAGE = 0.446388840278993;
	static final double HRA_PERCENTAGE = 0.192875178120546;
	static final double FLEXIBLE_BENEFITS_PERCENTAGE = 0.089277768055798;
	static final double BONUS_PERCENTAGE =  0.089277768055798;
	static final double PF_PERCENTAGE = 0.053558661033474;
	static final double GRATUITY_PERCENTAGE = 0.021479463013424;
	static final double VPI_PERCENTAGE = 0.107142321441963;
	static final double VPI_MAX_PERCENTAGE = 0.160714315475446;
	static final String PATH= "C:\\final-ELetter\\ELetter\\src\\main\\resources\\OfferLetters\\";
	String gender = null;

	





	public void generateOfferLetter(List<String> emails, OfferGenerationDetails details  ) throws IOException, UserException {




		for(String email: emails) {

			CandidateInfo candidateInfo = candidateInfoRepository.getReferenceById(email);
			offerLetterGeneration(candidateInfo,details);
		}
	}

	public void offerLetterGeneration(CandidateInfo info, OfferGenerationDetails details) throws IOException, UserException {
		date = LocalDate.now().toString();
		try {

			if(info.getPermanentState().equals("Telangana")) {
				fis = new FileInputStream("C:\\final-ELetter\\ELetter\\src\\main\\resources\\OfferLetter_template_no_relocation.docx");

			}
			else {
				fis = new FileInputStream("C:\\final-ELetter\\ELetter\\src\\main\\resources\\OfferLetter_template_relocation.docx");

			}


			document = new XWPFDocument(fis);


			Long compensation = Long.parseLong(details.getCompensation());

			switch (info.getGender()) {
			case "Male":
				gender="Mr.";
				break;
			case "Female":
				gender="Miss.";
				break;
			default:
				gender="Mx.";
				break;

			}

			String address  = info.getCurrentHouseNumber() + " " +
					info.getCurrentCity() + " " +
					info.getCurrentState();

			double  basicSalary  = (compensation*BASIC_SALARY_PERCENTAGE)/12;
			double hra = (compensation*HRA_PERCENTAGE)/12;
			double flexibleBenefits = (compensation*FLEXIBLE_BENEFITS_PERCENTAGE)/12;
			double bonus = (compensation*BONUS_PERCENTAGE)/12;
			double PF = (compensation*PF_PERCENTAGE)/12;
			double gratuity =( compensation*GRATUITY_PERCENTAGE)/12;

			
			double VPI = (compensation*VPI_PERCENTAGE);
			double VPIMax = (compensation*VPI_MAX_PERCENTAGE);

			int grossComp = Math.toIntExact((long) basicSalary) +
					Math.toIntExact((long) hra) + 
					Math.toIntExact((long) flexibleBenefits) +
					Math.toIntExact((long) bonus) +
					Math.toIntExact((long) PF) +
					Math.toIntExact((long) gratuity);

			int grossCompAnnual = 	Math.toIntExact((long) basicSalary*12) +
					Math.toIntExact((long) hra*12) + 
					Math.toIntExact((long) flexibleBenefits*12) +
					Math.toIntExact((long) bonus*12) +
					Math.toIntExact((long) PF*12) +
					Math.toIntExact((long) gratuity*12);




			for(XWPFParagraph paragraph : document.getParagraphs()){
				List<XWPFRun> runs = paragraph.getRuns();
				for(XWPFRun run: runs) {
					replaceText(run, "today",date);
					replaceText(run, "gender",gender);
					replaceText(run, "name", info.getName());
					replaceText(run, "address",address);
					replaceText(run, "position",details.getDesignation());
					replaceText(run, "grade",details.getGrade());
					replaceText(run, "JOIN",details.getJoiningDate().toString());
					replaceText(run, "compensation",details.getCompensation());
					replaceText(run, "locationn",details.getPlace());

				}
			}


			XWPFTable table = document.getTableArray(0);
			replaceCellValue(table, "basic",String.valueOf(Math.toIntExact((long) basicSalary)));
			replaceCellValue(table, "!",String.valueOf(Math.toIntExact((long) basicSalary)*12));
			replaceCellValue(table, "hra",String.valueOf(Math.toIntExact((long) hra)));					
			replaceCellValue(table, "@",String.valueOf(Math.toIntExact((long) hra*12)));					
			replaceCellValue(table, "flex",String.valueOf(Math.toIntExact((long) flexibleBenefits)));					
			replaceCellValue(table, "#",String.valueOf(Math.toIntExact((long) flexibleBenefits*12)));					
			replaceCellValue(table, "bonus",String.valueOf(Math.toIntExact((long) bonus))); 					
			replaceCellValue(table, "$",String.valueOf(Math.toIntExact((long) bonus*12)));					
			replaceCellValue(table, "pf",String.valueOf(Math.toIntExact((long) PF)));					
			replaceCellValue(table, "^",String.valueOf(Math.toIntExact((long) PF*12)));					
			replaceCellValue(table, "gra",String.valueOf(Math.toIntExact((long) gratuity)));					
			replaceCellValue(table, "&",String.valueOf(Math.toIntExact((long) gratuity*12)));					
			replaceCellValue(table, "gross",String.valueOf(grossComp)); 				
			replaceCellValue(table, "annual",String.valueOf(grossCompAnnual));		






			table = document.getTableArray(1);
			replaceCellValue(table, "@",String.valueOf(Math.toIntExact((long) VPI)));					
			replaceCellValue(table, "VAdditional",String.valueOf(Math.toIntExact((long) VPIMax))); 					
			replaceCellValue(table, "ctc",String.valueOf(details.getCompensation()));										



			String docPATH = PATH+info.getEmail()+".docx";

			FileOutputStream fos = new FileOutputStream(docPATH);
			document.write(fos);
			fos.close();



			String pdfPATH = PATH+info.getEmail()+".pdf";

			doc1 = new FileInputStream(new File(docPATH));
			document1 = new XWPFDocument(doc1);


			PdfOptions options = PdfOptions.create();

			out = new FileOutputStream(new File(pdfPATH));


			PdfConverter.getInstance().convert(document1, out, options);

			File file = new File(pdfPATH);


			if(candidateInfoService.saveOfferLetter(file, info.getEmail())) {
				logger.info("pdf saved");
			}
			candidateInfoService.updateOfferLetterGenerationStatus(info.getEmail());
			


		}
		finally {
			 File file
	            = new File(PATH+info.getEmail()+".docx");
			 File file1
	            = new File(PATH+info.getEmail()+".pdf");
	 
	        if (file.delete() && file1.delete()) {
	            logger.info("File deleted successfully");
	        }
	        else {
	        	logger.info("Files not deleted ");
	        }
			

		}
	}

	private static void replaceText(XWPFRun run, String placeholder, String replacement) {

		String text = run.getText(0);
		if (text != null && text.contains(placeholder)) {

			text = text.replace(placeholder,replacement);
			run.setText(text, 0);
		}

	}

	private static void replaceCellValue(XWPFTable table, String oldValue, String newValue) {

		for (XWPFTableRow row : table.getRows()) {

			for (XWPFTableCell cell : row.getTableCells()) {

				for (XWPFParagraph paragraph : cell.getParagraphs()) {

					for (int i = 0; i < paragraph.getRuns().size(); i++) {

						XWPFRun run = paragraph.getRuns().get(i);

						String text = run.getText(0);


						if (text != null && text.contains(oldValue)) {

							text = text.replace(oldValue, newValue);

							run.setText(text, 0);

						}

					}

				}

			}

		}

	}




}







