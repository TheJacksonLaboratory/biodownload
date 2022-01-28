package org.monarchinitiative.biodownload;

import java.net.URL;
import java.util.Objects;

/**
 * Class for storing resource URL and the name under which the resource will be stored on the local file system
 *
 * @author Baha El Kassaby
 * @author Daniel Danis
 */
class DownloadableResource {

    private final String name;
    private final URL url;

    /**
     * @param name how to name the downloaded file
     * @param url  {@link URL} of source file
     */
    DownloadableResource(String name, URL url) {
        this.name = Objects.requireNonNull(name, "Name must not be null");
        this.url = Objects.requireNonNull(url, "Url must not be null");
    }

    String getName() {
        return name;
    }

    URL getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DownloadableResource that = (DownloadableResource) o;
        return Objects.equals(name, that.name) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }

    @Override
    public String toString() {
        return "DownloadableResource{" +
                "name='" + name + '\'' +
                ", url=" + url +
                '}';
    }
}
