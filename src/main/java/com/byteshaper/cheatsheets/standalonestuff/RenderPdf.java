package com.byteshaper.cheatsheets.standalonestuff;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class RenderPdf {

    public static void main(String[] args) throws Exception {
        
        // create document in size DIN A4 / landscape orientation:
        Document document = new Document(PageSize.A4.rotate());
        
        // make sure PDF document is written to an actual file:
        Path filePath = Files.createTempFile("renderpdf", ".pdf");
        OutputStream fileOutputStream = new FileOutputStream(filePath.toFile());
        PdfWriter.getInstance(document, fileOutputStream);
        
        // open document for writing actual content: 
        document.open();
        
        // title (typically appearing as window title when opening PDF in a reader):
        document.addTitle("Some Document Title");
        
        // Defining fonts:
        Font helvetica16Bold = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font helvetica8Bold = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
        Font helvetica8Normal = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
        Font helvetica8NormalRed = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.RED);
        Font helvetica8NormalGreen = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.GREEN);
        Font helvetica8NormalCustomColor = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, new BaseColor(44, 12, 220));
        
        // Nesting paragraphs:
        Paragraph paragraph1 = new Paragraph();
        paragraph1.setSpacingAfter(20f);
        paragraph1.add(new Paragraph("This is a headline", helvetica16Bold));
        
        // inline text chunks in one row: 
        Paragraph labelandText = new Paragraph();
        labelandText.add(new Chunk("Label: ", helvetica8Bold));
        labelandText.add(new Chunk("Some text behind the label", helvetica8Normal));
        paragraph1.add(labelandText);
        
        // colored text:
        Paragraph colors = new Paragraph();
        colors.add(new Chunk("That's green. ", helvetica8NormalGreen));
        colors.add(new Chunk("And that's red. ", helvetica8NormalRed));
        colors.add(new Chunk("And that's a custom color. ", helvetica8NormalCustomColor));
        paragraph1.add(colors);
        
        // Background-Colors
        Paragraph backgroundColor = new Paragraph();
        Chunk hasBackgroundColor = new Chunk("This has a yellow background", helvetica8Normal);
        hasBackgroundColor.setBackground(BaseColor.YELLOW);
        backgroundColor.add(hasBackgroundColor);
        paragraph1.add(backgroundColor);
        
        // table with three columns and the first row regarded as header, so it will be repeated after page break:
        PdfPTable table = new PdfPTable(3);
        table.setHeaderRows(1);
        AtomicInteger index = new AtomicInteger(0);
        List<BaseColor> cellColors = Arrays.asList(
                BaseColor.BLUE, 
                BaseColor.GRAY,
                BaseColor.MAGENTA,
                BaseColor.CYAN,
                BaseColor.ORANGE,
                BaseColor.LIGHT_GRAY,
                BaseColor.PINK,
                BaseColor.YELLOW,
                BaseColor.RED
                );
        
        Stream.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine").forEachOrdered(s -> {
            PdfPCell cell = new PdfPCell(new Phrase(s, helvetica8Normal));
            cell.setBackgroundColor(cellColors.get(index.getAndIncrement()));
            table.addCell(cell);
        });
        
        // add stuff in the end - changing something after adding is not reflected in the PDF:
        document.add(paragraph1);
        document.add(table);
        
        // close document to trigger the actual writing to file:
        document.close();
        System.out.println("PDF written to: " + filePath);
    }
}
