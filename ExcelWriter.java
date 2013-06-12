import org.apache.poi.hssf.usermodel.*;
import java.util.*; 
import java.io.*;

/**
 * This class represents a tool that creates and write an excel spreadsheet.
 * 
 * There should be an invoice for every number in the sequences. Some invoices 
 * were accidentally missed. So, this programs creates an excel file that 
 * shows the missing invoices. 
 * 
 * @author vaniax
 *
 */
public class ExcelWriter {
  
	private ArrayList<Long> invoiceNumbers;
	private Long firstInvoice; 
	private Long lastInvoice;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private String pathname; 
	
	
	
/**
 * Constructs an excel worksheet, also finds the first invoice number, as well as
 * the last, so we know where the sequences begins and ends.  
 * 
 * @param invoiceNumbers sorted array list of all the invoice numbers found. 
 * @param pathname creates a file using this pathname 
 */
	public ExcelWriter(ArrayList<Long> invoiceNumbers, String pathname) {
		this.invoiceNumbers = invoiceNumbers;
		this.firstInvoice = invoiceNumbers.get(0);
		this.lastInvoice = invoiceNumbers.get(invoiceNumbers.size()-1);
		this.workbook = new HSSFWorkbook();
		this.sheet = workbook.createSheet("Invoice Numbers");
		this.pathname = pathname + "//InvoiceNumbers.xls"; 

	}
	
	/**
	 * This writes the left side of the columns which has every number from
	 * the first invoice number to the last. 
	 */
	public void writeColumns() {
		Long lastRow = lastInvoice - firstInvoice + 2; 
		Long currentInvoice = firstInvoice; 
		int rownum; 
		HSSFRow row = sheet.createRow(0);
		HSSFCell leftTitle = row.createCell(0);
		HSSFCell rightTitle = row.createCell(1); 
		leftTitle.setCellValue("Invoice Numbers");
		rightTitle.setCellValue("File Names");
		
		for (rownum = 1; rownum < lastRow; rownum++) { 
			row = sheet.createRow(rownum);
			HSSFCell cellLeft = row.createCell(0);
			cellLeft.setCellValue(currentInvoice);
			currentInvoice++;
		}


		
	}
	/**
	 * Goes through the left column, sees if the next entry in the array list 
	 * equals the current left column cell. If not, goes to the next row. If so, 
	 * moves on to the next row and next item in the array list. 
	 */
	public void writeRightColumn(){
		int index = 0; 
		for (int rownum = 1; rownum < lastInvoice - firstInvoice + 2; rownum++){
			HSSFRow row = sheet.getRow(rownum);
			HSSFCell cellRight = row.createCell(1); 
			Long fileName = invoiceNumbers.get(index); 
			Double invoiceNumber = row.getCell(0).getNumericCellValue(); 
			if ( (long) fileName == invoiceNumber) {
				cellRight.setCellValue(fileName);
				index++; 
			}
			
		}
	}
	/**
	 * Writes everything onto the excel spreadsheet. 
	 */
	public void writeExcel() {
		try {
			FileOutputStream out =
					new FileOutputStream(new File(pathname));
			workbook.write(out);
			out.close(); 
			System.out.println("Excel written successfully");
		}
		catch (FileNotFoundException e){
			e.printStackTrace(); 
		}
		catch (IOException e) {
			e.printStackTrace(); 
		}
	}
	
	
}
