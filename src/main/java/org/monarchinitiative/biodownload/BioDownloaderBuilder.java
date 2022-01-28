package org.monarchinitiative.biodownload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;

/**
 * A builder for {@link BioDownloader}.
 *
 * @author Baha El Kassaby
 * @author Daniel Danis
 */
public class BioDownloaderBuilder {

    private static final Logger logger = LoggerFactory.getLogger(BioDownloaderBuilder.class);

    private final List<DownloadableResource> resources;
    private final Path destination;
    private boolean overwrite = false;


    private BioDownloaderBuilder(Path destination) {
        this.resources = new LinkedList<>();
        this.destination = Objects.requireNonNull(destination, "Destination path cannot be null");
    }

    static BioDownloaderBuilder builder(Path destination) {
        return new BioDownloaderBuilder(destination);
    }

    /**
     * Set the <code>overwrite</code> flag.
     *
     * @param overwrite By default, set to False. If True file are overwritten if already existing.
     * @return a builder instance
     */
    public BioDownloaderBuilder overwrite(boolean overwrite) {
        this.overwrite = overwrite;
        return this;
    }

    /**
     * Download Go JSON
     *
     * @return a builder instance
     */
    public BioDownloaderBuilder goJson() {
        resources.add(DownloadableResources.GO_JSON);
        return this;
    }

    /**
     * Download Go OBO
     *
     * @return a builder instance
     */
    public BioDownloaderBuilder goObo() {
        resources.add(DownloadableResources.GO_OBO);
        return this;
    }

    /**
     * Download MedGene2MIM file
     *
     * @return a builder instance
     */
    public BioDownloaderBuilder medgene2MIM() {
        resources.add(DownloadableResources.MIM2GENE_MEDGEN);
        return this;
    }

    /**
     * Download Prosite file
     *
     * @return a builder instance
     */
    public BioDownloaderBuilder proSite() {
        resources.add(DownloadableResources.PROSITE);
        return this;
    }

    public BioDownloaderBuilder geneOntologyAssociationsHumanCurrentV22() {
        resources.add(DownloadableResources.GOA_HUMAN_CURRENT_V22_GAFGZ);
        return this;
    }

    public BioDownloaderBuilder mondoJson() {
        resources.add(DownloadableResources.MONDO_JSON);
        return this;
    }

    public BioDownloaderBuilder mondoOwl() {
        resources.add(DownloadableResources.MONDO_OWL);
        return this;
    }

    public BioDownloaderBuilder ectoJson() {
        resources.add(DownloadableResources.ECTO_JSON);
        return this;
    }

    public BioDownloaderBuilder ectoOwl() {
        resources.add(DownloadableResources.ECTO_OWL);
        return this;
    }

    public BioDownloaderBuilder maxoJson() {
        resources.add(DownloadableResources.MAXO_JSON);
        return this;
    }

    public BioDownloaderBuilder maxoOwl() {
        resources.add(DownloadableResources.MAXO_OWL);
        return this;
    }

    public BioDownloaderBuilder maxoObo() {
        resources.add(DownloadableResources.MAXO_OBO);
        return this;
    }

    public BioDownloaderBuilder hpoJson() {
        resources.add(DownloadableResources.HP_JSON);
        return this;
    }

    public BioDownloaderBuilder hpoObo() {
        resources.add(DownloadableResources.HP_OBO);
        return this;
    }

    public BioDownloaderBuilder hpDiseaseAnnotations() {
        resources.add(DownloadableResources.PHENOTYPE_HP);
        return this;
    }

    /**
     * Download Hgnc file
     *
     * @return a builder instance
     */
    public BioDownloaderBuilder hgnc() {
        resources.add(DownloadableResources.HGNC);
        return this;
    }

    public BioDownloaderBuilder gencodeGrch38v38Basic() {
        resources.add(DownloadableResources.GENCODE_GRCH38_v38_BASIC);
        return this;
    }

    public BioDownloaderBuilder gencodeGrch38v38Comprehensive() {
        resources.add(DownloadableResources.GENCODE_GRCH38_v38_COMPREHENSIVE);
        return this;
    }

    public BioDownloaderBuilder gencodeGrch38v39Basic() {
        resources.add(DownloadableResources.GENCODE_GRCH38_v39_BASIC);
        return this;
    }

    public BioDownloaderBuilder gencodeGrch38v39Comprehensive() {
        resources.add(DownloadableResources.GENCODE_GRCH38_v39_COMPREHENSIVE);
        return this;
    }


    public BioDownloaderBuilder geneInfoHuman() {
        resources.add(DownloadableResources.GENE_INFO);
        return this;
    }

    /**
     * Download file from given URL and save it with the default name, as derived from URL
     *
     * @param url  URL of source file
     * @return a builder instance
     */
    public BioDownloaderBuilder custom(URL url) {
        return custom(DownloadableResources.createFileName(url), url);
    }

    /**
     * Download file from given URL and save it with the given name
     *
     * @param name Name of file to be downloaded to
     * @param url  URL of source file
     * @return a builder instance
     */
    public BioDownloaderBuilder custom(String name, URL url) {
        resources.add(new DownloadableResource(name, url));
        return this;
    }

    /**
     * Build Downloader
     *
     * @return a {@link BioDownloader} object
     */
    public BioDownloader build() {
        validate();
        return new BioDownloaderImpl(resources, destination, overwrite);
    }

    /**
     * Validation of the Builder
     *
     * @throws IllegalStateException If the validation fails we throw an {@link IllegalStateException}
     */
    private void validate() throws IllegalStateException {
        List<String> errors = new LinkedList<>();
        if (resources.size() == 0) {
            errors.add("A name and a URL need to be included. Please pick one of the available options or add your custom name and URL.");
        }
        File destinationDirectoryFile = destination.toFile();
        if (! destinationDirectoryFile.exists()) {
            logger.info("Creating new download directory at {}", destinationDirectoryFile.getAbsoluteFile());
            boolean result = destinationDirectoryFile.mkdirs();
            if (! result) {
                errors.add("Could not create new destination directory for downloads");
            }
        }
        if (!destination.toFile().isDirectory()) {
            errors.add("Path must be a directory.");
        }
        if (!destination.toFile().canWrite()) {
            errors.add("Directory must be writable.");
        }

        errors.addAll(checkNoDuplicatedResources(resources));

        if (!errors.isEmpty()) {
            String error = String.join("\n", errors);
            logger.error(error);
            throw new IllegalStateException(error);
        } else {
            logger.info("The builder validation is successful.");
        }
    }

    private static List<String> checkNoDuplicatedResources(List<DownloadableResource> resources) {
        Map<DownloadableResource, Integer> counter = new HashMap<>();
        for (DownloadableResource resource : resources) {
            int previous = counter.getOrDefault(resource, 0);
            counter.put(resource, previous + 1);
        }

        List<String> errors = new LinkedList<>();
        for (Map.Entry<DownloadableResource, Integer> entry : counter.entrySet()) {
            if (entry.getValue() > 1) {
                DownloadableResource resource = entry.getKey();
                errors.add(String.format("Duplicated resource: [%s, %s]", resource.getName(), resource.getUrl()));
            }
        }
        return errors;
    }
}
