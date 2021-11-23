package org.jax.obodownload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Command to download the {@code hp.obo} and {@code phenotype.hpoa} files that
 * we will need to run the LIRICAL approach.
 * @author Peter N Robinson
 * @author Baha El Kassaby
 */
class HpoDownloaderImpl implements IOboDownloader {
    private static final Logger logger = LoggerFactory.getLogger(HpoDownloaderImpl.class);
    /** Directory to which we will download the files. */
    private final String downloadDirectory;
    /** If true, download new version whether or not the file is already present. */
    private final boolean overwrite;

    /**
     * Enum of Names and Urls for HPO files
     */
    enum HpoName {
        HP_OBO("hp.obo", "https://raw.githubusercontent.com/obophenotype/human-phenotype-ontology/master/hp.obo"),
        HP_ANNOTATION("phenotype.hpoa", "http://purl.obolibrary.org/obo/hp/hpoa/phenotype.hpoa"),
        GENE_INFO("Homo_sapiens_gene_info.gz", "ftp://ftp.ncbi.nih.gov/gene/DATA/GENE_INFO/Mammalia/Homo_sapiens.gene_info.gz"),
        MIM2GENE_MEDGEN("mim2gene_medgen", "ftp://ftp.ncbi.nlm.nih.gov/gene/DATA/mim2gene_medgen");

        private final String name;
        private final String url;

        HpoName(String name, String url) {
            this.name = name;
            this.url = url;
        }
        public String getName() {
            return this.name;
        }
        public String getUrl() {
            return this.url;
        }
    }

    /**
     * Default constructor
     * @param path directory where file is downloaded
     */
    HpoDownloaderImpl(String path){
        this(path,false);
    }

    HpoDownloaderImpl(String path, boolean overwrite){
        this.downloadDirectory = path;
        this.overwrite = overwrite;
        logger.info("overwrite=" + overwrite);
    }

    /**
     * Download the files unless they are already present.
     */
    @Override
    public List<File> download() throws FileDownloadException {
        List<File> downloadedFiles = new LinkedList<>();
        int numberOfFiles = 0;
        for(HpoName hpo : HpoName.values()) {
            File file = downloadFileIfNeeded(hpo.name, hpo.url);
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
