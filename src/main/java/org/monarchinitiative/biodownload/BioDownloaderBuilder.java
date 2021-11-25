package org.monarchinitiative.biodownload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class BioDownloaderBuilder {

    private static final Logger logger = LoggerFactory.getLogger(BioDownloaderBuilder.class);

    private final List<DownloadableResource> resources;
    private final Path destinationPath;
    private boolean overwrite = false;


    public BioDownloaderBuilder(String destinationPath) {
        this.resources = new LinkedList<>();
        this.destinationPath = Paths.get(destinationPath);
    }

    /**
     * Set the overwrite flag
     * @param overwrite By default set to False. If True file are overwritten if already existing.
     * @return a builder instance
     */
    public BioDownloaderBuilder overwrite(boolean overwrite) {
        this.overwrite = overwrite;
        return this;
    }

    /**
     * Download Go JSON
     * @return a builder instance
     */
    public BioDownloaderBuilder goJson() {
        resources.add(DownloadableResource.GO_JSON);
        return this;
    }

    /**
     * Download Go OBO
     * @return a builder instance
     */
    public BioDownloaderBuilder goObo() {
        resources.add(DownloadableResource.GO_OBO);
        return this;
    }

    /**
     * Download all Go files
     * @return a builder instance
     */
    public BioDownloaderBuilder go() {
        return goJson().goObo();
    }

    /**
     * Download MedGene2MIM file
     * @return a builder instance
     */
    public BioDownloaderBuilder medgene2MIM() {
        resources.add(DownloadableResource.MEDGENE_2MIM);
        return this;
    }

    /**
     * Download Prosite file
     * @return a builder instance
     */
    public BioDownloaderBuilder proSite() {
        resources.add(DownloadableResource.PROSITE);
        return this;
    }

    /**
     * Download Hgnc file
     * @return a builder instance
     */
    public BioDownloaderBuilder hgnc() {
        resources.add(DownloadableResource.HGNC);
        return this;
    }

    public BioDownloaderBuilder goGaf() {
        resources.add(DownloadableResource.GO_GAF);
        return this;
    }

    // TODO unzip downloaded zipped files
    public BioDownloaderBuilder goGafGz() {
        resources.add(DownloadableResource.GO_GAFGZ);
        return this;
    }

    public BioDownloaderBuilder mondoJson() {
        resources.add(DownloadableResource.MONDO_JSON);
        return this;
    }

    public BioDownloaderBuilder mondoOwl() {
        resources.add(DownloadableResource.MONDO_OWL);
        return this;
    }

    public BioDownloaderBuilder mondo() {
        return mondoOwl().mondoJson();
    }

    public BioDownloaderBuilder ectoJson() {
        resources.add(DownloadableResource.ECTO_JSON);
        return this;
    }

    public BioDownloaderBuilder ectoOwl() {
        resources.add(DownloadableResource.ECTO_OWL);
        return this;
    }

    public BioDownloaderBuilder ecto() {
        return ectoOwl().ectoJson();
    }

    public BioDownloaderBuilder maxoJson() {
        resources.add(DownloadableResource.MAXO_JSON);
        return this;
    }

    public BioDownloaderBuilder maxoOwl() {
        resources.add(DownloadableResource.MAXO_OWL);
        return this;
    }

    public BioDownloaderBuilder maxoObo() {
        resources.add(DownloadableResource.MAXO_OBO);
        return this;
    }

    public BioDownloaderBuilder maxo() {
        return maxoOwl().maxoObo().maxoJson();
    }

    public BioDownloaderBuilder hpoJson() {
        resources.add(DownloadableResource.HP_JSON);
        return this;
    }

    public BioDownloaderBuilder hpoObo() {
        resources.add(DownloadableResource.HP_OBO);
        return this;
    }

    public BioDownloaderBuilder hpo() {
        return hpoObo().hpoJson();
    }

    public BioDownloaderBuilder hpAnnotation() {
        resources.add(DownloadableResource.PHENOTYPE_HP);
        return this;
    }

    public BioDownloaderBuilder geneInfo() {
        resources.add(DownloadableResource.GENE_INFO);
        return this;
    }

    public BioDownloaderBuilder all() {
        return go().goGaf().goGafGz().hpo().mondo().ecto().hgnc().maxo().medgene2MIM().proSite().hpAnnotation().geneInfo();
    }

    /**
     * Download file from given URL and save it with the given name
     * @param name Name of file to be downloaded to
     * @param urlStr URL string of source file
     * @return a builder instance
     */
    public BioDownloaderBuilder custom(String name, String urlStr) {
        URL url = DownloadableResource.createURL(urlStr);
        resources.add(new DownloadableResource(name, url));
        return this;
    }

    /**
     * Build Downloader
     * @return a {@link IBioDownloader} object
     */
    public IBioDownloader build() {
        validate();
        return new BioDownloaderImpl(resources, destinationPath, overwrite);
    }

    /**
     * Validation of the Builder
     * @throws IllegalStateException If the validation fails we throw an {@link IllegalStateException}
     */
    private void validate() throws IllegalStateException {
        StringBuilder sb = new StringBuilder();
        if (resources.size() == 0) {
            sb.append("A name and a URL need to be included. Please pick one of the available options or add your custom name and URL.");
        }
        for (DownloadableResource resource : resources) {
            if (resource.getUrl() == null)
                sb.append("URL for resource \"").append(resource.getName()).append("\" was malformed.");
        }
        if (destinationPath.toFile().isDirectory()) {
            sb.append("Path must be a directory");
        }
        if (destinationPath.toFile().canWrite()) {
            sb.append("Directory must be writable.");
        }
        if (sb.length() > 0) {
            logger.error(sb.toString());
            throw new IllegalStateException(sb.toString());
        } else {
            logger.info("The builder validation is successful.");
        }
    }


}
