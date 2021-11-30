package org.monarchinitiative.biodownload.ftp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.monarchinitiative.biodownload.FileDownloadException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NIOftpTest {

    @Test
    public void testFTPDownload() throws FileDownloadException, MalformedURLException {
        Path resourcePath = Paths.get("src","test","resources");
        String targetFilePath = Paths.get("src","test","resources").resolve("prosite.dat").toString();
        NIOftp.ftp(new URL("ftp://ftp.expasy.org/databases/prosite/prosite.dat"), targetFilePath);
        File ftpFileDownloaded = new File(targetFilePath);
        Assertions.assertTrue(ftpFileDownloaded.exists());
        // clean up file
        boolean deleted = resourcePath.resolve("prosite.dat").toFile().delete();
        Assertions.assertTrue(deleted);
    }
}
