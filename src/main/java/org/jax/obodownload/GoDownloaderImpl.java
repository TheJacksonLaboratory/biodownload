package org.jax.obodownload;

import java.io.File;
import java.util.List;

/**
 * GoDownloader implementation of {@link IOboDownloader}
 */
class GoDownloaderImpl implements IOboDownloader {

    private final String downloadDirectory;
    private final boolean overwrite;

    GoDownloaderImpl(String path) {
        this(path, false);
    }
    
    GoDownloaderImpl(String path, boolean overwrite) {
        this.downloadDirectory = path;
        this.overwrite = overwrite;
    }

    @Override
    public List<File> download() throws FileDownloadException {
        throw(new FileDownloadException("Not Implemented"));
    }
}
