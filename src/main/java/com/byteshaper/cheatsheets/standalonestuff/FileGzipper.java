package com.byteshaper.cheatsheets.standalonestuff;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.io.IOUtils;

/**
 * Creates GZIP files.
 *
 */
public class FileGzipper {

    /**
     * Creates a gzip file containing the given file, reusing it's original filename with adding a "*.gz"
     * ending, in the same directory where the original file lives. The original file is not touched.
     *
     * @param inputFile
     * @return path to the gzip file just generated
     */
    public static Path gzip(Path inputFile) throws IOException {
        Path outputFile = Paths.get(inputFile.getParent().toString(), inputFile.getFileName() + ".gz");

        try(GZIPOutputStream gzos = new GZIPOutputStream(new FileOutputStream(outputFile.toFile()));
            FileInputStream fis = new FileInputStream(inputFile.toFile())) {

            IOUtils.copy(fis, gzos);
            return outputFile;

        }
    }
}
