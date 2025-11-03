package app;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.Photo;
import model.PhotoSize;
import util.PhotoDownloader;
import util.PhotoProcessor;
import util.PhotoSerializer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhotoCrawler {

    private static final Logger log = Logger.getLogger(PhotoCrawler.class.getName());

    private final PhotoDownloader photoDownloader;

    private final PhotoSerializer photoSerializer;

    private final PhotoProcessor photoProcessor;

    public PhotoCrawler() throws IOException {
        this.photoDownloader = new PhotoDownloader();
        this.photoSerializer = new PhotoSerializer("./photos");
        this.photoProcessor = new PhotoProcessor();
    }

    public void resetLibrary() throws IOException {
        photoSerializer.deleteLibraryContents();
    }

    public void downloadPhotoExamples() {
        try {
            photoDownloader.getPhotoExamples()
                    .compose(observable -> processPhotos(observable))
                    .subscribe((photo) -> {
                photoSerializer.savePhoto(photo);
            });
        } catch (IOException e) {
            log.log(Level.SEVERE, "Downloading photo examples error", e);
        }
    }

    public void downloadPhotosForQuery(String query) throws IOException {
        photoDownloader.searchForPhotos(query)
                .compose(observable -> processPhotos(observable))
//                .take(3)
                .subscribe((photo) -> {
            photoSerializer.savePhoto(photo);
        }, e -> System.out.println("Dupa"));
    }

    public void downloadPhotosForMultipleQueries(List<String> queries) {
        photoDownloader.searchForPhotos(queries)
                .compose(this::processPhotos)
                .subscribe((photo) -> {
                    photoSerializer.savePhoto(photo);
                }, e -> System.out.println("Dupa"));
    }

    public Observable<Photo> processPhotos(Observable<Photo> photo) {
        return photo
            .groupBy(PhotoSize::resolve)
            .flatMap(group -> {
                if (group.getKey() == null) return Observable.empty();
                return switch (group.getKey()) {
                    case SMALL ->
                        Observable.empty();
//                        group;
                    case MEDIUM ->
                        group
                            .buffer(5, TimeUnit.SECONDS)
                            .filter(list -> !list.isEmpty())
                            .doOnNext(photoSerializer::savePhotos)
                            .ignoreElements()
                            .toObservable();
                    case LARGE ->
                        group
                            .observeOn(Schedulers.computation())
                            .map(photoProcessor::convertToMiniature);
                };
            });
    }
}
