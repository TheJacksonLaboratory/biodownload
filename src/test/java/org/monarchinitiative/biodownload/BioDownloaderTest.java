package org.monarchinitiative.biodownload;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class BioDownloaderTest {

    private Path resourcePath;

    @BeforeEach
    public void init() {
        resourcePath = Paths.get("src","test", "resources");
    }

    @Test
    @Disabled("Requires internet access to run")
    public void testHpoOverwriteDownload() throws FileDownloadException {
        BioDownloader bioDownloader = BioDownloader.builder(resourcePath).overwrite(true).hpoJson().build();
        List<File> files = bioDownloader.download();
        assertEquals("hp.json", files.get(0).getName());
    }

    @Test
    public void testNullPointerExceptionFromCustom() {
        NullPointerException thrown = assertThrows(
                NullPointerException.class,
                () -> BioDownloader.builder(resourcePath).custom("test.json", null).build(),
                "Expected custom() to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Url must not be null"));
    }

    @Test
    public void testIllegalStateExceptionFromBuilder() {
        Path illegalResourcePath = Paths.get("src","test","resources", "mock.txt");
        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> BioDownloader.builder(illegalResourcePath).custom("test.json", new URL("http://url.com")).build(),
                "Expected build() to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Path must be a directory."));
    }

    @Test
    public void testExistingFileDownload() throws Exception {
        BioDownloader bioDownloader = BioDownloader.builder(resourcePath).overwrite(false).custom("hp.json", new URL("https://raw.githubusercontent.com/obophenotype/human-phenotype-ontology/master/hp.json")).build();
        List<File> downloadedFile = bioDownloader.download();
        assertTrue(downloadedFile.isEmpty());
    }

    @Test
    public void testNewNameFileDownload() throws Exception {
        URL hpoUrl = new URL("http://purl.obolibrary.org/obo/hp.json");
        BioDownloader bioDownloader = BioDownloader.builder(resourcePath)
                .overwrite(true)
                .custom("hp_new.json", hpoUrl)
                .build();
        List<File> downloadedFile = bioDownloader.download();
        assertEquals("hp_new.json", downloadedFile.get(0).getName());

        // clean up file
        boolean deleted = resourcePath.resolve("hp_new.json").toFile().delete();
        assertTrue(deleted);
    }

    @Test
    public void duplicateResourceIsDetected() {
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> BioDownloader.builder(resourcePath)
                .custom("abc.json", new URL("https://example.com/abc.json"))
                .custom("abc.json", new URL("https://example.com/abc.json"))
                .hpoJson()
                .geneOntologyAssociationsHumanCurrentV22()
                .build());

        assertEquals("Duplicated resource: [abc.json, https://example.com/abc.json]", e.getMessage());
    }

    @Test
    @Disabled("To run manually")
    public void downloadAll() throws Exception {
        Path target = Paths.get("target").resolve("download");
        BioDownloader downloader = BioDownloader.builder(target)
                .gencodeGrch38v38Basic()
                .gencodeGrch38v38Comprehensive()
                .gencodeGrch38v39Basic()
                .gencodeGrch38v39Comprehensive()
                .hgnc()
                .geneInfoHuman()
                .proSite()
                .medgene2MIM()
                .hpDiseaseAnnotations()
                .hpoJson()
                .hpoObo()
                .goJson()
                .goObo()
                .mondoJson()
                .mondoOwl()
                .maxoJson()
                .maxoOwl()
                .maxoObo()
                .ectoJson()
                .ectoOwl()
                .uberonJson()
                .genoJson()
                .geneOntologyAssociationsHumanCurrentV22()
                .build();
        downloader.download();
    }

    @Test
    @Disabled("Requires internet access to run")
    public void testPrositeFileDownload() throws Exception {
        BioDownloader bioDownloader = BioDownloaderBuilder.builder(resourcePath).proSite().build();
        List<File> downloadedFile = bioDownloader.download();
        assertEquals("prosite.dat", downloadedFile.get(0).getName());

        // clean up file
        boolean deleted = resourcePath.resolve("prosite.dat").toFile().delete();
        assertTrue(deleted);
    }
}
