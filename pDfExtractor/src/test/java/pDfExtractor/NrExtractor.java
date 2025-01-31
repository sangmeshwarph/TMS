package pDfExtractor;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class NrExtractor {
	
	
	@BeforeTest
	public void pdf() throws IOException {
		
		String pdfFilePath = "E:\\\\KSP\\\\Downloads&Work 13 JAN\\\\BNG APC 3064\\\\APC_3064_ME_NR _16th jan.pdf";
		
		//E:\\KSP\\Download&Work 23-SEP\\Nominal_Roll_APC3064_DKM_03-10-2024.pdf
		//E:\\KSP\\Downloads&Work 13 JAN\\BNG APC 3064\\APC_3064_ME_NR _16th jan.pdf
		
		String excelFilePath = "C:\\Users\\Admin\\Downloads\\NominalRollExtractor.xlsx";
		
		String ExtractText=extractTextFromPDF(pdfFilePath);
		if (ExtractText == null || ExtractText.isEmpty()) {
            System.out.println("No text extracted from the PDF.");
            return;
        }
		
		String[] lines=ExtractText.split("\n");
		
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Extracted Text");

            // Step 4: Write each line into a new row in Excel
            int rowNum = 0;
            for (String line : lines) {
                Row row = sheet.createRow(rowNum++);
                Cell cell = row.createCell(0); // Writing in the first column
                cell.setCellValue(line.trim()); // Write line to cell, trimming any leading/trailing spaces
            }

            // Step 5: Write the Excel file to disk
            try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
                workbook.write(fileOut);
            }
            System.out.println("Data has been extracted and saved to Excel at: " + excelFilePath);
            
            FileOutputStream fileClose=new FileOutputStream(excelFilePath);
            		workbook.write(fileClose);
            		fileClose.close();
            workbook.close();
           
            		
        }
		
	}

	private static String extractTextFromPDF(String pdfFilePath) {
		File pdf=new File(pdfFilePath);
		if (!pdf.exists()) {
            System.out.println("File not found: " + pdfFilePath);
            return null;
        }
		
		try (PDDocument document = PDDocument.load(pdf)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document); // Extract text from PDF
        } catch (IOException e) {
            System.out.println("Error extracting text from PDF: " + e.getMessage());
            return null;
        }
    }


	
	
	
	@AfterTest
	public void excel() throws IOException
	{
		String input="C:\\Users\\Admin\\Downloads\\NominalRollExtractor.xlsx";
		String output="C:\\Users\\Admin\\Downloads\\ApplicantWiseData.xlsx";
		
		FileInputStream file=new FileInputStream(input);
		Workbook book=new XSSFWorkbook(file);
		Sheet inputsheet=book.getSheetAt(0);
		
		Workbook applicantdata=new XSSFWorkbook();
		Sheet outputsheet=applicantdata.createSheet("ApplicantWiseData");
		
		int outputRowIndex = 0;
        int skipRows = 0;
        
        for (int rowIndex = 16; rowIndex < inputsheet.getPhysicalNumberOfRows(); rowIndex++) {
            if (skipRows > 0) {
                skipRows--;
                continue;
            }
            
            Row row = inputsheet.getRow(rowIndex);
            if (row == null) continue;

            // Check if the cell contains the specific string, and if so, skip the next 17 rows
           
            
            for (int i = 0; i < 30; i++) {
                Row currentRow = inputsheet.getRow(rowIndex + i);
                if (currentRow != null) {
                    Cell dataCell = currentRow.getCell(0); // 1st column
                    if (dataCell != null) {
                        // Write data to output sheet
                        Row outputRow = outputsheet.createRow(outputRowIndex++);
                        Cell outputCell = outputRow.createCell(0);
                        outputCell.setCellValue(dataCell.getStringCellValue());
                    }
                }
            }

            skipRows=17;
            
            rowIndex += 29; // Skip 5 rows after this batch of data
        
		
	}
        FileOutputStream fos = new FileOutputStream(output);
        applicantdata.write(fos);
        fos.close();
        book.close();
        file.close();

        System.out.println("Data extraction completed and saved to " + output);
	}

}
