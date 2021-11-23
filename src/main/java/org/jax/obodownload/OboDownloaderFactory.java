package org.jax.obodownload;

/**
 * Factory for creating instances of {@link IOboDownloader}
 */
public final class OboDownloaderFactory {

    private OboDownloaderFactory() {}

    /**
     * Creates an instance of {@link IOboDownloader} with the HpoFileDownloader implementation
     *
     * @param path path to download directory
     * @return the new instance
     */
    public static IOboDownloader createHpoFileDownloader(String path) {
        return new HpoDownloaderImpl(path);
    }

    /**
     * Creates an instance of {@link IOboDownloader} with the GoFileDownloader implementation
     *
     * @param path path to download directory
     * @return the new instance
     */
    public static IOboDownloader createGoFileDownloader(String path) {
        return new GoDownloaderImpl(path);
    }

}
