package crawler

import groovy.io.FileType
import java.nio.file.FileSystems
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.QueryBuilder

class LoadBookmarksJob {
    static triggers = {
      simple repeatInterval: 50000l//1 * 60 * 60 * 1000l // execute job every hour
    }

    def bookmarksService

    def execute() {
        // execute job
        log.info("Starting to load bookmarks")

        def dataPath = grailsApplication.config.crawler.dataPath
        /*if(!dataPath) {
            dataPath = FileSystems.getDefault().getPath("../../../data").normalize().toAbsolutePath().toString()
        }
        log.debug("Loading html files from ${dataPath}")
        def dir = new File(dataPath)
        dir.eachFileRecurse (FileType.FILES) { file ->
            if(file.name.toLowerCase().endsWith(".html") || file.name.toLowerCase().endsWith(".htm")){
                log.debug("Loading bookmarks from ${file.name}")
                def html = file.text
                def groups = bookmarksService.readBookmarks(html)
                log.debug("Processing ${groups.size()} groups from ${file.name}")
                bookmarksService.storeBookmarks(groups)
                log.debug("Finished loading bookmarks from ${file.name}")
            }
        }*/
        log.info("Finished loading html files from ${dataPath}")
    }
}
