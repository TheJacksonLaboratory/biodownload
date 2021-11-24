package org.jax.biodownload;

/**
 * Class used to store a downloadable resource and with all the resources objects that can be downloaded.
 */
public class DownloadableResource {

    private final String name;
    private final String url;

    public static DownloadableResource GO_JSON = new DownloadableResource("go.json", "http://purl.obolibrary.org/obo/go.json");
    public static DownloadableResource GO_OBO = new DownloadableResource("go.obo", "http://purl.obolibrary.org/obo/go.obo");
    public static DownloadableResource MEDGENE_2MIM = new DownloadableResource("mim2gene_medgen", "ftp://ftp.ncbi.nlm.nih.gov/gene/DATA/mim2gene_medgen");
    public static DownloadableResource PROSITE = new DownloadableResource("prosite.dat", "ftp://ftp.expasy.org/databases/prosite/prosite.dat");
    public static DownloadableResource HGNC = new DownloadableResource("hgnc_complete_set.txt", "ftp://ftp.ebi.ac.uk/pub/databases/genenames/hgnc/tsv/hgnc_complete_set.txt");
    public static DownloadableResource GO_GAF = new DownloadableResource("goa_human.gaf", "http://geneontology.org/gene-associations/goa_human.gaf");
    public static DownloadableResource GO_GAFGZ = new DownloadableResource("goa_human.gaf.gz", "http://geneontology.org/gene-associations/goa_human.gaf.gz");
    public static DownloadableResource MONDO_JSON = new DownloadableResource("mondo.json", ".http://purl.obolibrary.org/mondo/mondo.json");
    public static DownloadableResource MONDO_OWL = new DownloadableResource("mondo.owl", "http://purl.obolibrary.org/mondo/mondo.owl");
    public static DownloadableResource ECTO_JSON = new DownloadableResource("ecto.json", "https://raw.githubusercontent.com/EnvironmentOntology/environmental-exposure-ontology/master/ecto.json");
    public static DownloadableResource ECTO_OWL = new DownloadableResource("ecto.owl", "https://raw.githubusercontent.com/EnvironmentOntology/environmental-exposure-ontology/master/ecto.owl");
    public static DownloadableResource MAXO_JSON = new DownloadableResource("maxo.json", "https://raw.githubusercontent.com/monarch-initiative/MAxO/master/maxo.json");
    public static DownloadableResource MAXO_OWL = new DownloadableResource("maxo.owl", "https://raw.githubusercontent.com/monarch-initiative/MAxO/master/maxo.owl");
    public static DownloadableResource MAXO_OBO = new DownloadableResource("maxo.obo", "https://raw.githubusercontent.com/monarch-initiative/MAxO/master/maxo.obo");
    public static DownloadableResource HP_JSON = new DownloadableResource("hp.json", "https://raw.githubusercontent.com/obophenotype/human-phenotype-ontology/master/hp.json");
    public static DownloadableResource HP_OBO = new DownloadableResource("hp.obo", "https://raw.githubusercontent.com/obophenotype/human-phenotype-ontology/master/hp.obo");
    public static DownloadableResource PHENOTYPE_HP = new DownloadableResource("phenotype.hpoa", "http://purl.obolibrary.org/obo/hp/hpoa/phenotype.hpoa");
    public static DownloadableResource GENE_INFO = new DownloadableResource("Homo_sapiens_gene_info.gz", "ftp://ftp.ncbi.nih.gov/gene/DATA/GENE_INFO/Mammalia/Homo_sapiens.gene_info.gz");


    public DownloadableResource(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
