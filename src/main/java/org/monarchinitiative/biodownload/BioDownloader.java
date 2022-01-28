package org.monarchinitiative.biodownload;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 * Interface used to consume the possible different implementation of the <code>biodownload</code> library.
 *
 * @author Baha El Kassaby
 * @author Daniel Danis
 */
public interface BioDownloader {

    static BioDownloaderBuilder builder(Path destinationPath) {
        return BioDownloaderBuilder.builder(destinationPath);
    }

    /**
     * Download 1 or more file(s) to given path set up in the Builder
     * @return list of files
     * @throws FileDownloadException exception
     */
    List<File> download() throws FileDownloadException;

}
