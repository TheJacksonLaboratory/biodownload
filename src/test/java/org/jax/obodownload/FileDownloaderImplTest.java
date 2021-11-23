package org.jax.obodownload;

import org.junit.Before;
import org.junit.jupiter.api.Test;

public class FileDownloaderImplTest {

    private IOboDownloader hpoDownloader;
    private IOboDownloader goDownloader;

    @Before
    void init() {
        this.hpoDownloader = new HpoDownloaderImpl("");
        this.goDownloader = new GoDownloaderImpl("");
    }

    @Test
    void testHpoDownload() {
        // TODO
    }


    @Test
    void testGoDownload() {
        // TODO
    }
}
