package br.com.fiap.fiapcreditcard.util;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.fiap.fiapcreditcard.entity.Purchase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GeneralUtils {

	public static XSSFWorkbook extractGenerate(List<Purchase> purchases) throws IOException {

		String excelFileName = "extract.xlsx";

		String sheetName = "Extract";

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName);

		XSSFRow row = sheet.createRow(0);

		XSSFCell cell = row.createCell(0);
		cell.setCellValue("Description");
		cell = row.createCell(1);
		cell.setCellValue("Value");
		cell = row.createCell(2);
		cell.setCellValue("Date");

		int purchasesIndex = 1;
		for (Purchase purchase : purchases) {
			row = sheet.createRow(purchasesIndex);
			cell = row.createCell(0);
			cell.setCellValue(purchase.getDescription());
			cell = row.createCell(1);
			cell.setCellValue(purchase.getValue());
			cell = row.createCell(2);
			cell.setCellValue(formattedDate(purchase.getData()));

			purchasesIndex++;
		}

		FileOutputStream fileOut = new FileOutputStream(excelFileName);

		return wb;
	}

	public static void writeToFile(String fileName, String content) throws IOException {
		FileWriter writer = new FileWriter(fileName, false);
		writer.write(content);
		writer.close();
	}

	public static void deleteFile(String fileName) {
		File file = new File(fileName);
		file.delete();
	}

	private static String formattedDate(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}
}