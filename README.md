![CI CD](https://github.com/TheJacksonLaboratory/biodownload/actions/workflows/cicd.yml/badge.svg)

# BioDownload
BioDownload is a small utility library used at the Robinson lab to download HPO, MONDO, UBERON, GO ontologies.   

## How to use
Once the library is added to a project, it can be used the following way.

The builder has a mandatory <code>path()</code> parameter to its constructor and one or more of the <code>DownloadableResource</code>.<br><br>
```
// Create the Downloader, here with the goJson resource
private IBioDownloader bioDownloader = new BioDownloaderBuilder("/save/directory").goJson().build();
// Download files
List<File> downloadedFiles = bioDownloader.download();
```

