![CI CD](https://github.com/TheJacksonLaboratory/biodownload/actions/workflows/cicd.yml/badge.svg)

# BioDownload
BioDownload is a small utility library used at the Robinson lab to download HPO, MONDO, UBERON, GO ontologies.   

## How to use
Once the library is added to a project, it can be used the following way:

First, create `BioDownloaderBuilder` and provide path to the folder where the files will be downloaded:

```java
Path destination = Paths.get("destination");
BioDownloaderBuilder builder = BioDownloader.builder(destination);
```

Then, add resources for download by calling methods of the `builder`. 
For instance, download HPO JSON file and HP disease annotations:

```java
builder.hpoJson()
  .hpDiseaseAnnotations();
```

Finally, get the `downloader` and download the files:

```java
BioDownloader downloader = builder.build();
List<File> files = downloader.download();
```

The files are downloaded into the `destination` folder and paths to the downloaded files are returned in a `List`.

