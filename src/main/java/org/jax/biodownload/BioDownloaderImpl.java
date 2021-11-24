package org.jax.biodownload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
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
    private final String downloadDirectory;
    /** If true, download new version whether or not the file is already present. */
    private final boolean overwrite;

    BioDownloaderImpl(List<DownloadableResource> resources, String path, boolean overwrite) {
        this.resources = resources;
        this.downloadDirectory = path;
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
            File file = downloadFileIfNeeded(resource.getName(), resource.getUrl());
            downloadedFiles.add(file);
            numberOfFiles++;
            System.out.printf("[INFO] Downloaded \"%s\" file to \"%s\" (%d files were previously downloaded)\n",
                    file.getName(), downloadDirectory, numberOfFiles);
        }
        return downloadedFiles;
    }


    private File downloadFileIfNeeded(String filename, String webAddress) throws FileDownloadException {
        File f = new File(String.format("%s%s%s",downloadDirectory,File.separator,filename));
        if (f.exists() && (! overwrite)) {
            logger.trace(String.format("Cowardly refusing to download %s since we found it at %s",
                    filename,
                    f.getAbsolutePath()));
            return null;
        }
        FileDownloader downloader=new FileDownloader();
        try {
            URL url = new URL(webAddress);
            logger.debug("Created url from "+webAddress+": "+ url);
            return downloader.copyURLToFile(url, new File(f.getAbsolutePath()));
        } catch (MalformedURLException e) {
            logger.error(String.format("Malformed URL for %s [%s]",filename, webAddress));
            logger.error(e.getMessage());
        } catch (FileDownloadException e) {
            logger.error(String.format("Error downloading %s from %s" ,filename, webAddress));
            logger.error(e.getMessage());
            throw(e);
        }
        return null;
    }

}
