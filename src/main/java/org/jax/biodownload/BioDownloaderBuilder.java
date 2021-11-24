package org.jax.biodownload;

import java.util.LinkedList;
import java.util.List;

public class BioDownloaderBuilder {
    private final List<DownloadableResource> resources;
    private String path;
    private boolean overwrite = false;


    public BioDownloaderBuilder() {
        this.resources = new LinkedList<>();
    }

    /**
     * Set path where the file(s) will be downloaded
     * @param path
     * @return
     */
    public BioDownloaderBuilder path(String path) {
        this.path = path;
        return this;
    }

    /**
     * By default set to False. If True file are overwritten if already existing.
     * @param overwrite
     * @return
     */
    public BioDownloaderBuilder overwrite(boolean overwrite) {
        this.overwrite = overwrite;
        return this;
    }

    /**
     * Download Go JSON
     * @return
     */
    public BioDownloaderBuilder goJson() {
        resources.add(DownloadableResource.GO_JSON);
        return this;
    }

    /**
     * Download Go OBO
     * @return
     */
    public BioDownloaderBuilder goObo() {
        resources.add(DownloadableResource.GO_OBO);
        return this;
    }

    public BioDownloaderBuilder go() {
        return goJson().goObo();
    }

    public BioDownloaderBuilder medgene2MIM() {
        resources.add(DownloadableResource.MEDGENE_2MIM);
        return this;
    }

    public BioDownloaderBuilder proSite() {
        resources.add(DownloadableResource.PROSITE);
        return this;
    }

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
     * @param name
     * @param url
     * @return
     */
    public BioDownloaderBuilder custom(String name, String url) {
        resources.add(new DownloadableResource(name, url));
        return this;
    }

    /**
     * Build Downloader
     * @return
     */
    public IBioDownloader build() {
        validate();
        return new BioDownloaderImpl(resources, path, overwrite);
    }

    /**
     * Validation of the Builder
     * @throws IllegalStateException
     */
    private void validate() throws IllegalStateException {
        StringBuilder sb = new StringBuilder();
        if (resources.size() == 0) {
            sb.append("A name and a URL need to be included. Please pick one of the available options or add your custom name and URL.");
        }
        if (path == null) {
            sb.append("Path must not be null.");
        }
        if (sb.length() > 0) {
            throw new IllegalStateException(sb.toString());
        }
    }


}
