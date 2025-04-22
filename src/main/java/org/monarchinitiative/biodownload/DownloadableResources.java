package org.monarchinitiative.biodownload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Static utility class for loading URLs from <code>resource-url.properties</code>.
 *
 * @author Daniel Danis
 */
class DownloadableResources {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadableResources.class);

    private static final Properties RESOURCE_URLS;

    static {
        RESOURCE_URLS = readResourceUrls();
    }

    private static Properties readResourceUrls() {
        Properties properties = new Properties();
        try (InputStream is = DownloadableResources.class.getResourceAsStream("/resource-url.properties")) {
            properties.load(is);
        } catch (IOException e) {
            LOGGER.warn("Error while loading bundled resource URLs: {}", e.getMessage(), e);
        }
        return properties;
    }

    // Genes
    static final DownloadableResource GENCODE_GRCH38_v38_BASIC = createDownloadableResource("gencode.grch38.v38.basic");
    static final DownloadableResource GENCODE_GRCH38_v38_COMPREHENSIVE = createDownloadableResource("gencode.grch38.v38.comprehensive");
    static final DownloadableResource GENCODE_GRCH38_v39_BASIC = createDownloadableResource("gencode.grch38.v39.basic");
    static final DownloadableResource GENCODE_GRCH38_v39_COMPREHENSIVE = createDownloadableResource("gencode.grch38.v39.comprehensive");
    static final DownloadableResource GENE_INFO = createDownloadableResource("gene-info.human");
    static final DownloadableResource HGNC = createDownloadableResource("hgnc.complete-set");

    // Proteins
    static final DownloadableResource PROSITE = createDownloadableResource("prosite.dat");

    // Diseases
    static final DownloadableResource MIM2GENE_MEDGEN = createDownloadableResource("mim2gene.medgen");
    static final DownloadableResource PHENOTYPE_HP = createDownloadableResource("hp.disease.annotations");
    static final DownloadableResource ORPHA2GENE = createDownloadableResource("orpha-to-gene");

    // Ontologies
    static final DownloadableResource HP_JSON = createDownloadableResource("hp.json");
    static final DownloadableResource HP_OBO = createDownloadableResource("hp.obo");

    static final DownloadableResource GO_JSON = createDownloadableResource("go.json");
    static final DownloadableResource GO_OBO = createDownloadableResource("go.obo");

    static final DownloadableResource MONDO_JSON = createDownloadableResource("mondo.json");
    static final DownloadableResource MONDO_OWL = createDownloadableResource("mondo.owl");

    static final DownloadableResource MAXO_JSON = createDownloadableResource("maxo.json");
    static final DownloadableResource MAXO_OWL = createDownloadableResource("maxo.owl");
    static final DownloadableResource MAXO_OBO = createDownloadableResource("maxo.obo");

    static final DownloadableResource ECTO_JSON = createDownloadableResource("ecto.json");
    static final DownloadableResource ECTO_OWL = createDownloadableResource("ecto.owl");
    static final DownloadableResource UBERON_JSON = createDownloadableResource("uberon.json");
    static final DownloadableResource GENO_JSON = createDownloadableResource("geno.json");

    // Annotations/associations
    static final DownloadableResource GOA_HUMAN_CURRENT_V22_GAFGZ = createDownloadableResource("goa.human.current.v2.2.gaf.gz");


    private DownloadableResources() {
    }

    private static DownloadableResource createDownloadableResource(String key) {
        String urlString = RESOURCE_URLS.getProperty(key);
        try {
            URL url = new URL(urlString);
            String file = createFileName(url);
            return new DownloadableResource(file, url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            LOGGER.error("Malformed URL for {}", urlString);
            return null;
        }
    }

    static String createFileName(URL url) {
        return Path.of(url.getPath()).toFile().getName();
    }
}
