import java.io.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.util.*;

/**
 * The PDF class represents a PDF.
 * 
 * This class will convert a PDF to a string. It will find the invoice number of
 * the file, then rename the file.
 * 
 * @author Vania Xu
 * @version 1.0 06/05/2013
 * 
 */
public class PDF {
  private File input; // The PDF
	private PDFTextStripper stripper; // creates PDFTextStripper object which
										// will help with extracting data from
										// PDF
	private String pdfText; // the string that the PDF data will extract to

	/**
	 * Constructs a PDF
	 * 
	 * @param pathname
	 *            the pathname of the PDF
	 */
	public PDF(File pathname) {
		this.input = pathname;
		try {
			this.stripper = new PDFTextStripper();
		} catch (IOException e) {
			System.out.println("NOPE");
			e.printStackTrace();
		}

		this.pdfText = null;

	}

	/**
	 * Extracts text from PDF and adds to String
	 */
	public void PDFtoString() throws IOException {
		if (input.isFile()){
			PDDocument pd = PDDocument.load(input); // Loads the PDF as a PDDocument
												// object
			try {
				pdfText = stripper.getText(pd); // Gets the text from the PDDocument
											// and sets it as a string
			} finally {
				pd.close(); // closes the PDF document (must do this)
			}
		}
	}

	/**
	 * Find the invoice number in the PDF, will become the new name for the file
	 * 
	 * @return newName the invoice Number that will be the new name
	 */
	public Long invoiceNumber() {
		String newName;
		String start = "90000"; //Input what the invoice number should begin with. 
		int index = pdfText.indexOf(start); // Finds the invoice number 
		newName = pdfText.substring(index, index + 10); // Sets the string of
		// newName as only the invoice number. May have to edit the length of the substring. 
	//	if (!newName.substring(0,6).equals("900007") || !newName.substring(0,6).equals("900006")){
	//		index = pdfText.indexOf(start, index + 10);
	//		newName = pdfText.substring(index, index + 10);
	//	}

		Long newNameInt = Long.parseLong(newName);
		return newNameInt;
	}

	/**
	 * Renames the PDF file so that it matches the invoice number
	 * 
	 * @param dest
	 *            the abstract file pathname
	 */
	public void renamePDF(String dest) {
		boolean success = input.renameTo(new File(dest)); // Renames/moves the
															// file to an
															// abstract file
															// pathname. Returns
															// boolean.
		if (!success)
			System.out.println("File was not created.");

	}

}
