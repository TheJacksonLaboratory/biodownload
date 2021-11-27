package org.monarchinitiative.biodownload;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class BioDownloaderTest {

    private Path resourcePath;

    @BeforeEach
    void init() {
        resourcePath = Paths.get("src","test","resources");
    }

    @Test
    public void testHpoOverwriteDownload() throws FileDownloadException {
        IBioDownloader bioDownloader = new BioDownloaderBuilder(resourcePath).overwrite(true).hpoJson().build();
        List<File> files = bioDownloader.download();
        Assertions.assertEquals("hp.json", files.get(0).getName());
    }

    @Test
    void testNullPointerExceptionFromCustom() {
        NullPointerException thrown = Assertions.assertThrows(
                NullPointerException.class,
                () -> new BioDownloaderBuilder(resourcePath).custom("test.json", null).build(),
                "Expected custom() to throw, but it didn't"
        );
        Assertions.assertTrue(thrown.getMessage().contains("Url must not be null"));
    }

    @Test
    void testIllegalStateExceptionFromBuilder() {
        Path illegalResourcePath = Paths.get("src","test","resources", "mock.txt");
        IllegalStateException thrown = Assertions.assertThrows(
                IllegalStateException.class,
                () -> new BioDownloaderBuilder(illegalResourcePath).custom("test.json", new URL("http://url.com")).build(),
                "Expected build() to throw, but it didn't"
        );
        Assertions.assertTrue(thrown.getMessage().contains("Path must be a directory.\n"));
    }

    @Test
    public void testExistingFileDownload() {
        try {
            IBioDownloader bioDownloader = new BioDownloaderBuilder(resourcePath).overwrite(false).custom("hp.json", new URL("https://raw.githubusercontent.com/obophenotype/human-phenotype-ontology/master/hp.json")).build();
            List<File> downloadedFile = bioDownloader.download();
            Assertions.assertTrue(downloadedFile.get(0) == null);
        } catch (MalformedURLException | FileDownloadException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNewNameFileDownload() {
        try {
            IBioDownloader bioDownloader = new BioDownloaderBuilder(resourcePath).overwrite(true).custom("hp_new.json", new URL("https://raw.githubusercontent.com/obophenotype/human-phenotype-ontology/master/hp.json")).build();
            List<File> downloadedFile = bioDownloader.download();
            Assertions.assertEquals("hp_new.json", downloadedFile.get(0).getName());
        } catch (MalformedURLException | FileDownloadException e) {
            e.printStackTrace();
        }
        // clean up file
        boolean deleted = resourcePath.resolve("hp_new.json").toFile().delete();
        Assertions.assertTrue(deleted);
    }
}
