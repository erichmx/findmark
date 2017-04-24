package crawler

import grails.transaction.NotTransactional
import grails.transaction.Transactional
import org.jsoup.Jsoup

@Transactional
class BookmarksService {
    def elasticSearchService

    def readBookmarks(String html) {
        def doc = Jsoup.parse(html, "")

        def top = doc.getElementsByTag("dl")[0]

        def groupsTags = top.children().findAll { el -> el.tag().name == "dt" }

        def groups = []

        log.debug("${groupsTags.size()} groupTags")

        groupsTags.each { g ->
            def h3 = g.children().find { el -> el.tag().name == "h3" }
            def group = [:]
            group.title = h3.text().replaceAll("\\P{Print}", "");
            group.added = readJSDate(Long.parseLong(h3.attr("ADD_DATE")))


            def dts = g.children().find { el -> el.tag().name == "dl" }.children().findAll { it.tag().name == "dt" }
            group.bookmarks = dts.collect { dt ->
                def mark = [:]
                def a = dt.children().find { it.tag().name == "a" }
                mark.url = a.attr("href")
                mark.title = a.text().replaceAll("\\P{Print}", "")
                mark.added = readJSDate(Long.parseLong(a.attr("ADD_DATE")))
                if (dt.nextElementSibling()?.tag()?.name == 'dd')
                    mark.notes = dt.nextElementSibling().text()

                mark
            }

            groups<<group
        }

        groups
    }

    @NotTransactional
    def storeBookmarks(groups)
    {
        groups.each { g ->
            try {
                def group = Group.findOrCreateWhere(title: g.title)
                group.properties = g
                group.save(flush: true, failOnError: true)
                g.bookmarks.each { b->
                    def mark = Bookmark.findOrCreateWhere(url:b.url)
                    try {
                        //log.debug("Before ${mark.properties.toMapString()}")
                        mark.properties = b
                        //log.debug("After ${mark.properties.toMapString()}")
                        if(!mark.groups?.find {it.title == g.title})
                            mark.addToGroups(group)
                        mark.save(flush: true, failOnError: true)

                    } catch (Throwable ex){
                        log.error(mark)
                        log.error(mark.properties.toMapString(), ex)
                    }
                }

            } catch (Throwable ex){
                log.error(ex.message, ex)
            }
        }
    }


    private Date readJSDate(long val)
    {
        return new Date((long)(val/1000L))
    }}
