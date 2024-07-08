package ddt3;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

class Auth3 {

    @Test
    void test() throws IOException {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
		String testDate = formatter.format(date);
		String testerName = "Arpapat Yipram";
	   
		String path = "D:\\testdata.xlsx";
		FileInputStream fs = new FileInputStream(path);

		//Creating a workbook
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		XSSFSheet sheet = workbook.getSheetAt(0);
		int row = sheet.getLastRowNum()+1;
		//System.out.println(row);
		CellStyler styler = new CellStyler();
		CellStyle style = styler.createWarningColor(workbook);

		for(int i = 1; i<row; i++) {
			WebDriver driver = new ChromeDriver();
			driver.get("https://katalon-demo-cura.herokuapp.com/");
			driver.findElement(By.id("btn-make-appointment")).click();
			String testcaseid = sheet.getRow(i).getCell(0).toString();
			String username;
			String password;
			if(testcaseid.equals("tc104")) {
				username = "";
				password = "";
			}else {
				username = sheet.getRow(i).getCell(1).toString();			
				password = sheet.getRow(i).getCell(2).toString();		
			}
			driver.findElement(By.id("txt-username")).sendKeys(username);
			driver.findElement(By.id("txt-password")).sendKeys(password);
			driver.findElement(By.id("btn-login")).click();
			
			if(testcaseid.equals("tc101")) {
				//System.out.println("tc101");
				String actual = driver.findElement(By.xpath("/html/body/section/div/div/div/h2")).getText();
				String expected = sheet.getRow(i).getCell(3).toString();
				XSSFRow rows = sheet.getRow(i);
				XSSFCell cell = rows.createCell(4);
				cell.setCellValue(actual);
				assertEquals(expected,actual);
				if(actual.equals(expected)) {
					XSSFCell cell2 = rows.createCell(5);
					cell2.setCellStyle(style);
					cell2.setCellValue("Pass");
				}else {
					XSSFCell cell2 = rows.createCell(5);
					cell2.setCellValue("Fail");			
				}
				XSSFCell cell3 = rows.createCell(6);
				cell3.setCellValue(testDate);
				XSSFCell cell4 = rows.createCell(7);
				cell4.setCellValue(testerName);				
				FileOutputStream fos = new FileOutputStream(path);
				workbook.write(fos);
			} else {
				String actual = driver.findElement(By.xpath("/html/body/section/div/div/div[1]/p[2]")).getText();
				String expected = sheet.getRow(i).getCell(3).toString();
				XSSFRow rows = sheet.getRow(i);
				XSSFCell cell = rows.createCell(4);
				cell.setCellValue(actual);
				assertEquals(expected,actual);
				if(actual.equals(expected)) {
					XSSFCell cell2 = rows.createCell(5);
					cell2.setCellValue("Pass");
				}else {
					XSSFCell cell2 = rows.createCell(5);
					cell2.setCellValue("Fail");			
				}
				XSSFCell cell3 = rows.createCell(6);
				cell3.setCellValue(testDate);
				XSSFCell cell4 = rows.createCell(7);
				cell4.setCellValue(testerName);				
				FileOutputStream fos = new FileOutputStream(path);
				workbook.write(fos);				
			}
			driver.close();
		}

	}
}
