package org.jax.biodownload;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


public class FileDownloaderImplTest {

    private IBioDownloader bioDownloader;

    @BeforeEach
    void init() {
        this.bioDownloader = new BioDownloaderBuilder("/save/directory").overwrite(true).all().build();
    }

    @Test
    @Disabled
    public void testHpoDownload() {
        // TODO
    }


    @Test
    @Disabled
    public void testGoDownload() {
        // TODO
    }
}
