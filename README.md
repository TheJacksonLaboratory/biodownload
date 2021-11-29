![CI CD](https://github.com/TheJacksonLaboratory/biodownload/actions/workflows/cicd.yml/badge.svg)

# OboDownload
OboDownload is a small utility library used at the Robinson lab to download HPO, MONDO, UBERON, GO ontologies.   

## How to use
Once the library is added to a project, it can be used the following way.

The builder has a few mandatory set-up methods like <code>path()</code> and one or more of the <code>DownloadableResource</code>.<br><br>
```
// Create the Downloader, here with the goJson resource
private IBioDownloader bioDownloader = new BioDownloaderBuilder().path("/save/directory").goJson().build();
// Download files
List<File> downloadedFiles = bioDownloader.download();
```
