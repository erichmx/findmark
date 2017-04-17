@Grab("org.jsoup:jsoup:1.10.2")

import org.jsoup.nodes.Document
import org.jsoup.Jsoup;


def doc = Jsoup.parse(new File("../../../GoogleBookmarks.html"), null, "")

def top = doc.getElementsByTag("dl")[0]

def groupsTags = top.children().findAll {el -> el.tag().name ==  "dt"}

def groups = []

groupsTags.each { g ->
    def h3 = g.children().find {el -> el.tag().name ==  "h3"}
    def group = [:]
    group.name = h3.text()
    group.added = readJSDate(Long.parseLong(h3.attr("ADD_DATE")))


    def dts = g.children().find {el -> el.tag().name ==  "dl"}.children().findAll {it.tag().name == "dt"}
    group.bookmarks = dts.collect {dt ->
        def mark = [:]
        def a = dt.children().find { it.tag().name == "a"}
        mark.url = a.attr("href")
        mark.title = a.text()
        mark.added = readJSDate(Long.parseLong(a.attr("ADD_DATE")))
        if(dt.nextElementSibling()?.tag()?.name == 'dd')
            mark.notes = dt.nextElementSibling().text()

        mark
    }

    println(group)
}


Date readJSDate(long val)
{
    return new Date((long)(val/1000L))
}