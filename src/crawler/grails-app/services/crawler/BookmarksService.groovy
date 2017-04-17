package crawler

import grails.transaction.Transactional
import org.jsoup.Jsoup

@Transactional
class BookmarksService {

    def readBookmarks(String html) {
        def doc = Jsoup.parse(html, "")

        def top = doc.getElementsByTag("dl")[0]

        def groupsTags = top.children().findAll { el -> el.tag().name == "dt" }

        def groups = []

        groupsTags.each { g ->
            def h3 = g.children().find { el -> el.tag().name == "h3" }
            def group = [:]
            group.title = h3.text()
            group.added = readJSDate(Long.parseLong(h3.attr("ADD_DATE")))


            def dts = g.children().find { el -> el.tag().name == "dl" }.children().findAll { it.tag().name == "dt" }
            group.bookmarks = dts.collect { dt ->
                def mark = [:]
                def a = dt.children().find { it.tag().name == "a" }
                mark.url = a.attr("href")
                mark.title = a.text()
                mark.added = readJSDate(Long.parseLong(a.attr("ADD_DATE")))
                if (dt.nextElementSibling()?.tag()?.name == 'dd')
                    mark.notes = dt.nextElementSibling().text()

                mark
            }
        }

        groups
    }

    def storeBookmarks(groups)
    {
        groups.each { g ->
            def group = Group.findOrCreateWhere(title: g.title)
            group.properties = g
            group.save(flush: true, failOnError: true)
            g.bookmarks.each { b->
                def mark = Bookmark.findOrCreateWhere(url:b.url)
                mark.properties = b
                if(!mark.groups?.find {it.title == g.title})
                    mark.addToGroups(group)
                mark.save(flush: true, failOnError: true)
            }
        }
    }


    private Date readJSDate(long val)
    {
        return new Date((long)(val/1000L))
    }}
