package crawler

class Bookmark {
    String url
    String title = ""
    String notes = ""
    String summary = ""
    Date added
    boolean crawled = false
    Date lastCrawl
    int crawlRetried = 0
    boolean summarized = false

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
        url nullable: false, blank: false, maxSize: 2048
        content nullable: true
        lastCrawl nullable: true
        title maxSize: 1024
        notes maxSize: 4096
    }
}
