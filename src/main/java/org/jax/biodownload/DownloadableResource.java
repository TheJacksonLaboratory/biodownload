package org.jax.biodownload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class used to store a downloadable resource and with all the resources objects that can be downloaded.
 */
public class DownloadableResource {

    private static final Logger logger = LoggerFactory.getLogger(DownloadableResource.class);

    private final String name;
    private final URL url;

    public static DownloadableResource GO_JSON = new DownloadableResource("go.json", createURL("http://purl.obolibrary.org/obo/go.json"));
    public static DownloadableResource GO_OBO = new DownloadableResource("go.obo", createURL("http://purl.obolibrary.org/obo/go.obo"));
    public static DownloadableResource MEDGENE_2MIM = new DownloadableResource("mim2gene_medgen", createURL("ftp://ftp.ncbi.nlm.nih.gov/gene/DATA/mim2gene_medgen"));
    public static DownloadableResource PROSITE = new DownloadableResource("prosite.dat", createURL("ftp://ftp.expasy.org/databases/prosite/prosite.dat"));
    public static DownloadableResource HGNC = new DownloadableResource("hgnc_complete_set.txt", createURL("ftp://ftp.ebi.ac.uk/pub/databases/genenames/hgnc/tsv/hgnc_complete_set.txt"));
    public static DownloadableResource GO_GAF = new DownloadableResource("goa_human.gaf", createURL("http://geneontology.org/gene-associations/goa_human.gaf"));
    public static DownloadableResource GO_GAFGZ = new DownloadableResource("goa_human.gaf.gz", createURL("http://geneontology.org/gene-associations/goa_human.gaf.gz"));
    public static DownloadableResource MONDO_JSON = new DownloadableResource("mondo.json", createURL(".http://purl.obolibrary.org/mondo/mondo.json"));
    public static DownloadableResource MONDO_OWL = new DownloadableResource("mondo.owl", createURL("http://purl.obolibrary.org/mondo/mondo.owl"));
    public static DownloadableResource ECTO_JSON = new DownloadableResource("ecto.json", createURL("https://raw.githubusercontent.com/EnvironmentOntology/environmental-exposure-ontology/master/ecto.json"));
    public static DownloadableResource ECTO_OWL = new DownloadableResource("ecto.owl", createURL("https://raw.githubusercontent.com/EnvironmentOntology/environmental-exposure-ontology/master/ecto.owl"));
    public static DownloadableResource MAXO_JSON = new DownloadableResource("maxo.json", createURL("https://raw.githubusercontent.com/monarch-initiative/MAxO/master/maxo.json"));
    public static DownloadableResource MAXO_OWL = new DownloadableResource("maxo.owl", createURL("https://raw.githubusercontent.com/monarch-initiative/MAxO/master/maxo.owl"));
    public static DownloadableResource MAXO_OBO = new DownloadableResource("maxo.obo", createURL("https://raw.githubusercontent.com/monarch-initiative/MAxO/master/maxo.obo"));
    public static DownloadableResource HP_JSON = new DownloadableResource("hp.json", createURL("https://raw.githubusercontent.com/obophenotype/human-phenotype-ontology/master/hp.json"));
    public static DownloadableResource HP_OBO = new DownloadableResource("hp.obo", createURL("https://raw.githubusercontent.com/obophenotype/human-phenotype-ontology/master/hp.obo"));
    public static DownloadableResource PHENOTYPE_HP = new DownloadableResource("phenotype.hpoa", createURL("http://purl.obolibrary.org/obo/hp/hpoa/phenotype.hpoa"));
    public static DownloadableResource GENE_INFO = new DownloadableResource("Homo_sapiens_gene_info.gz", createURL("ftp://ftp.ncbi.nih.gov/gene/DATA/GENE_INFO/Mammalia/Homo_sapiens.gene_info.gz"));

    /**
     * Constructor
     * @param name Name of file to download to
     * @param url {@link URL} of source file
     */
    public DownloadableResource(String name, URL url) {
        this.name = name;
        this.url = url;
    }

    /**
     * Create a URL and return null if {@link MalformedURLException} occurs
     * @param urlStr URL string
     * @return a {@link URL} or null if malformed
     */
    public static URL createURL(String urlStr) {
        try {
            return createURLExc(urlStr);
        } catch(MalformedURLException mue) {
            logger.error(String.format("Malformed URL for %s", urlStr));
        }
        return null;
    }

    /**
     * Create a URL and throw {@link MalformedURLException} if error occurs
     * @param urlStr URL string
     * @return a {@link URL}
     * @throws MalformedURLException exception
     */
    public static URL createURLExc(String urlStr) throws MalformedURLException{
        URL url = new URL(urlStr);
        logger.debug("Created url from " + urlStr + ": "+ url);
        return url;
    }

    public String getName() {
        return name;
    }

    public URL getUrl() {
        return url;
    }
}
