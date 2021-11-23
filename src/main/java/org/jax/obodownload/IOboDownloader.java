package org.jax.obodownload;

import java.io.File;
import java.util.List;

/**
 * Interface used to consume the possible different implementation of the OboDownloader library
 */
public interface IOboDownloader {

    /**
     * Download 1 or more file(s) to given path in the Factory instantiator
     * @return list of files
     * @throws FileDownloadException
     */
    List<File> download() throws FileDownloadException;

}
