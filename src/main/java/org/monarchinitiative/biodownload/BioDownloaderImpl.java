package org.monarchinitiative.biodownload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * BioDownload implementation used to download from {@link DownloadableResource}.
 * @author Peter N Robinson
 */
class BioDownloaderImpl implements BioDownloader {
    private static final Logger logger = LoggerFactory.getLogger(BioDownloaderImpl.class);

    /** List of downloadable resources */
    private final List<DownloadableResource> resources;
    /** Directory to which we will download the files. */
    private final Path downloadDirectory;
    /** If true, download new version even if the file is already present. */
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
            Optional<File> optionalFile = downloadFileIfNeeded(downloadDirectory.resolve(resource.getName()), resource.getUrl());
            if (optionalFile.isPresent()){
                File file = optionalFile.get();
                downloadedFiles.add(file);
                logger.info("Downloaded \"{}\" file to \"{}\" ({} files were previously downloaded)",
                        file.getName(), downloadDirectory, numberOfFiles);
                numberOfFiles++;
            }
        }
        return downloadedFiles;
    }

    private Optional<File> downloadFileIfNeeded(Path destination, URL url) throws FileDownloadException {
        File f = destination.toFile();
        if (Files.isRegularFile(destination) && (!overwrite)) {
            logger.info("Cowardly refusing to download \"{}\" since we found it at \"{}\"", f.getName(), f.getAbsolutePath());
            return Optional.empty();
        }
        FileDownloader downloader = new FileDownloader();
        try {
            return Optional.ofNullable(downloader.copyURLToFile(url, f));
        } catch (FileDownloadException e) {
            logger.error("Error downloading \"{}\" from \"{}\": {}" , f.getName(), url.toString(), e.getMessage());
            throw e;
        }
    }

}
