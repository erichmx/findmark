package crawler

class Bookmark {
    String url
    String title = ""
    String notes = ""
    Date added
    boolean crawled = false
    Date lastCrawl
    int crawlRetried = 0

    static searchable = {
        root true
        content component:true
        groups component:true
        title boost:10
        notes boost: 5
    }
    static hasOne = [content: BookmarkContent]
    static hasMany = [groups:Group]

    static constraints = {
        url nullable: false, blank: false

    }
}
