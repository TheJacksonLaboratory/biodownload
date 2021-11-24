package org.jax.biodownload;

import java.io.File;
import java.util.List;

/**
 * Interface used to consume the possible different implementation of the OboDownloader library
 */
public interface IBioDownloader {

    /**
     * Download 1 or more file(s) to given path set up in the Builder
     * @return list of files
     * @throws FileDownloadException exception
     */
    List<File> download() throws FileDownloadException;

}
