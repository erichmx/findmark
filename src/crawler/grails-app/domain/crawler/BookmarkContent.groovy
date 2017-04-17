package crawler

class BookmarkContent {
    String contentType
    byte [] contentBytes

    static belongsTo = [bookmark: Bookmark]

    static searchable = {
        root false
    }

    static constraints = {
        contentType nullable: false, blank: false
    }

    static mapping = {
        contentBytes type: 'blob'
    }
}
