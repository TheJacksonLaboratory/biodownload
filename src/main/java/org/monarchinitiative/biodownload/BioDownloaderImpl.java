package org.monarchinitiative.biodownload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

/**
 * BioDownload implementation used to download from {@link DownloadableResource}
 * @author Peter N Robinson
 */
class BioDownloaderImpl implements IBioDownloader {
    private static final Logger logger = LoggerFactory.getLogger(BioDownloaderImpl.class);

    /** List of downloadable resources */
    private final List<DownloadableResource> resources;
    /** Directory to which we will download the files. */
    private final Path downloadDirectory;
    /** If true, download new version whether or not the file is already present. */
    private final boolean overwrite;

    /**
     * Constructor
     * @param resources List of resources
     * @param destinationDir destination directory {@link Path}
     * @param overwrite overwrite flag
     */
    BioDownloaderImpl(List<DownloadableResource> resources, Path destinationDir, boolean overwrite) {
        this.resources = resources;
        this.downloadDirectory = destinationDir;
        this.overwrite = overwrite;
    }

    /**
     * Download the files unless they are already present.
     */
    @Override
    public List<File> download() throws FileDownloadException {
        List<File> downloadedFiles = new LinkedList<>();
        int numberOfFiles = 0;

        for (DownloadableResource resource : resources) {
            File file = downloadFileIfNeeded(downloadDirectory.resolve(resource.getName()), resource.getUrl());
            downloadedFiles.add(file);
            numberOfFiles++;
            System.out.printf("[INFO] Downloaded \"%s\" file to \"%s\" (%d files were previously downloaded)\n",
                    file.getName(), downloadDirectory.toString(), numberOfFiles);
        }
        return downloadedFiles;
    }


    private File downloadFileIfNeeded(Path filePath, URL url) throws FileDownloadException {
        File f = filePath.toFile();
        if (f.isFile() && (! overwrite)) {
            logger.trace(String.format("Cowardly refusing to download %s since we found it at %s",
                    f.getName(),
                    f.getAbsolutePath()));
            return null;
        }
        FileDownloader downloader=new FileDownloader();
        try {
            return downloader.copyURLToFile(url, new File(f.getAbsolutePath()));
        } catch (FileDownloadException e) {
            logger.error(String.format("Error downloading %s from %s" ,f.getName(), url.toString()));
            logger.error(e.getMessage());
            throw(e);
        }
    }

}
