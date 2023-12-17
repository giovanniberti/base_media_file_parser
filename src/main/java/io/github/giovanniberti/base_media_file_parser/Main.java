package io.github.giovanniberti.base_media_file_parser;

import io.github.giovanniberti.base_media_file_parser.extractor.MdatExtractor;
import io.github.giovanniberti.base_media_file_parser.parser.BoxNode;
import io.github.giovanniberti.base_media_file_parser.parser.Parser;
import io.github.giovanniberti.base_media_file_parser.printer.PrettyPrinter;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, DocumentException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("text0.mp4");

        try (Parser parser = new Parser(is)) {
            List<BoxNode> parsed = parser.parse();

            PrettyPrinter prettyPrinter = new PrettyPrinter();
            System.out.println(prettyPrinter.prettyPrint(parsed));

            Map<String, byte[]> images = MdatExtractor.extractMdatImages(parsed);

            OffsetDateTime now = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
            String formattedDateTime = DateTimeFormatter.ISO_DATE_TIME.format(now);

            File outputDirectory = new File("outdir_%s".formatted(formattedDateTime));

            if (!outputDirectory.mkdir()) {
                throw new RuntimeException("Unable to create output directory!");
            }

            for (Map.Entry<String, byte[]> entry : images.entrySet()) {
                File outputImage = new File(outputDirectory, entry.getKey());

                try (FileOutputStream outputStream = new FileOutputStream(outputImage)) {
                    outputStream.write(entry.getValue());
                }
            }
        }
    }
}
