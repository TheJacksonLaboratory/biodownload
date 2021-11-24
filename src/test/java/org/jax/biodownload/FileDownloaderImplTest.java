package org.jax.biodownload;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileDownloaderImplTest {

    private IBioDownloader bioDownloader;

    @BeforeEach
    void init() {
        this.bioDownloader = new BioDownloaderBuilder().path("/save/directory").overwrite(true).all().build();
    }

    @Test
    public void testHpoDownload() {
        // TODO
    }


    @Test
    public void testGoDownload() {
        // TODO
    }
}
